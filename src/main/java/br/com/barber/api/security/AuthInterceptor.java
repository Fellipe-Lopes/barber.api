package br.com.barber.api.security;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.barber.api.model.PerfilAcesso;
import br.com.barber.api.model.SessaoModel;
import br.com.barber.api.repository.SessaoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final SessaoRepository sessaoRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        Autorizado autorizado = handlerMethod.getMethodAnnotation(Autorizado.class);
        if (autorizado == null) {
            autorizado = handlerMethod.getBeanType().getAnnotation(Autorizado.class);
        }

        if (autorizado == null) {
            return true;
        }

        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token de acesso nao informado.");
            return false;
        }

        String token = authorization.substring(7);
        SessaoModel sessao = this.sessaoRepository.findByToken(token).orElse(null);

        if (sessao == null || sessao.getExpiracaoEm().isBefore(LocalDateTime.now())) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sessao invalida ou expirada.");
            return false;
        }

        PerfilAcesso perfil = sessao.getPerfil();
        boolean permitido = Arrays.stream(autorizado.value()).anyMatch(role -> role == perfil);
        if (!permitido) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Perfil sem permissao para acessar este recurso.");
            return false;
        }

        request.setAttribute("sessaoAutenticada", sessao);
        return true;
    }
}

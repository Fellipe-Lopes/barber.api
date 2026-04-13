package br.com.barber.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.barber.api.dto.AgendamentoResponse;
import br.com.barber.api.model.PerfilAcesso;
import br.com.barber.api.model.SessaoModel;
import br.com.barber.api.security.Autorizado;
import br.com.barber.api.service.FuncionarioService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
@Autorizado({ PerfilAcesso.FUNCIONARIO })
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @GetMapping("/agenda")
    public ResponseEntity<List<AgendamentoResponse>> visualizarAgenda(HttpServletRequest servletRequest) {
        SessaoModel sessao = (SessaoModel) servletRequest.getAttribute("sessaoAutenticada");
        return ResponseEntity.ok(this.funcionarioService.visualizarAgenda(sessao.getUsuarioId()));
    }

    @GetMapping("/atendimentos")
    public ResponseEntity<List<AgendamentoResponse>> visualizarAtendimentos(HttpServletRequest servletRequest) {
        SessaoModel sessao = (SessaoModel) servletRequest.getAttribute("sessaoAutenticada");
        return ResponseEntity.ok(this.funcionarioService.visualizarAtendimentos(sessao.getUsuarioId()));
    }
}

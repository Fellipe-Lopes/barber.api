package br.com.barber.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.barber.api.dto.AgendamentoRequest;
import br.com.barber.api.dto.AgendamentoResponse;
import br.com.barber.api.dto.CadastroClienteRequest;
import br.com.barber.api.dto.ClienteResponse;
import br.com.barber.api.dto.NotificacaoResponse;
import br.com.barber.api.model.ClienteModel;
import br.com.barber.api.model.PerfilAcesso;
import br.com.barber.api.model.SessaoModel;
import br.com.barber.api.security.Autorizado;
import br.com.barber.api.service.ClienteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/cadastro")
    public ResponseEntity<ClienteResponse> cadastrarCliente(@RequestBody CadastroClienteRequest request) {
        ClienteModel cliente = this.clienteService.cadastrarCliente(request);
        return new ResponseEntity<>(ClienteResponse.fromModel(cliente), HttpStatus.CREATED);
    }

    @Autorizado({ PerfilAcesso.CLIENTE })
    @PostMapping("/agendamentos")
    public ResponseEntity<AgendamentoResponse> agendarServico(@RequestBody AgendamentoRequest request,
            HttpServletRequest servletRequest) {
        SessaoModel sessao = (SessaoModel) servletRequest.getAttribute("sessaoAutenticada");
        return new ResponseEntity<>(this.clienteService.agendarServico(sessao.getUsuarioId(), request), HttpStatus.CREATED);
    }

    @Autorizado({ PerfilAcesso.CLIENTE })
    @PutMapping("/agendamentos/{agendamentoId}/cancelar")
    public ResponseEntity<AgendamentoResponse> cancelarServico(@PathVariable Long agendamentoId,
            HttpServletRequest servletRequest) {
        SessaoModel sessao = (SessaoModel) servletRequest.getAttribute("sessaoAutenticada");
        return ResponseEntity.ok(this.clienteService.cancelarServico(sessao.getUsuarioId(), agendamentoId));
    }

    @Autorizado({ PerfilAcesso.CLIENTE })
    @GetMapping("/notificacoes")
    public ResponseEntity<List<NotificacaoResponse>> listarNotificacoes(HttpServletRequest servletRequest) {
        SessaoModel sessao = (SessaoModel) servletRequest.getAttribute("sessaoAutenticada");
        return ResponseEntity.ok(this.clienteService.listarNotificacoes(sessao.getUsuarioId()));
    }

    @Autorizado({ PerfilAcesso.CLIENTE })
    @GetMapping("/agendamentos")
    public ResponseEntity<List<AgendamentoResponse>> listarAgendamentos(HttpServletRequest servletRequest) {
        SessaoModel sessao = (SessaoModel) servletRequest.getAttribute("sessaoAutenticada");
        return ResponseEntity.ok(this.clienteService.listarAgendamentos(sessao.getUsuarioId()));
    }
}

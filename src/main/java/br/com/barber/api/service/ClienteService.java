package br.com.barber.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.barber.api.dto.AgendamentoRequest;
import br.com.barber.api.dto.AgendamentoResponse;
import br.com.barber.api.dto.CadastroClienteRequest;
import br.com.barber.api.dto.NotificacaoResponse;
import br.com.barber.api.model.AgendamentoModel;
import br.com.barber.api.model.ClienteModel;
import br.com.barber.api.model.FuncionarioModel;
import br.com.barber.api.model.NotificacaoModel;
import br.com.barber.api.model.StatusAgendamento;
import br.com.barber.api.repository.AgendamentoRepository;
import br.com.barber.api.repository.ClienteRepository;
import br.com.barber.api.repository.FuncionarioRepository;
import br.com.barber.api.repository.NotificacaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final NotificacaoRepository notificacaoRepository;

    public ClienteModel cadastrarCliente(CadastroClienteRequest request) {
        this.clienteRepository.findByEmail(request.getEmail()).ifPresent(cliente -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ja existe cliente com este email.");
        });

        ClienteModel cliente = new ClienteModel();
        cliente.setNome(request.getNome());
        cliente.setContato(request.getContato());
        cliente.setEmail(request.getEmail());
        cliente.setSenha(request.getSenha());
        cliente.setAtivo(true);

        return this.clienteRepository.save(cliente);
    }

    public AgendamentoResponse agendarServico(Long clienteId, AgendamentoRequest request) {
        ClienteModel cliente = buscarCliente(clienteId);
        FuncionarioModel funcionario = null;

        if (request.getFuncionarioId() != null) {
            funcionario = this.funcionarioRepository.findById(request.getFuncionarioId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionario nao encontrado."));
        }

        AgendamentoModel agendamento = new AgendamentoModel();
        agendamento.setCliente(cliente);
        agendamento.setFuncionario(funcionario);
        agendamento.setServico(request.getServico());
        agendamento.setDataHorario(request.getDataHorario());
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        AgendamentoModel agendamentoSalvo = this.agendamentoRepository.save(agendamento);
        criarNotificacaoCliente(cliente, "Servico agendado para " + agendamentoSalvo.getDataHorario() + ".");

        return AgendamentoResponse.fromModel(agendamentoSalvo);
    }

    public AgendamentoResponse cancelarServico(Long clienteId, Long agendamentoId) {
        AgendamentoModel agendamento = this.agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento nao encontrado."));

        if (!agendamento.getCliente().getCodigo().equals(clienteId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Agendamento nao pertence ao cliente logado.");
        }

        agendamento.setStatus(StatusAgendamento.CANCELADO);
        AgendamentoModel atualizado = this.agendamentoRepository.save(agendamento);
        criarNotificacaoCliente(atualizado.getCliente(), "Servico cancelado com sucesso.");

        return AgendamentoResponse.fromModel(atualizado);
    }

    public List<NotificacaoResponse> listarNotificacoes(Long clienteId) {
        buscarCliente(clienteId);
        return this.notificacaoRepository.findByClienteCodigoOrderByDataCriacaoDesc(clienteId).stream()
                .map(NotificacaoResponse::fromModel)
                .toList();
    }

    public List<AgendamentoResponse> listarAgendamentos(Long clienteId) {
        buscarCliente(clienteId);
        return this.agendamentoRepository.findByClienteCodigoOrderByDataHorarioDesc(clienteId).stream()
                .map(AgendamentoResponse::fromModel)
                .toList();
    }

    private ClienteModel buscarCliente(Long clienteId) {
        return this.clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado."));
    }

    private void criarNotificacaoCliente(ClienteModel cliente, String mensagem) {
        NotificacaoModel notificacao = new NotificacaoModel();
        notificacao.setCliente(cliente);
        notificacao.setMensagem(mensagem);
        notificacao.setDataCriacao(LocalDateTime.now());
        notificacao.setLida(false);
        this.notificacaoRepository.save(notificacao);
    }
}

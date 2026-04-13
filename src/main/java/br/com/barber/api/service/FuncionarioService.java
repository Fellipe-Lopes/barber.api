package br.com.barber.api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.barber.api.dto.AgendamentoResponse;
import br.com.barber.api.model.FuncionarioModel;
import br.com.barber.api.model.StatusAgendamento;
import br.com.barber.api.repository.AgendamentoRepository;
import br.com.barber.api.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final AgendamentoRepository agendamentoRepository;

    public List<AgendamentoResponse> visualizarAgenda(Long funcionarioId) {
        buscarFuncionario(funcionarioId);
        return this.agendamentoRepository.findByFuncionarioIdOrderByDataHorarioAsc(funcionarioId).stream()
                .map(AgendamentoResponse::fromModel)
                .toList();
    }

    public List<AgendamentoResponse> visualizarAtendimentos(Long funcionarioId) {
        buscarFuncionario(funcionarioId);
        return this.agendamentoRepository
                .findByFuncionarioIdAndStatusOrderByDataHorarioAsc(funcionarioId, StatusAgendamento.AGENDADO).stream()
                .map(AgendamentoResponse::fromModel)
                .toList();
    }

    private void buscarFuncionario(Long funcionarioId) {
        FuncionarioModel funcionario = this.funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionario nao encontrado."));

        if (Boolean.FALSE.equals(funcionario.getAtivo())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Funcionario inativo.");
        }
    }
}

package br.com.barber.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.barber.api.model.AgendamentoModel;
import br.com.barber.api.model.StatusAgendamento;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoModel, Long> {

    List<AgendamentoModel> findByClienteCodigoOrderByDataHorarioDesc(Long clienteId);

    List<AgendamentoModel> findByFuncionarioIdOrderByDataHorarioAsc(Long funcionarioId);

    List<AgendamentoModel> findByFuncionarioIdAndStatusOrderByDataHorarioAsc(Long funcionarioId, StatusAgendamento status);
}

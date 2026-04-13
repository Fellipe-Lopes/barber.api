package br.com.barber.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.barber.api.model.NotificacaoModel;

@Repository
public interface NotificacaoRepository extends JpaRepository<NotificacaoModel, Long> {

    List<NotificacaoModel> findByClienteCodigoOrderByDataCriacaoDesc(Long clienteId);
}

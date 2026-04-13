package br.com.barber.api.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.barber.api.model.SessaoModel;

@Repository
public interface SessaoRepository extends JpaRepository<SessaoModel, Long> {

    Optional<SessaoModel> findByToken(String token);

    void deleteByExpiracaoEmBefore(LocalDateTime dataHora);
}

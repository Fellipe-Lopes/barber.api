package br.com.barber.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.barber.api.model.AdministradorModel;

@Repository
public interface AdministradorRepository extends JpaRepository<AdministradorModel, Long> {

    Optional<AdministradorModel> findByEmail(String email);
}

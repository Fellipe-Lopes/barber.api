package br.com.barber.saas.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.barber.saas.model.cliente.ClienteModel;

@Repository
public interface ClienteRepositorio extends CrudRepository<ClienteModel, Long> {
    
}

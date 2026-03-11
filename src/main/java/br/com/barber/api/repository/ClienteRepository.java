package br.com.barber.api.repository;
    
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.barber.api.model.ClienteModel;


@Repository
public interface ClienteRepository  extends CrudRepository<ClienteModel, Long>{
    
}
package br.com.barber.api.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.barber.api.model.ClienteModel;
import br.com.barber.api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteRepository pr;
    
// rota para cadastrar cliente
    @PostMapping("/cadastrar")
    public ResponseEntity<ClienteModel> cadastrarCliente(@RequestBody ClienteModel cliente){
        return new ResponseEntity<>(this.pr.save(cliente), HttpStatus.CREATED);
    }
}

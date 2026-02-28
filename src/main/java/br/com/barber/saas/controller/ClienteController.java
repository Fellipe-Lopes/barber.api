package br.com.barber.saas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.barber.saas.model.cliente.ClienteModel;
import br.com.barber.saas.repositories.ClienteRepositorio;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteRepositorio cliente;

    @GetMapping("/clientes")
    public Iterable<ClienteModel> listarClientes(){
        return this.cliente.findAll();
    }
}

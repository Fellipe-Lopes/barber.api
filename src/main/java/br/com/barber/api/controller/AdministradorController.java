package br.com.barber.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.barber.api.dto.CadastroFuncionarioRequest;
import br.com.barber.api.dto.FuncionarioResponse;
import br.com.barber.api.model.PerfilAcesso;
import br.com.barber.api.security.Autorizado;
import br.com.barber.api.service.AdministradorService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/administradores")
@RequiredArgsConstructor
@Autorizado({ PerfilAcesso.ADMINISTRADOR })
public class AdministradorController {

    private final AdministradorService administradorService;

    @GetMapping("/funcionarios")
    public ResponseEntity<List<FuncionarioResponse>> listarFuncionarios() {
        return ResponseEntity.ok(this.administradorService.listarFuncionarios());
    }

    @PostMapping("/funcionarios")
    public ResponseEntity<FuncionarioResponse> cadastrarFuncionario(@RequestBody CadastroFuncionarioRequest request) {
        return new ResponseEntity<>(this.administradorService.cadastrarFuncionario(request), HttpStatus.CREATED);
    }

    @PutMapping("/funcionarios/{funcionarioId}")
    public ResponseEntity<FuncionarioResponse> atualizarFuncionario(@PathVariable Long funcionarioId,
            @RequestBody CadastroFuncionarioRequest request) {
        return ResponseEntity.ok(this.administradorService.atualizarFuncionario(funcionarioId, request));
    }

    @DeleteMapping("/funcionarios/{funcionarioId}")
    public ResponseEntity<Void> desativarFuncionario(@PathVariable Long funcionarioId) {
        this.administradorService.desativarFuncionario(funcionarioId);
        return ResponseEntity.noContent().build();
    }
}

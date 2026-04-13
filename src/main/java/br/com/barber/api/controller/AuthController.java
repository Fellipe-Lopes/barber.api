package br.com.barber.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.barber.api.dto.LoginRequest;
import br.com.barber.api.dto.LoginResponse;
import br.com.barber.api.dto.RecuperarSenhaRequest;
import br.com.barber.api.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/clientes/login")
    public ResponseEntity<LoginResponse> loginCliente(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(this.authService.loginCliente(request));
    }

    @PostMapping("/funcionarios/login")
    public ResponseEntity<LoginResponse> loginFuncionario(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(this.authService.loginFuncionario(request));
    }

    @PostMapping("/administradores/login")
    public ResponseEntity<LoginResponse> loginAdministrador(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(this.authService.loginAdministrador(request));
    }

    @PostMapping("/clientes/recuperar-senha")
    public ResponseEntity<String> recuperarSenhaCliente(@RequestBody RecuperarSenhaRequest request) {
        this.authService.recuperarSenhaCliente(request);
        return ResponseEntity.ok("Senha do cliente atualizada com sucesso.");
    }

    @PostMapping("/funcionarios/recuperar-senha")
    public ResponseEntity<String> recuperarSenhaFuncionario(@RequestBody RecuperarSenhaRequest request) {
        this.authService.recuperarSenhaFuncionario(request);
        return ResponseEntity.ok("Senha do funcionario atualizada com sucesso.");
    }

    @PostMapping("/administradores/recuperar-senha")
    public ResponseEntity<String> recuperarSenhaAdministrador(@RequestBody RecuperarSenhaRequest request) {
        this.authService.recuperarSenhaAdministrador(request);
        return ResponseEntity.ok("Senha do administrador atualizada com sucesso.");
    }
}

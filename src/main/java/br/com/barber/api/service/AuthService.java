package br.com.barber.api.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.barber.api.dto.LoginRequest;
import br.com.barber.api.dto.LoginResponse;
import br.com.barber.api.dto.RecuperarSenhaRequest;
import br.com.barber.api.model.AdministradorModel;
import br.com.barber.api.model.ClienteModel;
import br.com.barber.api.model.FuncionarioModel;
import br.com.barber.api.model.PerfilAcesso;
import br.com.barber.api.model.SessaoModel;
import br.com.barber.api.repository.AdministradorRepository;
import br.com.barber.api.repository.ClienteRepository;
import br.com.barber.api.repository.FuncionarioRepository;
import br.com.barber.api.repository.SessaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final AdministradorRepository administradorRepository;
    private final SessaoRepository sessaoRepository;

    public LoginResponse loginCliente(LoginRequest request) {
        ClienteModel cliente = this.clienteRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado."));
        validarSenha(request.getSenha(), cliente.getSenha());
        validarAtivo(cliente.getAtivo());
        return criarSessao(cliente.getCodigo(), cliente.getNome(), PerfilAcesso.CLIENTE);
    }

    public LoginResponse loginFuncionario(LoginRequest request) {
        FuncionarioModel funcionario = this.funcionarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionario nao encontrado."));
        validarSenha(request.getSenha(), funcionario.getSenha());
        validarAtivo(funcionario.getAtivo());
        return criarSessao(funcionario.getId(), funcionario.getNome(), PerfilAcesso.FUNCIONARIO);
    }

    public LoginResponse loginAdministrador(LoginRequest request) {
        AdministradorModel administrador = this.administradorRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Administrador nao encontrado."));
        validarSenha(request.getSenha(), administrador.getSenha());
        validarAtivo(administrador.getAtivo());
        return criarSessao(administrador.getId(), administrador.getNome(), PerfilAcesso.ADMINISTRADOR);
    }

    public void recuperarSenhaCliente(RecuperarSenhaRequest request) {
        ClienteModel cliente = this.clienteRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado."));
        cliente.setSenha(request.getNovaSenha());
        this.clienteRepository.save(cliente);
    }

    public void recuperarSenhaFuncionario(RecuperarSenhaRequest request) {
        FuncionarioModel funcionario = this.funcionarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionario nao encontrado."));
        funcionario.setSenha(request.getNovaSenha());
        this.funcionarioRepository.save(funcionario);
    }

    public void recuperarSenhaAdministrador(RecuperarSenhaRequest request) {
        AdministradorModel administrador = this.administradorRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Administrador nao encontrado."));
        administrador.setSenha(request.getNovaSenha());
        this.administradorRepository.save(administrador);
    }

    private LoginResponse criarSessao(Long usuarioId, String nome, PerfilAcesso perfil) {
        this.sessaoRepository.deleteByExpiracaoEmBefore(LocalDateTime.now());

        SessaoModel sessao = new SessaoModel();
        sessao.setPerfil(perfil);
        sessao.setUsuarioId(usuarioId);
        sessao.setToken(UUID.randomUUID().toString());
        sessao.setExpiracaoEm(LocalDateTime.now().plusHours(8));

        SessaoModel sessaoSalva = this.sessaoRepository.save(sessao);

        return LoginResponse.builder()
                .token(sessaoSalva.getToken())
                .perfil(sessaoSalva.getPerfil().name())
                .usuarioId(sessaoSalva.getUsuarioId())
                .nome(nome)
                .expiraEm(sessaoSalva.getExpiracaoEm().toString())
                .build();
    }

    private void validarSenha(String senhaInformada, String senhaBanco) {
        if (senhaInformada == null || !senhaInformada.equals(senhaBanco)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Senha invalida.");
        }
    }

    private void validarAtivo(Boolean ativo) {
        if (Boolean.FALSE.equals(ativo)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario inativo.");
        }
    }
}

package br.com.barber.api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.barber.api.dto.CadastroFuncionarioRequest;
import br.com.barber.api.dto.FuncionarioResponse;
import br.com.barber.api.model.FuncionarioModel;
import br.com.barber.api.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdministradorService {

    private final FuncionarioRepository funcionarioRepository;

    public List<FuncionarioResponse> listarFuncionarios() {
        return this.funcionarioRepository.findAll().stream()
                .map(FuncionarioResponse::fromModel)
                .toList();
    }

    public FuncionarioResponse cadastrarFuncionario(CadastroFuncionarioRequest request) {
        this.funcionarioRepository.findByEmail(request.getEmail()).ifPresent(funcionario -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ja existe funcionario com este email.");
        });

        FuncionarioModel funcionario = new FuncionarioModel();
        preencherFuncionario(funcionario, request);
        funcionario.setAtivo(true);

        return FuncionarioResponse.fromModel(this.funcionarioRepository.save(funcionario));
    }

    public FuncionarioResponse atualizarFuncionario(Long funcionarioId, CadastroFuncionarioRequest request) {
        FuncionarioModel funcionario = this.funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionario nao encontrado."));

        this.funcionarioRepository.findByEmail(request.getEmail()).ifPresent(outroFuncionario -> {
            if (!outroFuncionario.getId().equals(funcionarioId)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ja existe funcionario com este email.");
            }
        });

        preencherFuncionario(funcionario, request);
        return FuncionarioResponse.fromModel(this.funcionarioRepository.save(funcionario));
    }

    public void desativarFuncionario(Long funcionarioId) {
        FuncionarioModel funcionario = this.funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionario nao encontrado."));
        funcionario.setAtivo(false);
        this.funcionarioRepository.save(funcionario);
    }

    private void preencherFuncionario(FuncionarioModel funcionario, CadastroFuncionarioRequest request) {
        funcionario.setNome(request.getNome());
        funcionario.setContato(request.getContato());
        funcionario.setEmail(request.getEmail());
        funcionario.setSenha(request.getSenha());
        funcionario.setSalario(request.getSalario());
    }
}

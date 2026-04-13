package br.com.barber.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroFuncionarioRequest {
    private String nome;
    private String contato;
    private String email;
    private String senha;
    private double salario;
}

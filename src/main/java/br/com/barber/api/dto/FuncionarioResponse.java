package br.com.barber.api.dto;

import br.com.barber.api.model.FuncionarioModel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FuncionarioResponse {
    private Long id;
    private String nome;
    private String contato;
    private String email;
    private double salario;
    private Boolean ativo;

    public static FuncionarioResponse fromModel(FuncionarioModel model) {
        return FuncionarioResponse.builder()
                .id(model.getId())
                .nome(model.getNome())
                .contato(model.getContato())
                .email(model.getEmail())
                .salario(model.getSalario())
                .ativo(model.getAtivo())
                .build();
    }
}

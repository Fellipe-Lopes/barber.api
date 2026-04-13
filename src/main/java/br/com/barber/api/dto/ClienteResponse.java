package br.com.barber.api.dto;

import br.com.barber.api.model.ClienteModel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClienteResponse {
    private Long id;
    private String nome;
    private String contato;
    private String email;

    public static ClienteResponse fromModel(ClienteModel model) {
        return ClienteResponse.builder()
                .id(model.getCodigo())
                .nome(model.getNome())
                .contato(model.getContato())
                .email(model.getEmail())
                .build();
    }
}

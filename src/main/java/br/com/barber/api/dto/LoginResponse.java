package br.com.barber.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String token;
    private String perfil;
    private Long usuarioId;
    private String nome;
    private String expiraEm;
}

package br.com.barber.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecuperarSenhaRequest {
    private String email;
    private String novaSenha;
}

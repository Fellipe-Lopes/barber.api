package br.com.barber.api.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendamentoRequest {
    private String servico;
    private LocalDateTime dataHorario;
    private Long funcionarioId;
}

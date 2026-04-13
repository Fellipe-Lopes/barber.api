package br.com.barber.api.dto;

import java.time.LocalDateTime;

import br.com.barber.api.model.NotificacaoModel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificacaoResponse {
    private Long id;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private Boolean lida;

    public static NotificacaoResponse fromModel(NotificacaoModel model) {
        return NotificacaoResponse.builder()
                .id(model.getId())
                .mensagem(model.getMensagem())
                .dataCriacao(model.getDataCriacao())
                .lida(model.getLida())
                .build();
    }
}

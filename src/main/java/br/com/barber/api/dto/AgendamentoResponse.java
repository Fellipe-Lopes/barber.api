package br.com.barber.api.dto;

import java.time.LocalDateTime;

import br.com.barber.api.model.AgendamentoModel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AgendamentoResponse {
    private Long id;
    private String servico;
    private LocalDateTime dataHorario;
    private String status;
    private Long clienteId;
    private String clienteNome;
    private Long funcionarioId;
    private String funcionarioNome;

    public static AgendamentoResponse fromModel(AgendamentoModel model) {
        return AgendamentoResponse.builder()
                .id(model.getId())
                .servico(model.getServico())
                .dataHorario(model.getDataHorario())
                .status(model.getStatus().name())
                .clienteId(model.getCliente().getCodigo())
                .clienteNome(model.getCliente().getNome())
                .funcionarioId(model.getFuncionario() != null ? model.getFuncionario().getId() : null)
                .funcionarioNome(model.getFuncionario() != null ? model.getFuncionario().getNome() : null)
                .build();
    }
}

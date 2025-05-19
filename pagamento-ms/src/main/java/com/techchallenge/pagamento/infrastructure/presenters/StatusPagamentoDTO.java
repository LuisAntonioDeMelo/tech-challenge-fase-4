package com.techchallenge.pagamento.infrastructure.presenters;


import com.techchallenge.pagamento.domain.Pagamento;
import com.techchallenge.pagamento.domain.TipoPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusPagamentoDTO {

    private Long id;
    private BigDecimal valor;
    private String descricao;
    private TipoPagamento tipoPagamento;
    private String status;

    public static StatusPagamentoDTO dto(Pagamento pagamento) {
        return new StatusPagamentoDTO(
                pagamento.getId(),
                pagamento.getValor(),
                pagamento.getDescricao(),
                pagamento.getTipoPagamento(),
                pagamento.getStatusPagamento().name()
        );
    }
}

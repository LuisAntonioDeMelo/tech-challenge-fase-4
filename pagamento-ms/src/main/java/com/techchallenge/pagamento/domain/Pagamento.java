package com.techchallenge.pagamento.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Pagamento {

    private Long id;
    private BigDecimal valor;
    private String descricao;
    private String emv;
    private String base64;
    private LocalDateTime expiracao;
    private TipoPagamento tipoPagamento;
    private StatusPagamento statusPagamento;
}

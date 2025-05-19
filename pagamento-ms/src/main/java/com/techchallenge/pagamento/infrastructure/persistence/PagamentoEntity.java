package com.techchallenge.pagamento.infrastructure.persistence;


import com.techchallenge.pagamento.domain.StatusPagamento;
import com.techchallenge.pagamento.domain.TipoPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID codigoPedido;

    private BigDecimal valor;
    private String descricao;
    private String emv;
    private String base64;
    private LocalDateTime expiracao;
    private TipoPagamento tipoPagamento;
    private StatusPagamento statusPagamento;
}

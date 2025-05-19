package com.techchallenge.pedido.infrastructure.persistence;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID codigoPedido;
    private UUID clienteId;
    private UUID pagamentoId;

    @ElementCollection
    @CollectionTable(name = "pedido_produtos", joinColumns = @JoinColumn(name = "pedido_id"))
    @Column(name = "produto_id")
    private List<UUID> produtoIds;

    private LocalDateTime horarioInicio;
    private LocalDateTime horarioFinalizacao;

    @Enumerated(EnumType.STRING)
    private SituacaoPedidoEnum situacaoPedidoEnum;

    private BigDecimal valorTotalPedido;

}

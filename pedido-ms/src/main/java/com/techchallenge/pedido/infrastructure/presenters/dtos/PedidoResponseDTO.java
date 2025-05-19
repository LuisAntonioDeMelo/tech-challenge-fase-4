package com.techchallenge.pedido.infrastructure.presenters.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PedidoResponseDTO {

    private Long idPedido;

    private String codigoPedido;

    private ClienteDTO cliente;

    private List<ProdutoDTO> produtos;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime horarioInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime horarioFinalizacao;

    private String situacaoPedido;

    private BigDecimal valorTotal;

    //private PagamentoDTO pagamento;
}

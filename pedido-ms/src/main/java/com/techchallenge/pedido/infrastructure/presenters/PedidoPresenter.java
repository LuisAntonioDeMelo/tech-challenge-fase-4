package com.techchallenge.pedido.infrastructure.presenters;


import com.techchallenge.pedido.domain.Pedido;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class PedidoPresenter {

    public PedidoViewModel apresentar(Pedido pedido) {
        return new PedidoViewModel(pedido.getIdPedido(),
                pedido.getCodigoPedido(),
                pedido.getSituacaoPedido().name(),
                pedido.getHorarioInicio(),
                pedido.getHorarioFinalizacao(),
                pedido.getValorTotalPedido());
    }

    public List<PedidoViewModel> apresentar(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> new PedidoViewModel(
                        pedido.getIdPedido(),
                        pedido.getCodigoPedido(),
                        pedido.getSituacaoPedido().name(),
                        pedido.getHorarioInicio(),
                        pedido.getHorarioFinalizacao(),
                        pedido.getValorTotalPedido()))
                .toList();
    }


    @Data
    public static class PedidoViewModel {

        private Long idPedido;
        private String codigoPedido;
        private String name;
        private LocalDateTime horarioInicio;
        private LocalDateTime horarioFinalizacao;
        private BigDecimal valorTotalPedido;

        public PedidoViewModel(
                Long idPedido,
                String codigoPedido,
                String name,
                LocalDateTime horarioInicio,
                LocalDateTime horarioFinalizacao,
                BigDecimal valorTotalPedido
        ) {
            this.idPedido = idPedido;
            this.codigoPedido = codigoPedido;
            this.name = name;
            this.horarioInicio = horarioInicio;
            this.horarioFinalizacao = horarioFinalizacao;
            this.valorTotalPedido = valorTotalPedido;
        }
    }

}

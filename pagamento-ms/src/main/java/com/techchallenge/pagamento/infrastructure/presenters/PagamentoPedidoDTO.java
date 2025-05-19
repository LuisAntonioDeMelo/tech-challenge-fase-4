package com.techchallenge.pagamento.infrastructure.presenters;

import com.techchallenge.pagamento.domain.Pagamento;
import lombok.Data;

@Data
public class PagamentoPedidoDTO {
    PedidoDTO pedido;
    Pagamento pagamento;
    ClienteDTO cliente;
}

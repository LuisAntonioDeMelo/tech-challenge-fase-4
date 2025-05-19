package com.techchallenge.pagamento.application.interactors;


import com.techchallenge.pagamento.application.gateway.PagamentoGateway;
import com.techchallenge.pagamento.application.usecases.ConsultarPagamentoUseCase;
import com.techchallenge.pagamento.domain.Pagamento;
import com.techchallenge.pagamento.infrastructure.presenters.StatusPagamentoDTO;

public class ConsultarPagamentoInteractor implements ConsultarPagamentoUseCase {
    private final PagamentoGateway pagamentoGateway;

    public ConsultarPagamentoInteractor(PagamentoGateway pagamentoGateway) {
        this.pagamentoGateway = pagamentoGateway;
    }

    @Override
    public StatusPagamentoDTO consultarStatusPagamento(Long idPedido) {
        Pagamento pagamento = pagamentoGateway.consultarStatusPagamento(idPedido);
        if (pagamento == null) {
            throw new IllegalArgumentException("Pedido não possui pagamento");
        }
        return  StatusPagamentoDTO.dto(pagamento);
    }
}

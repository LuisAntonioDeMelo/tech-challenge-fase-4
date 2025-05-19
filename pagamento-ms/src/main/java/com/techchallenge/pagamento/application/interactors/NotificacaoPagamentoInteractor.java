package com.techchallenge.pagamento.application.interactors;


import com.techchallenge.pagamento.application.gateway.PagamentoGateway;
import com.techchallenge.pagamento.application.usecases.NotificacaoPagamentoUseCase;
import com.techchallenge.pagamento.domain.Pagamento;
import com.techchallenge.pagamento.infrastructure.presenters.NotificacaoPagamento;

public class NotificacaoPagamentoInteractor implements NotificacaoPagamentoUseCase {

    private final PagamentoGateway pagamentoGateway;

    public NotificacaoPagamentoInteractor(PagamentoGateway pagamentoGateway) {
        this.pagamentoGateway = pagamentoGateway;
    }

    @Override
    public Pagamento processarPagamentoAprovado(NotificacaoPagamento notificacao) {
        Pagamento pagamento = pagamentoGateway.consultarStatusPagamento(Long.getLong(notificacao.getPaymentId()));
        pagamento = pagamentoGateway.aprovarPagamento(pagamento.getId(), "APROVADO");
        return  pagamento;
    }

}

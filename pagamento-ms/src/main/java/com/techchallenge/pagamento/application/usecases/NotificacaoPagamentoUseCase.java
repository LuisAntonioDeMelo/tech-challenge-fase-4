package com.techchallenge.pagamento.application.usecases;


import com.techchallenge.pagamento.domain.Pagamento;
import com.techchallenge.pagamento.infrastructure.presenters.NotificacaoPagamento;

public interface NotificacaoPagamentoUseCase {
    Pagamento processarPagamentoAprovado(NotificacaoPagamento notificacao);
}

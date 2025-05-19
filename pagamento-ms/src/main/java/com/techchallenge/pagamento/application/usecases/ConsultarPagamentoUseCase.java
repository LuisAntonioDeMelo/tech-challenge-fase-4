package com.techchallenge.pagamento.application.usecases;


import com.techchallenge.pagamento.infrastructure.presenters.StatusPagamentoDTO;

public interface ConsultarPagamentoUseCase {
    StatusPagamentoDTO consultarStatusPagamento(Long idPedido);
}

package com.techchallenge.pedido.application.usecases.exceptions;

public class PedidoNaoEncontratoException extends RuntimeException {

    public PedidoNaoEncontratoException(String message) {
        super(message);
    }

}

package com.techchallenge.produto.application.usecases.exceptions;

public class ProdutoExistenteException extends RuntimeException {
    public ProdutoExistenteException(String message) {
        super(message);
    }
}

package com.techchallenge.produto.application.exceptions;

public class ProdutoInexistenteException extends RuntimeException {

    public ProdutoInexistenteException(String message) {
        super(message);
    }
}

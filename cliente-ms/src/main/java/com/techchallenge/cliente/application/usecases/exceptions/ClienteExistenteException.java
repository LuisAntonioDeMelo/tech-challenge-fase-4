package com.techchallenge.cliente.application.usecases.exceptions;

public class ClienteExistenteException extends RuntimeException {

    public ClienteExistenteException(String message) {
        super(message);
    }
}

package com.techchallenge.cliente.application.usecases.exceptions;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(String s) {
        super(s);
    }
}

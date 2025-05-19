package com.techchallenge.cliente.application.usecases.exceptions;

public class InvalidCpfException extends RuntimeException{

    public InvalidCpfException(String msg) {
        super(msg);
    }
}

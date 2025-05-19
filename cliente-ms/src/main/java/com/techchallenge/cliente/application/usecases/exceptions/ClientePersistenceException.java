package com.techchallenge.cliente.application.usecases.exceptions;

public class ClientePersistenceException extends RuntimeException{
    public ClientePersistenceException(String mensagem){
        super(mensagem);
    }
}

package com.techchallenge.cliente.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    private UUID id;
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
}
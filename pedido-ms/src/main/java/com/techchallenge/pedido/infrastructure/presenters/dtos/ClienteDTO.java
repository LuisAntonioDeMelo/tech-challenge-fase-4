package com.techchallenge.pedido.infrastructure.presenters.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private UUID id;
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private boolean ativo;
}

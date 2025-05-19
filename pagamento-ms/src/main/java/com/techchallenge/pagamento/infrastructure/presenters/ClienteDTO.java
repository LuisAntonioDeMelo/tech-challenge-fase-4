package com.techchallenge.pagamento.infrastructure.presenters;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long id;
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
}

package com.techchallenge.cliente.infrastructure.persistence.cliente;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "cliente")
public class ClienteEntity {

    @Id
    private UUID id;

    private String cpf;

    private String nome;

    private String email;

    private String telefone;

    public ClienteEntity() {
        this.id = UUID.randomUUID();
    }

}

package com.techchallenge.cliente.infrastructure.controllers.outputs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@Data
public class ClienteResponseDTO {

    @JsonProperty("codigo")
    private UUID codigoCliente;

    @JsonProperty("nome")
    private String nomeCliente;

    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("numero_telefone")
    private String numeroTelefone;

    @JsonProperty("email_contato")
    private String emailCliente;
}
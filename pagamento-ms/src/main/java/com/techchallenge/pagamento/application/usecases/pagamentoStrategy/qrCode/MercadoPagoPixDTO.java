package com.techchallenge.pagamento.application.usecases.pagamentoStrategy.qrCode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.techchallenge.pagamento.infrastructure.presenters.PagamentoPedidoDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MercadoPagoPixDTO {
    private String description;

    @JsonProperty("payment_method_id")
    private String paymentMethodId;

    @JsonProperty("transaction_amount")
    private BigDecimal amount;

    private String payerEmail;

    public MercadoPagoPixDTO toDto(PagamentoPedidoDTO pedido) {
        MercadoPagoPixDTO mercadoPagoPixDTO = new MercadoPagoPixDTO();
        mercadoPagoPixDTO.setDescription(pedido.getPagamento().getDescricao());
        mercadoPagoPixDTO.setPayerEmail(pedido.getCliente().getEmail());
        mercadoPagoPixDTO.setAmount(pedido.getPagamento().getValor());
        mercadoPagoPixDTO.setPaymentMethodId("pix");
        return mercadoPagoPixDTO;
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            return "Erro ao serializar para JSON: " + e.getMessage();
        }
    }
}

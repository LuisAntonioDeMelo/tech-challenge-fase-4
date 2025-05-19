package com.techchallenge.pagamento.infrastructure.presenters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacaoPagamento {

    private String paymentId;
    private String externalReference;
    private Map<String, Object> payload;
}

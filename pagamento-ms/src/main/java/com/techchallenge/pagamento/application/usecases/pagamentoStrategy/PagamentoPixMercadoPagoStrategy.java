package com.techchallenge.pagamento.application.usecases.pagamentoStrategy;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.resources.payment.Payment;

import com.techchallenge.pagamento.application.usecases.pagamentoStrategy.qrCode.MercadoPagoPixDTO;
import com.techchallenge.pagamento.infrastructure.presenters.PagamentoPedidoDTO;
import org.springframework.stereotype.Component;

@Component("PIX")
public class PagamentoPixMercadoPagoStrategy implements PagamentoStrategy {

    @Override
    public String processarPagamento(PagamentoPedidoDTO pagamentoPedidoDTO) {
        MercadoPagoPixDTO mercadoPagoPixDTO = new MercadoPagoPixDTO().toDto(null);
        String token = "APP_USR-1877326991340430-011322-919848db466f6190c81d772e5e864f16-140502149";

        return gerarQrCodePagamento(token, mercadoPagoPixDTO);
    }


    public String gerarQrCodePagamento(String token, MercadoPagoPixDTO mercadoPagoPixDTO) {
        try {
            MercadoPagoConfig.setAccessToken(token);
            PaymentCreateRequest paymentRequest = PaymentCreateRequest.builder()
                    .transactionAmount(mercadoPagoPixDTO.getAmount())
                    .description(mercadoPagoPixDTO.getDescription())
                    .payer(PaymentPayerRequest.builder()
                            .email(mercadoPagoPixDTO.getPayerEmail())
                            .build())
                    .paymentMethodId(mercadoPagoPixDTO.getPaymentMethodId())
                    .build();

            PaymentClient paymentClient = new PaymentClient();
            MPRequestOptions requestOptions = MPRequestOptions.builder()
                    .accessToken(token)
                    .socketTimeout(10000)
                    .build();
            Payment payment = paymentClient.create(paymentRequest, requestOptions);
            return payment.getPointOfInteraction().getTransactionData().getQrCode();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar QR Code de pagamento", e);
        }
    }

}

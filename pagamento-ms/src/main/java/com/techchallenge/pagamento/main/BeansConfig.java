package com.techchallenge.pagamento.main;


import com.techchallenge.pagamento.application.gateway.PagamentoGateway;
import com.techchallenge.pagamento.application.interactors.ConsultarPagamentoInteractor;
import com.techchallenge.pagamento.application.interactors.NotificacaoPagamentoInteractor;
import com.techchallenge.pagamento.application.interactors.ProcessarPagamentoInteractor;
import com.techchallenge.pagamento.application.usecases.ConsultarPagamentoUseCase;
import com.techchallenge.pagamento.application.usecases.NotificacaoPagamentoUseCase;
import com.techchallenge.pagamento.application.usecases.ProcessarPagamentoUseCase;
import com.techchallenge.pagamento.application.usecases.pagamentoStrategy.PagamentoStrategy;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class BeansConfig {

    @Bean
    public ProcessarPagamentoUseCase processarPagamentoUseCase(Map<String, PagamentoStrategy> strategies, PagamentoGateway pagamentoGateway) {
        return new ProcessarPagamentoInteractor(strategies, pagamentoGateway);
    }

    @Bean
    public ConsultarPagamentoUseCase consultarPagamentoUseCase(PagamentoGateway pagamentoGateway) {
        return new ConsultarPagamentoInteractor(pagamentoGateway);
    }

    @Bean
    public NotificacaoPagamentoUseCase notificacaoPagamentoUseCase(PagamentoGateway pagamentoGateway) {
        return new NotificacaoPagamentoInteractor(pagamentoGateway);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}

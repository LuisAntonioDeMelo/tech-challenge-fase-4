package com.techchallenge.cliente.main;


import com.techchallenge.cliente.application.gateway.ClienteGateway;
import com.techchallenge.cliente.application.usecases.ClienteUseCase;
import com.techchallenge.cliente.application.usecases.interactors.ClienteInteractor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {


    @Bean
    public ClienteUseCase clienteUseCase(ClienteGateway clienteGateway) {
        return new ClienteInteractor(clienteGateway);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}

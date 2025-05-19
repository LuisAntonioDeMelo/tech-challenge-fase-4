package com.techchallenge.produto.main;


import com.techchallenge.produto.application.gateway.ProdutoGateway;
import com.techchallenge.produto.application.interactors.ProdutoInteractor;
import com.techchallenge.produto.application.usecases.ProdutoUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeansConfig {

    @Bean
    public ProdutoUseCase produtoUseCase(ProdutoGateway produtoGateway) {
        return new ProdutoInteractor(produtoGateway);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}

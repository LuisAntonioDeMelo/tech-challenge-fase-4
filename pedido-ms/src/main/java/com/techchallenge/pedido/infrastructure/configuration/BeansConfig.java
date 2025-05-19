package com.techchallenge.pedido.infrastructure.configuration;



import com.techchallenge.pedido.application.usecases.*;
import com.techchallenge.pedido.domain.port.PedidoPort;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public CriarPedidoUseCase pedidoUseCase(PedidoPort pedidoPort) {
        return new CriarPedidoUseCase(pedidoPort);
    }

    @Bean
    public AlterarPedidoUseCase alterarPedidoUseCase(PedidoPort pedidoPort) {
        return new AlterarPedidoUseCase(pedidoPort);
    }

    @Bean
    public ListarPedidosUseCase listarPedidosUseCase(PedidoPort pedidoPort) {
        return new ListarPedidosUseCase(pedidoPort);
    }

    @Bean
    public CheckoutPedidoUseCase checkoutPedidoUseCase(PedidoPort pedidoPort) {
        return new CheckoutPedidoUseCase(pedidoPort);
    }

    @Bean
    public FilaPedidoUseCase filaPedidosUseCase(PedidoPort pedidoPort, NotificaFilaUseCase notificaFilaUseCase) {
        return new FilaPedidoUseCase(pedidoPort, notificaFilaUseCase);
    }

    @Bean
    NotificaFilaUseCase notificaFilaUseCase() {
        return new NotificaFilaUseCase();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}

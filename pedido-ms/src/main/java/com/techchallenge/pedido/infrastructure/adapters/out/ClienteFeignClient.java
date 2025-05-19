package com.techchallenge.pedido.infrastructure.adapters.out;

import com.techchallenge.pedido.infrastructure.presenters.dtos.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@FeignClient(name = "cliente-ms")
public interface ClienteFeignClient {

    @GetMapping("/api/cliente/{id}")
    Optional<ClienteDTO> obterCliente(@PathVariable String id);

    @PostMapping("/api/cliente")
    ClienteDTO notifyCliente();
}

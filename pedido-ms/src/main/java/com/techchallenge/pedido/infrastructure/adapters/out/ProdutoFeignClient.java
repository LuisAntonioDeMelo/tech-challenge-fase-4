package com.techchallenge.pedido.infrastructure.adapters.out;

import com.techchallenge.pedido.infrastructure.presenters.dtos.ProdutoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "produto-ms")
public interface ProdutoFeignClient {

     @GetMapping("api/produtos/")
     List<ProdutoDTO> getProdutoByListId(@RequestBody List<UUID> ids);
}

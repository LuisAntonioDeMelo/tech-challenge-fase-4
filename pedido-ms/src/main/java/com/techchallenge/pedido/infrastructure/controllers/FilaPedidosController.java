package com.techchallenge.pedido.infrastructure.controllers;


import com.techchallenge.pedido.application.usecases.FilaPedidoUseCase;
import com.techchallenge.pedido.infrastructure.presenters.dtos.PedidoFilaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/filaPedidos")
@RequiredArgsConstructor
@Tag(name = "Fila de Pedidos", description = "Operações relacionadas à fila de pedidos e pedidos prontos para retirada")
public class FilaPedidosController {

    private final FilaPedidoUseCase filaPedidosUseCase;

    @Operation(
            summary = "Obter fila de pedidos em preparação",
            description = "Retorna uma lista de pedidos que estão atualmente em preparação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de pedidos em preparação retornada com sucesso."),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
            }
    )
    @GetMapping("/fila")
    public ResponseEntity<List<PedidoFilaDTO>> obterFilaDePedidos() {
        return ResponseEntity.ok(filaPedidosUseCase.listarPedidosNaFila());
    }

    @Operation(
            summary = "Obter pedidos prontos para retirada",
            description = "Retorna uma lista de pedidos que estão prontos para retirada pelo cliente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de pedidos prontos retornada com sucesso."),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
            }
    )
    @GetMapping("/pedidos-prontos")
    public ResponseEntity<List<PedidoFilaDTO>> obterFilaDePedidosRetirada() {
        return ResponseEntity.ok(filaPedidosUseCase.listarPedidosProntos());
    }
}
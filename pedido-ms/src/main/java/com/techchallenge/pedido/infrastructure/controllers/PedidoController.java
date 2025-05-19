package com.techchallenge.pedido.infrastructure.controllers;


import com.techchallenge.pedido.application.usecases.AlterarPedidoUseCase;
import com.techchallenge.pedido.application.usecases.CheckoutPedidoUseCase;
import com.techchallenge.pedido.application.usecases.CriarPedidoUseCase;
import com.techchallenge.pedido.application.usecases.ListarPedidosUseCase;
import com.techchallenge.pedido.domain.Pedido;

import com.techchallenge.pedido.infrastructure.presenters.PedidoPresenter;
import com.techchallenge.pedido.infrastructure.presenters.PedidoPresenter.PedidoViewModel;
import com.techchallenge.pedido.infrastructure.presenters.dtos.PedidoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Operações relacionadas aos pedidos")
public class PedidoController {

    private final CheckoutPedidoUseCase checkoutPedidoUseCase;
    private final CriarPedidoUseCase criarPedidoUseCase;
    private final AlterarPedidoUseCase alterarPedidoUseCase;
    private final ListarPedidosUseCase listarPedidoUseCase;
    private final PedidoPresenter pedidoPresenter;

    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma lista de todos os pedidos cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso.")
    @GetMapping
    public ResponseEntity<List<PedidoViewModel>> obterPedidos() {
        List<Pedido> pedidos = listarPedidoUseCase.listarPedidos();
        List<PedidoViewModel> pedidoViewModels = pedidoPresenter.apresentar(pedidos);
        return ResponseEntity.ok(pedidoViewModels);
    }

    @Operation(summary = "Criar um novo pedido", description = "Cria um novo pedido com base nos dados fornecidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos.")
    })
    @PostMapping
    public ResponseEntity<PedidoViewModel> criarPedido(@RequestBody PedidoRequest pedidoRequest) {
        Pedido pedido = criarPedidoUseCase.criarPedido(pedidoRequest);
        PedidoViewModel pedidoViewModel = pedidoPresenter.apresentar(pedido);
        return ResponseEntity.ok(pedidoViewModel);
    }

    @Operation(summary = "Alterar a situação de um pedido", description = "Atualiza a situação de um pedido existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Situação do pedido atualizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado.")
    })
    @PutMapping("alterar-situacao/{id}")
    public ResponseEntity<PedidoViewModel> alterarSituacaoPedido(
            @PathVariable @Parameter(description = "ID do pedido") Long id,
            @RequestParam @Parameter(description = "Nova situação do pedido") String situacao) {
        Pedido pedido = alterarPedidoUseCase.alterarSituacaoPedido(id, situacao);
        PedidoViewModel pedidoViewModel = pedidoPresenter.apresentar(pedido);
        return ResponseEntity.ok(pedidoViewModel);
    }

    @Operation(summary = "Realizar checkout de um pedido", description = "Finaliza o pedido e realiza o processo de checkout.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Checkout realizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado.")
    })
    @GetMapping("/checkout/{id}")
    public ResponseEntity<PedidoViewModel> checkoutPedido(
            @PathVariable @Parameter(description = "ID do pedido a ser finalizado") Long id) {
        Pedido pedido = checkoutPedidoUseCase.checkoutPedido(id);
        PedidoViewModel pedidoViewModel = pedidoPresenter.apresentar(pedido);
        return ResponseEntity.ok(pedidoViewModel);
    }

    @Operation(summary = "Listar pedidos por situação", description = "Retorna uma lista de pedidos filtrados pela situação fornecida.")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso.")
    @GetMapping("/situacao-ordenados")
    public ResponseEntity<List<PedidoViewModel>> obterPedidosPorSituacao(
            @RequestParam(required = false)
            @Parameter(description = "Situação dos pedidos (opcional)") String situacao) {
        List<Pedido> pedidos = listarPedidoUseCase.listarPedidosPorSituacao(situacao);
        List<PedidoViewModel> pedidoViewModels = pedidoPresenter.apresentar(pedidos);
        return ResponseEntity.ok(pedidoViewModels);
    }
}

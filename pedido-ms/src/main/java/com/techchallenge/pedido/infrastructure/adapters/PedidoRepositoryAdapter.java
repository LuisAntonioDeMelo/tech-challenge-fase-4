package com.techchallenge.pedido.infrastructure.adapters;


import com.techchallenge.pedido.domain.port.PedidoPort;
import com.techchallenge.pedido.application.usecases.exceptions.PedidoNaoEncontratoException;
import com.techchallenge.pedido.domain.Pedido;
import com.techchallenge.pedido.infrastructure.adapters.out.ClienteFeignClient;
import com.techchallenge.pedido.infrastructure.adapters.out.ProdutoFeignClient;
import com.techchallenge.pedido.infrastructure.persistence.PedidoEntity;
import com.techchallenge.pedido.infrastructure.persistence.PedidoRepository;
import com.techchallenge.pedido.infrastructure.persistence.SituacaoPedidoEnum;
import com.techchallenge.pedido.infrastructure.presenters.dtos.ClienteDTO;
import com.techchallenge.pedido.infrastructure.presenters.dtos.ProdutoDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PedidoRepositoryAdapter implements PedidoPort {

    private final ModelMapper modelMapper;
    private final PedidoRepository pedidoRepository;
    private final ClienteFeignClient clienteFeignClient;
    private final ProdutoFeignClient produtoFeignClient;

    @Override
    public List<Pedido> listarPedidos() {
       return pedidoRepository.findAll().stream().map(p -> modelMapper.map(p, Pedido.class)).toList();
    }

    @Override
    public List<Pedido> listarPedidosPorSituacao(String situacao) {
//        if(Objects.isNull(situacao)){
//            List<PedidoEntity> pedidos = pedidoRepository.findAll().stream()
//                    .filter(Objects::nonNull)
//                    .filter(p -> p.getSituacaoPedidoEnum().compareTo(SituacaoPedidoEnum.FINALIZADO) < 0)
//                    .sorted(Comparator
//                            .comparing((PedidoEntity p) -> {
//                                if (p.getSituacaoPedidoEnum() == SituacaoPedidoEnum.PRONTO) return 1;
//                                else if (p.getSituacaoPedidoEnum() == SituacaoPedidoEnum.EM_PREPARACAO) return 2;
//                                else return 3;
//                            })
//                            .thenComparing(PedidoEntity::getHorarioInicio))
//                    .toList();
//            return pedidos.stream().map(p -> modelMapper.map(p, Pedido.class)).toList();
//        }
        Integer situacaoPedido = situacao != null ? SituacaoPedidoEnum.obter(situacao).getCodigo() : null;
        List<String> situacoesNotIn = List.of("CRIADO", "INCIAR_PREPARACAO", "MONTAGEM", "FINALIZADO");
        List<PedidoEntity> pedidos = pedidoRepository.listarPedidosPorSituacao(situacaoPedido, situacoesNotIn);
        return pedidos.stream().map(p -> modelMapper.map(p, Pedido.class)).toList();
    }

    @Override
    public Pedido criarPedido(Pedido pedido) {
        PedidoEntity entity = new PedidoEntity();
        Optional<ClienteDTO> clienteDTOOptional = clienteFeignClient.obterCliente(pedido.getCliente().toString());
        if (clienteDTOOptional.isEmpty()) {
            throw new PedidoNaoEncontratoException("Cliente não encontrado: " + pedido.getCliente());
        }
        ClienteDTO clienteDTO = clienteDTOOptional.get();
        entity.setClienteId(clienteDTO.getId());

        List<ProdutoDTO> produtoStok = produtoFeignClient
                .getProdutoByListId(pedido.getProdutos()
                        .stream()
                        .map(ProdutoDTO::getId)
                        .toList());


        //ntity.setProdutos(produtoStok.stream().map(ProdutoDTO::getId).toList());
        entity.setHorarioInicio(LocalDateTime.now());
        entity.setSituacaoPedidoEnum(SituacaoPedidoEnum.CRIADO);

        BigDecimal valorTotalPedido = pedido.getProdutos().stream()
                .map(produto -> produtoStok.stream()
                        .filter(produtoEntity -> produtoEntity.getId().equals(produto.getId()))
                        .findFirst()
                        .map(produtoEntity -> produtoEntity.getValor().multiply(BigDecimal.valueOf(produto.getQuantidade())))
                        .orElse(BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        entity.setValorTotalPedido(valorTotalPedido);
        PedidoEntity pedidoEntity = pedidoRepository.save(entity);

        clienteFeignClient.notifyCliente();
        return modelMapper.map(pedidoEntity, Pedido.class);
    }

    @Override
    public Pedido alterarSituacaoPedido(Long idPedido, String situacaoPedido) {
        Optional<PedidoEntity> pedidoOpt = pedidoRepository.findById(idPedido);
        if (pedidoOpt.isPresent()) {
            PedidoEntity entity = pedidoOpt.get();

            SituacaoPedidoEnum situacaoPedidoEnum = SituacaoPedidoEnum.obter(situacaoPedido);
            entity.setSituacaoPedidoEnum(situacaoPedidoEnum);
            entity = pedidoRepository.save(entity);
            return modelMapper.map(entity, Pedido.class);
        }
        throw new PedidoNaoEncontratoException("Pedido não encontrado: " + idPedido);
    }

    @Override
    public Pedido buscarPedidoPorClienteId(String idPedido) {
        PedidoEntity entity = pedidoRepository.oterPedidoPorClienteId(idPedido);
        return entity != null ? modelMapper.map(entity, Pedido.class) : null;
    }


    @Override
    public Pedido checkoutPedido(Long idPedido) {
        Optional<PedidoEntity> pedidoOpt = pedidoRepository.findById(idPedido);
        if (pedidoOpt.isEmpty()) {
            throw new PedidoNaoEncontratoException("Pedido não encontrado");
        }
        PedidoEntity entity = pedidoOpt.get();
        return modelMapper.map(entity, Pedido.class);
    }

    @Override
    public Pedido alterarPedido(Pedido pedido) {
        PedidoEntity entity = modelMapper.map(pedido, PedidoEntity.class);
        entity = pedidoRepository.save(entity);
        return modelMapper.map(entity, Pedido.class);
    }

}

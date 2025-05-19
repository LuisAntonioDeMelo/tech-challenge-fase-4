package com.techchallenge.pagamento.infrastructure.gateways;


import com.techchallenge.pagamento.application.gateway.PagamentoGateway;
import com.techchallenge.pagamento.domain.Pagamento;
import com.techchallenge.pagamento.domain.StatusPagamento;
import com.techchallenge.pagamento.infrastructure.persistence.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PagamentoRepositoryGateway implements PagamentoGateway {

    private final PagamentoRepository pagamentoRepository;
    private final ModelMapper modelMapper;

    @Override
    public Pagamento consultarStatusPagamento(Long idPedido) {
        var pagamentoEntity = pagamentoRepository.obterPagamentoPorIdPedido(idPedido);
        return modelMapper.map(pagamentoEntity, Pagamento.class);
    }

    @Override
    public Pagamento aprovarPagamento(Long id, String aprovado) {
        var pagamentoEntity = pagamentoRepository.obterPagamentoPorIdPedido(id);
        pagamentoEntity.setStatusPagamento(StatusPagamento.PAGO);
        pagamentoRepository.save(pagamentoEntity);
        return modelMapper.map(pagamentoRepository.save(pagamentoEntity), Pagamento.class);
    }

    @Override
    public Pagamento criarPagamentoPedido(Long id, String tipoPagamento) {
        return null;
    }
}

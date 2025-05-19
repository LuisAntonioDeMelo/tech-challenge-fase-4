package com.techchallenge.pagamento.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PagamentoRepository extends JpaRepository<PagamentoEntity, Long> {

    @Query("""
            SELECT p From PagamentoEntity p 
            WHERE p.id = :idPagamento
            """)
    PagamentoEntity obterPagamentoPorIdPedido(Long idPagamento);
}

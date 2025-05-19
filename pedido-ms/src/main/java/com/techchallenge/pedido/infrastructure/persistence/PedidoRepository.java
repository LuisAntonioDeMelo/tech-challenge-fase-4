package com.techchallenge.pedido.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {
    @Query("""
            SELECT p From PedidoEntity p 
            WHERE p.clienteId = :id
            """)
    PedidoEntity oterPedidoPorClienteId(@Param("id") String id);


    @Query("""
    select p from PedidoEntity p
    where (:situacaoPedidoEnum is null or p.situacaoPedidoEnum = :situacaoPedidoEnum)
    and p.situacaoPedidoEnum not in ( :situacoesNotIn )
    order by 
        case 
            when p.situacaoPedidoEnum = 'PRONTO' then 1
            when p.situacaoPedidoEnum = 'EM_PREPARACAO' then 2
            when p.situacaoPedidoEnum = 'RECEBIDO' then 3
        end,
        p.horarioInicio asc
""")
    List<PedidoEntity> listarPedidosPorSituacao(
            @Param("situacaoPedidoEnum") Integer situacaoPedidoEnum,
            @Param("situacoesNotIn") List<String> situacoesNotIn);
}


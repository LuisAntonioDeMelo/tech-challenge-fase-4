package com.techchallenge.produto.infrastructure.persistence;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProdutoEntity is a Querydsl query type for ProdutoEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProdutoEntity extends EntityPathBase<ProdutoEntity> {

    private static final long serialVersionUID = -1190305499L;

    public static final QProdutoEntity produtoEntity = new QProdutoEntity("produtoEntity");

    public final NumberPath<Double> altura = createNumber("altura", Double.class);

    public final EnumPath<CategoriaProdutoEnum> categoriaProdutoEnum = createEnum("categoriaProdutoEnum", CategoriaProdutoEnum.class);

    public final StringPath descricao = createString("descricao");

    public final StringPath EAN = createString("EAN");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final NumberPath<Double> largura = createNumber("largura", Double.class);

    public final StringPath nomeProduto = createString("nomeProduto");

    public final ListPath<java.util.UUID, ComparablePath<java.util.UUID>> pedidos = this.<java.util.UUID, ComparablePath<java.util.UUID>>createList("pedidos", java.util.UUID.class, ComparablePath.class, PathInits.DIRECT2);

    public final NumberPath<java.math.BigDecimal> peso = createNumber("peso", java.math.BigDecimal.class);

    public final NumberPath<Integer> quantidade = createNumber("quantidade", Integer.class);

    public final NumberPath<java.math.BigDecimal> valor = createNumber("valor", java.math.BigDecimal.class);

    public QProdutoEntity(String variable) {
        super(ProdutoEntity.class, forVariable(variable));
    }

    public QProdutoEntity(Path<? extends ProdutoEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProdutoEntity(PathMetadata metadata) {
        super(ProdutoEntity.class, metadata);
    }

}


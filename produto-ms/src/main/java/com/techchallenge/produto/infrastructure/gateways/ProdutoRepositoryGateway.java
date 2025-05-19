package com.techchallenge.produto.infrastructure.gateways;


import com.techchallenge.produto.application.exceptions.ProdutoInexistenteException;
import com.techchallenge.produto.application.gateway.ProdutoGateway;
import com.techchallenge.produto.domain.Produto;
import com.techchallenge.produto.infrastructure.persistence.CategoriaProdutoEnum;
import com.techchallenge.produto.infrastructure.persistence.ProdutoEntity;
import com.techchallenge.produto.infrastructure.persistence.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProdutoRepositoryGateway implements ProdutoGateway {

    private final ModelMapper modelMapper;
    private final ProdutoRepository produtoRepository;

    @Override
    public Produto criarProduto(Produto produto) {
        ProdutoEntity entity = modelMapper.map(produto, ProdutoEntity.class);
        entity.setCategoriaProdutoEnum(CategoriaProdutoEnum.obter(produto.getCategoriaProduto().name()));
        ProdutoEntity produtoEntity = produtoRepository.save(entity);
        return modelMapper.map(produtoEntity, Produto.class);
    }

    @Override
    public Produto alterarProduto(Produto produto) {
        Optional<ProdutoEntity> entityDb = produtoRepository.findById(produto.getId());

        if (entityDb.isEmpty()) {
            throw new ProdutoInexistenteException("Produto não foi encontrado :" + produto.getNomeProduto());
        }

        ProdutoEntity produtoEntity = entityDb.get();
        BeanUtils.copyProperties(modelMapper.map(produto, ProdutoEntity.class), produtoEntity);
        produtoEntity.setCategoriaProdutoEnum(CategoriaProdutoEnum.obter(produto.getCategoriaProduto().name()));
        produtoEntity = produtoRepository.save(produtoEntity);
        return modelMapper.map(produtoEntity, Produto.class);
    }

    @Override
    public void deletarProduto(Long id) {
        Optional<ProdutoEntity> entityDb = produtoRepository.findById(id);
        entityDb.ifPresentOrElse(produtoRepository::delete, () -> new ProdutoInexistenteException(" Produto não encontrado!"));
    }

    @Override
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll().stream().map(p -> modelMapper.map(p, Produto.class)).toList();
    }

    @Override
    public List<Produto> listarProdutosPorCategoria(String categoria) {
        CategoriaProdutoEnum categoriaProdutoEnum = CategoriaProdutoEnum.obterPorNome(categoria);
        return produtoRepository.findAllByCategoriaProdutoEnum(categoriaProdutoEnum)
                .stream()
                .map(p -> modelMapper.map(p, Produto.class)).toList();
    }

    @Override
    public Optional<Produto> obterPorId(Long id) {
        Optional<ProdutoEntity> produtoEntity = produtoRepository.findById(id);
        return produtoEntity.map(entity -> modelMapper.map(entity, Produto.class));
    }
}

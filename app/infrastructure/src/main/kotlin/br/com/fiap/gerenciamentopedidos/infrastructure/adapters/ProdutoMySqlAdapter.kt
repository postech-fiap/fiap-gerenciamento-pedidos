package br.com.fiap.gerenciamentopedidos.infrastructure.adapters

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.ProdutoEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ProdutoJpaRepository

class ProdutoMySqlAdapter(private val repository: ProdutoJpaRepository) : ProdutoRepository {
    override fun get(id: Long) = repository.getReferenceById(id).toDomain()
    override fun get(categoria: Categoria) = repository.findByCategoria(categoria).map { it.toDomain() }
    override fun save(produto: Produto) = repository.save(ProdutoEntity.fromDomain(produto)).toDomain()
    override fun update(produto: Produto) = repository.save(ProdutoEntity.fromDomain(produto)).toDomain()
}
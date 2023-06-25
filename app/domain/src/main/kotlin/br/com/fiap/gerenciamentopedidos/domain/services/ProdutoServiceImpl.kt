package br.com.fiap.gerenciamentopedidos.domain.services

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.services.ProdutoService
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

class ProdutoServiceImpl(var repository: ProdutoRepository) : ProdutoService {
    override fun get(id: Long): Produto = repository.get(id)
    override fun get(categoria: Categoria): List<Produto> = repository.get(categoria)
    override fun add(produto: Produto): Produto = repository.save(produto)
    override fun update(produto: Produto): Produto = repository.update(produto)
    override fun delete(id: Long) = repository.delete(id)
}
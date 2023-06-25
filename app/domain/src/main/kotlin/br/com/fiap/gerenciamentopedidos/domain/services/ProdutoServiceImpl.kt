package br.com.fiap.gerenciamentopedidos.domain.services

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.services.ProdutoService
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

class ProdutoServiceImpl(var repository: ProdutoRepository) : ProdutoService {
    override fun get(id: Long) = repository.get(id)
    override fun get(categoria: Categoria) = repository.get(categoria)
    override fun add(produto: Produto) = repository.save(produto)
    override fun update(produto: Produto) = repository.update(produto)
    override fun delete(id: Long) {
        val produto = repository.get(id)
        produto.excluir()
        repository.update(produto)
    }
}
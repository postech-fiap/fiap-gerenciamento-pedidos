package br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

interface ProdutoRepository {
    fun get(id: Long): Produto
    fun get(categoria: Categoria): List<Produto>
    fun save(produto: Produto): Produto
    fun update(produto: Produto): Produto
    fun delete(id: Long)
}
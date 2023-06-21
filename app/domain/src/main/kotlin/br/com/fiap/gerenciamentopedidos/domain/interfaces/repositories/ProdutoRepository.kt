package br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

interface ProdutoRepository {
    fun get(id: Int): Produto
    fun get(categoria: Categoria): List<Produto>
    fun add(produto: Produto): Produto
    fun update(produto: Produto): Produto
    fun delete(id: Int)
}
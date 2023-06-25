package br.com.fiap.gerenciamentopedidos.domain.interfaces.services

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

interface ProdutoService {
    fun get(id: Long): Produto
    fun get(categoria: Categoria): List<Produto>
    fun add(produto: Produto): Produto
    fun update(produto: Produto): Produto
    fun delete(id: Long)
}
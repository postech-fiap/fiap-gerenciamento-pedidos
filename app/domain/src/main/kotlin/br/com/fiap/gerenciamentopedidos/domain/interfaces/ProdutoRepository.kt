package br.com.fiap.gerenciamentopedidos.domain.interfaces

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import java.util.*

interface ProdutoRepository {
    fun get(id: Long): Optional<Produto>
    fun get(ids: List<Long>): List<Produto>
    fun get(categoria: Categoria): List<Produto>
    fun create(produto: Produto): Produto
    fun update(produto: Produto): Produto
    fun remove(id: Long)
}

package br.com.fiap.gerenciamentopedidos.domain.adapters

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import java.util.Optional

interface ProdutoAdapter {
    fun get(id: Long): Optional<Produto>
    fun get(categoria: Categoria): List<Produto>
    fun create(produto: Produto): Produto
    fun update(produto: Produto): Produto
}

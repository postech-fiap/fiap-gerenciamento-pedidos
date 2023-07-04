package br.com.fiap.gerenciamentopedidos.domain.ports

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import java.util.Optional

interface ProdutoPort {
    fun get(id: Long): Optional<ProdutoDto>
    fun get(categoria: Categoria): List<ProdutoDto>
    fun create(produto: ProdutoDto): ProdutoDto
    fun update(produto: ProdutoDto): ProdutoDto
}

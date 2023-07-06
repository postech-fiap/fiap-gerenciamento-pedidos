package br.com.fiap.gerenciamentopedidos.domain.interfaces

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import java.util.*

interface ProdutoRepository {
    fun get(id: Long): Optional<ProdutoDto>
    fun get(ids: List<Long>): List<ProdutoDto>
    fun get(categoria: Categoria): List<ProdutoDto>
    fun create(produto: ProdutoDto): ProdutoDto
    fun update(produto: ProdutoDto): ProdutoDto
}

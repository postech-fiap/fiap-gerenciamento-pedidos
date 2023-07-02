package br.com.fiap.gerenciamentopedidos.domain.ports.drivens

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import java.util.*

interface ProdutoPort {
    fun get(id: Long): Optional<ProdutoDto>
    fun get(categoria: Categoria): List<ProdutoDto>
    fun create(produto: ProdutoDto): ProdutoDto
    fun update(produto: ProdutoDto): ProdutoDto
}

package br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria

interface ListarProdutosPorCategoriaUseCase {
    fun executar(categoria: Categoria): List<ProdutoDto>
}

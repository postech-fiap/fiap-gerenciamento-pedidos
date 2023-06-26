package br.com.fiap.gerenciamentopedidos.application.produto.interfaces

import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria

interface ListarProdutosPorCategoriaUseCase {
    fun executar(categoria: Categoria): List<ProdutoResponse>
}

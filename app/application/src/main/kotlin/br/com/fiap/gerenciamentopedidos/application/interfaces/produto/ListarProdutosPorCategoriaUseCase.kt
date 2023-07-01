package br.com.fiap.gerenciamentopedidos.application.interfaces.produto

import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria

interface ListarProdutosPorCategoriaUseCase {
    fun executar(categoria: Categoria): List<ProdutoResponse>
}

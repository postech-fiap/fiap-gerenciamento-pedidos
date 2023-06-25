package br.com.fiap.gerenciamentopedidos.application.usecases

import br.com.fiap.gerenciamentopedidos.application.pedido.interfaces.UseCase
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.services.ProdutoService

class ListarProdutosPorCategoriaUseCase(val service: ProdutoService) : UseCase {
    fun executar(categoria: Categoria) = service.get(categoria).map { ProdutoResponse(it) }
}

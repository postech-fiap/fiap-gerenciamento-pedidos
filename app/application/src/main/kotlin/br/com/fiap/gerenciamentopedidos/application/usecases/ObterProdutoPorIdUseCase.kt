package br.com.fiap.gerenciamentopedidos.application.usecases

import br.com.fiap.gerenciamentopedidos.application.pedido.interfaces.UseCase
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.services.ProdutoService

class ObterProdutoPorIdUseCase(val service: ProdutoService) : UseCase {
    fun executar(id: Long) = ProdutoResponse(service.get(id))
}

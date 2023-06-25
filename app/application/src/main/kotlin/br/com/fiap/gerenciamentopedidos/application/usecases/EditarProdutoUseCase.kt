package br.com.fiap.gerenciamentopedidos.application.usecases

import br.com.fiap.gerenciamentopedidos.application.pedido.interfaces.UseCase
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.services.ProdutoService

class EditarProdutoUseCase(val service: ProdutoService) : UseCase {
    fun executar(request: EditarProdutoRequest) = ProdutoResponse(service.update(request.toDomain()))
}

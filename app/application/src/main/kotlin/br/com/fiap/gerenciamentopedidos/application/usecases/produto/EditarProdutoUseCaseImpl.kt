package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.interfaces.produto.EditarProdutoUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.adapters.ProdutoAdapter

class EditarProdutoUseCaseImpl(private val adapter: ProdutoAdapter) : EditarProdutoUseCase {
    override fun executar(request: EditarProdutoRequest) = ProdutoResponse(adapter.update(request.toDomain()))
}

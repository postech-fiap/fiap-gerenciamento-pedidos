package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.interfaces.produto.CadastrarProdutoUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.adapters.ProdutoAdapter

class CadastrarProdutoUseCaseImpl(private val adapter: ProdutoAdapter) : CadastrarProdutoUseCase {
    override fun executar(request: CadastrarProdutoRequest) = ProdutoResponse(adapter.create(request.toDomain()))
}

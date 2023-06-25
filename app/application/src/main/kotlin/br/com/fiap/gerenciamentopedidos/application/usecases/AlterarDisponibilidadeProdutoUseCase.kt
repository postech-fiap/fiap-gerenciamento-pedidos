package br.com.fiap.gerenciamentopedidos.application.usecases

import br.com.fiap.gerenciamentopedidos.application.pedido.interfaces.UseCase
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.services.ProdutoService

class AlterarDisponibilidadeProdutoUseCase(val service: ProdutoService) : UseCase {
    fun executar(id: Long, disponivel: Boolean): ProdutoResponse {
        val produto = service.get(id)
        produto.alterarDisponibilidade(disponivel)
        return ProdutoResponse(service.update(produto))
    }
}

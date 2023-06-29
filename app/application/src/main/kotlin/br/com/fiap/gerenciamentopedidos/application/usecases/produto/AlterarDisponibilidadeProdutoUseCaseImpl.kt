package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.interfaces.produto.AlterarDisponibilidadeProdutoUseCase
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.adapters.ProdutoAdapter

class AlterarDisponibilidadeProdutoUseCaseImpl(private val adapter: ProdutoAdapter) :
    AlterarDisponibilidadeProdutoUseCase {
    override fun executar(id: Long, disponivel: Boolean): ProdutoResponse {
        val produto = adapter.get(id).orElseThrow { RecursoNaoEncontradoException("Produto não encontrado") }
        produto.alterarDisponibilidade(disponivel)
        return ProdutoResponse(adapter.update(produto))
    }
}

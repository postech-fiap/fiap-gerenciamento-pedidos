package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.interfaces.produto.ObterProdutoPorIdUseCase
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.adapters.ProdutoAdapter

class ObterProdutoPorIdUseCaseImpl(private val adapter: ProdutoAdapter) : ObterProdutoPorIdUseCase {
    override fun executar(id: Long) = ProdutoResponse(adapter.get(id)
        .orElseThrow { RecursoNaoEncontradoException("Produto não encontrado") })
}

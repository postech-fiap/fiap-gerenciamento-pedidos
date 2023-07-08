package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.interfaces.produto.ObterProdutoPorIdUseCase
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository

class ObterProdutoPorIdUseCaseImpl(private val produtoRepository: ProdutoRepository) : ObterProdutoPorIdUseCase {
    override fun executar(id: Long) = ProdutoResponse(produtoRepository.get(id)
        .orElseThrow { RecursoNaoEncontradoException("Produto não encontrado") })
}

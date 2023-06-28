package br.com.fiap.gerenciamentopedidos.application.produto.usecases

import br.com.fiap.gerenciamentopedidos.application.produto.interfaces.ObterProdutoPorIdUseCase
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository

class ObterProdutoPorIdUseCaseImpl(private val repository: ProdutoRepository) : ObterProdutoPorIdUseCase {
    override fun executar(id: Long) = ProdutoResponse(repository.get(id)
        .orElseThrow { RecursoNaoEncontradoException("Produto não encontrado") })
}

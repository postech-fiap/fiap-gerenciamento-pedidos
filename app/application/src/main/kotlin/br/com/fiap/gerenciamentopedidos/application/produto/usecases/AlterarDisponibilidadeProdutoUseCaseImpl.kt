package br.com.fiap.gerenciamentopedidos.application.produto.usecases

import br.com.fiap.gerenciamentopedidos.application.produto.interfaces.AlterarDisponibilidadeProdutoUseCase
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository

class AlterarDisponibilidadeProdutoUseCaseImpl(private val repository: ProdutoRepository) :
    AlterarDisponibilidadeProdutoUseCase {
    override fun executar(id: Long, disponivel: Boolean): ProdutoResponse {
        val produto = repository.get(id).orElseThrow { RecursoNaoEncontradoException("Produto não encontrado") }
        produto.alterarDisponibilidade(disponivel)
        return ProdutoResponse(repository.update(produto))
    }
}

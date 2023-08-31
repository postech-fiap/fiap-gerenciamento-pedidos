package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.interfaces.produto.AlterarDisponibilidadeProdutoUseCase
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

class AlterarDisponibilidadeProdutoUseCaseImpl(private val produtoRepository: ProdutoRepository) :
    AlterarDisponibilidadeProdutoUseCase {
    override fun executar(id: Long, disponivel: Boolean): Produto {
        val produto = produtoRepository.get(id)
            .orElseThrow { RecursoNaoEncontradoException("Produto não encontrado") }
        produto.alterarDisponibilidade(disponivel)
        return produtoRepository.update(produto)
    }
}

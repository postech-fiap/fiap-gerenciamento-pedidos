package br.com.fiap.gerenciamentopedidos.application.usecases.produto

import br.com.fiap.gerenciamentopedidos.application.interfaces.produto.AlterarDisponibilidadeProdutoUseCase
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository

class AlterarDisponibilidadeProdutoUseCaseImpl(private val produtoPort: ProdutoRepository) :
    AlterarDisponibilidadeProdutoUseCase {
    override fun executar(id: Long, disponivel: Boolean): ProdutoResponse {
        val produto =
            produtoPort.get(id).orElseThrow { RecursoNaoEncontradoException("Produto não encontrado") }.toModel()
        produto.alterarDisponibilidade(disponivel)
        return ProdutoResponse(produtoPort.update(ProdutoDto.fromModel(produto)))
    }
}

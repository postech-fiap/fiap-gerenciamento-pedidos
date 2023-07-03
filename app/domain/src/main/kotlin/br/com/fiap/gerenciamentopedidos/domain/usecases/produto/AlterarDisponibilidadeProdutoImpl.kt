package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ProdutoPort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto.AlterarDisponibilidadeProduto

class AlterarDisponibilidadeProdutoImpl(private val produtoPort: ProdutoPort) :
    AlterarDisponibilidadeProduto {
    override fun executar(id: Long, disponivel: Boolean): ProdutoResponse {
        val produto = produtoPort.get(id)
            .orElseThrow { RecursoNaoEncontradoException("Produto não encontrado") }.toModel()

        return ProdutoResponse(
            produtoPort.update(ProdutoDto.fromModel(produto.alterarDisponibilidade(disponivel))).toModel()
        )
    }
}

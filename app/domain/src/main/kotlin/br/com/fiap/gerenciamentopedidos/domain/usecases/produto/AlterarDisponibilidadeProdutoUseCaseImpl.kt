package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ProdutoPort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto.AlterarDisponibilidadeProdutoPort

class AlterarDisponibilidadeProdutoUseCaseImpl(private val produtoPort: ProdutoPort) :
    AlterarDisponibilidadeProdutoPort {
    override fun executar(id: Long, disponivel: Boolean): ProdutoDto {
        val produto = produtoPort.get(id)
            .orElseThrow { RecursoNaoEncontradoException("Produto não encontrado") }.toModel()
        return produtoPort.update(ProdutoDto.fromModel(produto.alterarDisponibilidade(disponivel)))
    }
}

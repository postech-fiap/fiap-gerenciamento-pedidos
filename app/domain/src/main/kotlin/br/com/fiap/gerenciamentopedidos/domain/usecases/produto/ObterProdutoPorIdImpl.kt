package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ProdutoPort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto.ObterProdutoPorId

class ObterProdutoPorIdImpl(private val produtoPort: ProdutoPort) : ObterProdutoPorId {
    override fun executar(id: Long) = ProdutoResponse(
        produtoPort.get(id)
            .orElseThrow { RecursoNaoEncontradoException("Produto não encontrado") }.toModel()
    )
}

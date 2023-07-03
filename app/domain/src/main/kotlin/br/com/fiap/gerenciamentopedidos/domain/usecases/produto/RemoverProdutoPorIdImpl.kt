package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ProdutoPort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto.RemoverProdutoPorId

class RemoverProdutoPorIdImpl(private val produtoPort: ProdutoPort) : RemoverProdutoPorId {
    override fun executar(id: Long) {
        produtoPort.update(ProdutoDto.fromModel(
            produtoPort.get(id)
                .orElseThrow { RecursoNaoEncontradoException("Produto não encontrado") }
                .toModel().excluir()))
    }
}

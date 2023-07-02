package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ProdutoPort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto.RemoverProdutoPorIdUseCase

class RemoverProdutoPorIdUseCaseImpl(private val produtoPort: ProdutoPort) : RemoverProdutoPorIdUseCase {
    override fun executar(id: Long) {
        produtoPort.update(ProdutoDto.fromModel(
            produtoPort.get(id)
                .orElseThrow { RecursoNaoEncontradoException("Produto não encontrado") }
                .toModel().excluir()))
    }
}

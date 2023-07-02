package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ProdutoPort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto.EditarProdutoUseCase

class EditarProdutoUseCaseImpl(private val produtoPort: ProdutoPort) : EditarProdutoUseCase {
    override fun executar(request: ProdutoDto) = produtoPort.update(request)
}

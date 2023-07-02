package br.com.fiap.gerenciamentopedidos.domain.usecases.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ProdutoPort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto.CadastrarProdutoPort

class CadastrarProdutoUseCaseImpl(private val produtoPort: ProdutoPort) : CadastrarProdutoPort {
    override fun executar(produto: ProdutoDto) = produtoPort.create(ProdutoDto.fromModel(produto.toModel()))
}

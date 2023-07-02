package br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto

interface CadastrarProdutoPort {
    fun executar(produto: ProdutoDto): ProdutoDto
}

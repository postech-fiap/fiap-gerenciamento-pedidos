package br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.responses.ProdutoResponse

interface ObterProdutoPorId {
    fun executar(id: Long): ProdutoResponse
}

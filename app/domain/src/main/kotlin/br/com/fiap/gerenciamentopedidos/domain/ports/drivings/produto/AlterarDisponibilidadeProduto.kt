package br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.responses.ProdutoResponse

interface AlterarDisponibilidadeProduto {
    fun executar(id: Long, disponivel: Boolean): ProdutoResponse
}

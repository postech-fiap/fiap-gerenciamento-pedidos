package br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto

interface AlterarDisponibilidadeProdutoPort {
    fun executar(id: Long, disponivel: Boolean): ProdutoDto
}

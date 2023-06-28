package br.com.fiap.gerenciamentopedidos.application.produto.interfaces

import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse

interface AlterarDisponibilidadeProdutoUseCase {
    fun executar(id: Long, disponivel: Boolean): ProdutoResponse
}

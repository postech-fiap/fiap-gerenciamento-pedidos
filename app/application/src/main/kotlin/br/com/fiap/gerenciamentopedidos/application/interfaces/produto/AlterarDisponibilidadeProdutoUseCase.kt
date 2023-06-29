package br.com.fiap.gerenciamentopedidos.application.interfaces.produto

import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse

interface AlterarDisponibilidadeProdutoUseCase {
    fun executar(id: Long, disponivel: Boolean): ProdutoResponse
}

package br.com.fiap.gerenciamentopedidos.application.interfaces.produto

import br.com.fiap.gerenciamentopedidos.domain.models.Produto

interface AlterarDisponibilidadeProdutoUseCase {
    fun executar(id: Long, disponivel: Boolean): Produto
}

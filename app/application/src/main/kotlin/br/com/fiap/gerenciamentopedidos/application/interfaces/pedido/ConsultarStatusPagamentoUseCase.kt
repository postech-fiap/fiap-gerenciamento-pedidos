package br.com.fiap.gerenciamentopedidos.application.interfaces.pedido

import br.com.fiap.gerenciamentopedidos.application.responses.PagamentoStatusResponse

interface ConsultarStatusPagamentoUseCase {
    fun executar(id: Long): PagamentoStatusResponse
}

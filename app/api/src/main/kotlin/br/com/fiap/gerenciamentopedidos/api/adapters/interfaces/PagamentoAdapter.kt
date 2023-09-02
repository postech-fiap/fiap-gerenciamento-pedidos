package br.com.fiap.gerenciamentopedidos.api.adapters.interfaces

import br.com.fiap.gerenciamentopedidos.api.requests.PagamentoCriadoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.StatusPagamentoResponse

interface PagamentoAdapter {

    fun finalizarPagamento(request: PagamentoCriadoRequest): StatusPagamentoResponse
}

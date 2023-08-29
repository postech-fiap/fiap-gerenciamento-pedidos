package br.com.fiap.gerenciamentopedidos.api.facades.interfaces

import br.com.fiap.gerenciamentopedidos.api.requests.PagamentoCriadoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.DetalhePagamentoResponse

interface PagamentoFacade {

    fun finalizarPagamento(request: PagamentoCriadoRequest): DetalhePagamentoResponse
}

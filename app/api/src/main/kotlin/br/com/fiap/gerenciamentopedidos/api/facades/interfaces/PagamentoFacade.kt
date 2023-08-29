package br.com.fiap.gerenciamentopedidos.api.facades.interfaces

import br.com.fiap.gerenciamentopedidos.api.requests.PagamentoCriadoRequest

interface PagamentoFacade {

    fun finalizarPagamento(request: PagamentoCriadoRequest)
}

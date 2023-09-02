package br.com.fiap.gerenciamentopedidos.api.adapters

import br.com.fiap.gerenciamentopedidos.api.adapters.interfaces.PagamentoAdapter
import br.com.fiap.gerenciamentopedidos.api.requests.PagamentoCriadoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.StatusPagamentoResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pagamento.FinalizarPagamentoUseCase

class PagamentoAdapterImpl(
    private val finalizarPagamentoUseCase: FinalizarPagamentoUseCase
) : PagamentoAdapter {

    override fun finalizarPagamento(request: PagamentoCriadoRequest) =
            StatusPagamentoResponse(finalizarPagamentoUseCase.executar(request.toModel()))
}

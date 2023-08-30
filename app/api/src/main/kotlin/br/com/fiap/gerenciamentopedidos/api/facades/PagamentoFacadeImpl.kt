package br.com.fiap.gerenciamentopedidos.api.facades

import br.com.fiap.gerenciamentopedidos.api.facades.interfaces.PagamentoFacade
import br.com.fiap.gerenciamentopedidos.api.requests.PagamentoCriadoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.StatusPagamentoResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pagamento.FinalizarPagamentoUseCase

class PagamentoFacadeImpl(
    private val finalizarPagamentoUseCase: FinalizarPagamentoUseCase
) : PagamentoFacade {

    override fun finalizarPagamento(request: PagamentoCriadoRequest) =
            StatusPagamentoResponse(finalizarPagamentoUseCase.executar(request.toModel()))
}

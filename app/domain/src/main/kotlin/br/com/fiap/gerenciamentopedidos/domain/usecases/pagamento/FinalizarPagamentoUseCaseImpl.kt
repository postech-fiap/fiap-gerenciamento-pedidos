package br.com.fiap.gerenciamentopedidos.domain.usecases.pagamento

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.BuscarPagamentoPorIdGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pagamento.FinalizarPagamentoUseCase
import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.PagamentoCriado

private const val STATUS_APROVADO = "approved"

class FinalizarPagamentoUseCaseImpl(
    private val buscarPagamentoPorIdGateway: BuscarPagamentoPorIdGateway
) : FinalizarPagamentoUseCase {

    override fun executar(pagamentoCriado: PagamentoCriado): PagamentoStatus {
        val pagamento = buscarPagamentoPorIdGateway.executar(pagamentoCriado.data.id)
        return if (!STATUS_APROVADO.equals(pagamento.status, true)) {
            PagamentoStatus.REPROVADO
        } else {
            PagamentoStatus.APROVADO
        }
    }

}

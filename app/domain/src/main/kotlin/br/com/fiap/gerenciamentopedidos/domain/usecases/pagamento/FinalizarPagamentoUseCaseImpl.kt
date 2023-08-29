package br.com.fiap.gerenciamentopedidos.domain.usecases.pagamento

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.BuscarPagamentoPorIdGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pagamento.FinalizarPagamentoUseCase
import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.DetalhePagamento
import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.PagamentoCriado

private const val STATUS_APROVADO = "approved"

class FinalizarPagamentoUseCaseImpl(
        private val buscarPagamentoPorIdGateway: BuscarPagamentoPorIdGateway,
        private val pagamentoRepository: PagamentoRepository,
        private val pedidoRepository: PedidoRepository
) : FinalizarPagamentoUseCase {

    override fun executar(pagamentoCriado: PagamentoCriado): DetalhePagamento {
        val pagamento = buscarPagamentoPorIdGateway.executar(pagamentoCriado.data.id)

        val pagamentoAndPedidoStatus: Pair<PagamentoStatus, PedidoStatus> =
                if (!STATUS_APROVADO.equals(pagamento.status, true)) {
                    Pair(PagamentoStatus.REPROVADO, PedidoStatus.PENDENTE)
                } else {
                    Pair(PagamentoStatus.APROVADO, PedidoStatus.RECEBIDO)
                }

        pagamentoRepository.alterarStatusPagamento(pagamentoAndPedidoStatus.first, pagamento.externalReference.toLong())
        pedidoRepository.alterarStatusPedido(pagamentoAndPedidoStatus.second, pagamento.externalReference.toLong())

        return pagamento
                .copy(status = pagamentoAndPedidoStatus.first.toString())
    }

}

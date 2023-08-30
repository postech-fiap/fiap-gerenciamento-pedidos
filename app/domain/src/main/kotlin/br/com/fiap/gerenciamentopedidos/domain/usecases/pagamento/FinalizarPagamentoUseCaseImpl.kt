package br.com.fiap.gerenciamentopedidos.domain.usecases.pagamento

import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.BuscarPagamentoPorIdGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pagamento.FinalizarPagamentoUseCase
import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.MerchantOrders.Elements.Payments
import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.PagamentoCriado

class FinalizarPagamentoUseCaseImpl(
        private val buscarPagamentoPorIdGateway: BuscarPagamentoPorIdGateway,
        private val pagamentoRepository: PagamentoRepository,
        private val pedidoRepository: PedidoRepository
) : FinalizarPagamentoUseCase {

    override fun executar(pagamentoCriado: PagamentoCriado): Payments {
        val ordemComPagamento = buscarPagamentoPorIdGateway.executar(pagamentoCriado.data.id)
        val pagamento = ordemComPagamento.obterUltimoPagamento()

        requireNotNull(pagamento) { "O pagamento não deve ser nulo" }

        val idDoPedido = ordemComPagamento.obterIdDoPedido()?.toLong()

        requireNotNull(idDoPedido) { "O id do pedido não deve ser nulo" }

        val statusPagamentoEPedido = pagamento.calcularStatusPagamentoPedido()

        pagamentoRepository.alterarStatusPagamento(statusPagamentoEPedido.first, idDoPedido)
        pedidoRepository.alterarStatusPedido(statusPagamentoEPedido.second, idDoPedido)

        return pagamento
                .copy(status = statusPagamentoEPedido.first.toString())
    }

}

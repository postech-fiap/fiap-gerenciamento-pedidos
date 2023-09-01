package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.ConsultarStatusPagamentoUseCase

class ConsultarStatusPagamentoUseCaseImpl(private val pedidoRepository: PedidoRepository) : ConsultarStatusPagamentoUseCase {
    override fun executar(pedidoId: Long) : PagamentoStatus {
        val pedidoResult = pedidoRepository.buscarPedidoPorId(pedidoId)
        return pedidoResult.pagamento!!.status
    }
}

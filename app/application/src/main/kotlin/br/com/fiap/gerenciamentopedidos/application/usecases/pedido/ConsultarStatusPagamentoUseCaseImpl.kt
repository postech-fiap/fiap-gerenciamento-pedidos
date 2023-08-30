package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.ConsultarStatusPagamentoUseCase
import br.com.fiap.gerenciamentopedidos.application.responses.PagamentoStatusResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
class ConsultarStatusPagamentoUseCaseImpl(
    private val pedidoRepository: PedidoRepository
) : ConsultarStatusPagamentoUseCase {
    override fun executar(id: Long): PagamentoStatusResponse {
        val pedido = pedidoRepository.buscarPedidoPorId(id)
        val pagamento = pedido.pagamento
        return PagamentoStatusResponse(pagamento?.status!!)
    }
}

package br.com.fiap.gerenciamentopedidos.domain.usecases.pagamento

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.ConsultarStatusPagamentoUseCase

class ConsultarStatusPagamentoUseCaseImpl(private val pedidoRepository: PedidoRepository) :
    ConsultarStatusPagamentoUseCase {
    override fun executar(pedidoId: Long): PagamentoStatus {
        val pedidoResult = pedidoRepository.buscarPedidoPorId(pedidoId)
            .orElseThrow { RecursoNaoEncontradoException("Pedido não encontrado") }
        return pedidoResult.pagamento!!.status
    }
}

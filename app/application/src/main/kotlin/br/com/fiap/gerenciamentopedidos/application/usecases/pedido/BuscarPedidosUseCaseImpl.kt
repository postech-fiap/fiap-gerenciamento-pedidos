package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.BuscarPedidosUseCase
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import java.time.OffsetDateTime

class BuscarPedidosUseCaseImpl(private val pedidoRepository: PedidoRepository) : BuscarPedidosUseCase {
    override fun executar(status: PedidoStatus, dataInicial: OffsetDateTime, dataFinal: OffsetDateTime) =
        pedidoRepository.buscarPedidos(status, dataInicial, dataFinal)
}

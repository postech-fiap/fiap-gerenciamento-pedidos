package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import java.time.OffsetDateTime

interface BuscarPedidosUseCase {
    fun executar(status: PedidoStatus, dataInicial: OffsetDateTime, dataFinal: OffsetDateTime): List<Pedido>
}

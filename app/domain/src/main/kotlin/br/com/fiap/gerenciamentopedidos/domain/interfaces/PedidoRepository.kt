package br.com.fiap.gerenciamentopedidos.domain.interfaces

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import java.util.*

interface PedidoRepository {
    fun obterUltimoNumeroPedidoDoDia(): String
    fun salvar(pedido: Pedido): Pedido
    fun buscarPedidoPorReferencia(referencia: UUID): Optional<Pedido>
    fun update(pedido: Pedido): Pedido
}

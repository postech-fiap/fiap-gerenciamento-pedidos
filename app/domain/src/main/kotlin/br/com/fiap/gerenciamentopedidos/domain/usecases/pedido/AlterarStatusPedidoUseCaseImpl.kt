package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.dtos.ItemPedidoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BusinessException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.ProducaoGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.AlterarStatusPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import org.springframework.transaction.annotation.Transactional

@Transactional
class AlterarStatusPedidoUseCaseImpl(
    private val pedidoRepository: PedidoRepository,
    private val producaoGateway: ProducaoGateway
) : AlterarStatusPedidoUseCase {
    override fun executar(pedidoId: Long, status: PedidoStatus) {
        val pedidoResult = pedidoRepository.buscarPedidoPorId(pedidoId)

        when (pedidoResult.get().status) {
            status -> throw BusinessException("O status do pedido ja está igual à $status")
            else -> {
                producaoGateway.enviar(criarPedidoDto(pedidoResult.get()))
                return pedidoRepository.alterarStatusPedido(status, pedidoId)
            }
        }
    }

    private fun criarPedidoDto(pedido: Pedido) = PedidoDto(
        pedido.id,
        pedido.numero,
        pedido.dataHora,
        pedido.items.map { ItemPedidoDto(it.produto!!.nome, it.quantidade, it.comentario) }
    )
}

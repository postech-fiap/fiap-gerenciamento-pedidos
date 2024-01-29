package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.dtos.ItemPedidoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.ProducaoGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.AlterarStatusPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
class AlterarStatusPedidoUseCaseImpl(
    private val pedidoRepository: PedidoRepository,
    private val producaoGateway: ProducaoGateway
) : AlterarStatusPedidoUseCase {
    override fun executar(referencia: UUID, status: PagamentoStatus) {
        val pedido = pedidoRepository
            .buscarPedidoPorReferencia(referencia)
            .orElseThrow { RecursoNaoEncontradoException("Pedido não encontrado") }

        pedido.alterarPagamentoStatus(status)

        pedidoRepository.update(pedido)

        if (pedido.isAprovado())
            producaoGateway.enviar(criarPedidoDto(pedido))
    }

    private fun criarPedidoDto(pedido: Pedido) = PedidoDto(
        pedido.id,
        pedido.numero,
        pedido.dataHora,
        pedido.items.map { ItemPedidoDto(it.produto!!.nome, it.quantidade, it.comentario) }
    )
}

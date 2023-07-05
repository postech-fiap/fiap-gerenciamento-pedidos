package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoService
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import java.time.OffsetDateTime

class CadastrarPedidoUseCaseImpl(
    private val pedidoRepository: PedidoRepository,
    private val pagamentoService: PagamentoService
) : CadastrarPedidoUseCase {

    override fun executar(request: CadastrarPedidoRequest): PedidoResponse {
        val now = OffsetDateTime.now()
        val initOfDay = now
            .withHour(0)
            .withMinute(0)
            .withSecond(0)

        val endOfDay = now
            .withHour(23)
            .withMinute(59)
            .withSecond(59)

        val numero =
            pedidoRepository.buscarUltimoPedidoDoDia(initOfDay, endOfDay).map { it.numero?.toLong()?.plus(1) }
                .orElse(1).toString()

        val pedido = Pedido(
            numero = numero,
            clienteId = request.clienteId,
            produtos = request.produtos?.map {
                PedidoProdutoDto(
                    produto = ProdutoDto(it.produtoId),
                    quantidade = it.quantidade!!,
                    comentario = it.comentario
                ).toModel()
            }
        )

        val pagamento = pagamentoService.efetuarPagamento(pedido.numero!!, pedido.getValorTotal()!!)
        pedido.incluirPagamento(pagamento.dataHora, pagamento.status)

        return PedidoResponse(pedidoRepository.salvar(PedidoDto.fromModel(pedido)))
    }
}

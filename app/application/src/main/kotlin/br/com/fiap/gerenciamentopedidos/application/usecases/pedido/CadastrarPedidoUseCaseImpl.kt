package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoService
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import java.time.LocalDate
import java.time.OffsetDateTime

class CadastrarPedidoUseCaseImpl(
    private val pedidoRepository: PedidoRepository,
    private val clienteRepository: ClienteRepository,
    private val pagamentoRepository: PagamentoService,
    private val produtoRepository: ProdutoRepository
) : CadastrarPedidoUseCase {

    override fun executar(request: CadastrarPedidoRequest): PedidoResponse {
        //PROBLEMA N+1
        val produtos = request.produtos.map {
            val produto = produtoRepository.get(it.produtoId!!).get()
            PedidoProdutoDto(
                produto = produto,
                quantidade = it.quantidade!!,
                comentario = it.comentario
            ).toModel()
        }

        val numero =
            pedidoRepository.buscarUltimoPedidoDiDia(OffsetDateTime.now()).map { it.numero?.toLong()?.plus(1) }
                .orElse(1).toString()

        val pedido = Pedido(
            numero = numero,
            cliente = clienteRepository.buscarPorId(request.clienteId).toModel(),
            produtos = produtos
        )

        val pagamento = pagamentoRepository.efetuarPagamento(pedido.numero!!, pedido.getValorTotal()!!)
        pedido.incluirPagamento(pagamento.dataHora, pagamento.status)

        return PedidoResponse(pedidoRepository.salvar(PedidoDto.fromModel(pedido)))
    }
}

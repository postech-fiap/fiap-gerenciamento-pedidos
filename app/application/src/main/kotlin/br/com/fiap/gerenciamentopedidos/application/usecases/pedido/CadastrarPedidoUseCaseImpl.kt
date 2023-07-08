package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoService
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.domain.models.PedidoProduto
import java.math.BigInteger

class CadastrarPedidoUseCaseImpl(
    private val pedidoRepository: PedidoRepository,
    private val produtoRepository: ProdutoRepository,
    private val clienteRepository: ClienteRepository,
    private val pagamentoService: PagamentoService
) : CadastrarPedidoUseCase {
    override fun executar(request: CadastrarPedidoRequest): PedidoResponse {
        val numero = (pedidoRepository.obterUltimoNumeroPedidoDoDia().toInt() + 1).toString()

        val produtos = produtoRepository.get(request.produtos?.map { it.produtoId }!!)

        val pedido = Pedido(
            numero = numero,
            cliente = request.clienteId?.let { clienteRepository.buscarPorId(it) }?.toModel(),
            pagamento = pagamentoService.efetuarPagamento(numero).toModel(),
            produtos = request.produtos.map {
                val produto = produtos.first { p -> p.id == it.produtoId }.toModel()
                if (produto.disponivel) {
                    PedidoProduto(
                        quantidade = it.quantidade,
                        comentario = it.comentario,
                        produto = produto,
                        valorPago = produto.valor
                    )
                } else {
                    throw Exception("Produto ${produto.nome} não está disponível")
                }
            }
        )
        return PedidoResponse(pedidoRepository.salvar(PedidoDto.fromModel(pedido)))
    }
}

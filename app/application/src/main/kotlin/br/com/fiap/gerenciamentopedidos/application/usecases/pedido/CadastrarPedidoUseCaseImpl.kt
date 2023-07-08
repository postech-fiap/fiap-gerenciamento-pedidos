package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BusinessException
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoService
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.domain.models.PedidoProduto

class CadastrarPedidoUseCaseImpl(
    private val pedidoRepository: PedidoRepository,
    private val produtoRepository: ProdutoRepository,
    private val clienteRepository: ClienteRepository,
    private val pagamentoService: PagamentoService
) : CadastrarPedidoUseCase {
    override fun executar(request: CadastrarPedidoRequest): PedidoResponse {
        val numero = (pedidoRepository.obterUltimoNumeroPedidoDoDia().toInt() + 1).toString()

        val produtos = produtoRepository.get(request.produtos?.map { it.produtoId }!!)

        val cliente = request.clienteId?.let {
            clienteRepository.buscarPorId(it)
                .orElseThrow { RecursoNaoEncontradoException("Cliente não encontrado") }.toModel()
        }

        val pedido = Pedido(
            numero = numero,
            cliente = cliente,
            pagamento = pagamentoService.efetuarPagamento(numero).toModel(),
            produtos = request.produtos.map {
                val produto = produtos.firstOrNull { p -> p.id == it.produtoId }?.toModel()
                    ?: throw RecursoNaoEncontradoException("Produto ${it.produtoId} não encontrado ou indisponível")
                PedidoProduto(
                    quantidade = it.quantidade,
                    comentario = it.comentario,
                    produto = produto,
                    valorPago = produto.valor
                )
            }
        )
        return PedidoResponse(pedidoRepository.salvar(PedidoDto.fromModel(pedido)))
    }
}

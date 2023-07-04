package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import java.time.LocalDate

class CadastrarPedidoUseCaseImpl(
    private val pedidoRepository: PedidoRepository,
    private val clientePort: ClienteRepository,
    private val pagamentoPort: PagamentoRepository
    ): CadastrarPedidoUseCase{

    override fun executar(cadastrarPedidoRequest: CadastrarPedidoRequest): PedidoResponse {

        val cliente = clientePort.buscarPorId(cadastrarPedidoRequest.clienteId)
        val pedido = pedidoRepository.buscarUltimoPedidoDiDia(LocalDate.now().dayOfMonth).numero

        var numeroPedido = pedido?.plus(1L) ?: 1L

        val tempoEspera = cadastrarPedidoRequest.produtos!!.stream()
            .mapToLong() { it.tempoPreparo!! }
            .average().asDouble.toInt()

        var pagamento = pagamentoPort.efetuarPagamento(numeroPedido.toString());

        return PedidoResponse(pedidoRepository.salvar(PedidoDto.fromModel(cadastrarPedidoRequest.toPedido(cadastrarPedidoRequest.produtos, tempoEspera, numeroPedido.toString(), cliente.toModel(), pagamento))))
    }
}

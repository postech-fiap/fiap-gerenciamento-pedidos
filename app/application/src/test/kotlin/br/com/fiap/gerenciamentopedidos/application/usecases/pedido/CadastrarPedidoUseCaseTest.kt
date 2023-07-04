package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.domain.dtos.ClienteDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Cliente
import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Cpf
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Email
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.OffsetDateTime

@ExtendWith(MockKExtension::class)
class CadastrarPedidoUseCaseTest {

    @InjectMockKs
    lateinit var cadastrarUseCaseImpl: CadastrarPedidoUseCaseImpl

    @InjectMockKs
    lateinit var pagamentoRepository: PagamentoRepository

    @InjectMockKs
    lateinit var clienteRepository: ClienteRepository

    @InjectMockKs
    lateinit var pedidoRepository: PedidoRepository

    @Test
    fun `deve cadastrar um pedido`() {
        // given
        val pedido = Pedido(1, OffsetDateTime.now(), PedidoStatus.PENDENTE, 10, "10", null, null, null)
        val cliente = Cliente(1, Cpf("12345678910"), "Gabriel", Email("Teste@teste.com.br"))
        val pagamento = Pagamento("1", OffsetDateTime.now() , PagamentoStatus.APROVADO)
        val clienteId = 10L
        val produtos = listOf(Produto(1, "Produto 1", "lanche", Categoria.LANCHE, 10.0, 10, true, false, null))
        val request = CadastrarPedidoRequest(clienteId, produtos)

        every { pedidoRepository.buscarUltimoPedidoDiDia(10) } returns PedidoDto.fromModel(pedido)
        every { pagamentoRepository.efetuarPagamento(pedido.numero) } returns pagamento
        every { clienteRepository.buscarPorId(clienteId) } returns ClienteDto.fromModel(cliente)
        every { pedidoRepository.buscarUltimoPedidoDiDia(10) } returns PedidoDto.fromModel(pedido)

        // when
        val result = cadastrarUseCaseImpl.executar(request)
        // then
        verify { pagamentoRepository.efetuarPagamento(pedido.numero) }

    }
}

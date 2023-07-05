package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.domain.dtos.ClienteDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.*
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Cpf
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Email
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.OffsetDateTime
import java.util.*

@ExtendWith(MockKExtension::class)
class CadastrarPedidoUseCaseTest {

    @InjectMockKs
    lateinit var cadastrarUseCaseImpl: CadastrarPedidoUseCaseImpl

    @MockK
    lateinit var pagamentoRepository: PagamentoRepository

    @MockK
    lateinit var clienteRepository: ClienteRepository

    @MockK
    lateinit var pedidoRepository: PedidoRepository

    @MockK
    lateinit var produtoRepository: ProdutoRepository

    @Test
    fun `deve cadastrar um pedido`() {

        var produtoList = listOf(
            PedidoProduto(
                10,
                Produto(1, "Hamburguer", "O brabo", Categoria.LANCHE, 10.0, 10, true, false, null ),
                10,
                "Sem mostarda"
            )
        )
        // given

        var produto = Produto(
            id = 1L,
            nome = "Nome",
            descricao = null,
            categoria = Categoria.BEBIDA,
            valor = 1.0,
            tempoPreparo = 1,
            disponivel = true,
            excluido = false,
            imagem = null
        )

        var pedido = Pedido(1, "1", OffsetDateTime.now(), PedidoStatus.PENDENTE, null, produtoList, 15, null)
        val cliente = Cliente(1, Cpf("12345678910"), "Gabriel", Email("Teste@teste.com.br"))
        val pagamento = Pagamento(1, OffsetDateTime.now() , PagamentoStatus.APROVADO)
        val clienteId = 10L
        val produtos = listOf(CadastrarPedidoProdutoRequest(1, 10, "Sem mostarda"))
        val request = CadastrarPedidoRequest(clienteId, produtos)


        every { pedidoRepository.buscarUltimoPedidoDiDia(any()) } returns Optional.of(PedidoDto.fromModel(pedido))
        every { pagamentoRepository.efetuarPagamento(any(), any()) } returns pagamento
        every { clienteRepository.buscarPorId(any()) } returns ClienteDto.fromModel(cliente)
        every { pedidoRepository.buscarUltimoPedidoDiDia(any()) } returns Optional.of(PedidoDto.fromModel(pedido))
        every { pedidoRepository.salvar(any()) } returns PedidoDto.fromModel(pedido)
        every { produtoRepository.get(1L) } returns Optional.of(ProdutoDto.fromModel(produto))

        // when
        val result = cadastrarUseCaseImpl.executar(request)

        verify { pedidoRepository.salvar(any()) }

    }



}

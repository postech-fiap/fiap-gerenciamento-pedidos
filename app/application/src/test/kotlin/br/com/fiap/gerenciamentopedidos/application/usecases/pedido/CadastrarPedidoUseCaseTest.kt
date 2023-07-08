package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.domain.dtos.*
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoService
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
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.OffsetDateTime

@ExtendWith(MockKExtension::class)
class CadastrarPedidoUseCaseTest {

    @InjectMockKs
    lateinit var cadastrarUseCaseImpl: CadastrarPedidoUseCaseImpl

    @MockK
    lateinit var pagamentoService: PagamentoService

    @MockK
    lateinit var clienteRepository: ClienteRepository

    @MockK
    lateinit var pedidoRepository: PedidoRepository

    @MockK
    lateinit var produtoRepository: ProdutoRepository

    @Test
    fun `deve cadastrar um pedido com sucesso`() {
        // Arrange
        val pedido = criarPedido()

        val request = CadastrarPedidoRequest(10L, listOf(CadastrarPedidoProdutoRequest(1, 10, "Sem mostarda")))

        every { produtoRepository.get(any<List<Long>>()) } returns pedido.produtos.map { ProdutoDto.fromModel(it.produto!!) }
        every { pedidoRepository.obterUltimoNumeroPedidoDoDia() } returns "1"
        every { pagamentoService.efetuarPagamento(any()) } returns PagamentoDto.fromModel(pedido.pagamento!!)
        every { clienteRepository.buscarPorId(any()) } returns ClienteDto.fromModel(pedido.cliente!!)
        every { pedidoRepository.salvar(any()) } returns PedidoDto.fromModel(pedido)

        // Act
        val result = cadastrarUseCaseImpl.executar(request)

        // Assert
        val produtosResult = result.produtos?.toList()!!

        assertEquals("1", result.numero)
        assertEquals(1, produtosResult.size)
        assertEquals(1, produtosResult[0].quantidade)
        assertEquals("Sem mostarda", produtosResult[0].comentario)
        assertEquals(BigDecimal(10), produtosResult[0].valorPago)
        assertEquals(10L, result.tempoEsperaMinutos)
        assertEquals(PedidoStatus.RECEBIDO, result.status)
        assertEquals(1, result.cliente?.id)

        verify(exactly = 1) { pedidoRepository.salvar(any()) }
    }

    private fun criarPedido() = Pedido(
        1,
        "1",
        OffsetDateTime.now(),
        PedidoStatus.RECEBIDO,
        Cliente(1, Cpf("22233388878"), "Derick Silva", Email("dsilva@gmail.com")),
        listOf(
            PedidoProduto(
                id = 1,
                quantidade = 1,
                comentario = "Sem mostarda",
                valorPago = BigDecimal(10),
                produto = Produto(
                    id = 1,
                    nome = "Produto 1",
                    descricao = "descricao",
                    categoria = Categoria.BEBIDA,
                    valor = BigDecimal(10),
                    tempoPreparo = 10,
                    disponivel = true,
                    excluido = false,
                    imagem = Imagem(1, "/caminho.jpg")
                )
            )
        ),
        Pagamento(1, OffsetDateTime.now(), PagamentoStatus.APROVADO),
        10
    )
}

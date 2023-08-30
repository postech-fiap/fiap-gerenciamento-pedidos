package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente.BuscarClientePorIdUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pagamento.GerarQrCodePagamentoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.GerarNumeroPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.ObterProdutosPorIdsUseCase
import br.com.fiap.gerenciamentopedidos.domain.models.*
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Cpf
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Email
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.OffsetDateTime

@ExtendWith(MockKExtension::class)
class CadastrarPedidoUseCaseImplTest {

    @InjectMockKs
    lateinit var cadastrarUseCaseImpl: CadastrarPedidoUseCaseImpl

    @MockK
    lateinit var pedidoRepository: PedidoRepository

    @MockK
    lateinit var buscarClientePorIdUseCase: BuscarClientePorIdUseCase

    @MockK
    lateinit var gerarNumeroPedidoUseCase: GerarNumeroPedidoUseCase

    @MockK
    lateinit var obterProdutosPorIdsUseCase: ObterProdutosPorIdsUseCase

    @MockK
    lateinit var gerarQrCodePagamentoUseCase: GerarQrCodePagamentoUseCase

    @Test
    fun `deve cadastrar um pedido com sucesso`() {
        // Arrange
        val pedido = criarPedido()
        val clienteId = 10L
        val itens = listOf(criarItem())

        every { obterProdutosPorIdsUseCase.executar(any<List<Long>>()) } returns pedido.items.map { it.produto!! }
        every { gerarNumeroPedidoUseCase.executar() } returns "1"
        every { gerarQrCodePagamentoUseCase.executar(any()) } returns pedido.pagamento!!
        every { buscarClientePorIdUseCase.executar(any()) } returns pedido.cliente!!
        every { pedidoRepository.salvar(any()) } returns pedido

        // Act
        val result = cadastrarUseCaseImpl.executar(clienteId, itens)

        // Assert
        val produtosResult = result.items.toList()

        assertEquals("1", result.numero)
        assertEquals(1, produtosResult.size)
        assertEquals(1, produtosResult[0].quantidade)
        assertEquals("Sem mostarda", produtosResult[0].comentario)
        assertEquals(BigDecimal(10), produtosResult[0].valorPago)
        assertEquals(10L, result.tempoEsperaMinutos)
        assertEquals(PedidoStatus.PENDENTE, result.status)
        assertEquals(1, result.cliente?.id)

        verify(exactly = 1) { pedidoRepository.salvar(any()) }
    }

    private fun criarPedido(): Pedido {
        val pedido = Pedido("1")
        pedido.gerarQrCodePagamento(Pagamento(1, OffsetDateTime.now(), PagamentoStatus.APROVADO, "", BigDecimal(10)))
        pedido.atribuirCliente(Cliente(1, Cpf("22233388878"), "Derick Silva", Email("dsilva@gmail.com")))
        pedido.adicionarItem(criarItem())
        return pedido
    }

    private fun criarItem() = Item(
        id = 1,
        quantidade = 1,
        comentario = "Sem mostarda",
        valorPago = BigDecimal(10),
        produto = criarProduto()
    )

    private fun criarProduto() = Produto(
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
}

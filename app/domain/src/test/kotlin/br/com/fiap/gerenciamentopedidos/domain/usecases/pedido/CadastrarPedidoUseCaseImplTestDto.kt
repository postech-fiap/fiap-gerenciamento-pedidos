package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.dtos.ItemPagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.PagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoPagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.PagamentoGateway
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.GerarNumeroPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.ObterProdutosPorIdsUseCase
import br.com.fiap.gerenciamentopedidos.domain.models.Imagem
import br.com.fiap.gerenciamentopedidos.domain.models.Item
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.OffsetDateTime

@ExtendWith(MockKExtension::class)
class CadastrarPedidoUseCaseImplTestDto {

    @InjectMockKs
    lateinit var cadastrarUseCaseImpl: CadastrarPedidoUseCaseImpl

    @MockK
    lateinit var pedidoRepository: PedidoRepository

    @MockK
    lateinit var gerarNumeroPedidoUseCase: GerarNumeroPedidoUseCase

    @MockK
    lateinit var obterProdutosPorIdsUseCase: ObterProdutosPorIdsUseCase

    @MockK
    lateinit var pagamentoGateway: PagamentoGateway

    @Test
    fun `deve cadastrar um pedido com sucesso`() {
        // Arrange
        val pedido = criarPedido()
        val clienteId = "10"
        val itens = listOf(criarItem())

        every { obterProdutosPorIdsUseCase.executar(any<List<Long>>()) } returns pedido.items.map { it.produto!! }
        every { gerarNumeroPedidoUseCase.executar() } returns "1"
        every { pedidoRepository.salvar(any()) } returns pedido
        every { pagamentoGateway.criar(any()) } returns criarPagamento()

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
        assertEquals("1", result.clienteId)

        verify(exactly = 1) { pedidoRepository.salvar(any()) }
    }

    @Test
    fun `deve propagar erro ao cadastrar um pedido - produto nao existente`() {
        // Arrange
        val errorMessage = "Produto 1 não encontrado ou indisponível"
        val pedido = criarPedido()
        val clienteId = "10"
        val itens = listOf(criarItem())

        every { obterProdutosPorIdsUseCase.executar(any<List<Long>>()) } returns emptyList()
        every { gerarNumeroPedidoUseCase.executar() } returns "1"
        every { pedidoRepository.salvar(any()) } returns pedido

        // Act
        val exception =
            Assertions.assertThrows(Exception::class.java) { cadastrarUseCaseImpl.executar(clienteId, itens) }

        // Assert
        assertEquals(errorMessage, exception.message)
    }

    @Test
    fun `deve propagar erro ao cadastrar um pedido - produto indisponivel`() {
        // Arrange
        val errorMessage = "Produto 1 não encontrado ou indisponível"
        val pedido = criarPedido()
        val clienteId = "10"
        val itens = listOf(criarItem())

        every { obterProdutosPorIdsUseCase.executar(any<List<Long>>()) } returns listOf(criarProdutoIndisponivel(1))
        every { gerarNumeroPedidoUseCase.executar() } returns "1"
        every { pedidoRepository.salvar(any()) } returns pedido

        // Act
        val exception =
            Assertions.assertThrows(Exception::class.java) { cadastrarUseCaseImpl.executar(clienteId, itens) }

        // Assert
        assertEquals(errorMessage, exception.message)
    }

    private fun criarPedido(): Pedido {
        val pedido = Pedido(numero = "1", clienteId = "1")
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

    private fun criarProdutoIndisponivel(id: Long) = Produto(
        id = id,
        nome = "Produto 1",
        descricao = "descricao",
        categoria = Categoria.BEBIDA,
        valor = BigDecimal(10),
        tempoPreparo = 10,
        disponivel = false,
        excluido = false,
        imagem = Imagem(1, "/caminho.jpg")
    )

    private fun criarPagamento() = PagamentoDto(
        id = "1",
        referenciaPedido = "1",
        numeroPedido = "1",
        dataHora = OffsetDateTime.now(),
        valorTotal = BigDecimal(1),
        status = PagamentoStatus.PENDENTE,
        qrCode = "qrCode",
        items = listOf(
            ItemPagamentoDto(
                quantidade = 1,
                valorPago = BigDecimal(1),
                produto = ProdutoPagamentoDto(
                    id = 1,
                    nome = "nome",
                    descricao = "descricao",
                    categoria = Categoria.BEBIDA.name,
                    valor = BigDecimal(1),
                )
            )
        )
    )
}

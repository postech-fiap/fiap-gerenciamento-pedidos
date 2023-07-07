package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoProdutoResponse
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
import java.util.stream.Collectors

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
    fun `deve cadastrar um pedido`() {
        val produtos = listOf(
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
        )
        val cliente = Cliente(1, Cpf("22233388878"), "Derick Silva", Email("dsilva@gmail.com"))
        val pagamento = Pagamento(1, OffsetDateTime.now(), PagamentoStatus.APROVADO)
        val pagamentoDto = PagamentoDto.fromModel(pagamento)
        val pedido = Pedido(1, "1", OffsetDateTime.now(), PedidoStatus.PENDENTE, cliente, produtos, pagamento, 10)
        val clienteId = 10L
        val produtoss = listOf(CadastrarPedidoProdutoRequest(1, 10, "Sem mostarda"))
        val request = CadastrarPedidoRequest(clienteId, produtoss)

        val requestProdutos = request.produtos?.map { it.produtoId } ?: emptyList()
        val produtosRetorno = requestProdutos.map {
            ProdutoDto(
                id = it,
                nome = "Nome do Produto",
                descricao = "Descrição do Produto",
                categoria = Categoria.BEBIDA,
                valor = BigDecimal(10),
                tempoPreparo = 10,
                disponivel = true,
                excluido = false,
                imagem = ImagemDto(1, "/caminho.jpg")
            )
        }

        every { produtoRepository.get(requestProdutos) } returns produtosRetorno

        every { pedidoRepository.obterProximoNumeroPedidoDoDia() } returns "1"
        every { pagamentoService.efetuarPagamento(any()) } returns pagamentoDto
        every { clienteRepository.buscarPorId(any()) } returns ClienteDto.fromModel(cliente)
        every { pedidoRepository.salvar(any()) } returns PedidoDto.fromModel(pedido)


        // when
        val result = cadastrarUseCaseImpl.executar(request)

        assertEquals("1", result.numero)
        assertEquals(1, result.produtos?.size)
        assertEquals(1, result.produtos?.get(0)?.quantidade)
        assertEquals("Sem mostarda", result.produtos?.get(0)?.comentario)
        assertEquals(BigDecimal(10), result.produtos?.get(0)?.valorPago)
        assertEquals(10L, result.tempoEsperaMinutos)
        assertEquals(PedidoStatus.PENDENTE, result.status)
        assertEquals(1, result.cliente?.id)

        verify { pedidoRepository.salvar(any()) }
    }

    @Test
    fun `deve criar lista de produtosResponse corretamente`() {
        // Arrange
        val produtos = listOf(
            PedidoProduto(
                id = 1,
                quantidade = 1,
                comentario = "comentario",
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
        )

        val cliente = Cliente(1, Cpf("22233388878"), "Derick Silva", Email("dsilva@gmail.com"))
        val pagamento = Pagamento(1, OffsetDateTime.now(), PagamentoStatus.APROVADO)
        val pedido = Pedido(1, "1", OffsetDateTime.now(), PedidoStatus.PENDENTE, cliente, produtos, pagamento, 10)
        val pedidoProdutoDto = PedidoProdutoDto.fromModel(produtos[0])
        // Act
        val produtosResponse = pedido.produtos?.stream()
            ?.map { PedidoProdutoResponse(pedidoProdutoDto) }
            ?.collect(Collectors.toList())

        assertNotNull(produtosResponse)
        assertEquals(1, produtosResponse?.size)

        val produto1Response = produtosResponse?.get(0)
        assertNotNull(produto1Response)
        assertEquals("Produto 1", produto1Response?.nome)
        assertEquals(BigDecimal(10.0), produto1Response?.valorPago)
        assertEquals(1, produto1Response?.quantidade)
        assertNotNull(produto1Response?.comentario)
    }
}

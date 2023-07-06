package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.domain.dtos.ClienteDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.PagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
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
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*

@ExtendWith(MockKExtension::class)
class CadastrarPedidoUseCaseTest {

    @InjectMockKs
    lateinit var cadastrarUseCaseImpl: CadastrarPedidoUseCaseImpl

    @MockK
    lateinit var pagamentoRepository: PagamentoService

    @MockK
    lateinit var clienteRepository: ClienteRepository

    @MockK
    lateinit var pedidoRepository: PedidoRepository

    @MockK
    lateinit var produtoRepository: ProdutoRepository

    @Test
    fun `deve cadastrar um pedido`() {

        var produtos = listOf(
            PedidoProduto(
                1, 1, "comentario",
                Produto(
                    id = 1,
                    nome= "produto",
                    descricao= "descricao",
                    categoria= Categoria.BEBIDA,
                    valor= BigDecimal(10),
                    tempoPreparo= 10,
                    disponivel= true,
                    excluido = false,
                    imagem= Imagem(1, "nome")
                )))

        var cliente = Cliente(1, Cpf("22233388878"), "Derick Silva", Email("dsilva@gmail.com"))

        var pagamento = Pagamento(1, OffsetDateTime.now(), PagamentoStatus.APROVADO)
        var pagamentoDto = PagamentoDto.fromModel(pagamento)

        var pedido = Pedido(1, "1", OffsetDateTime.now(), PedidoStatus.PENDENTE, cliente, produtos, pagamento, 10)
       // val cliente = Cliente(1, Cpf("12345678910"), "Gabriel", Email("Teste@teste.com.br"))
        //val pagamento = Pagamento(1, OffsetDateTime.now() , PagamentoStatus.APROVADO)
        val clienteId = 10L
        val produtoss = listOf(CadastrarPedidoProdutoRequest(1, 10, "Sem mostarda"))
        val request = CadastrarPedidoRequest(clienteId, produtoss)


        every { pedidoRepository.obterProximoNumeroPedidoDoDia() } returns "1"
        every { pagamentoRepository.efetuarPagamento(any()) } returns pagamentoDto
        every { clienteRepository.buscarPorId(any()) } returns ClienteDto.fromModel(cliente)
        every { pedidoRepository.salvar(any()) } returns PedidoDto.fromModel(pedido)
       // every { produtoRepository.get(1L) } returns Optional.of(ProdutoDto.fromModel(produtoDto))

        // when
        val result = cadastrarUseCaseImpl.executar(request)

        verify { pedidoRepository.salvar(any()) }

    }



}

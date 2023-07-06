package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.requests.BuscarPedidosRequest
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.*
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Cpf
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Email
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.OffsetDateTime
import kotlin.RuntimeException

@ExtendWith(MockKExtension::class)
class BuscarPedidosUseCaseTest {

    @InjectMockKs
    lateinit var buscarUseCaseImpl: BuscarPedidosUseCaseImpl

    @MockK
    lateinit var pedidoPort: PedidoRepository

    @Test
    fun `deve retornar um pedido`() {

        var produtos = listOf(
            PedidoProduto(
                1, 1, "comentario",
                Produto(
                    id = 1,
                    nome= "Produto 1",
                    descricao= "descricao",
                    categoria= Categoria.BEBIDA,
                    valor= BigDecimal(10),
                    tempoPreparo= 10,
                    disponivel= true,
                    excluido = false,
                    imagem= Imagem(1, "nome")
                ), valorPago = BigDecimal(10)))

        var cliente = Cliente(1, Cpf("22233388878"), "Derick Silva", Email("dsilva@gmail.com"))

        var pagamento = Pagamento(1, OffsetDateTime.now(), PagamentoStatus.APROVADO)
        // given

        var pedido = Pedido(1, "1", OffsetDateTime.now(), PedidoStatus.PENDENTE, cliente, produtos, pagamento, 10)
        val pedidoList = listOf(PedidoDto.fromModel(pedido))

        val status = PedidoStatus.PENDENTE
        val dataInicial = OffsetDateTime.now().minusHours(24)
        val dataFinal = OffsetDateTime.now()
        val buscarPedidosRequest = BuscarPedidosRequest(status, dataInicial, dataFinal)

        every { pedidoPort.buscarPedidos(status, dataInicial, dataFinal) } returns pedidoList

        // when
        val result = buscarUseCaseImpl.executar(buscarPedidosRequest)

        // then
        Assertions.assertEquals(pedidoList.map { PedidoResponse(it) }, result)

        verify(exactly = 1) { pedidoPort.buscarPedidos(status, dataInicial, dataFinal) }
    }

    @Test
    fun `deve propagar erro quando ocorrer falha ao buscar pedidos`() {
        // given
        val status = PedidoStatus.PENDENTE
        val dataInicial = OffsetDateTime.now().minusHours(24)
        val dataFinal = OffsetDateTime.now()
        val buscarPedidosRequest = BuscarPedidosRequest(status, dataInicial, dataFinal)

        val errorMessage = "Erro na base de dados"

        every { pedidoPort.buscarPedidos(status, dataInicial, dataFinal) } throws RuntimeException(errorMessage)

        // when-then
        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            buscarUseCaseImpl.executar(buscarPedidosRequest)
        }

        Assertions.assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { pedidoPort.buscarPedidos(status, dataInicial, dataFinal) }
    }

}

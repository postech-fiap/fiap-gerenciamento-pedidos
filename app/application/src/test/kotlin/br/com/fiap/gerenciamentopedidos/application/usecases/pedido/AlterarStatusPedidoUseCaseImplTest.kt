package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BusinessException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.*
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Cpf
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Email
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.OffsetDateTime

@ExtendWith(MockKExtension::class)
class AlterarStatusPedidoUseCaseImplTest {

    @InjectMockKs
    lateinit var useCase: AlterarStatusPedidoUseCaseImpl

    @MockK
    lateinit var pedidoPort: PedidoRepository

    @Test
    fun `deve alterar o status do pedido para EM_PREPARACAO com Sucesso`() {
        //given
        val pedidoId = 1L
        val produtos = listOf(
            Item(
                1, 1, "comentario",
                Produto(
                    id = 1,
                    nome = "Produto 1",
                    descricao = "descricao",
                    categoria = Categoria.BEBIDA,
                    valor = BigDecimal(10),
                    tempoPreparo = 10,
                    disponivel = true,
                    excluido = false,
                    imagem = Imagem(1, "nome.jpg")
                ), valorPago = BigDecimal(10)
            )
        )

        val cliente = Cliente(1, Cpf("22233388878"), "Derick Silva", Email("dsilva@gmail.com"))

        val pagamento = Pagamento(1, OffsetDateTime.now(), PagamentoStatus.APROVADO)
        val pedido = Pedido(1, "1", OffsetDateTime.now(), PedidoStatus.RECEBIDO, cliente, produtos, pagamento, 10)
        val copyPedido = pedido.copy(status = PedidoStatus.EM_PREPARACAO)

        every { pedidoPort.buscarPedidoPorId(pedidoId) } returns pedido
        every { pedidoPort.alterarStatusPedido(copyPedido.status, copyPedido.id!!) } returns Unit

        //when
        useCase.executar(pedidoId, PedidoStatus.EM_PREPARACAO)

        //then
        verify(exactly = 1) { pedidoPort.buscarPedidoPorId(pedidoId) }
        verify(exactly = 1) { pedidoPort.alterarStatusPedido(copyPedido.status, copyPedido.id!!) }
    }


    @Test
    fun `nao deve alterar o status do pedido para RECEBIDO porque o ja possui esse status`() {
        //given
        val pedidoId = 1L

        val produtos = listOf(
            Item(
                1, 1, "comentario",
                Produto(
                    id = 1,
                    nome = "Produto 1",
                    descricao = "descricao",
                    categoria = Categoria.BEBIDA,
                    valor = BigDecimal(10),
                    tempoPreparo = 10,
                    disponivel = true,
                    excluido = false,
                    imagem = Imagem(1, "nome.jpg")
                ), valorPago = BigDecimal(10)
            )
        )

        val cliente = Cliente(1, Cpf("22233388878"), "Derick Silva", Email("dsilva@gmail.com"))

        val pagamento = Pagamento(1, OffsetDateTime.now(), PagamentoStatus.APROVADO)
        val errorMessage = "O status do pedido ja está igual à RECEBIDO"
        val pedido = Pedido(1, "1", OffsetDateTime.now(), PedidoStatus.RECEBIDO, cliente, produtos, pagamento, 10)
        val copyPedido = pedido.copy(status = PedidoStatus.RECEBIDO)

        every { pedidoPort.buscarPedidoPorId(pedidoId) } returns pedido
        every { pedidoPort.alterarStatusPedido(copyPedido.status, copyPedido.id!!) } returns Unit

        //when
        val exception = assertThrows(BusinessException::class.java) {
            useCase.executar(pedidoId, PedidoStatus.RECEBIDO)
        }

        //then
        assertEquals(exception.message, errorMessage)

        verify(exactly = 1) { pedidoPort.buscarPedidoPorId(pedidoId) }
        verify(exactly = 0) { pedidoPort.alterarStatusPedido(copyPedido.status, copyPedido.id!!) }

    }

}
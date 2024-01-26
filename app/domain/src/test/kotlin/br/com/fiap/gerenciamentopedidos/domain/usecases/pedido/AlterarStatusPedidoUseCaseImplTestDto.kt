package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BusinessException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.ProducaoGateway
import br.com.fiap.gerenciamentopedidos.domain.models.Imagem
import br.com.fiap.gerenciamentopedidos.domain.models.Item
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
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
import java.util.*

@ExtendWith(MockKExtension::class)
class AlterarStatusPedidoUseCaseImplTestDto {

    @InjectMockKs
    lateinit var useCase: AlterarStatusPedidoUseCaseImpl

    @MockK
    lateinit var pedidoPort: PedidoRepository

    @MockK
    lateinit var producaoGateway: ProducaoGateway

    @Test
    fun `deve alterar o status do pedido para APROVADO com Sucesso`() {
        val pedido = criarPedido()

        every { pedidoPort.buscarPedidoPorReferencia(any()) } returns Optional.of(pedido)
        every { pedidoPort.update(any()) } returns pedido
        every { producaoGateway.enviar(any()) } returns Unit

        useCase.executar(UUID.randomUUID(), PagamentoStatus.APROVADO)

        verify(exactly = 1) { pedidoPort.buscarPedidoPorReferencia(any()) }
        verify(exactly = 1) { pedidoPort.update(any()) }
    }

    @Test
    fun `deve alterar o status do pedido para REPROVADO com Sucesso`() {
        val pedido = criarPedido()

        every { pedidoPort.buscarPedidoPorReferencia(any()) } returns Optional.of(pedido)
        every { pedidoPort.update(any()) } returns pedido
        every { producaoGateway.enviar(any()) } returns Unit

        useCase.executar(UUID.randomUUID(), PagamentoStatus.REPROVADO)

        verify(exactly = 1) { pedidoPort.buscarPedidoPorReferencia(any()) }
        verify(exactly = 1) { pedidoPort.update(any()) }
    }

    @Test
    fun `nao deve alterar o status do pedido para APROVADO porque o ja possui esse status`() {
        val pedido = criarPedido()
        val errorMessage = "O pedido já possui o status APROVADO"

        every { pedidoPort.buscarPedidoPorReferencia(any()) } returns Optional.of(pedido)
        every { pedidoPort.update(any()) } returns pedido
        every { producaoGateway.enviar(any()) } returns Unit

        pedido.alterarPagamentoStatus(PagamentoStatus.APROVADO)

        val exception = assertThrows(BusinessException::class.java) {
            useCase.executar(UUID.randomUUID(), PagamentoStatus.APROVADO)
        }

        assertEquals(errorMessage, exception.message)
        verify(exactly = 1) { pedidoPort.buscarPedidoPorReferencia(any()) }
        verify(exactly = 0) { pedidoPort.update(any()) }
    }

    @Test
    fun `deve lancar erro ao alterar status do pedido para PENDENTE`() {
        val pedido = criarPedido()
        val errorMessage = "O status do pagamento deve ser APROVADO ou REPROVADO"

        every { pedidoPort.buscarPedidoPorReferencia(any()) } returns Optional.of(pedido)
        every { pedidoPort.update(any()) } returns pedido
        every { producaoGateway.enviar(any()) } returns Unit

        val exception = assertThrows(BusinessException::class.java) {
            useCase.executar(UUID.randomUUID(), PagamentoStatus.PENDENTE)
        }

        assertEquals(errorMessage, exception.message)
        verify(exactly = 1) { pedidoPort.buscarPedidoPorReferencia(any()) }
        verify(exactly = 0) { pedidoPort.update(any()) }
    }

    private fun criarPedido(): Pedido {
        val pedido = Pedido(numero = "1", clienteId = 1)
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
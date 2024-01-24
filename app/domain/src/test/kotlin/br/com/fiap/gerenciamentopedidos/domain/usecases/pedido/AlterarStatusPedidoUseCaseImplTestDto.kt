package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
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

//    @Test
//    fun `deve alterar o status do pedido para APROVADO com Sucesso`() {
//        //given
//        val pedidoId = 1L
//        val pedido = criarPedido()
//        val copyPedido = pedido.copy()
//        copyPedido.alterarStatus(PedidoStatus.APROVADO)
//
//        every { pedidoPort.buscarPedidoPorId(pedidoId) } returns Optional.of(pedido)
//        every { pedidoPort.alterarStatusPedido(copyPedido.status, any()) } returns Unit
//        every { producaoGateway.enviar(any()) } returns PedidoDto(pedidoId, pedido.numero, pedido.dataHora, listOf())
//
//        //when
//        useCase.executar(UUID.randomUUID(), PagamentoStatus.APROVADO)
//
//        //then
//        verify(exactly = 1) { pedidoPort.buscarPedidoPorId(pedidoId) }
//        verify(exactly = 1) { pedidoPort.alterarStatusPedido(copyPedido.status, any()) }
//    }

//    @Test
//    fun `nao deve alterar o status do pedido para RECEBIDO porque o ja possui esse status`() {
//        //given
//        val pedidoId = 1L
//        val errorMessage = "O status do pedido ja está igual à PENDENTE"
//        val pedido = criarPedido()
//        val copyPedido = pedido.copy()
//        copyPedido.alterarStatus(PedidoStatus.PENDENTE)
//
//        every { pedidoPort.buscarPedidoPorId(pedidoId) } returns Optional.of(pedido)
//        every { pedidoPort.alterarStatusPedido(copyPedido.status, any()) } returns Unit
//
//        //when
//        val exception = assertThrows(BusinessException::class.java) {
//            useCase.executar(UUID.randomUUID(), PagamentoStatus.APROVADO)
//        }
//
//        //then
//        assertEquals(exception.message, errorMessage)
//
//        verify(exactly = 1) { pedidoPort.buscarPedidoPorId(pedidoId) }
//        verify(exactly = 0) { pedidoPort.alterarStatusPedido(copyPedido.status, any()) }
//    }

    private fun criarPedido(): Pedido {
        val pedido = Pedido(numero = "1", clienteId = 1)
        pedido.alterarPagamentoStatus(PagamentoStatus.APROVADO)
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
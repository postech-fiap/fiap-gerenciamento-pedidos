package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
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
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal

@ExtendWith(MockKExtension::class)
class BuscarPedidosUseCaseImplTest {

    @InjectMockKs
    lateinit var buscarUseCaseImpl: BuscarPedidosUseCaseImpl

    @MockK
    lateinit var pedidoRepository: PedidoRepository

    @Test
    fun `deve retornar um pedido`() {
        // given
        val pedido = criarPedido()
        val pedidoList = listOf(pedido)

        every { pedidoRepository.buscarPedidos() } returns pedidoList

        // when
        val result = buscarUseCaseImpl.executar()

        // then
        Assertions.assertEquals(pedidoList.map { it.id }, result.map { it.id })

        verify(exactly = 1) { pedidoRepository.buscarPedidos() }
    }

    @Test
    fun `deve propagar erro quando ocorrer falha ao buscar pedidos`() {
        // given
        val errorMessage = "Erro na base de dados"

        every { pedidoRepository.buscarPedidos() } throws RuntimeException(errorMessage)

        // when-then
        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            buscarUseCaseImpl.executar()
        }

        Assertions.assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { pedidoRepository.buscarPedidos() }
    }

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

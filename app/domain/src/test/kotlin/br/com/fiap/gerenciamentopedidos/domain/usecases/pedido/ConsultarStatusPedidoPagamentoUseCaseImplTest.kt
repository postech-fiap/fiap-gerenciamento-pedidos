package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.*
import br.com.fiap.gerenciamentopedidos.domain.usecases.pagamento.ConsultarStatusPagamentoUseCaseImpl
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
class ConsultarStatusPedidoPagamentoUseCaseImplTest {

    @InjectMockKs
    lateinit var useCase: ConsultarStatusPagamentoUseCaseImpl

    @MockK
    lateinit var pedidoPort: PedidoRepository

    @Test
    fun `deve retornar o status do pagamento de um pedido`() {
        //given
        val pedidoId = 1L
        val pedido = criarPedido()
        val copyPedido = pedido.copy()
        copyPedido.alterarStatus(PedidoStatus.EM_PREPARACAO)

        every { pedidoPort.buscarPedidoPorId(pedidoId) } returns pedido

        //when
        val result = useCase.executar(pedidoId)

        //then
        assertEquals(PagamentoStatus.APROVADO, result)
        verify(exactly = 1) { pedidoPort.buscarPedidoPorId(pedidoId) }
    }

    private fun criarPedido(): Pedido {
        val pedido = Pedido("1")
        pedido.gerarQrCodePagamento(Pagamento(1, OffsetDateTime.now(), PagamentoStatus.APROVADO, "", BigDecimal(10)))
        pedido.atribuirCliente(Cliente(1, Cpf("73139333552"), "Derick Silva", Email("dsilva@gmail.com")))
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

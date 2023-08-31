package br.com.fiap.gerenciamentopedidos.domain.usecases.pagamento

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoService
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

@ExtendWith(MockKExtension::class)
class EfetuarPagamentoUseCaseImplTest {

    @MockK
    lateinit var pagamentoService: PagamentoService

    @InjectMockKs
    lateinit var efetuarPagamentoUseCaseImpl: EfetuarPagamentoUseCaseImpl

    @Test
    fun `deve efetuar pagamento com sucesso`() {
        //given
        val pedido = criarPedido()
        val pagamento = Pagamento(1, OffsetDateTime.now(), PagamentoStatus.APROVADO, "", BigDecimal(10))

        every { pagamentoService.gerarPagamento(pedido) } returns pagamento

        //when
        val result = efetuarPagamentoUseCaseImpl.executar(pedido)

        //then
        verify(exactly = 1) { pagamentoService.gerarPagamento(pedido) }

        Assertions.assertNotNull(result)
        Assertions.assertEquals(PagamentoStatus.APROVADO, result.status)
    }

    private fun criarPedido(): Pedido {
        val pedido = Pedido("1")
        pedido.atribuirPagamento(Pagamento(1, OffsetDateTime.now(), PagamentoStatus.APROVADO, "", BigDecimal(10)))
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
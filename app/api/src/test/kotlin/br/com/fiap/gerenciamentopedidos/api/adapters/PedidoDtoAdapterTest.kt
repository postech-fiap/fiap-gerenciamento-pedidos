package br.com.fiap.gerenciamentopedidos.api.adapters

import br.com.fiap.gerenciamentopedidos.api.requests.AlterarStatusPedidoRequest
import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarPedidoProdutoRequest
import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.AlterarStatusPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.models.Imagem
import br.com.fiap.gerenciamentopedidos.domain.models.Item
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.util.*

@ExtendWith(MockKExtension::class)
class PedidoDtoAdapterTest {

    @MockK
    lateinit var cadastrarPedidoUseCase: CadastrarPedidoUseCase

    @MockK
    lateinit var alterarStatusPedidoUseCase: AlterarStatusPedidoUseCase

    @InjectMockKs
    lateinit var adapter: PedidoAdapterImpl

    @Test
    fun `deve cadastrar pedido com sucesso`() {
        every { cadastrarPedidoUseCase.executar(any(), any()) } returns criarPedido()

        val request = CadastrarPedidoRequest(1, listOf(CadastrarPedidoProdutoRequest(1, 1, "Sem mostarda")))

        val response = adapter.cadastrarPedido(request)

        assertNotNull(response)
    }

    @Test
    fun `deve alterar status do pedido com sucesso`() {
        every { alterarStatusPedidoUseCase.executar(any(), any()) } returns Unit

        val request = AlterarStatusPedidoRequest(UUID.randomUUID().toString(), 1, PagamentoStatus.APROVADO.name)

        Assertions.assertDoesNotThrow { adapter.alterarStatusPedido(request) }
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
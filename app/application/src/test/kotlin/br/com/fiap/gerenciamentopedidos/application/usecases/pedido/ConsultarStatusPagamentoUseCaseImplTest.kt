package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.requests.AlterarStatusPedidoRequest
import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BusinessException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Cliente
import br.com.fiap.gerenciamentopedidos.domain.models.Imagem
import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.domain.models.PedidoProduto
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
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
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class ConsultarStatusPagamentoUseCaseImplTest {

    @InjectMockKs
    lateinit var useCase: ConsultarStatusPagamentoUseCaseImpl

    @MockK
    lateinit var pedidoPort: PedidoRepository

    @Test
    fun `deve retornar o status do pagamento`() {
        // given
        val pedidoId = 1L
        val status = PagamentoStatus.APROVADO

        val pedido = Pedido(
            1,
            "1",
            OffsetDateTime.now(),
            PedidoStatus.RECEBIDO,
            Cliente(1, Cpf("22233388878"), "Derick Silva", Email("dsilva@gmail.com")),
            listOf(
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
            ),
            Pagamento(1,
                OffsetDateTime.now(),
                status,
                Random.nextLong().toString(),
                BigDecimal(10)
            ),
            10
        )

        val pedidoDto = PedidoDto.fromModel(pedido)

        every { pedidoPort.buscarPedidoPorId(pedidoId) } returns pedidoDto

        val result = useCase.executar(pedidoId)

        //then
        assertEquals(status, result.status)

    }

}

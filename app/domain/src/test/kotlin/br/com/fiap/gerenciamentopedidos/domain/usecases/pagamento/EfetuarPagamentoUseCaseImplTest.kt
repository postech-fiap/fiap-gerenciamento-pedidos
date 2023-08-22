package br.com.fiap.gerenciamentopedidos.domain.usecases.pagamento

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PagamentoService
import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
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
        val numeroPedido = "123456789"
        val pagamento = Pagamento(1, OffsetDateTime.now(), PagamentoStatus.APROVADO)

        every { pagamentoService.efetuarPagamento(numeroPedido) } returns pagamento

        //when
        val result = efetuarPagamentoUseCaseImpl.executar(numeroPedido)

        //then
        verify(exactly = 1) { pagamentoService.efetuarPagamento(numeroPedido) }

        Assertions.assertNotNull(result)
        Assertions.assertEquals(PagamentoStatus.APROVADO, result.status)
    }
}
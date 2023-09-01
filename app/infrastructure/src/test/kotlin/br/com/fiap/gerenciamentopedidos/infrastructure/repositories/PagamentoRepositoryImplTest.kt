package br.com.fiap.gerenciamentopedidos.infrastructure.repositories

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.PagamentoJpaRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class PagamentoRepositoryImplTest {

    @MockK
    lateinit var pagamentoJpaRepository: PagamentoJpaRepository

    @InjectMockKs
    lateinit var pagamentoRepository: PagamentoRepositoryImpl

    @Test
    fun `deve atualizar o status do pagamento com sucesso`() {
        // given
        val pagamentoStatus = PagamentoStatus.APROVADO
        val pedidoId = Random.nextLong()

        every {
            pagamentoJpaRepository.updateStatusByPedidoId(pagamentoStatus, pedidoId)
        } returns Unit

        // when
        pagamentoRepository.alterarStatusPagamento(pagamentoStatus, pedidoId)

        // then
        verify(exactly = 1) {
            pagamentoJpaRepository.updateStatusByPedidoId(pagamentoStatus, pedidoId)
        }
    }

    @Test
    fun `deve propagar erro ao atualizar o pagamento`() {
        // given
        val pagamentoStatus = PagamentoStatus.APROVADO
        val pedidoId = Random.nextLong()
        val errorMessage = "Erro ao realizar a atualização do status do pagamento. Detalhes: Error"

        every {
            pagamentoJpaRepository.updateStatusByPedidoId(pagamentoStatus, pedidoId)
        } throws Exception("Error")

        // when-then
        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) {
            pagamentoRepository.alterarStatusPagamento(pagamentoStatus, pedidoId)
        }

        // then
        Assertions.assertEquals(errorMessage, exception.message)

        verify(exactly = 1) {
            pagamentoJpaRepository.updateStatusByPedidoId(pagamentoStatus, pedidoId)
        }
    }
}

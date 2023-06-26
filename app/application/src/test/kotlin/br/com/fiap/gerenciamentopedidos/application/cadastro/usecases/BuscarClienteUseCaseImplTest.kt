package br.com.fiap.gerenciamentopedidos.application.cadastro.usecases

import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.cadastro.interfaces.repositories.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.ClienteDomain
import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.Cpf
import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.Email
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*
import kotlin.random.Random

private const val CPF = "111.111.111-11"
private const val EMAIL = "test@test.com"

@ExtendWith(MockKExtension::class)
class BuscarClienteUseCaseImplTest {

    @InjectMockKs
    lateinit var buscarClientePorCpfUseCaseImpl: BuscarClientePorCpfUseCaseImpl

    @MockK
    lateinit var clienteRepository: ClienteRepository

    @Test
    fun `deve retornar um cliente por cpf quando existir`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val clienteDomain = ClienteDomain(cpf = cpf, nome = nome, email = email)

        every { clienteRepository.buscarPorCpf(clienteDomain.cpf.numero) } returns Optional.of(clienteDomain)

        //when
        val result = buscarClientePorCpfUseCaseImpl.executar(clienteDomain.cpf.numero)

        //then
        Assertions.assertEquals(clienteDomain, result)

        verify(exactly = 1) { clienteRepository.buscarPorCpf(clienteDomain.cpf.numero) }
    }

    @Test
    fun `deve propagar um erro não encontrado quando o cliente não existir`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val clienteDomain = ClienteDomain(cpf = cpf, nome = nome, email = email)

        every { clienteRepository.buscarPorCpf(clienteDomain.cpf.numero) } returns Optional.empty()

        //when-then
        val exception = Assertions.assertThrows(RecursoNaoEncontradoException::class.java) {
            buscarClientePorCpfUseCaseImpl.executar(clienteDomain.cpf.numero)
        }

        Assertions.assertEquals("CPF ${cpf.numero} não encontrado", exception.message)

        verify(exactly = 1) { clienteRepository.buscarPorCpf(clienteDomain.cpf.numero) }
    }

    @Test
    fun `deve propagar erro quando ocorrer falha ao buscar o cliente`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val clienteDomain = ClienteDomain(cpf = cpf, nome = nome, email = email)
        val errorMessage = "Erro na base de dados"

        every { clienteRepository.buscarPorCpf(clienteDomain.cpf.numero) } throws BaseDeDadosException(errorMessage)

        //when-then
        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) {
            buscarClientePorCpfUseCaseImpl.executar(clienteDomain.cpf.numero)
        }

        Assertions.assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { clienteRepository.buscarPorCpf(clienteDomain.cpf.numero) }
    }

}
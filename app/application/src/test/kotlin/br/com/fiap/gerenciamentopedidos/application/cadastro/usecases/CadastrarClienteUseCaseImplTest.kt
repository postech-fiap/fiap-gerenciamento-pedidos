package br.com.fiap.gerenciamentopedidos.application.cadastro.usecases

import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.RecursoJaExisteException
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
class CadastrarClienteUseCaseImplTest {

    @InjectMockKs
    lateinit var cadastrarClienteUseCase: CadastrarClienteUseCaseImpl

    @MockK
    lateinit var clienteRepository: ClienteRepository

    @Test
    fun `deve cadastrar um cliente com sucesso quando ele não existir`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val clienteDomain = ClienteDomain(cpf = cpf, nome = nome, email = email)

        every { clienteRepository.buscarPorCpf(clienteDomain.cpf.numero) } returns Optional.empty()
        every { clienteRepository.salvar(clienteDomain) } returns clienteDomain

        //when
        val result = cadastrarClienteUseCase.executar(clienteDomain)

        //then
        Assertions.assertEquals(clienteDomain, result)

        verify(exactly = 1) { clienteRepository.buscarPorCpf(clienteDomain.cpf.numero) }
        verify(exactly = 1) { clienteRepository.salvar(clienteDomain) }
    }

    @Test
    fun `não deve cadastrar um cliente quando ele já existir`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val clienteDomain = ClienteDomain(cpf = cpf, nome = nome, email = email)

        every { clienteRepository.buscarPorCpf(clienteDomain.cpf.numero) } returns Optional.of(clienteDomain)

        //when-then
        val exception = Assertions.assertThrows(RecursoJaExisteException::class.java) {
            cadastrarClienteUseCase.executar(clienteDomain)
        }

        Assertions.assertEquals("CPF ${cpf.numero} já está cadastrado", exception.message)

        verify(exactly = 1) { clienteRepository.buscarPorCpf(clienteDomain.cpf.numero) }
        verify(exactly = 0) { clienteRepository.salvar(clienteDomain) }
    }

    @Test
    fun `deve propagar o erro quando ocorrer falha ao buscar o cliente`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val clienteDomain = ClienteDomain(cpf = cpf, nome = nome, email = email)
        val errorMessage = "Erro na base de dados"

        every { clienteRepository.buscarPorCpf(clienteDomain.cpf.numero) } throws BaseDeDadosException(errorMessage)

        //when-then
        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) {
            cadastrarClienteUseCase.executar(clienteDomain)
        }

        Assertions.assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { clienteRepository.buscarPorCpf(clienteDomain.cpf.numero) }
        verify(exactly = 0) { clienteRepository.salvar(clienteDomain) }
    }

    @Test
    fun `deve propagar o erro quando ocorrer falha ao salvar o cliente`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val clienteDomain = ClienteDomain(cpf = cpf, nome = nome, email = email)
        val errorMessage = "Erro na base de dados"

        every { clienteRepository.buscarPorCpf(clienteDomain.cpf.numero) } returns Optional.empty()
        every { clienteRepository.salvar(clienteDomain) } throws BaseDeDadosException(errorMessage)

        //when-then
        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) {
            cadastrarClienteUseCase.executar(clienteDomain)
        }

        Assertions.assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { clienteRepository.buscarPorCpf(clienteDomain.cpf.numero) }
        verify(exactly = 1) { clienteRepository.salvar(clienteDomain) }
    }

}
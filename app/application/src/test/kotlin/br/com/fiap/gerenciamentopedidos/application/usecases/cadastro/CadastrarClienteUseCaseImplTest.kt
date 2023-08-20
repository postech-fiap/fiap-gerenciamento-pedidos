package br.com.fiap.gerenciamentopedidos.application.usecases.cadastro

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarClienteRequest
import br.com.fiap.gerenciamentopedidos.application.usecases.cliente.CadastrarClienteUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoJaExisteException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ClienteRepository
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
        val request = CadastrarClienteRequest(cpf = cpf.numero, nome = nome, email = email.endereco)
        val cliente = request.toModel()

        every { clienteRepository.buscarPorCpf(request.cpf) } returns Optional.empty()
        every { clienteRepository.salvar(cliente) } returns cliente

        //when
        val result = cadastrarClienteUseCase.executar(cliente)

        //then
        Assertions.assertEquals(cliente.cpf, result.cpf)

        verify(exactly = 1) { clienteRepository.buscarPorCpf(request.cpf) }
        verify(exactly = 1) { clienteRepository.salvar(cliente) }
    }

    @Test
    fun `não deve cadastrar um cliente quando ele já existir`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val request = CadastrarClienteRequest(cpf = cpf.numero, nome = nome, email = email.endereco)
        val cliente = request.toModel()

        every { clienteRepository.buscarPorCpf(request.cpf) } returns Optional.of(cliente)

        //when-then
        val exception = Assertions.assertThrows(RecursoJaExisteException::class.java) {
            cadastrarClienteUseCase.executar(cliente)
        }

        Assertions.assertEquals("CPF $cpf já está cadastrado", exception.message)

        verify(exactly = 1) { clienteRepository.buscarPorCpf(request.cpf) }
        verify(exactly = 0) { clienteRepository.salvar(cliente) }
    }

    @Test
    fun `deve propagar o erro quando ocorrer falha ao buscar o cliente`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val request = CadastrarClienteRequest(cpf = cpf.numero, nome = nome, email = email.endereco)
        val cliente = request.toModel()
        val errorMessage = "Erro na base de dados"

        every { clienteRepository.buscarPorCpf(request.cpf) } throws RuntimeException(errorMessage)

        //when-then
        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            cadastrarClienteUseCase.executar(cliente)
        }

        Assertions.assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { clienteRepository.buscarPorCpf(request.cpf) }
        verify(exactly = 0) { clienteRepository.salvar(cliente) }
    }

    @Test
    fun `deve propagar o erro quando ocorrer falha ao salvar o cliente`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val request = CadastrarClienteRequest(cpf = cpf.numero, nome = nome, email = email.endereco)
        val cliente = request.toModel()
        val errorMessage = "Erro na base de dados"

        every { clienteRepository.buscarPorCpf(request.cpf) } returns Optional.empty()
        every { clienteRepository.salvar(cliente) } throws RuntimeException(errorMessage)

        //when-then
        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            cadastrarClienteUseCase.executar(cliente)
        }

        Assertions.assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { clienteRepository.buscarPorCpf(request.cpf) }
        verify(exactly = 1) { clienteRepository.salvar(cliente) }
    }
}

package br.com.fiap.gerenciamentopedidos.application.usecases.cadastro

import br.com.fiap.gerenciamentopedidos.application.usecases.cliente.CadastrarClienteUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoJaExisteException
import br.com.fiap.gerenciamentopedidos.domain.adapters.ClienteAdapter
import br.com.fiap.gerenciamentopedidos.domain.models.Cliente
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
    lateinit var clienteAdapter: ClienteAdapter

    @Test
    fun `deve cadastrar um cliente com sucesso quando ele não existir`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val clienteDomain = Cliente(cpf = cpf, nome = nome, email = email)

        every { clienteAdapter.buscarPorCpf(clienteDomain.cpf.numero) } returns Optional.empty()
        every { clienteAdapter.salvar(clienteDomain) } returns clienteDomain

        //when
        val result = cadastrarClienteUseCase.executar(clienteDomain)

        //then
        Assertions.assertEquals(clienteDomain, result)

        verify(exactly = 1) { clienteAdapter.buscarPorCpf(clienteDomain.cpf.numero) }
        verify(exactly = 1) { clienteAdapter.salvar(clienteDomain) }
    }

    @Test
    fun `não deve cadastrar um cliente quando ele já existir`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val clienteDomain = Cliente(cpf = cpf, nome = nome, email = email)

        every { clienteAdapter.buscarPorCpf(clienteDomain.cpf.numero) } returns Optional.of(clienteDomain)

        //when-then
        val exception = Assertions.assertThrows(RecursoJaExisteException::class.java) {
            cadastrarClienteUseCase.executar(clienteDomain)
        }

        Assertions.assertEquals("CPF ${cpf.numero} já está cadastrado", exception.message)

        verify(exactly = 1) { clienteAdapter.buscarPorCpf(clienteDomain.cpf.numero) }
        verify(exactly = 0) { clienteAdapter.salvar(clienteDomain) }
    }

    @Test
    fun `deve propagar o erro quando ocorrer falha ao buscar o cliente`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val clienteDomain = Cliente(cpf = cpf, nome = nome, email = email)
        val errorMessage = "Erro na base de dados"

        every { clienteAdapter.buscarPorCpf(clienteDomain.cpf.numero) } throws BaseDeDadosException(errorMessage)

        //when-then
        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) {
            cadastrarClienteUseCase.executar(clienteDomain)
        }

        Assertions.assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { clienteAdapter.buscarPorCpf(clienteDomain.cpf.numero) }
        verify(exactly = 0) { clienteAdapter.salvar(clienteDomain) }
    }

    @Test
    fun `deve propagar o erro quando ocorrer falha ao salvar o cliente`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val clienteDomain = Cliente(cpf = cpf, nome = nome, email = email)
        val errorMessage = "Erro na base de dados"

        every { clienteAdapter.buscarPorCpf(clienteDomain.cpf.numero) } returns Optional.empty()
        every { clienteAdapter.salvar(clienteDomain) } throws BaseDeDadosException(errorMessage)

        //when-then
        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) {
            cadastrarClienteUseCase.executar(clienteDomain)
        }

        Assertions.assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { clienteAdapter.buscarPorCpf(clienteDomain.cpf.numero) }
        verify(exactly = 1) { clienteAdapter.salvar(clienteDomain) }
    }

}
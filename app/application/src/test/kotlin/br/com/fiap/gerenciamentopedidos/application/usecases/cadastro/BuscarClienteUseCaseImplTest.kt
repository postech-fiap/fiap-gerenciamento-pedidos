package br.com.fiap.gerenciamentopedidos.application.usecases.cadastro

import br.com.fiap.gerenciamentopedidos.application.responses.ClienteResponse
import br.com.fiap.gerenciamentopedidos.application.usecases.cliente.BuscarClientePorCpfUseCaseImpl
import br.com.fiap.gerenciamentopedidos.domain.dtos.ClienteDto
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.models.Cliente
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
class BuscarClienteUseCaseImplTest {

    @InjectMockKs
    lateinit var buscarClientePorCpfUseCaseImpl: BuscarClientePorCpfUseCaseImpl

    @MockK
    lateinit var clientePort: ClienteRepository

    @Test
    fun `deve retornar um cliente por cpf quando existir`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val cliente = ClienteDto.fromModel(Cliente(cpf = cpf, nome = nome, email = email))

        every { clientePort.buscarPorCpf(cliente.cpf!!.numero) } returns Optional.of(cliente)

        //when
        val result = buscarClientePorCpfUseCaseImpl.executar(cliente.cpf!!.numero)

        //then
        Assertions.assertEquals(ClienteResponse.fromDomain(cliente), result)

        verify(exactly = 1) { clientePort.buscarPorCpf(cliente.cpf!!.numero) }
    }

    @Test
    fun `deve propagar um erro não encontrado quando o cliente não existir`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val clienteDomain = Cliente(cpf = cpf, nome = nome, email = email)

        every { clientePort.buscarPorCpf(clienteDomain.cpf.numero) } returns Optional.empty()

        //when-then
        val exception = Assertions.assertThrows(RecursoNaoEncontradoException::class.java) {
            buscarClientePorCpfUseCaseImpl.executar(clienteDomain.cpf.numero)
        }

        Assertions.assertEquals("CPF ${cpf.numero} não encontrado", exception.message)

        verify(exactly = 1) { clientePort.buscarPorCpf(clienteDomain.cpf.numero) }
    }

    @Test
    fun `deve propagar erro quando ocorrer falha ao buscar o cliente`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val clienteDomain = Cliente(cpf = cpf, nome = nome, email = email)
        val errorMessage = "Erro na base de dados"

        every { clientePort.buscarPorCpf(clienteDomain.cpf.numero) } throws BaseDeDadosException(errorMessage)

        //when-then
        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) {
            buscarClientePorCpfUseCaseImpl.executar(clienteDomain.cpf.numero)
        }

        Assertions.assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { clientePort.buscarPorCpf(clienteDomain.cpf.numero) }
    }

}
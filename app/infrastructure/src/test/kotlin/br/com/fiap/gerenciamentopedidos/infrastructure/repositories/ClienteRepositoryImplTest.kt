package br.com.fiap.gerenciamentopedidos.infrastructure.repositories

import br.com.fiap.gerenciamentopedidos.domain.dtos.ClienteDto
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.domain.models.Cliente
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Cpf
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Email
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.ClienteEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.ClienteJpaRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*
import kotlin.random.Random

private const val CPF = "111.111.111-11"
private const val EMAIL = "test@test.com"

@ExtendWith(MockKExtension::class)
class ClienteRepositoryImplTest {

    @MockK
    lateinit var clienteJpaRepository: ClienteJpaRepository

    @InjectMockKs
    lateinit var clienteRepository: ClienteRepositoryImpl

    @Test
    fun `deve salvar um cliente com sucesso`() {
        //given
        val cpf = CPF
        val email = EMAIL
        val nome = Random.nextLong().toString()
        val cliente = Cliente(cpf = Cpf(cpf), email = Email(email), nome = nome)
        val clienteDto = ClienteDto.fromModel(cliente)

        every { clienteJpaRepository.save(any()) } returns ClienteEntity.fromDto(clienteDto)

        //when
        val result = clienteRepository.salvar(ClienteDto.fromModel(cliente))

        //then
        assertEquals(clienteDto, result)

        verify(exactly = 1) { clienteJpaRepository.save(any()) }
    }

    @Test
    fun `deve buscar um cliente com sucesso`() {
        //given
        val cpf = CPF
        val email = EMAIL
        val nome = Random.nextLong().toString()
        val cliente = Cliente(cpf = Cpf(cpf), email = Email(email), nome = nome)
        val clienteDto = ClienteDto.fromModel(cliente)
        val cpfSemMascara = Cpf.removeMascara(cpf)

        every { clienteJpaRepository.findByCpf(any()) } returns Optional.of(ClienteEntity.fromDto(clienteDto))

        //when
        val result = clienteRepository.buscarPorCpf(cpf)

        //then
        assertEquals(clienteDto, result.get())

        verify(exactly = 1) { clienteJpaRepository.findByCpf(cpfSemMascara) }
    }


    @Test
    fun `deve propagar BaseDeDadosException quando ocorrer uma falha ao buscar um cliente`() {
        //given
        val cpf = CPF
        val cpfSemMascara = Cpf.removeMascara(cpf)
        val errorMessage = "Erro ao buscar o cliente na base de dados. Detalhes: Error"

        every { clienteJpaRepository.findByCpf(any()) } throws Exception("Error")

        //when-then
        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) {
            clienteRepository.buscarPorCpf(cpf)
        }

        //then
        assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { clienteJpaRepository.findByCpf(cpfSemMascara) }
    }

    @Test
    fun `deve propagar BaseDeDadosException quando ocorrer uma falha ao salvar um cliente`() {
        //given
        val cpf = CPF
        val email = EMAIL
        val nome = Random.nextLong().toString()
        val errorMessage = "Erro ao salvar o cliente na base de dados. Detalhes: Error"
        val clienteDomain = Cliente(cpf = Cpf(cpf), email = Email(email), nome = nome)
        val dto = ClienteDto.fromModel(clienteDomain)
        val clienteEntity = ClienteEntity.fromDto(dto)

        every { clienteJpaRepository.save(any()) } throws Exception("Error")

        //when-then
        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) {
            clienteRepository.salvar(dto)
        }

        //then
        assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { clienteJpaRepository.save(clienteEntity) }
    }
}
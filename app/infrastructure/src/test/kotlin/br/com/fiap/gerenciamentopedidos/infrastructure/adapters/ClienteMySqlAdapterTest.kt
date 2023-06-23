package br.com.fiap.gerenciamentopedidos.infrastructure.adapters

import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.ClienteDomain
import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.Cpf
import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.Email
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.ClienteEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ClienteJpaRepository
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
class ClienteMySqlAdapterTest {

    @MockK
    lateinit var clienteJpaRepository: ClienteJpaRepository

    @InjectMockKs
    lateinit var clienteMySqlAdapter: ClienteMySqlAdapter

    @Test
    fun `deve salvar um cliente com sucesso`() {
        //given
        val cpf = CPF
        val email = EMAIL
        val nome = Random.nextLong().toString()
        val clienteDomain = ClienteDomain(cpf = Cpf(cpf), email = Email(email), nome = nome)
        val clienteEntity = ClienteEntity.fromDomain(clienteDomain)

        every { clienteJpaRepository.save(any()) } returns clienteEntity

        //when
        val result = clienteMySqlAdapter.salvar(clienteDomain)

        //then
        assertEquals(clienteDomain, result)

        verify(exactly = 1) { clienteJpaRepository.save(clienteEntity) }
    }

    @Test
    fun `deve buscar um cliente com sucesso`() {
        //given
        val cpf = CPF
        val email = EMAIL
        val nome = Random.nextLong().toString()
        val clienteDomain = ClienteDomain(cpf = Cpf(cpf), email = Email(email), nome = nome)
        val clienteEntity = ClienteEntity.fromDomain(clienteDomain)
        val cpfSemMascara = Cpf.removeMascara(cpf)

        every { clienteJpaRepository.findByCpf(any()) } returns Optional.of(clienteEntity)

        //when
        val result = clienteMySqlAdapter.buscarPorCpf(cpf)

        //then
        assertEquals(clienteDomain, result.get())

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
            clienteMySqlAdapter.buscarPorCpf(cpf)
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
        val clienteDomain = ClienteDomain(cpf = Cpf(cpf), email = Email(email), nome = nome)
        val clienteEntity = ClienteEntity.fromDomain(clienteDomain)

        every { clienteJpaRepository.save(any()) } throws Exception("Error")

        //when-then
        val exception = Assertions.assertThrows(BaseDeDadosException::class.java) {
            clienteMySqlAdapter.salvar(clienteDomain)
        }

        //then
        assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { clienteJpaRepository.save(clienteEntity) }
    }
}
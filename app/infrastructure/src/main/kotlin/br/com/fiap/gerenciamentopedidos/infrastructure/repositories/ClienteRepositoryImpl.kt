package br.com.fiap.gerenciamentopedidos.infrastructure.repositories

import br.com.fiap.gerenciamentopedidos.domain.dtos.ClienteDto
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Cpf
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.ClienteEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.ClienteJpaRepository
import java.util.*

private const val ERROR_MESSAGE_TO_SAVE = "Erro ao salvar o cliente na base de dados. Detalhes: %s"
private const val ERROR_MESSAGE_TO_FIND = "Erro ao buscar o cliente na base de dados. Detalhes: %s"

class ClienteRepositoryImpl(private val clienteJpaRepository: ClienteJpaRepository) : ClienteRepository {

    override fun salvar(cliente: ClienteDto): ClienteDto {
        var clienteEntity: ClienteEntity? = null

        try {
            clienteEntity = clienteJpaRepository.save(ClienteEntity.fromDto(cliente))
        } catch (ex: Exception) {
            lancaDataBaseException(ex, ERROR_MESSAGE_TO_SAVE)
        }
        return clienteEntity!!.toDto(cliente.cpf!!.numero)
    }

    override fun buscarPorCpf(cpf: String): Optional<ClienteDto> {
        var clienteEntity: Optional<ClienteEntity> = Optional.empty()

        try {
            clienteEntity = clienteJpaRepository.findByCpf(Cpf.removeMascara(cpf))
        } catch (ex: Exception) {
            lancaDataBaseException(ex, ERROR_MESSAGE_TO_FIND)
        }
        return clienteEntity.map { it.toDto(Cpf.adicionaMascara(cpf)) }
    }

    override fun buscarPorId(id: Long): Optional<ClienteDto> {
        try {
            return clienteJpaRepository.findById(id).map { it.cpf?.let { it1 -> it.toDto(it1) } }
        } catch (ex: Exception) {
            lancaDataBaseException(ex, ERROR_MESSAGE_TO_FIND)
        }
        TODO("Not yet implemented")
    }

    private fun lancaDataBaseException(ex: Exception, errorMessage: String) {
        throw BaseDeDadosException(
            String.format(errorMessage, ex.message)
        )
    }
}

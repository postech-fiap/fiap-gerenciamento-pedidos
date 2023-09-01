package br.com.fiap.gerenciamentopedidos.infrastructure.repositories

import br.com.fiap.gerenciamentopedidos.domain.interfaces.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Cliente
import br.com.fiap.gerenciamentopedidos.domain.valueobjects.Cpf
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.ClienteEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.ClienteJpaRepository
import java.util.*

private const val ERROR_MESSAGE_TO_SAVE = "Erro ao salvar o cliente na base de dados. Detalhes: %s"
private const val ERROR_MESSAGE_TO_FIND = "Erro ao buscar o cliente na base de dados. Detalhes: %s"

class ClienteRepositoryImpl(private val clienteJpaRepository: ClienteJpaRepository) : ClienteRepository {

    override fun salvar(cliente: Cliente): Cliente {
        try {
            return clienteJpaRepository.save(ClienteEntity.fromModel(cliente))
                .toModel()
        } catch (ex: Exception) {
            throw obterDataBaseException(ex, ERROR_MESSAGE_TO_SAVE)
        }
    }

    override fun buscarPorCpf(cpf: String): Optional<Cliente> {
        try {
            return clienteJpaRepository.findByCpf(Cpf.removeMascara(cpf)).map { it.toModel() }
        } catch (ex: Exception) {
            throw obterDataBaseException(ex, ERROR_MESSAGE_TO_FIND)
        }
    }

    override fun buscarPorId(id: Long): Optional<Cliente> {
        try {
            return clienteJpaRepository.findById(id).map { it.toModel() }
        } catch (ex: Exception) {
            throw obterDataBaseException(ex, ERROR_MESSAGE_TO_FIND)
        }
    }

    private fun obterDataBaseException(ex: Exception, errorMessage: String) =
        BaseDeDadosException(String.format(errorMessage, ex.message))
}

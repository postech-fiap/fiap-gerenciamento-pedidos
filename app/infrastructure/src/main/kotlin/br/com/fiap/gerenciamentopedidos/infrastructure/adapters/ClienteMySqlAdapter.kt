package br.com.fiap.gerenciamentopedidos.infrastructure.adapters

import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.domain.cadastro.interfaces.repositories.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.ClienteDomain
import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.Cpf
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.ClienteEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ClienteJpaRepository
import java.util.*

private const val ERROR_MESSAGE_TO_SAVE = "Erro ao salvar o cliente na base de dados. Detalhes: %s"
private const val ERROR_MESSAGE_TO_FIND = "Erro ao buscar o cliente na base de dados. Detalhes: %s"

class ClienteMySqlAdapter(val clienteJpaRepository: ClienteJpaRepository) : ClienteRepository {

    override fun salvar(clienteDomain: ClienteDomain): ClienteDomain {
        var clienteEntity: ClienteEntity? = null

        try {
            clienteEntity = clienteJpaRepository.save(ClienteEntity.fromDomain(clienteDomain))
        } catch (ex: Exception) {
            lancaDataBaseException(ex, ERROR_MESSAGE_TO_SAVE)
        }
        return clienteEntity!!
            .toDomain(clienteDomain.cpf.numero)
    }

    override fun buscarPorCpf(cpf: String): Optional<ClienteDomain> {
        var clienteEntity: Optional<ClienteEntity> = Optional.empty()

        try {
            clienteEntity = clienteJpaRepository.findByCpf(Cpf.removeMascara(cpf))
        } catch (ex: Exception) {
            lancaDataBaseException(ex, ERROR_MESSAGE_TO_FIND)
        }

        return clienteEntity
            .map { it.toDomain(Cpf.adicionaMascara(cpf)) }
    }

    private fun lancaDataBaseException(ex: Exception, errorMessage: String) {
        throw BaseDeDadosException(
            String.format(errorMessage, ex.message)
        )
    }

}
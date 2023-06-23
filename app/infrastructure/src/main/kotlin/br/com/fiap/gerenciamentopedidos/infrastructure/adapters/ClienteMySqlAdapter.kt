package br.com.fiap.gerenciamentopedidos.infrastructure.adapters

import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.domain.cadastro.interfaces.repositories.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.ClienteDomain
import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.Cpf
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.ClienteEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ClienteJpaRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.util.*

@Component
@Primary
class ClienteMySqlAdapter(val clienteJpaRepository: ClienteJpaRepository) : ClienteRepository {

    override fun buscarPorCpf(cpf: String): Optional<ClienteDomain> {
        var clienteEntity: Optional<ClienteEntity> = Optional.empty()

        try {
            clienteEntity = clienteJpaRepository.findByCpf(Cpf.removeMascara(cpf))
        } catch (ex: Exception) {
            lancaDataBaseException(ex)
        }

        return clienteEntity
            .map { it.toDomain(Cpf.adicionaMascara(cpf)) }
    }

    private fun lancaDataBaseException(ex: Exception) {
        throw BaseDeDadosException(
            String.format(
                "Erro ao salvar o cliente na base de dados. Detalhes: %s",
                ex.message
            )
        )
    }

}
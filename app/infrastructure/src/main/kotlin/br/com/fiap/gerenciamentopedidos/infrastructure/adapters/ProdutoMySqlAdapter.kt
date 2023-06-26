package br.com.fiap.gerenciamentopedidos.infrastructure.adapters

import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.ProdutoEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ProdutoJpaRepository
import java.util.*

private const val ERROR_MESSAGE_GET_BY_ID = "Erro ao listar produtos por id. Detalhes: %s"
private const val ERROR_MESSAGE_GET_BY_CATEGORIA = "Erro ao listar produtos por categoria. Detalhes: %s"
private const val ERROR_MESSAGE_CREATE = "Erro ao salvar produtos. Detalhes: %s"
private const val ERROR_MESSAGE_UPDATE = "Erro ao atualizar produtos. Detalhes: %s"

class ProdutoMySqlAdapter(private val repository: ProdutoJpaRepository) : ProdutoRepository {
    override fun get(id: Long): Optional<Produto> {
        try {
            return repository.findById(id).map(ProdutoEntity::toDomain)
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_GET_BY_ID, ex.message)
            )
        }
    }

    override fun get(categoria: Categoria): List<Produto> {
        try {
            return repository.findByCategoria(categoria).map { it.toDomain() }.toList()
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_GET_BY_CATEGORIA, ex.message)
            )
        }
    }

    override fun create(produto: Produto): Produto {
        try {
            return repository.save(ProdutoEntity.fromDomain(produto)).toDomain()
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_CREATE, ex.message)
            )
        }
    }

    override fun update(produto: Produto): Produto {
        try {
            return repository.save(ProdutoEntity.fromDomain(produto)).toDomain()
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_UPDATE, ex.message)
            )
        }
    }
}

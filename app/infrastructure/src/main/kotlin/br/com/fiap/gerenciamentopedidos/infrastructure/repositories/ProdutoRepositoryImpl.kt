package br.com.fiap.gerenciamentopedidos.infrastructure.repositories

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.ImagemEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.ProdutoEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa.ProdutoJpaRepository
import java.util.*

private const val ERROR_MESSAGE_GET_BY_ID = "Erro ao listar produtos por id. Detalhes: %s"
private const val ERROR_MESSAGE_GET_BY_CATEGORIA = "Erro ao listar produtos por categoria. Detalhes: %s"
private const val ERROR_MESSAGE_CREATE = "Erro ao salvar produto. Detalhes: %s"
private const val ERROR_MESSAGE_UPDATE = "Erro ao atualizar produto. Detalhes: %s"
private const val ERROR_MESSAGE_DELETE = "Erro ao excluir produto. Detalhes: %s"

class ProdutoRepositoryImpl(private val repository: ProdutoJpaRepository) : ProdutoRepository {
    override fun get(id: Long): Optional<ProdutoDto> {
        try {
            return repository.findByIdAndExcluidoFalse(id).map { it.toDto() }
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_GET_BY_ID, ex.message)
            )
        }
    }

    override fun get(ids: List<Long>): List<ProdutoDto> {
        try {
            return repository.findByIdInAndExcluidoFalseAndDisponivelTrue(ids).map { it.toDto() }
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_GET_BY_ID, ex.message)
            )
        }
    }

    override fun get(categoria: Categoria): List<ProdutoDto> {
        try {
            return repository.findByCategoriaAndExcluidoAndDisponivel(
                categoria,
                excluido = false,
                disponivel = true
            ).map { it.toDto() }.toList()
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_GET_BY_CATEGORIA, ex.message)
            )
        }
    }

    override fun create(produto: ProdutoDto): ProdutoDto {
        try {
            return repository.save(ProdutoEntity.fromDto(produto)).toDto()
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_CREATE, ex.message)
            )
        }
    }

    override fun update(produto: ProdutoDto): ProdutoDto {
        try {
            val entity = repository.findByIdAndExcluidoFalse(produto.id!!)
                .orElseThrow { BaseDeDadosException("Produto não encontrado") }
                .copy(
                    nome = produto.nome,
                    descricao = produto.descricao,
                    categoria = produto.categoria,
                    valor = produto.valor,
                    tempoPreparo = produto.tempoPreparo
                )
            if (entity.imagem == null && produto.imagem != null) {
                entity.imagem = ImagemEntity(caminho = produto.imagem?.caminho, produto = entity)
            } else if (entity.imagem != null && produto.imagem == null) {
                entity.imagem = null
            } else if (entity.imagem?.caminho != produto.imagem?.caminho) {
                entity.imagem?.caminho = produto.imagem?.caminho
            }
            return repository.save(entity).toDto()
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_UPDATE, ex.message)
            )
        }
    }

    override fun remove(id: Long) {
        try {
            repository.deleteById(id)
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_DELETE, ex.message)
            )
        }
    }
}
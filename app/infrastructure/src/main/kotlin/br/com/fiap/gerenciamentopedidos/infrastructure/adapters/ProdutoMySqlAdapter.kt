package br.com.fiap.gerenciamentopedidos.infrastructure.adapters

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ProdutoPort
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.ImagemEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.ProdutoEntity
import br.com.fiap.gerenciamentopedidos.infrastructure.repositories.ProdutoJpaRepository
import java.util.*

private const val ERROR_MESSAGE_GET_BY_ID = "Erro ao listar produtos por id. Detalhes: %s"
private const val ERROR_MESSAGE_GET_BY_CATEGORIA = "Erro ao listar produtos por categoria. Detalhes: %s"
private const val ERROR_MESSAGE_CREATE = "Erro ao salvar produtos. Detalhes: %s"
private const val ERROR_MESSAGE_UPDATE = "Erro ao atualizar produtos. Detalhes: %s"

class ProdutoMySqlAdapter(private val repository: ProdutoJpaRepository) : ProdutoPort {
    override fun get(id: Long): Optional<ProdutoDto> {
        try {
            return repository.findById(id).map(ProdutoEntity::toDto)
        } catch (ex: Exception) {
            throw BaseDeDadosException(
                String.format(ERROR_MESSAGE_GET_BY_ID, ex.message)
            )
        }
    }

    override fun get(categoria: Categoria): List<ProdutoDto> {
        try {
            return repository.findByCategoria(categoria).map { it.toDto() }.toList()
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
            val entity = repository.findById(produto.id!!).orElseThrow().copy(
                nome = produto.nome,
                descricao = produto.descricao,
                categoria = produto.categoria,
                valor = produto.valor,
                tempoPreparo = produto.tempoPreparo,
                disponivel = produto.disponivel,
                excluido = produto.excluido
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
}

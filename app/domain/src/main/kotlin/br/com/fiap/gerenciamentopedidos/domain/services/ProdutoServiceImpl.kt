package br.com.fiap.gerenciamentopedidos.domain.services

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.services.ProdutoService
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

class ProdutoServiceImpl(var repository: ProdutoRepository) : ProdutoService {
    override fun get(id: Long): Produto {
        try {
            return repository.get(id)
        } catch (e: Exception) {
            //TODO: Lançar exceção de negócio
            throw Exception("Não foi possível excluir o produto", e)
        }
    }

    override fun get(categoria: Categoria): List<Produto> {
        try {
            return repository.get(categoria)
        } catch (e: Exception) {
            //TODO: Lançar exceção de negócio
            throw Exception("Não foi possível excluir o produto", e)
        }
    }

    override fun add(produto: Produto): Produto {
        try {
            return repository.save(produto)
        } catch (e: Exception) {
            //TODO: Lançar exceção de negócio
            throw Exception("Não foi possível excluir o produto", e)
        }
    }

    override fun update(produto: Produto): Produto {
        try {
            return repository.update(produto)
        } catch (e: Exception) {
            //TODO: Lançar exceção de negócio
            throw Exception("Não foi possível excluir o produto", e)
        }
    }

    override fun delete(id: Long) {
        try {
            val produto = repository.get(id)
            produto.excluir()
            repository.update(produto)
        } catch (e: Exception) {
            //TODO: Lançar exceção de negócio
            throw Exception("Não foi possível excluir o produto", e)
        }
    }
}
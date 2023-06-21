package br.com.fiap.gerenciamentopedidos.domain.services

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.services.ProdutoService
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

class ProdutoServiceImpl(var repository: ProdutoRepository) : ProdutoService {
    override fun get(id: Int): Produto {
        return repository.get(id)
    }

    override fun get(categoria: Categoria): List<Produto> {
        return repository.get(categoria)
    }

    override fun add(produto: Produto): Produto {
        return repository.add(produto)
    }

    override fun update(produto: Produto): Produto {
        return repository.update(produto)
    }

    override fun delete(id: Int) {
        repository.delete(id)
    }
}
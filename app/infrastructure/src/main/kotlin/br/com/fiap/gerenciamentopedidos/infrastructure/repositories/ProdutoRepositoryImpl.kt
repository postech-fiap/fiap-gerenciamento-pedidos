package br.com.fiap.gerenciamentopedidos.infrastructure.repositories

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.ProdutoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Produto

class ProdutoRepositoryImpl : ProdutoRepository {
    override fun get(id: Int): Produto {
        TODO("Not yet implemented")
    }

    override fun get(categoria: Categoria): List<Produto> {
        TODO("Not yet implemented")
    }

    override fun add(produto: Produto): Produto {
        TODO("Not yet implemented")
    }

    override fun update(produto: Produto): Produto {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int) {
        TODO("Not yet implemented")
    }
}
package br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.ProdutoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface ProdutoJpaRepository : JpaRepository<ProdutoEntity, Long> {
    fun findByCategoria(categoria: Categoria): List<ProdutoEntity>
}

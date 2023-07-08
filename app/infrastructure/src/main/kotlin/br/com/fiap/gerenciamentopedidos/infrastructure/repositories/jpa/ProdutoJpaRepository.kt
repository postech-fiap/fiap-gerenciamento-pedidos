package br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.ProdutoEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ProdutoJpaRepository : JpaRepository<ProdutoEntity, Long> {
    fun findByCategoriaAndExcluidoAndDisponivel(
        categoria: Categoria,
        excluido: Boolean,
        disponivel: Boolean
    ): List<ProdutoEntity>

    fun findByIdInAndExcluidoFalse(ids: List<Long>): List<ProdutoEntity>

    fun findByIdAndExcluidoFalse(id: Long): Optional<ProdutoEntity>
}

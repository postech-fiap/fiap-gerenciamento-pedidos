package br.com.fiap.gerenciamentopedidos.infrastructure.entities

import br.com.fiap.gerenciamentopedidos.domain.dtos.ProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import jakarta.persistence.*

@Entity
@Table(name = "produto")
data class ProdutoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "nome", nullable = false)
    val nome: String? = null,

    @Column(name = "descricao", nullable = true)
    val descricao: String? = null,

    @Column(name = "categoria", nullable = false)
    @Enumerated(EnumType.STRING)
    val categoria: Categoria? = null,

    @Column(name = "valor", nullable = false)
    val valor: Double? = null,

    @Column(name = "tempo_preparo_minutos", nullable = false)
    val tempoPreparo: Long? = null,

    @Column(name = "disponivel", nullable = false)
    val disponivel: Boolean? = null,

    @Column(name = "excluido", nullable = false)
    val excluido: Boolean? = null,

    @OneToOne(mappedBy = "produto", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var imagem: ImagemEntity? = null
) {
    companion object {
        fun fromDto(produto: ProdutoDto): ProdutoEntity {
            val entity = ProdutoEntity(
                produto.id,
                produto.nome,
                produto.descricao,
                produto.categoria,
                produto.valor,
                produto.tempoPreparo,
                produto.disponivel,
                produto.excluido
            )
            entity.imagem = produto.imagem?.let { ImagemEntity(produto.imagem?.id, produto.imagem?.caminho, entity) }
            return entity
        }
    }

    fun toDto() =
        ProdutoDto(id, nome, descricao, categoria, valor!!, tempoPreparo!!, disponivel!!, excluido!!, imagem?.toDto())
}

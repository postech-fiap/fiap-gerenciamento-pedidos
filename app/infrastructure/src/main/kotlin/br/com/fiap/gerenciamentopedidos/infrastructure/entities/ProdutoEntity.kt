package br.com.fiap.gerenciamentopedidos.infrastructure.entities

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
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
        fun fromDomain(produto: Produto): ProdutoEntity {
            val entity = ProdutoEntity(
                id = produto.id,
                nome = produto.nome,
                descricao = produto.descricao,
                categoria = produto.categoria,
                valor = produto.valor,
                tempoPreparo = produto.tempoPreparo,
                disponivel = produto.disponivel,
                excluido = produto.excluido
            )

            entity.imagem = produto.imagem?.let {
                ImagemEntity(
                    id = produto.imagem?.id,
                    caminho = produto.imagem?.caminho,
                    produto = entity
                )
            }

            return entity
        }
    }

    fun toDomain() =
        Produto(id, nome, descricao, categoria, valor!!, tempoPreparo!!, disponivel!!, excluido!!, imagem?.toDomain())
}

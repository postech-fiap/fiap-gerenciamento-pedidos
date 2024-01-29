package br.com.fiap.gerenciamentopedidos.infrastructure.entities

import br.com.fiap.gerenciamentopedidos.domain.models.Imagem
import jakarta.persistence.*

@Entity
@Table(name = "imagem")
data class ImagemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "caminho", nullable = false, length = 100)
    var caminho: String? = null,

    @OneToOne(fetch = FetchType.LAZY)
    val produto: ProdutoEntity? = null
) {
    fun toModel() = Imagem(id, caminho)
}
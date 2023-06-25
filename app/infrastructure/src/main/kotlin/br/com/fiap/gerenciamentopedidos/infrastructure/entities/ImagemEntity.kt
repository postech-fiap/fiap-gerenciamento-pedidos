package br.com.fiap.gerenciamentopedidos.infrastructure.entities

import jakarta.persistence.*

@Entity
@Table(name = "imagem")
class ImagemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "caminho", nullable = false)
    val caminho: String? = null,

    @OneToOne(fetch = FetchType.LAZY)
    val produto: ProdutoEntity? = null
)
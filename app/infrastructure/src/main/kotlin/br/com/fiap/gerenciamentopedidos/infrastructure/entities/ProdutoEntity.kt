package br.com.fiap.gerenciamentopedidos.infrastructure.entities

import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import jakarta.persistence.*

class ProdutoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    @Column(name = "nome", nullable = false)
    val nome: String,
    @Column(name = "descricao", nullable = true)
    val descricao: String,
    @Column(name = "categoria", nullable = false)
    val categoria: Categoria,
    @Column(name = "valor", nullable = false)
    val valor: Double,
    @Column(name = "tempo_preparo_minutos", nullable = false)
    val tempoPreparo: Int,
    @Column(name = "disponivel", nullable = false)
    val disponivel: Boolean,
    @Column(name = "excluido", nullable = false)
    val excluido: Boolean,
    @OneToOne(mappedBy = "produto", cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
    val imagemEntity: ImagemEntity
) {
}
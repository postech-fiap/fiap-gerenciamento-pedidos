package br.com.fiap.gerenciamentopedidos.domain.ports.drivings.produto

import br.com.fiap.gerenciamentopedidos.domain.dtos.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria

interface ListarProdutosPorCategoria {
    fun executar(categoria: Categoria): List<ProdutoResponse>
}

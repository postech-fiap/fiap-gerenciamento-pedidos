package br.com.fiap.gerenciamentopedidos.application.interfaces

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria

interface ProdutoController {
    fun cadastrarProduto(request: CadastrarProdutoRequest): ProdutoResponse

    fun editarProduto(request: EditarProdutoRequest): ProdutoResponse

    fun alterarDisponibilidadeProduto(id: Long, disponivel: Boolean): ProdutoResponse

    fun listarProdutosPorCategoria(categoria: Categoria): List<ProdutoResponse>

    fun obterProdutoPorId(id: Long): ProdutoResponse

    fun removerProdutoPorId(id: Long)
}

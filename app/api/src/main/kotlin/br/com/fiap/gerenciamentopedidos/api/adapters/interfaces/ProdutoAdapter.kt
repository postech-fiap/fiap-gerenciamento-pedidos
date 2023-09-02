package br.com.fiap.gerenciamentopedidos.api.adapters.interfaces

import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.api.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria

interface ProdutoAdapter {
    fun cadastrarProduto(request: CadastrarProdutoRequest): ProdutoResponse

    fun editarProduto(request: EditarProdutoRequest): ProdutoResponse

    fun alterarDisponibilidadeProduto(id: Long, disponivel: Boolean): ProdutoResponse

    fun listarProdutosPorCategoria(categoria: Categoria): List<ProdutoResponse>

    fun obterProdutoPorId(id: Long): ProdutoResponse

    fun removerProdutoPorId(id: Long)
}

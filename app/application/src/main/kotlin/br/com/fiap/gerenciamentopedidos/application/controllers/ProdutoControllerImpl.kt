package br.com.fiap.gerenciamentopedidos.application.controllers

import br.com.fiap.gerenciamentopedidos.application.interfaces.ProdutoController
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.requests.EditarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.domain.enums.Categoria
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.produto.*

class ProdutoControllerImpl(
    private val cadastrarProdutoUseCase: CadastrarProdutoUseCase,
    private val editarProdutoUseCase: EditarProdutoUseCase,
    private val listarProdutosPorCategoriaUseCase: ListarProdutosPorCategoriaUseCase,
    private val obterProdutoPorIdUseCase: ObterProdutoPorIdUseCase,
    private val removerProdutoPorIdUseCase: RemoverProdutoPorIdUseCase,
    private val alterarDisponibilidadeProdutoUseCase: AlterarDisponibilidadeProdutoUseCase
) : ProdutoController {
    override fun cadastrarProduto(request: CadastrarProdutoRequest) =
        ProdutoResponse(cadastrarProdutoUseCase.executar(request.toModel()))

    override fun editarProduto(request: EditarProdutoRequest) =
        ProdutoResponse(editarProdutoUseCase.executar(request.toModel()))

    override fun alterarDisponibilidadeProduto(id: Long, disponivel: Boolean) =
        ProdutoResponse(alterarDisponibilidadeProdutoUseCase.executar(id, disponivel))

    override fun listarProdutosPorCategoria(categoria: Categoria) =
        listarProdutosPorCategoriaUseCase.executar(categoria)
            .map { ProdutoResponse(it) }

    override fun obterProdutoPorId(id: Long) = ProdutoResponse(obterProdutoPorIdUseCase.executar(id))

    override fun removerProdutoPorId(id: Long) = removerProdutoPorIdUseCase.executar(id)
}

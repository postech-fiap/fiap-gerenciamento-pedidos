package br.com.fiap.gerenciamentopedidos.infrastructure.gateways

import br.com.fiap.gerenciamentopedidos.domain.dtos.PagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.PagamentoGateway
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.IntegracaoAPIException
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate

private const val ERROR_MESSAGE = "Erro de integração para gerar o pagamento. Detalhes: %s"

class PagamentoGatewayImpl(
    private val url: String,
    private val restTemplate: RestTemplate
) : PagamentoGateway {
    override fun criar(pagamento: PagamentoDto): PagamentoDto {
        try {
            val response = restTemplate.postForEntity("${url}/v1/pagamentos/criar", pagamento, PagamentoDto::class.java)

            if (response.statusCode != HttpStatus.CREATED)
                throw RuntimeException("[status_code: ${response.statusCode}")

            return response.body!!
        } catch (ex: Exception) {
            throw IntegracaoAPIException(String.format(ERROR_MESSAGE, ex.message))
        }
    }
}
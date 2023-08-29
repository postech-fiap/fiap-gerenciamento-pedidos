package br.com.fiap.gerenciamentopedidos.infrastructure.gateways

import br.com.fiap.gerenciamentopedidos.domain.dtos.MercadoPagoResponsePagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways.BuscarPagamentoPorIdGateway
import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.DetalhePagamento
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.IntegracaoAPIException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

private const val HEADER_NAME_AUTH = "Authorization"
private const val ERROR_MESSAGE = "Erro de integração para buscar o pagamento. Detalhes: %s"

class BuscarPagamentoPorIdHttpGatewayImpl(
    private val restTemplate: RestTemplate,
    private val mercadoPagoApiPagamento: String,
    private val mercadoPagoToken: String
) : BuscarPagamentoPorIdGateway {

    override fun executar(id: String): DetalhePagamento {
        val url = UriComponentsBuilder.fromUriString(mercadoPagoApiPagamento)
            .buildAndExpand(id)
            .toUriString()

        val entity = HttpEntity<Void>(buildHeaders())

        try {
            val response = restTemplate.exchange(url, HttpMethod.GET, entity, MercadoPagoResponsePagamentoDto::class.java)

            if (response.statusCode != HttpStatus.OK) {
                throw IntegracaoAPIException(
                    String.format(
                        ERROR_MESSAGE,
                        "[status_code: " + "${response.statusCode}"
                    )
                )
            }

            requireNotNull(response.body) { "A resposta não deve ser nula " }

            return response.body!!.toModel()

        } catch (ex: IntegracaoAPIException) {
            throw ex
        } catch (ex: Exception) {
            throw IntegracaoAPIException(String.format(ERROR_MESSAGE, ex.message))
        }
    }

    private fun buildHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers[HEADER_NAME_AUTH] = mercadoPagoToken
        return headers
    }
}
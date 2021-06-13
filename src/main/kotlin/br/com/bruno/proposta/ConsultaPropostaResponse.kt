package br.com.bruno.proposta

import br.com.bruno.Endereco
import br.com.bruno.PropostaConsultaResponse
import io.micronaut.core.annotation.Introspected


@Introspected
class ConsultaPropostaResponse(response: PropostaConsultaResponse) {

    val idProposta = response.idProposta
    val documento = response.documento
    val nome = response.nome
    val salario = response.salario
    val status = response.status
    val endereco = EnderecoResponse(response.endereco)
    }

class EnderecoResponse(endereco: Endereco) {
    val rua = endereco.rua
    val complemento = endereco.complemento
    val cidade = endereco.cidade
    val estado = endereco.estado
    val cep = endereco.cep
}
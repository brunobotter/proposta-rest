package br.com.bruno.proposta

import br.com.bruno.PropostaConsultaRequest
import br.com.bruno.PropostaServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/proposta")
class PropostaController(private val propostaService: PropostaServiceGrpc.PropostaServiceBlockingStub) {


    @Post
    fun adicionar(@Valid @Body request: NovaProposta)=
        propostaService.salvar(request.paraModeloGrpc()).let {
            HttpResponse.uri("/proposta/${it.id}")
        }.let { HttpResponse.created<Any>(it) }


    @Get("{idProposta}")
    fun consultar(@PathVariable idProposta: Long)
    = propostaService.consultar(PropostaConsultaRequest.newBuilder()
            .setIdProposta(idProposta.toString())
            .build()).let { HttpResponse.ok<Any>(ConsultaPropostaResponse(it)) }
}
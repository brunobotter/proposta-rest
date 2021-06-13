package br.com.bruno.bloqueio

import br.com.bruno.BloqueioServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated

@Validated
@Controller("/bloqueio")
class BloqueioController(private val bloqueioService: BloqueioServiceGrpc.BloqueioServiceBlockingStub) {


    @Post("/{idCartao}")
    fun bloquear(
        @PathVariable idCartao: String,
        @Body request: BloqueioCartaoRequest
    ) = bloqueioService.bloquear(request.paraModelGrpc(idCartao)).let {
        HttpResponse.ok<Any>()
    }


}
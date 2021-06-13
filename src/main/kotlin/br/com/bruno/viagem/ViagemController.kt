package br.com.bruno.viagem

import br.com.bruno.ViagemServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated


@Validated
@Controller("/viagem")
class ViagemController(private val viagemService: ViagemServiceGrpc.ViagemServiceBlockingStub) {

    @Post("/{idCartao}")
    fun adicionar(
        @PathVariable idCartao: String,
        @Body request: ViagemCartaoRequest
    ) = viagemService.avisoViagem(request.paraModelGrpc(idCartao)).let {
        HttpResponse.ok<Any>()
    }

}
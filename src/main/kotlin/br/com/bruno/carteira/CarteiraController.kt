package br.com.bruno.carteira

import br.com.bruno.CarteiraServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated

@Validated
@Controller("/carteira")
class CarteiraController(private val carteiraService: CarteiraServiceGrpc.CarteiraServiceBlockingStub) {


    @Post("/{idCartao}")
    fun cadastrar(
        @PathVariable idCartao: String,
        @Body request: CarteiraCartaoRequest
    )= carteiraService.adicionar(request.paraModelGrpc(idCartao)).let {
        HttpResponse.ok<Any>()
    }

}
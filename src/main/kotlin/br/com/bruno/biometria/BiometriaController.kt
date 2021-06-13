package br.com.bruno.biometria

import br.com.bruno.BiometriaServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated

@Validated
@Controller("/biometria")
class BiometriaController(private val biometriaService: BiometriaServiceGrpc.BiometriaServiceBlockingStub) {

    @Post("/{idCartao}")
    fun cadastrar(
        @PathVariable idCartao: String,
        @Body request: BiometriaCartaoRequest
    ) = biometriaService.salvar(request.paraModelGrpc(idCartao)).let {
        HttpResponse.uri("/api/v1/biometria/${it.id}")
    }.let{
        HttpResponse.created<Any>(it)
    }




}
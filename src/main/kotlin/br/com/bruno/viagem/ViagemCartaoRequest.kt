package br.com.bruno.viagem

import br.com.bruno.AvisoRequest
import io.micronaut.core.annotation.Introspected
import java.time.LocalDate

@Introspected
class ViagemCartaoRequest(
    val destinoViagem: String,
    val terminoViagem: LocalDate
) {
    fun paraModelGrpc(idCartao: String)= AvisoRequest.newBuilder()
            .setDestino(destinoViagem)
            .setIdCartao(idCartao)
            .setValidoAte(terminoViagem.toString())
            .build()


}

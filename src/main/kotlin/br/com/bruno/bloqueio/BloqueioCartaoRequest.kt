package br.com.bruno.bloqueio

import br.com.bruno.BloqueioRequest
import io.micronaut.core.annotation.Introspected

@Introspected
class BloqueioCartaoRequest(val sistemaResponsavel: String) {

    fun paraModelGrpc(idCartao: String)= BloqueioRequest.newBuilder()
            .setIdCartao(idCartao)
            .setSistemaResponsavel(sistemaResponsavel)
            .build()


}

package br.com.bruno.carteira

import br.com.bruno.CarteiraRequest
import br.com.bruno.IdentificadorCarteira
import io.micronaut.core.annotation.Introspected


@Introspected
class CarteiraCartaoRequest(val email: String, val identificadorCarteira: IdentificadorCarteiraRequest) {

    fun paraModelGrpc(idCartao: String)= CarteiraRequest.newBuilder()
            .setEmail(email)
            .setIdCartao(idCartao)
            .setCarteira(identificadorCarteira.atributo)
            .build()


}

enum class IdentificadorCarteiraRequest(val atributo: IdentificadorCarteira){
    PAYPAL(IdentificadorCarteira.PAYPAL),
    SAMSUNG_PLAY(IdentificadorCarteira.SAMSUNG_PLAY)
}

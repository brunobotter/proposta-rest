package br.com.bruno.biometria

import br.com.bruno.BiometriaRequest
import io.micronaut.core.annotation.Introspected

@Introspected
class BiometriaCartaoRequest(val fingerprint: String) {

    fun paraModelGrpc(idCartao: String) = BiometriaRequest.newBuilder()
            .setFingerprint(fingerprint)
            .setIdCartao(idCartao)
            .build()



}

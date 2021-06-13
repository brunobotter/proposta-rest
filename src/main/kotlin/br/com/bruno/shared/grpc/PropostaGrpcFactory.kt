package br.com.bruno.shared.grpc

import br.com.bruno.*
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class PropostaGrpcFactory(@GrpcChannel("manager")val channel: ManagedChannel) {

    @Singleton
    fun proposta() = PropostaServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun biometria() = BiometriaServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun bloqueio() = BloqueioServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun viagem() = ViagemServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun carteira() = CarteiraServiceGrpc.newBlockingStub(channel)
}

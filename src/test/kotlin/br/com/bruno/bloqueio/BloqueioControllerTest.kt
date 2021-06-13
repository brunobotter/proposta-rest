package br.com.bruno.bloqueio

import br.com.bruno.BiometriaResponse
import br.com.bruno.BiometriaServiceGrpc
import br.com.bruno.BloqueioResponse
import br.com.bruno.BloqueioServiceGrpc
import br.com.bruno.biometria.BiometriaCartaoRequest
import br.com.bruno.shared.grpc.PropostaGrpcFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class BloqueioControllerTest{

    @field:Inject
    lateinit var bloqueioStub: BloqueioServiceGrpc.BloqueioServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Factory
    @Replaces(factory = PropostaGrpcFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubMock() = Mockito.mock(BloqueioServiceGrpc.BloqueioServiceBlockingStub::class.java)
    }

    @Test
    internal fun `deve adicionar um bloqueio a um cartao`(){
        val bloqueioGrpc = BloqueioResponse.newBuilder()
            .setStatus("BLOQUEADO")
            .build()
        val sistemaResponsavel = "proposta"
        val idCartao = "5288-5414-1323-9133"
        val novoBloqueio = BloqueioCartaoRequest(sistemaResponsavel)
        BDDMockito.given(bloqueioStub.bloquear(Mockito.any())).willReturn(bloqueioGrpc)

        val request = HttpRequest.POST("/bloqueio/$idCartao", novoBloqueio)
        val response = client.toBlocking().exchange(request, BloqueioCartaoRequest::class.java)
        assertEquals(HttpStatus.OK, response.status)
    }
}
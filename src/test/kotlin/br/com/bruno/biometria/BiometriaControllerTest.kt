package br.com.bruno.biometria

import br.com.bruno.BiometriaResponse
import br.com.bruno.BiometriaServiceGrpc
import br.com.bruno.PropostaServiceGrpc
import br.com.bruno.proposta.NovaProposta
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
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class BiometriaControllerTest{

    @field:Inject
    lateinit var biometriaStub: BiometriaServiceGrpc.BiometriaServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Factory
    @Replaces(factory = PropostaGrpcFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubMock() = Mockito.mock(BiometriaServiceGrpc.BiometriaServiceBlockingStub::class.java)
    }

    @Test
    internal fun `deve adicionar uma biometria a um cartao`(){
        val biometriaGrpc = BiometriaResponse.newBuilder()
            .setId("10")
            .build()
        val fingerprint = "fdfdsfsd"
        val idCartao = "5288-5414-1323-9133"
        val novaBiometria = BiometriaCartaoRequest(fingerprint)
        given(biometriaStub.salvar(Mockito.any())).willReturn(biometriaGrpc)

        val request = HttpRequest.POST("/biometria/$idCartao", novaBiometria)
        val response = client.toBlocking().exchange(request, BiometriaCartaoRequest::class.java)
        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!.contains("10"))
    }
}
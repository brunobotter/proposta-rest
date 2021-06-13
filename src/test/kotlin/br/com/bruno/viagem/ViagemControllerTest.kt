package br.com.bruno.viagem

import br.com.bruno.AvisoResponse
import br.com.bruno.BiometriaResponse
import br.com.bruno.BiometriaServiceGrpc
import br.com.bruno.ViagemServiceGrpc
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
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ViagemControllerTest{

    @field:Inject
lateinit var viagemStub: ViagemServiceGrpc.ViagemServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Factory
    @Replaces(factory = PropostaGrpcFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubMock() = Mockito.mock(ViagemServiceGrpc.ViagemServiceBlockingStub::class.java)
    }

    @Test
    internal fun `deve informar ao cartao a viagem`(){
        val viagemGrpc = AvisoResponse.newBuilder()
            .setResultado("VIAJANDO")
            .build()
        val destinoViagem = "Rio de janeiro"
        val idCartao = "5288-5414-1323-9133"
        val terminoViagem = LocalDate.now()
        val novaViagem = ViagemCartaoRequest(destinoViagem,terminoViagem)
        given(viagemStub.avisoViagem(Mockito.any())).willReturn(viagemGrpc)

        val request = HttpRequest.POST("/viagem/$idCartao", novaViagem)
        val response = client.toBlocking().exchange(request, ViagemCartaoRequest::class.java)
        assertEquals(HttpStatus.OK, response.status)
    }
}

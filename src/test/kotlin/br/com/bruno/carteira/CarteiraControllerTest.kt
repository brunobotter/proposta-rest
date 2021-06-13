package br.com.bruno.carteira

import br.com.bruno.CarteiraResponse
import br.com.bruno.CarteiraServiceGrpc
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
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class CarteiraControllerTest{

    @field:Inject
    lateinit var carteiraStub: CarteiraServiceGrpc.CarteiraServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Factory
    @Replaces(factory = PropostaGrpcFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubMock() = Mockito.mock(CarteiraServiceGrpc.CarteiraServiceBlockingStub::class.java)
    }

    @Test
    internal fun `deve adicionar uma carteira digital ao cartao`(){
        val carteiraGrpc = CarteiraResponse.newBuilder()
            .setId("10")
            .setResultado("PAYPAL")
            .build()
        val email = "bruno_vales@hotmail.com"
        val identificadorCarteira = IdentificadorCarteiraRequest.PAYPAL
        val idCartao = "5288-5414-1323-9133"
        val novaCarteira = CarteiraCartaoRequest(email,identificadorCarteira)
        given(carteiraStub.adicionar(Mockito.any())).willReturn(carteiraGrpc)

        val request = HttpRequest.POST("/carteira/$idCartao", novaCarteira)
        val response = client.toBlocking().exchange(request, CarteiraCartaoRequest::class.java)
        assertEquals(HttpStatus.OK, response.status)
    }
}
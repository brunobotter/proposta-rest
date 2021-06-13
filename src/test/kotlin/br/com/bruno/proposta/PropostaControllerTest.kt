package br.com.bruno.proposta

import br.com.bruno.Endereco
import br.com.bruno.PropostaRequest
import br.com.bruno.PropostaResponse
import br.com.bruno.PropostaServiceGrpc
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
internal class PropostaControllerTest{

    @field:Inject
    lateinit var propostaStub: PropostaServiceGrpc.PropostaServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Factory
    @Replaces(factory = PropostaGrpcFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubMock() = Mockito.mock(PropostaServiceGrpc.PropostaServiceBlockingStub::class.java)
    }

    @Test
    internal fun `deve registrar nova proposta`(){
        val documento = "905.181.950-15"
        val email = "bruno_bruno@hotmail.com"
        val salario = 2000.0
        val nome = "bruno"
        val rua = "doutir nunes"
        val cidade = "rio de janeiro"
        val estado = "rio de janeiro"
        val cep = "21021360"
        val complemento = "casa"
        val endereco = NovoEndereco(rua, cidade, estado, cep, complemento)
        val propostaGrpc = PropostaResponse.newBuilder()
            .setId("50")
            .build()
        given(propostaStub.salvar(Mockito.any())).willReturn(propostaGrpc)
        val novaProposta = NovaProposta(documento, email, nome, endereco, salario)
        val request =HttpRequest.POST("/proposta/", novaProposta)
        val response =client.toBlocking().exchange(request, NovaProposta::class.java)
        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!.contains("50"))
    }
}
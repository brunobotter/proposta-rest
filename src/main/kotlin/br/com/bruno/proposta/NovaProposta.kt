package br.com.bruno.proposta

import br.com.bruno.Endereco
import br.com.bruno.PropostaRequest
import br.com.bruno.shared.validation.CpfOuCnpj
import io.micronaut.core.annotation.Introspected
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive


@Introspected
class NovaProposta(
    @field:CpfOuCnpj val documento: String,
    @field:Email val email: String,
    @field:NotNull @field:NotBlank val nome: String,
    @Valid val endereco: NovoEndereco,
    @field:Positive val salario: Double
) {
    fun paraModeloGrpc()=PropostaRequest.newBuilder()
            .setDocumento(documento)
            .setEmail(email)
            .setNome(nome)
            .setSalario(salario)
            .setEndereco(Endereco.newBuilder()
                .setRua(endereco.rua)
                .setEstado(endereco.estado)
                .setComplemento(endereco.complemento)
                .setCidade(endereco.cidade)
                .setCep(endereco.cep)
                .build())
            .build()


}
@Introspected
data class NovoEndereco(
    @field:NotNull @field:NotBlank val rua: String,
    @field:NotNull @field:NotBlank val cidade: String,
    @field:NotNull @field:NotBlank val complemento: String,
    @field:NotNull @field:NotBlank val estado: String,
    @field:NotNull @field:NotBlank val cep: String){

}
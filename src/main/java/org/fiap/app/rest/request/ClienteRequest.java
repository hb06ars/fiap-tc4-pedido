package org.fiap.app.rest.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRequest {
    private Long id;

    @NotNull(message = "O nome não pode ser nulo. Por favor, forneça um valor.")
    private String nome;

    @NotNull(message = "O cpf não pode ser nulo. Por favor, forneça um valor.")
    private String cpf;

    private LocalDate dataNascimento;
    private String rua;
    private Integer numero;
    private String cep;
    private String cidade;
    private String estado;
    private String complemento;
    private LocalDate dtCriacao;
    private LocalDate dtAtualizacao;

}

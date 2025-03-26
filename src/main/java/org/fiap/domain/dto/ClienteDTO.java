package org.fiap.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fiap.app.rest.request.ClienteRequest;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ClienteDTO {

    private Long id;
    private String nome;
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

    public ClienteDTO(ClienteRequest request) {
        this.id = request.getId();
        this.nome = request.getNome();
        this.cpf = request.getCpf();
        this.dataNascimento = request.getDataNascimento();
        this.rua = request.getRua();
        this.numero = request.getNumero();
        this.cep = request.getCep();
        this.cidade = request.getCidade();
        this.estado = request.getEstado();
        this.complemento = request.getComplemento();
        this.dtCriacao = request.getDtCriacao();
        this.dtAtualizacao = request.getDtAtualizacao();
    }
}

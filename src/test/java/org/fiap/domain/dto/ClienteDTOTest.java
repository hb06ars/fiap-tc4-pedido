package org.fiap.domain.dto;

import org.fiap.app.rest.request.cliente.ClienteRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClienteDTOTest {

    private ClienteRequest request;

    @BeforeEach
    void setUp() {
        // Criando um ClienteRequest de exemplo para a conversão
        request = ClienteRequest.builder()
                .id(1L)
                .nome("João da Silva")
                .cpf("12345678901")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .rua("Rua A")
                .numero(100)
                .cep("12345-678")
                .cidade("São Paulo")
                .estado("SP")
                .complemento("Apt 101")
                .dtCriacao(LocalDate.now())
                .dtAtualizacao(LocalDate.now())
                .build();
    }

    @Test
    void testClienteDTOConstructor() {
        // Convertendo o ClienteRequest em um ClienteDTO
        ClienteDTO dto = new ClienteDTO(request);

        assertNotNull(dto);
        assertEquals(request.getId(), dto.getId());
        assertEquals(request.getNome(), dto.getNome());
        assertEquals(request.getCpf(), dto.getCpf());
        assertEquals(request.getDataNascimento(), dto.getDataNascimento());
        assertEquals(request.getRua(), dto.getRua());
        assertEquals(request.getNumero(), dto.getNumero());
        assertEquals(request.getCep(), dto.getCep());
        assertEquals(request.getCidade(), dto.getCidade());
        assertEquals(request.getEstado(), dto.getEstado());
        assertEquals(request.getComplemento(), dto.getComplemento());
        assertEquals(request.getDtCriacao(), dto.getDtCriacao());
        assertEquals(request.getDtAtualizacao(), dto.getDtAtualizacao());
    }

    @Test
    void testClienteDTOBuilder() {
        // Criando um ClienteDTO com o Builder
        ClienteDTO dto = ClienteDTO.builder()
                .id(1L)
                .nome("João da Silva")
                .cpf("12345678901")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .rua("Rua A")
                .numero(100)
                .cep("12345-678")
                .cidade("São Paulo")
                .estado("SP")
                .complemento("Apt 101")
                .dtCriacao(LocalDate.now())
                .dtAtualizacao(LocalDate.now())
                .build();

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("João da Silva", dto.getNome());
        assertEquals("12345678901", dto.getCpf());
        assertEquals(LocalDate.of(1990, 1, 1), dto.getDataNascimento());
        assertEquals("Rua A", dto.getRua());
        assertEquals(100, dto.getNumero());
        assertEquals("12345-678", dto.getCep());
        assertEquals("São Paulo", dto.getCidade());
        assertEquals("SP", dto.getEstado());
        assertEquals("Apt 101", dto.getComplemento());
        assertEquals(LocalDate.now(), dto.getDtCriacao());
        assertEquals(LocalDate.now(), dto.getDtAtualizacao());
    }

    @Test
    void testClienteDTOEquality() {
        // Verificando a igualdade de dois objetos ClienteDTO
        ClienteDTO dto1 = new ClienteDTO(request);
        ClienteDTO dto2 = new ClienteDTO(request);

        assertEquals(dto1, dto2);
    }

    @Test
    void testClienteDTOHashCode() {
        // Verificando o hashCode de dois objetos ClienteDTO iguais
        ClienteDTO dto1 = new ClienteDTO(request);
        ClienteDTO dto2 = new ClienteDTO(request);

        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testClienteDTOEmptyConstructor() {
        // Testando o construtor padrão sem parâmetros
        ClienteDTO dto = new ClienteDTO();

        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getNome());
        assertNull(dto.getCpf());
        assertNull(dto.getDataNascimento());
        assertNull(dto.getRua());
        assertNull(dto.getNumero());
        assertNull(dto.getCep());
        assertNull(dto.getCidade());
        assertNull(dto.getEstado());
        assertNull(dto.getComplemento());
        assertNull(dto.getDtCriacao());
        assertNull(dto.getDtAtualizacao());
    }
}

package org.fiap.app.rest.request.cliente;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClienteRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidClienteRequest() {
        ClienteRequest cliente = ClienteRequest.builder()
                .id(1L)
                .nome("João da Silva")
                .cpf("12345678901")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .rua("Rua A")
                .numero(100)
                .cep("12345-678")
                .cidade("São Paulo")
                .estado("SP")
                .complemento("Apto 101")
                .dtCriacao(LocalDate.now())
                .dtAtualizacao(LocalDate.now())
                .build();

        Set<ConstraintViolation<ClienteRequest>> violations = validator.validate(cliente);
        assertTrue(violations.isEmpty(), "Não deve haver violações para um cliente válido.");
    }

    @Test
    void testNomeNaoPodeSerNulo() {
        ClienteRequest cliente = ClienteRequest.builder()
                .cpf("12345678901")
                .build();

        Set<ConstraintViolation<ClienteRequest>> violations = validator.validate(cliente);
        assertFalse(violations.isEmpty());
        assertEquals("O nome não pode ser nulo. Por favor, forneça um valor.", violations.iterator().next().getMessage());
    }

    @Test
    void testCpfNaoPodeSerNulo() {
        ClienteRequest cliente = ClienteRequest.builder()
                .nome("Maria Souza")
                .build();

        Set<ConstraintViolation<ClienteRequest>> violations = validator.validate(cliente);
        assertFalse(violations.isEmpty());
        assertEquals("O cpf não pode ser nulo. Por favor, forneça um valor.", violations.iterator().next().getMessage());
    }

    @Test
    void testGettersAndSetters() {
        ClienteRequest cliente = new ClienteRequest();
        cliente.setId(1L);
        cliente.setNome("Carlos Oliveira");
        cliente.setCpf("98765432100");
        cliente.setDataNascimento(LocalDate.of(1985, 5, 20));
        cliente.setRua("Rua B");
        cliente.setNumero(50);
        cliente.setCep("54321-000");
        cliente.setCidade("Rio de Janeiro");
        cliente.setEstado("RJ");
        cliente.setComplemento("Casa 2");
        cliente.setDtCriacao(LocalDate.now());
        cliente.setDtAtualizacao(LocalDate.now());

        assertEquals(1L, cliente.getId());
        assertEquals("Carlos Oliveira", cliente.getNome());
        assertEquals("98765432100", cliente.getCpf());
        assertEquals(LocalDate.of(1985, 5, 20), cliente.getDataNascimento());
        assertEquals("Rua B", cliente.getRua());
        assertEquals(50, cliente.getNumero());
        assertEquals("54321-000", cliente.getCep());
        assertEquals("Rio de Janeiro", cliente.getCidade());
        assertEquals("RJ", cliente.getEstado());
        assertEquals("Casa 2", cliente.getComplemento());
        assertNotNull(cliente.getDtCriacao());
        assertNotNull(cliente.getDtAtualizacao());
    }

    @Test
    void testConstrutores() {
        ClienteRequest cliente1 = new ClienteRequest();
        assertNotNull(cliente1);

        ClienteRequest cliente2 = new ClienteRequest(1L, "Ana Paula", "98765432100",
                LocalDate.of(1992, 3, 15), "Rua C", 200, "67890-123",
                "Belo Horizonte", "MG", "Bloco 3", LocalDate.now(), LocalDate.now());

        assertEquals(1L, cliente2.getId());
        assertEquals("Ana Paula", cliente2.getNome());
        assertEquals("98765432100", cliente2.getCpf());
    }
}

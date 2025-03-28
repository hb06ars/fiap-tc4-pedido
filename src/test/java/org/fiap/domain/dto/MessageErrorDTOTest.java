package org.fiap.domain.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageErrorDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testNoArgsConstructor() {
        MessageErrorDTO messageErrorDTO = new MessageErrorDTO();
        assertNotNull(messageErrorDTO);
        assertNull(messageErrorDTO.getErro());
        assertNull(messageErrorDTO.getDetalhe());
    }

    @Test
    void testAllArgsConstructor() {
        MessageErrorDTO messageErrorDTO = new MessageErrorDTO("Erro 404", "Página não encontrada");
        assertEquals("Erro 404", messageErrorDTO.getErro());
        assertEquals("Página não encontrada", messageErrorDTO.getDetalhe());
    }

    @Test
    void testEqualsAndHashCode() {
        MessageErrorDTO message1 = new MessageErrorDTO("Erro 404", "Página não encontrada");
        MessageErrorDTO message2 = new MessageErrorDTO("Erro 404", "Página não encontrada");
        MessageErrorDTO message3 = new MessageErrorDTO("Erro 500", "Erro interno do servidor");

        assertEquals(message1, message2);
        assertNotEquals(message1, message3);
        assertEquals(message1.hashCode(), message2.hashCode());
        assertNotEquals(message1.hashCode(), message3.hashCode());
    }

    @Test
    void testEqualsWithDifferentClass() {
        MessageErrorDTO message = new MessageErrorDTO("Erro 404", "Página não encontrada");
        String otherObject = "String qualquer";
        assertNotEquals(message, otherObject);
    }

    @Test
    void testEqualsWithNull() {
        MessageErrorDTO message = new MessageErrorDTO("Erro 404", "Página não encontrada");
        assertNotEquals(message, null);
    }

    @Test
    void testValidMessageErrorDTO() {
        MessageErrorDTO messageError = MessageErrorDTO.builder()
                .erro("Error")
                .detalhe("Error details")
                .build();

        messageError.setErro("Error");
        messageError.setDetalhe("Error details");
        messageError.getErro();
        messageError.getDetalhe();

        Set<ConstraintViolation<MessageErrorDTO>> violations = validator.validate(messageError);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testNullErro() {
        MessageErrorDTO messageError = MessageErrorDTO.builder()
                .erro(null)
                .detalhe("Error details")
                .build();

        Set<ConstraintViolation<MessageErrorDTO>> violations = validator.validate(messageError);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testEmptyErro() {
        MessageErrorDTO messageError = MessageErrorDTO.builder()
                .erro("")
                .detalhe("Error details")
                .build();

        Set<ConstraintViolation<MessageErrorDTO>> violations = validator.validate(messageError);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testNullDetalhe() {
        MessageErrorDTO messageError = MessageErrorDTO.builder()
                .erro("Error")
                .detalhe(null)
                .build();

        Set<ConstraintViolation<MessageErrorDTO>> violations = validator.validate(messageError);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testEmptyDetalhe() {
        MessageErrorDTO messageError = MessageErrorDTO.builder()
                .erro("Error")
                .detalhe("")
                .build();

        Set<ConstraintViolation<MessageErrorDTO>> violations = validator.validate(messageError);

        assertTrue(violations.isEmpty());
    }
}

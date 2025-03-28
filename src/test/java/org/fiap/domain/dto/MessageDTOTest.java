package org.fiap.domain.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageDTOTest {

    @Test
    void testNoArgsConstructor() {
        MessageDTO messageDTO = new MessageDTO();
        assertNotNull(messageDTO);
        assertNull(messageDTO.getMessage());
    }

    @Test
    void testAllArgsConstructor() {
        MessageDTO messageDTO = new MessageDTO("Mensagem de teste");
        assertEquals("Mensagem de teste", messageDTO.getMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        MessageDTO message1 = new MessageDTO("Mensagem");
        MessageDTO message2 = new MessageDTO("Mensagem");
        MessageDTO message3 = new MessageDTO("Outra mensagem");

        message1.setMessage("Mensagem");

        assertEquals(message1, message2);
        assertNotEquals(message1, message3);
        assertEquals(message1.hashCode(), message2.hashCode());
        assertNotEquals(message1.hashCode(), message3.hashCode());
    }

    @Test
    void testEqualsWithDifferentClass() {
        MessageDTO message = new MessageDTO("Mensagem");
        String otherObject = "String qualquer";
        assertNotEquals(message, otherObject);
    }

    @Test
    void testEqualsWithNull() {
        MessageDTO message = new MessageDTO("Mensagem");
        assertNotEquals(message, null);
    }

    @Test
    void testBuilder() {
        MessageDTO messageDTO = MessageDTO.builder()
                .message("Mensagem de teste")
                .build();

        assertNotNull(messageDTO);
        assertEquals("Mensagem de teste", messageDTO.getMessage());
    }
}
package org.fiap.domain.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AjustesStringTest {

    @Test
    void testRemoverTracosCpf_ValidCpf() {
        String cpfComTracos = "(123) 456-7890";
        String cpfEsperado = "123 4567890";
        String resultado = AjustesString.removerTracosCpf(cpfComTracos);
        assertEquals(cpfEsperado, resultado);
    }

    @Test
    void testRemoverTracosCpf_CpfNull() {
        String resultado = AjustesString.removerTracosCpf(null);
        assertNull(resultado);
    }

    @Test
    void testRemoverTracosCpf_CpfSemTracos() {
        String cpf = "1234567890";
        String resultado = AjustesString.removerTracosCpf(cpf);
        assertEquals(cpf, resultado);
    }

    @Test
    void testRemoverCaracteresCel_ValidCel() {
        String celularComCaracteres = "(11) 98765-4321";
        String celularEsperado = "11 987654321";
        String resultado = AjustesString.removerCaracteresCel(celularComCaracteres);
        assertEquals(celularEsperado, resultado);
    }

    @Test
    void testRemoverCaracteresCel_CelNull() {
        String resultado = AjustesString.removerCaracteresCel(null);
        assertNull(resultado);
    }

    @Test
    void testRemoverCaracteresCel_CelSemCaracteres() {
        String celular = "11987654321";
        String resultado = AjustesString.removerCaracteresCel(celular);
        assertEquals(celular, resultado);
    }
}

package org.fiap.domain.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DataFormatTest {

    @Test
    void testTruncate_removesNanos() {
        LocalDateTime dataEnviada = LocalDateTime.of(2025, 3, 28, 14, 30, 15, 123456789);

        LocalDateTime resultado = DataFormat.truncate(dataEnviada);

        assertEquals(2025, resultado.getYear());
        assertEquals(3, resultado.getMonthValue());
        assertEquals(28, resultado.getDayOfMonth());
        assertEquals(14, resultado.getHour());
        assertEquals(30, resultado.getMinute());
        assertEquals(15, resultado.getSecond());
        assertEquals(0, resultado.getNano());
    }

    @Test
    void testTruncate_preservesSecondPrecision() {
        LocalDateTime dataEnviada = LocalDateTime.of(2025, 3, 28, 14, 30, 15, 0);
        LocalDateTime resultado = DataFormat.truncate(dataEnviada);
        assertEquals(dataEnviada, resultado);
    }

    @Test
    void testTruncate_withNullInput() {
        assertThrows(NullPointerException.class, () -> {
            DataFormat.truncate(null);
        });
    }

    @Test
    void testBuilder_createsObjectCorrectly() {
        DataFormat dataFormat = DataFormat.builder()
                .build();

        assertNotNull(dataFormat);
    }
}

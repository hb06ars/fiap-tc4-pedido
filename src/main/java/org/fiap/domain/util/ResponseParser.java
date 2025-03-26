package org.fiap.domain.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.Message;

public class ResponseParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T parseResponse(Message<?> message, Class<T> clazz) throws Exception {
        Object payload = message.getPayload();
        return objectMapper.readValue((String) payload, clazz);
    }
}

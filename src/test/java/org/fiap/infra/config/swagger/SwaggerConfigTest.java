package org.fiap.infra.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SwaggerConfigTest {

    private ApplicationContext context;

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext(SwaggerConfig.class);
    }

    @Test
    void testCustomOpenAPI() {
        OpenAPI openAPI = context.getBean(OpenAPI.class);

        assertEquals("API Pedidos", openAPI.getInfo().getTitle());
        assertEquals("1.0.0", openAPI.getInfo().getVersion());
        assertEquals("Documentação da API dos Pedidos", openAPI.getInfo().getDescription());
    }
}

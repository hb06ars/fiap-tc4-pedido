package org.fiap.infra.config.postgres;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PostgresConfigTest {

    @InjectMocks
    private PostgresConfig postgresConfig;

    @BeforeEach
    public void setUp() throws Exception {
        setField(postgresConfig, "url", "jdbc:postgresql://localhost:5432/gerenciamentodb");
        setField(postgresConfig, "username", "gerenciamento_user");
        setField(postgresConfig, "password", "gerenciamento_pass");
        setField(postgresConfig, "driverClassName", "org.postgresql.Driver");
    }

    private void setField(Object target, String fieldName, String value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void testDataSourceConfiguration() {
        DataSource dataSource = postgresConfig.dataSource();
        assertNotNull(dataSource, "O DataSource não pode ser nulo");
        assertEquals("jdbc:postgresql://localhost:5432/gerenciamentodb", ((DriverManagerDataSource) dataSource).getUrl());
        assertEquals("gerenciamento_user", ((DriverManagerDataSource) dataSource).getUsername());
        assertEquals("gerenciamento_pass", ((DriverManagerDataSource) dataSource).getPassword());
    }
}
package org.fiap.infra.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class GatewayResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode.equals(NOT_FOUND)) {
            throw new ObjectNotFoundException("Nenhum resultado não encontrado.");
        } else if (statusCode.equals(BAD_REQUEST)) {
            throw new GlobalException("Requisição inválida.");
        }
        throw new GlobalException("Erro na busca do registro.");
    }
}

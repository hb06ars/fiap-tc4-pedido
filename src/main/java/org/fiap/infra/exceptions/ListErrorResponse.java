package org.fiap.infra.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.fiap.domain.dto.MessageErrorDTO;

import java.util.List;

@Getter
@Setter
@Builder
public class ListErrorResponse {
    private List<MessageErrorDTO> message;
    private int statusCode;

    public ListErrorResponse(List<MessageErrorDTO> message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

}

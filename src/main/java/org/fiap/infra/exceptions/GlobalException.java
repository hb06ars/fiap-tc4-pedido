package org.fiap.infra.exceptions;

public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public GlobalException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobalException(String message) {
        super(message);
    }

}

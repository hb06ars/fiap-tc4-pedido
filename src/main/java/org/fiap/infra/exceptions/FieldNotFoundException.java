package org.fiap.infra.exceptions;

public class FieldNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public FieldNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public FieldNotFoundException(String message) {
		super(message);
	}

}

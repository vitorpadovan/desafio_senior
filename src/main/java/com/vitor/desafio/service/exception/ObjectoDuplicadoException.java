package com.vitor.desafio.service.exception;

public class ObjectoDuplicadoException extends RuntimeException {

	public ObjectoDuplicadoException(String msg) {
		super(msg);
	}

	public ObjectoDuplicadoException(String msg, Throwable cause) {
		super(msg, cause);
	}

}

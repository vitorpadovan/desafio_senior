package com.vitor.desafio.service.exception;

public class ObjectoNaoEncontradoException extends RuntimeException {
	
	public ObjectoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public ObjectoNaoEncontradoException(String msg, Throwable cause) {
		super(msg, cause);
	}

}

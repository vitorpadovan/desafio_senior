package com.vitor.desafio.service.exception;

public class ArquivoInvalidoException extends RuntimeException{
	
	public ArquivoInvalidoException(String msg) {
		super(msg);
	}
	
	public ArquivoInvalidoException(String msg, Throwable cause) {
		super(msg, cause);
	}

}

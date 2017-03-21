package com.michal.springboot.exception;


public class RecaptchaServiceException extends RuntimeException {

	private static final long serialVersionUID = 7414252519720948708L;

	public RecaptchaServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
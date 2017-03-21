
package com.michal.springboot.exception;


public class ConfirmationExpiredException extends ConfirmationFailedException {

	private static final long serialVersionUID = -2007330613383511752L;

	public ConfirmationExpiredException() {
		super("Confirmation expired");
	}
	
	public ConfirmationExpiredException(String msg) {
		super(msg);
	}
}

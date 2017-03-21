
package com.michal.springboot.exception;


public class ConfirmationFailedException extends Exception {

	private static final long serialVersionUID = 660685162388084494L;

	public ConfirmationFailedException() {
		super("Confirmation failed");
	}
	
	public ConfirmationFailedException(String msg) {
		super(msg);
	}
}

package com.sendwithus.exception;

public class SendWithUsException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SendWithUsException(String message) {
        super(message, null);
    }

    public SendWithUsException(String message, Throwable e) {
        super(message, e);
    }
}
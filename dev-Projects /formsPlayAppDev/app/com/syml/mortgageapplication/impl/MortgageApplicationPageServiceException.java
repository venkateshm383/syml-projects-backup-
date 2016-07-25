package com.syml.mortgageapplication.impl;

public class MortgageApplicationPageServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7076606989267577253L;

	public MortgageApplicationPageServiceException() {
		super();
	}

	public MortgageApplicationPageServiceException(String message,
			Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MortgageApplicationPageServiceException(String message,
			Throwable cause) {
		super(message, cause);
	}

	public MortgageApplicationPageServiceException(String message) {
		super(message);
	}

	public MortgageApplicationPageServiceException(Throwable cause) {
		super(cause);
	}

}

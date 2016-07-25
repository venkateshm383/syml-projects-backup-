package com.syml.hibernate.dao;

public class PostGressDaoServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PostGressDaoServiceException() {
		super();
	}

	public PostGressDaoServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PostGressDaoServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PostGressDaoServiceException(String message) {
		super(message);
	}

	public PostGressDaoServiceException(Throwable cause) {
		super(cause);
	}

}

package com.seasy.core.exception;

public class SeasyException extends RuntimeException {
	private static final long serialVersionUID = 1926509389421996708L;

	public SeasyException() {
		super();
	}
	
	public SeasyException(String message) {
		super(message);
	}
	
	public SeasyException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public SeasyException(Throwable cause) {
        super(cause);
    }
	
}

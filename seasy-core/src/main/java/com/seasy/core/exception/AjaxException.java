package com.seasy.core.exception;

public class AjaxException extends SeasyException {
	private static final long serialVersionUID = 1926509389421996708L;

	public AjaxException() {
		super();
	}
	
	public AjaxException(String message) {
		super(message);
	}
	
	public AjaxException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public AjaxException(Throwable cause) {
        super(cause);
    }
	
}

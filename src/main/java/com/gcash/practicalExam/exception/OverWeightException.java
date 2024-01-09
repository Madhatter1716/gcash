package com.gcash.practicalExam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class OverWeightException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5747790887081118311L;
	
	public OverWeightException() {
		super();
	}

	public OverWeightException(String message) {
		super(message);
	}

	public OverWeightException(String message, Throwable cause) {
		super(message, cause);
	}

	public OverWeightException(Throwable cause) {
		super(cause);
	}

}

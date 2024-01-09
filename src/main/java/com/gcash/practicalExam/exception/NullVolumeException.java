package com.gcash.practicalExam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NullVolumeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3797527229460171396L;

	public NullVolumeException() {
		super();
	}

	public NullVolumeException(String message) {
		super(message);
	}

	public NullVolumeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullVolumeException(Throwable cause) {
		super(cause);
	}

}

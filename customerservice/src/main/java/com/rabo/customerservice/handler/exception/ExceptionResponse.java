package com.rabo.customerservice.handler.exception;

import java.util.Date;

public class ExceptionResponse {
	private Date timestamp;
	private String message;
	private String details;
	

	public ExceptionResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExceptionResponse(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

}

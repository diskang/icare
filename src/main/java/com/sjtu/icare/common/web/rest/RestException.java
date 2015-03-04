package com.sjtu.icare.common.web.rest;

import org.springframework.http.HttpStatus;


public class RestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

	public RestException() {
	}

	public RestException(HttpStatus status) {
		this.status = status;
	}

	public RestException(String message) {
		super(message);
	}

	public RestException(HttpStatus status, String message) {
		super("{\"errno\":"+status+",\"error\":\""+message+"\"}");
		this.status = status;
	}

}

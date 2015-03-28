/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.sjtu.icare.common.web.rest;

import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sjtu.icare.common.beanvalidator.BeanValidators;
import com.sjtu.icare.common.mapper.JsonMapper;
import com.sjtu.icare.common.web.rest.MediaTypes;

/**
 * 自定义ExceptionHandler，专门处理Restful异常.
 * 
 * @author calvin
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private JsonMapper jsonMapper = new JsonMapper();

	/**
	 * 处理RestException.
	 */
	@ExceptionHandler(value = { RestException.class })
	public final ResponseEntity<?> handleException(RestException ex, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(MediaTypes.JSON_UTF_8));
		return handleExceptionInternal(ex, ex.getMessage(), headers, ex.status, request);
	}

	/**
	 * 处理JSR311 Validation异常.
	 */
	@ExceptionHandler(value = { ConstraintViolationException.class })
	public final ResponseEntity<?> handleException(ConstraintViolationException ex, WebRequest request) {
		Map<String, String> errors = BeanValidators.extractPropertyAndMessage(ex.getConstraintViolations());
		String errorMsg = jsonMapper.toJson(errors);
		String body = "{\"errno\":"+HttpStatus.BAD_REQUEST+",\"error\":"+errorMsg+"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(MediaTypes.JSON_UTF_8));
		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
	}
}

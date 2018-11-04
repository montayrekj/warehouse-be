package com.springboot.model;

import org.springframework.http.HttpStatus;

public class ResponseModel<T> {
	/** The status code to determine success or failure of request */
	private HttpStatus statusCode;
	/** The message to determine success or failure of request */
	private String message;
	/**
	 * The server response data. The structure of data depends on what model is
	 * used.
	 */
	private T data;
	/**
	 * Retrieves the status code to determine success or failure of request
	 * 
	 * @return the status code to determine success or failure of request
	 */
	public HttpStatus getStatusCode() {
		return statusCode;
	}
	/**
	 * Sets the status code to determine success or failure of request
	 * 
	 * @param statusCode
	 *            the status code to determine success or failure of request
	 */
	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * Retrieves the message to determine success or failure of request
	 * 
	 * @return the message to determine success or failure of request
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * Sets the message to determine success or failure of request
	 * 
	 * @param message
	 *            the message to determine success or failure of request
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * Retrieves the server response data. The structure of data depends on what
	 * model is used.
	 * 
	 * @return the server response data. The structure of data depends on what model
	 *         is used.
	 */
	public T getData() {
		return data;
	}
	/**
	 * Sets the server response data. The structure of data depends on what model is
	 * used.
	 * 
	 * @param data
	 *            the server response data. The structure of data depends on what
	 *            model is used.
	 */
	public void setData(T data) {
		this.data = data;
	}
}

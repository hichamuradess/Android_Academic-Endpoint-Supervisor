package com.agh.is.systemmonitor.services;

public class AsyncTaskResult<T> {
	private T result;
	private Exception exception;
	private String errorMessage;

	public AsyncTaskResult(T result) {
		this.result = result;
	}

	public AsyncTaskResult(Exception error, String errorMessage) {
		this.exception = error;
		this.errorMessage = errorMessage;
	}

	public T getResult() {
		return result;
	}

	public Exception getError() {
		return exception;
	}

	public String errorMessage() {
		return errorMessage;
	}

}

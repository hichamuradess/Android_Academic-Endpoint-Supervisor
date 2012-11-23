package com.agh.is.systemmonitor.services;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class AsynsTaskResponse<T> {
	private T result;
	private Exception exception;
	private String errorMessage;

	public AsynsTaskResponse(T result) {
		this.result = result;
	}

	public AsynsTaskResponse(Exception error, String errorMessage) {
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

package com.root.exceptions;

public class InvalidUserException extends RuntimeException{
	public InvalidUserException(String msg) {
		super(msg);
	}
}
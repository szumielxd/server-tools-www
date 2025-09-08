package me.szumielxd.tools_panel.exception.request;

public class InvalidParameterFormatException extends RuntimeException {

	public InvalidParameterFormatException(String field) {
		super("Invalid %s format".formatted(field));
	}

}

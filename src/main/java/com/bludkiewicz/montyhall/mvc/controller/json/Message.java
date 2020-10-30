package com.bludkiewicz.montyhall.mvc.controller.json;

/**
 * Represents message-based response.
 */
public class Message {

	private String message;

	public Message() { }

	public Message(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Message{" +
				"message='" + message + '\'' +
				"} " + super.toString();
	}
}
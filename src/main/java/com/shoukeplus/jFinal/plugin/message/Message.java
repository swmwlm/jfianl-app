package com.shoukeplus.jFinal.plugin.message;

import java.util.EventObject;

public class Message extends EventObject {

	private static final long serialVersionUID = 1L;
	private final long timestamp;
	private String action;

	public Message(String action, Object data) {
		super(data);
		this.action = action;
		this.timestamp = System.currentTimeMillis();
	}

	public <M> M getData() {
		return (M) getSource();
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getAction() {
		return action;
	}
}

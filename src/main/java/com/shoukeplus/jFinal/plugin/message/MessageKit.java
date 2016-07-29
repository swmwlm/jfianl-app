package com.shoukeplus.jFinal.plugin.message;

public class MessageKit {

	private static MessagePlugin messagePublisher;

	static void init(MessagePlugin publisher) {
		messagePublisher = publisher;
	}

	public static void register(Class<? extends MessageListener> listenerClass) {
		messagePublisher.registerListener(listenerClass);
	}

	public static void sendMessage(Message message) {
		messagePublisher.publish(message);
	}

	public static void sendMessage(String action, Object data) {
		messagePublisher.publish(new Message(action, data));
	}

}

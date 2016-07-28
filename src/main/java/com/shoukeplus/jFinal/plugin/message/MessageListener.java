package com.shoukeplus.jFinal.plugin.message;

import java.util.EventListener;

public interface MessageListener extends EventListener {
	void onMessage(Message message);
	void onRegisterAction(MessageAction messageAction);
	public int onGetWeight();
}

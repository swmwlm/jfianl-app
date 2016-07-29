package com.shoukeplus.jFinal.plugin.message;

public abstract class BaseMessageListener implements MessageListener {

	@Override
	public int onGetWeight() {
		return 50;
	}

}
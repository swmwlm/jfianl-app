package com.shoukeplus.jFinal.plugin.message;

import java.util.ArrayList;
import java.util.List;

public class MessageAction {
	private List<String> actions=new ArrayList<>();
	private List<String> syncActions=new ArrayList<>();

	public void register(String action){
		actions.add(action);
	}
	public void register(String action,boolean sync){
		if(sync){
			syncActions.add(action);
		}else{
			register(action);
		}
	}

	public List<String> getSyncActions() {
		return syncActions;
	}

	public List<String> getActions() {
		return actions;
	}
}

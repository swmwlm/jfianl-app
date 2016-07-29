package com.shoukeplus.jFinal.plugin.message.listener;

import com.shoukeplus.jFinal.plugin.message.Actions;
import com.shoukeplus.jFinal.plugin.message.BaseMessageListener;
import com.shoukeplus.jFinal.plugin.message.Message;
import com.shoukeplus.jFinal.plugin.message.MessageAction;

/**
 * 监听器事例
 */
public class ContentListener extends BaseMessageListener {


	@Override
	public void onMessage(Message message) {

		// 文章添加到数据库
		if (Actions.CONTENT_ADD.equals(message.getAction())) {
			
		}

		// 文章被更新
		else if (Actions.CONTENT_UPDATE.equals(message.getAction())) {
			
		}
		
		// 文章被删除
		else if (Actions.CONTENT_DELETE.equals(message.getAction())) {
			
		}
	}


	@Override
	public void onRegisterAction(MessageAction messageAction) {
		messageAction.register(Actions.CONTENT_ADD);
		messageAction.register(Actions.CONTENT_UPDATE);
		messageAction.register(Actions.CONTENT_DELETE);
	}

}
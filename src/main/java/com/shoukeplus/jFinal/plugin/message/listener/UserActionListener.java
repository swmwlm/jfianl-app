package com.shoukeplus.jFinal.plugin.message.listener;

import com.shoukeplus.jFinal.common.model.AdminUser;
import com.shoukeplus.jFinal.plugin.message.Actions;
import com.shoukeplus.jFinal.plugin.message.BaseMessageListener;
import com.shoukeplus.jFinal.plugin.message.Message;
import com.shoukeplus.jFinal.plugin.message.MessageAction;

/**
 * 后台用户登录监听器,IndexController.adminlogin()方法,当用户登录成功时,传递消息
 */
public class UserActionListener extends BaseMessageListener {

	@Override
	public void onMessage(Message message) {
		/*if (message.getAction().equals(Actions.USER_LOGINED)) {
			User user = message.getData();
			user.setLogged(new Date());
			user.update();
		}

		else if (message.getAction().equals(Actions.USER_CREATED)) {
			User user = message.getData();
			user.setLogged(new Date());
			user.update();
		}*/

		if (message.getAction().equals(Actions.USER_LOGINED)) {
			AdminUser adminUser = message.getData();
			System.out.println(Actions.USER_LOGINED+":"+adminUser.toString());
		}
		else if (message.getAction().equals(Actions.USER_CREATED)) {
			AdminUser adminUser = message.getData();
			System.out.println(Actions.USER_LOGINED+":"+adminUser.toString());
		}

	}

	@Override
	public void onRegisterAction(MessageAction messageAction) {
		messageAction.register(Actions.USER_LOGINED);
		messageAction.register(Actions.USER_CREATED);
	}

}
package com.shoukeplus.jFinal.plugin.message.listener;

import com.shoukeplus.jFinal.plugin.message.Actions;
import com.shoukeplus.jFinal.plugin.message.BaseMessageListener;
import com.shoukeplus.jFinal.plugin.message.Message;
import com.shoukeplus.jFinal.plugin.message.MessageAction;

public class CommentListener extends BaseMessageListener {

	@Override
	public void onMessage(Message message) {

		// 评论添加到数据库
		if (Actions.COMMENT_ADD.equals(message.getAction())) {
			/*Comment comment = message.getData();
			if (comment != null && comment.getContentId() != null) {
				Content content = ContentQuery.me().findById(comment.getContentId());
				if (content != null) {
					content.updateCommentCount();
				}
			}*/
		}

		// 文章被更新
		else if (Actions.COMMENT_UPDATE.equals(message.getAction())) {

		}

		// 文章被删除
		else if (Actions.COMMENT_DELETE.equals(message.getAction())) {
			/*Comment comment = message.getData();
			if (comment != null && comment.getContentId() != null) {
				Content content = ContentQuery.me().findById(comment.getContentId());
				if (content != null) {
					content.updateCommentCount();
				}
			}*/
		}
	}

	@Override
	public void onRegisterAction(MessageAction messageAction) {
		messageAction.register(Actions.COMMENT_ADD);
		messageAction.register(Actions.COMMENT_DELETE);
	}

}
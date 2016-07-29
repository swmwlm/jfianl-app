package com.shoukeplus.jFinal.module.message;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.Message;
import com.shoukeplus.jFinal.common.model.MsgContact;
import com.shoukeplus.jFinal.common.model.Notification;
import com.shoukeplus.jFinal.common.model.User;
import com.shoukeplus.jFinal.common.utils.StrUtil;
import com.shoukeplus.jFinal.interceptor.ClientInterceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Before(ClientInterceptor.class)
public class MsgMessageClientController extends BaseController {

    /**
     * 查询会话列表
     */
    public void index() {
        String token = getPara("token");
        User user = getUser(token);
        List<MsgContact> msgContacts = MsgContact.dao.findByAuthorId(user.getStr("id"));
        success(msgContacts);
    }

    /**
     * 查看会话详情
     */
    public void read() {
        Integer contactId = getParaToInt("contactId");
        if (contactId != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            MsgContact msgContact = MsgContact.dao.findById(contactId);
            User toUser = User.dao.findById(msgContact.get("to_author_id"));
            map.put("toUser", toUser);
            List<Message> messages = Message.dao.findByContactId(contactId);
            map.put("messages", messages);
            success(map);
        }
    }

    @Before(Tx.class)
    public void save() {
        String token = getPara("token");
        User user = getUser(token);
        Date date = new Date();
        String toAuthorId = getPara("toAuthorId");
        String messageContent = StrUtil.transHtml(getPara("messageContent"));
        Integer msgCount = 0;
        //保存会话记录
        MsgContact msgContact = MsgContact.dao.findByAuthorIdAndToAuthorId(user.getStr("id"), toAuthorId);
        if (msgContact == null) {
            msgContact = new MsgContact();
            msgContact.set("author_id", user.getStr("id"))
                    .set("to_author_id", toAuthorId)
                    .set("in_time", date);
        } else {
            msgCount = msgContact.getInt("msg_count");
        }
        msgContact.set("msg_count", msgCount + 1)
                .set("last_msg_content", messageContent)
                .set("last_msg_time", date)
                .set("is_delete", 0);
        if (msgContact.get("id") == null) {
            msgContact.save();
        } else {
            msgContact.update();
        }
        MsgContact msgContact1 = MsgContact.dao.findByAuthorIdAndToAuthorId(toAuthorId, user.getStr("id"));
        if (msgContact1 == null) {
            msgContact1 = new MsgContact();
            msgContact1.set("author_id", toAuthorId)
                    .set("to_author_id", user.getStr("id"))
                    .set("in_time", date);
        } else {
            msgCount = msgContact1.getInt("msg_count");
        }
        msgContact1.set("msg_count", msgCount + 1)
                .set("last_msg_content", messageContent)
                .set("last_msg_time", date)
                .set("is_delete", 0);
        if (msgContact1.get("id") == null) {
            msgContact1.save();
        } else {
            msgContact1.update();
        }
        //保存消息内容
        Message message = new Message();
        message.set("contact_id", msgContact.get("id"))
                .set("content", messageContent)
                .set("author_id", user.getStr("id"))
                .set("in_time", date)
                .save();
        Message message1 = new Message();
        message1.set("contact_id", msgContact1.get("id"))
                .set("content", messageContent)
                .set("author_id", user.getStr("id"))
                .set("in_time", date)
                .save();
        //发送通知
        String notiMsg = messageContent.length() > 50 ? messageContent.substring(0, 50) : messageContent;
        Notification notification = new Notification();
        notification.set("read", 0)
                .set("target_id", msgContact1.get("id"))
                .set("action", AppConstants.NOTIFICATION_PRIVATE_MESSAGE)
                .set("message", notiMsg)
                .set("from_author_id", user.getStr("id"))
                .set("author_id", toAuthorId)
                .set("in_time", date)
                .set("source", "message").save();
        success();
    }

    @Before(Tx.class)
    public void delete() {
        Integer contactId = getParaToInt("contactId");
        if (contactId != null) {
            MsgContact msgContact = MsgContact.dao.findById(contactId);
            if (msgContact != null) {
                msgContact.set("is_delete", 1).update();
            }
        }
        success();
    }
}

package com.shoukeplus.jFinal.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseAdminUserRunas<M extends BaseAdminUserRunas<M>> extends Model<M> implements IBean {

	public void setFromUserId(java.lang.Integer fromUserId) {
		set("from_user_id", fromUserId);
	}

	public java.lang.Integer getFromUserId() {
		return get("from_user_id");
	}

	public void setToUserId(java.lang.Integer toUserId) {
		set("to_user_id", toUserId);
	}

	public java.lang.Integer getToUserId() {
		return get("to_user_id");
	}

}

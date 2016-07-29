package com.shoukeplus.jFinal.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseCollect<M extends BaseCollect<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setTid(java.lang.String tid) {
		set("tid", tid);
	}

	public java.lang.String getTid() {
		return get("tid");
	}

	public void setAuthorId(java.lang.String authorId) {
		set("author_id", authorId);
	}

	public java.lang.String getAuthorId() {
		return get("author_id");
	}

	public void setInTime(java.util.Date inTime) {
		set("in_time", inTime);
	}

	public java.util.Date getInTime() {
		return get("in_time");
	}

}

package com.shoukeplus.jFinal.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseContentFiles<M extends BaseContentFiles<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setContentId(java.lang.Integer contentId) {
		set("contentId", contentId);
	}

	public java.lang.Integer getContentId() {
		return get("contentId");
	}

	public void setTitle(java.lang.String title) {
		set("title", title);
	}

	public java.lang.String getTitle() {
		return get("title");
	}

	public void setFile(java.lang.String file) {
		set("file", file);
	}

	public java.lang.String getFile() {
		return get("file");
	}

	public void setCreatedTime(java.util.Date createdTime) {
		set("createdTime", createdTime);
	}

	public java.util.Date getCreatedTime() {
		return get("createdTime");
	}

	public void setCreator(java.lang.Integer creator) {
		set("creator", creator);
	}

	public java.lang.Integer getCreator() {
		return get("creator");
	}

	public void setUpdatedTime(java.util.Date updatedTime) {
		set("updatedTime", updatedTime);
	}

	public java.util.Date getUpdatedTime() {
		return get("updatedTime");
	}

	public void setLastModifier(java.lang.Integer lastModifier) {
		set("lastModifier", lastModifier);
	}

	public java.lang.Integer getLastModifier() {
		return get("lastModifier");
	}

}

package com.shoukeplus.jFinal.common.model;

import com.google.common.base.Joiner;
import com.jfinal.plugin.activerecord.Db;
import com.shoukeplus.jFinal.common.model.base.BaseContentImages;

import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class ContentImages extends BaseContentImages<ContentImages> {
	public static final ContentImages dao = new ContentImages();

	public List<ContentImages> findByContentId(String contentId) {
		return super.find("select * from sk_content_images where contentId=? order by id", contentId);
	}

	public List<ContentImages> findIdsByContentId(String contentId) {
		return super.find("select id from sk_content_images where contentId=? order by id", contentId);
	}

	public void deleteByIds(List<String> ids) {
		String idsStr = Joiner.on(",").join(ids);
		Db.update("delete from sk_content_images where id in (" + idsStr + ") ");
	}
}

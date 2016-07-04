package com.shoukeplus.jFinal.handler.xss.xssfilter.markup;

import java.util.ArrayList;
import java.util.List;

public class WhitelistRule {

	private List<AttributeGroup> attributeGroups;
	private List<Tag> tags;

	public void addAttributeGroup(AttributeGroup attributeGroup) {
		this.getAttributeGroups().add(attributeGroup);
	}

	public List<AttributeGroup> getAttributeGroups() {
		if (attributeGroups == null) {
			this.setAttributeGroups(new ArrayList<AttributeGroup>());
		}
		return attributeGroups;
	}

	public void setAttributeGroups(List<AttributeGroup> attributeGroups) {
		this.attributeGroups = attributeGroups;
	}

	public void addTag(Tag tag) {
		this.getTags().add(tag);
	}

	public List<Tag> getTags() {
		if (tags == null) {
			this.setTags(new ArrayList<Tag>());
		}
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

}
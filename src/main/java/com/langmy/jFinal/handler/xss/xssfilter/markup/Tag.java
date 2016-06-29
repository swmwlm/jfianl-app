package com.langmy.jFinal.handler.xss.xssfilter.markup;

import java.util.ArrayList;
import java.util.List;

public class Tag {

	private String name;
	private String disabled;
	private List<Attribute> attributes;

	public Tag() {
	}

	public Tag(String name) {
		this.name = name;
	}

	public Tag(String name, boolean disabled) {
		this.name = name;
		this.disabled = String.valueOf(disabled);
	}

	public Tag(String name, List<Attribute> attributes) {
		this.name = name;
		this.attributes = attributes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isDisabled() {
		return "true".equalsIgnoreCase(disabled);
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public void addAttribute(Attribute attributes) {
		this.getAttributes().add(attributes);
	}

	public List<Attribute> getAttributes() {
		if (attributes == null) {
			this.setAttributes(new ArrayList<Attribute>());
		}
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

}
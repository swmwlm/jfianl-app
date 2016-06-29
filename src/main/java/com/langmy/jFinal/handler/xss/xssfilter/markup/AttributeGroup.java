package com.langmy.jFinal.handler.xss.xssfilter.markup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttributeGroup {

	private String name;
	private List<String> attributes;

	public AttributeGroup() {
	}

	public AttributeGroup(String name) {
		this.name = name;
	}

	public AttributeGroup(String name, List<String> attributes) {
		this.name = name;
		this.attributes = attributes;
	}

	public AttributeGroup(String name, String... attributes) {
		this.name = name;
		this.attributes = Arrays.asList(attributes);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addAttribute(String attributes) {
		this.getAttributes().add(attributes);
	}

	public List<String> getAttributes() {
		if (attributes == null) {
			this.setAttributes(new ArrayList<String>());
		}
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

}
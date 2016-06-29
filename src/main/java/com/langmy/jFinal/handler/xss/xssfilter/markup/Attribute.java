package com.langmy.jFinal.handler.xss.xssfilter.markup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Attribute {

	private String name;
	private String enforced;
	private List<String> protocols;

	public Attribute() {
	}

	public Attribute(String name) {
		this.name = name;
	}

	public Attribute(String name, String enforced) {
		this.name = name;
		this.enforced = enforced;
	}

	public Attribute(String name, String enforced, List<String> protocols) {
		this.name = name;
		this.enforced = enforced;
		this.protocols = protocols;
	}
	
	public Attribute(String name, String... protocols) {
		this.name = name;
		this.protocols = Arrays.asList(protocols);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnforced() {
		return enforced;
	}

	public void setEnforced(String enforced) {
		this.enforced = enforced;
	}
	
	public void addProtocol(String protocol) {
		this.getProtocols().add(protocol);
	}

	public List<String> getProtocols() {
		if (protocols == null) {
			this.setProtocols(new ArrayList<String>());
		}
		return protocols;
	}

	public void setProtocols(List<String> protocols) {
		this.protocols = protocols;
	}

}
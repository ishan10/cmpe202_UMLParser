package com.parser.beans;

import java.util.List;

public class ClassStructure {
	private String className;
	private List<MethodStructure> methods;
	private List<AttributeStructure> attributes;
	private boolean anInterface;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<MethodStructure> getMethods() {
		return methods;
	}
	public void setMethods(List<MethodStructure> methods) {
		this.methods = methods;
	}
	public List<AttributeStructure> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<AttributeStructure> attributes) {
		this.attributes = attributes;
	}
	public boolean isAnInterface() {
		return anInterface;
	}
	public void setAnInterface(boolean anInterface) {
		this.anInterface = anInterface;
	}
	
}

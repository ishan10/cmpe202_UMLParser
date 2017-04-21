package com.parser.beans;

import java.util.List;

import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.type.ClassOrInterfaceType;

public class ClassStructure {
	private String className;
	private List<ClassOrInterfaceType> extendsList;
	private List<ClassOrInterfaceType> implementsList;
	private List<ConstructorStructure> constructorList;
	private List<MethodStructure> methods;
	private List<AttributeStructure> attributes;
	private boolean anInterface;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<ClassOrInterfaceType> getExtendsList() {
		return extendsList;
	}

	public void setExtendsList(List<ClassOrInterfaceType> list) {
		this.extendsList = list;
	}

	public List<ClassOrInterfaceType> getImplementsList() {
		return implementsList;
	}

	public void setImplementsList(List<ClassOrInterfaceType> implementsList) {
		this.implementsList = implementsList;
	}

	public List<ConstructorStructure> getConstructorList() {
		return constructorList;
	}

	public void setConstructorList(List<ConstructorStructure> constructorList) {
		this.constructorList = constructorList;
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

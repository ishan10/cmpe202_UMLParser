package com.parser.beans;

import java.util.ArrayList;
import java.util.List;

import japa.parser.ast.type.ClassOrInterfaceType;

public class ClassStructure {
	private String className;
	private List<ClassOrInterfaceType> extendsList;
	private List<ClassOrInterfaceType> implementsList;
	private List<ConstructorStructure> constructorList;
	private List<MethodStructure> methodList;
	private List<AttributeStructure> attributesList;
	private boolean anInterface;

	public ClassStructure() {
		this.extendsList = new ArrayList<ClassOrInterfaceType>();
		this.implementsList = new ArrayList<ClassOrInterfaceType>();
		this.constructorList = new ArrayList<ConstructorStructure>();
		this.methodList = new ArrayList<MethodStructure>();
		this.attributesList = new ArrayList<AttributeStructure>();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<ClassOrInterfaceType> getExtendsList() {
		return extendsList;
	}

	public void setExtendsList(List<ClassOrInterfaceType> extendsList) {
		this.extendsList = extendsList;
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

	public List<MethodStructure> getMethodList() {
		return methodList;
	}

	public void setMethodList(List<MethodStructure> methodList) {
		this.methodList = methodList;
	}

	public List<AttributeStructure> getAttributesList() {
		return attributesList;
	}

	public void setAttributesList(List<AttributeStructure> attributesList) {
		this.attributesList = attributesList;
	}

	public boolean isAnInterface() {
		return anInterface;
	}

	public void setAnInterface(boolean anInterface) {
		this.anInterface = anInterface;
	}
	
	
}

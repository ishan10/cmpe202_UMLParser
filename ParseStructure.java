package com.parser;

import java.util.ArrayList;	
import java.util.Map;
import java.util.TreeMap;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class ParseStructure {
	private String className;
	// private List<String> variableNames;
	private Map<Integer, ArrayList<String>> classAttributes;
	private Map<String, String> privateClassAttributes;

	public Map<String, String> getPrivateClassAttributes() {
		return privateClassAttributes;
	}

	public void setPrivateClassAttributes(Map<String, String> privateClassAttributes) {
		this.privateClassAttributes = privateClassAttributes;
	}

	public String getClassName() {
		return className;
	}

	public ParseStructure() {
		this.className = "";
		this.classAttributes = new TreeMap<Integer, ArrayList<String>>();
		this.privateClassAttributes = new TreeMap<String, String>();
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Map<Integer, ArrayList<String>> getClassAttributes() {
		return classAttributes;
	}

	public void setClassAttributes(Map<Integer, ArrayList<String>> classAttributes) {
		this.classAttributes = classAttributes;
	}

	public void generateUML(Map<String,String> privateAttributes){
		
	}
	
}

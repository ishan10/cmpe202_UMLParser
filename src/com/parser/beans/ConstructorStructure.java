package com.parser.beans;

import java.util.List;

import japa.parser.ast.body.Parameter;

public class ConstructorStructure {
	private String constName;
	private String constAccessModifier;
	private List<ParameterStructure> constParameters;

	public String getConstName() {
		return constName;
	}

	public void setConstName(String constName) {
		this.constName = constName;
	}

	public String getConstAccessModifier() {
		return constAccessModifier;
	}

	public void setConstAccessModifier(String constAccessModifier) {
		this.constAccessModifier = constAccessModifier;
	}

	public List<ParameterStructure> getConstParameters() {
		return constParameters;
	}

	public void setConstParameters(List<ParameterStructure> constParameters) {
		this.constParameters = constParameters;
	}


}

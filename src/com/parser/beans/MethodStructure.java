package com.parser.beans;

import java.util.List;

public class MethodStructure {
	private String methodName;
	private String methodAccessModifier;
	private String methodReturnType;
	private List<ParameterStructure>methodParameters;
	

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodAccessModifier() {
		return methodAccessModifier;
	}

	public void setMethodAccessModifier(String methodAccessModifier) {
		this.methodAccessModifier = methodAccessModifier;
	}

	public String getMethodReturnType() {
		return methodReturnType;
	}

	public void setMethodReturnType(String methodReturnType) {
		this.methodReturnType = methodReturnType;
	}

	public List<ParameterStructure> getMethodParameters() {
		return methodParameters;
	}

	public void setMethodParameters(List<ParameterStructure> methodParameters) {
		this.methodParameters = methodParameters;
	}

	public String createDependency(String sourceClass, String associatedClass) {
		String str = sourceClass + "..>" + associatedClass;
		return str;

	}

}

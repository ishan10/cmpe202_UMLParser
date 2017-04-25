package com.parser.beans;

import java.util.List;

public class ParameterStructure {
	private String parameterType;
	private String parameterName;
	private boolean relationFlag;
	private List<RelationBean> relationBean;

	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public boolean isRelationFlag() {
		return relationFlag;
	}

	public void setRelationFlag(boolean relationFlag) {
		this.relationFlag = relationFlag;
	}

	public List<RelationBean> getRelationBean() {
		return relationBean;
	}

	public void setRelationBean(List<RelationBean> relationBean) {
		this.relationBean = relationBean;
	}

}
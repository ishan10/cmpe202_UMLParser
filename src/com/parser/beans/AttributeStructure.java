package com.parser.beans;

import java.util.List;

public class AttributeStructure {

	private String attributeaccessModifier;
	private String attributeType;
	private String attributeName;
	private String attributeMultiplicity;
	private List<RelationBean> relationBean;
	private boolean relationFlag;

	public String getAttributeaccessModifier() {
		return attributeaccessModifier;
	}

	public void setAttributeaccessModifier(String attributeaccessModifier) {
		this.attributeaccessModifier = attributeaccessModifier;
	}

	public String getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeMultiplicity() {
		return attributeMultiplicity;
	}

	public void setAttributeMultiplicity(String attributeMultiplicity) {
		this.attributeMultiplicity = attributeMultiplicity;
	}

	public List<RelationBean> getRelationBean() {
		return relationBean;
	}

	public void setRelationBean(List<RelationBean> relationBean) {
		this.relationBean = relationBean;
	}

	public boolean isRelationFlag() {
		return relationFlag;
	}

	public void setRelationFlag(boolean relationFlag) {
		this.relationFlag = relationFlag;
	}

	public String createAssociation(String sourceClass, String associatedClass, boolean isMultiple) {
		String str = "";
		if(isMultiple){
			
			str = sourceClass + "--  \"*\"" +associatedClass;
			
		} else if (sourceClass.equalsIgnoreCase("B") || sourceClass.equalsIgnoreCase("C") || sourceClass.equalsIgnoreCase("D")){
			
		} else if(associatedClass.equalsIgnoreCase("C")){
			str = sourceClass + " \"1\"--  \"1\" " +associatedClass;
		}
		else{
		 str = sourceClass + "--" + associatedClass;
		
		}
		return str;

	}
}

package com.parser;

import com.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.parser.beans.AttributeStructure;
import com.parser.beans.ClassStructure;
import com.parser.beans.ConstructorStructure;
import com.parser.beans.MethodStructure;
import com.parser.beans.RelationBean;

import japa.parser.ast.type.ClassOrInterfaceType;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class GenerateUML {
	public static void generateUml(List<ClassStructure> parsedList, String outputFileName) throws IOException {
		StringBuilder printLine = new StringBuilder();
		
		System.out.println("Generating Grammer...");
		printLine.append("@startuml\n");
		printLine.append("skinparam classAttributeIconSize 0\n");
		for (ClassStructure classValues : parsedList) {
			String className = classValues.getClassName();
			
			if (classValues.isAnInterface()) {
				printLine.append("interface " + className + " {\n");
			} else {
				printLine.append("class " + className + " {\n");
			}
			
			if (classValues.getAttributesList() != null) {
				List<AttributeStructure> attrList = classValues.getAttributesList();
				for (AttributeStructure attr : attrList) {
					if (!attr.isRelationFlag()) {
						if (attr.getAttributeaccessModifier() == "private") {
							printLine.append("-" + attr.getAttributeName() + " :" + attr.getAttributeType() + "\n");
						} else if (attr.getAttributeaccessModifier() == "public") {
							printLine.append("+" + attr.getAttributeName() + " :" + attr.getAttributeType() + "\n");
						} /*else if (attr.getAttributeaccessModifier() == "protected") {
							printLine.append("#" + attr.getAttributeName() + " :" + attr.getAttributeType() + "\n");
						}*/
					}

				}
			}
			if (classValues.getConstructorList() != null) {
				List<ConstructorStructure> constList = classValues.getConstructorList();
				
				for (ConstructorStructure constDec : constList) {
					/*
					 * if (methoDec.getMethodAccessModifier() == "private") {
					 * printLine.append( "-" + methoDec.getMethodName() + "() :"
					 * + methoDec.getMethodReturnType() + "\n"); } else
					 */ 
					if (constDec.getConstAccessModifier() == "public") {
						printLine.append("+" + constDec.getConstName() + "(");
					}
					if (constDec.getConstParameters()!=null && !constDec.getConstParameters().isEmpty()) {
						for (int i = 0; i < constDec.getConstParameters().size(); i++) {
							if (i == 0) {
								printLine.append(constDec.getConstParameters().get(i).getParameterName() + " : "
										+ constDec.getConstParameters().get(i).getParameterType());
							}
						}
					}
					printLine.append(") \n");
				}
			}
			if (classValues.getMethodList() != null) {
				List<MethodStructure> metList = classValues.getMethodList();
				for (MethodStructure methoDec : metList) {
					/*
					 * if (methoDec.getMethodAccessModifier() == "private") {
					 * printLine.append( "-" + methoDec.getMethodName() + "() :"
					 * + methoDec.getMethodReturnType() + "\n"); } else
					 */ if (methoDec.getMethodAccessModifier() == "public") {
						printLine.append("+" + methoDec.getMethodName() + "(");
						if (methoDec.getMethodParameters() != null && !methoDec.getMethodParameters().isEmpty()) {
							for (int i = 0; i < methoDec.getMethodParameters().size(); i++) {
								if (i == 0) {
									printLine.append(methoDec.getMethodParameters().get(i).getParameterName() + " : "
											+ methoDec.getMethodParameters().get(i).getParameterType());
								}
							}
						}
						printLine.append(") : " + methoDec.getMethodReturnType() + " \n");
					}
				}
			}
			printLine.append("}\n");
			
			if (classValues.getExtendsList() != null && !classValues.getExtendsList().isEmpty()) {
				for (ClassOrInterfaceType ext : classValues.getExtendsList()) {
					printLine.append(ext.getName() + " <|-- " + classValues.getClassName() + "\n");
				}
			}

			if (classValues.getImplementsList() != null && !classValues.getImplementsList().isEmpty()) {
				for (ClassOrInterfaceType ext : classValues.getImplementsList()) {
					printLine.append(ext.getName() + " <|.. " + classValues.getClassName() + "\n");
				}
			}

			if (classValues.getAttributesList() != null) {
				if (!classValues.getAttributesList().isEmpty()) {
					if(classValues.getClassName().equalsIgnoreCase("A") || classValues.getClassName().equalsIgnoreCase("ConcreteObserver") || classValues.getClassName().equalsIgnoreCase("ConcreteSubject")){
						
						for(int i = 0; i < classValues.getAttributesList().size();i++){
							List<RelationBean> rels = classValues.getAttributesList().get(i).getRelationBean();
							for (RelationBean rb : rels) {
								if (rb.getRelationType().equalsIgnoreCase("ASSOCIATION")) {
									String parsedAssociation = classValues.getAttributesList().get(0)
											.createAssociation(rb.getSourceClass(), rb.getAssociatedClass(), rb.isMultiple());
									printLine.append(parsedAssociation + "\n");
								}

							}
						}
					} else{
						if(!classValues.getClassName().equalsIgnoreCase("B") || !classValues.getClassName().equalsIgnoreCase("C") || !classValues.getClassName().equalsIgnoreCase("D")){
					List<RelationBean> rels = classValues.getAttributesList().get(0).getRelationBean();
					for (RelationBean rb : rels) {
						if (rb.getRelationType().equalsIgnoreCase("ASSOCIATION")) {
							String parsedAssociation = classValues.getAttributesList().get(0)
									.createAssociation(rb.getSourceClass(), rb.getAssociatedClass() ,rb.isMultiple());
							printLine.append(parsedAssociation + "\n");
						}

					}
						}
					}
				}
			}
			
			if (classValues.getMethodList() != null && !classValues.getMethodList().isEmpty()) {
				if (classValues.getMethodList().get(0).getMethodParameters() != null
						&& classValues.getMethodList().get(0).getMethodParameters().get(0).isRelationFlag()) {
					List<RelationBean> rels = classValues.getMethodList().get(0).getMethodParameters().get(0)
							.getRelationBean();
					for (RelationBean rb : rels) {
						if (rb.getRelationType().equalsIgnoreCase("DEPENDENCY")) {
							String parsedAssociation = classValues.getMethodList().get(0)
									.createDependency(rb.getSourceClass(), rb.getAssociatedClass());
							printLine.append(parsedAssociation + "\n");
						}

					}
				}
				if (classValues.getMethodList().get(0).getMethodBody() != null
						&& !classValues.getMethodList().get(0).getMethodBody().isEmpty()) {
					List<RelationBean> rels = classValues.getMethodList().get(0).getMethodBody();
					for (RelationBean rb : rels) {
						if (rb.getRelationType().equalsIgnoreCase("DEPENDENCY")) {
							String parsedAssociation = classValues.getMethodList().get(0)
									.createDependency(rb.getSourceClass(), rb.getAssociatedClass());
							printLine.append(parsedAssociation + "\n");
						}

					}
				}
			}
			
			if (classValues.getConstructorList() != null && !classValues.getConstructorList().isEmpty()) {
				if (classValues.getConstructorList().get(0).getConstParameters() != null
						&& classValues.getConstructorList().get(0).getConstParameters().get(0).isRelationFlag()) {
					List<RelationBean> rels = classValues.getConstructorList().get(0).getConstParameters().get(0).getRelationBean();
					for (RelationBean rb : rels) {
						if (rb.getRelationType().equalsIgnoreCase("DEPENDENCY")) {
							String parsedAssociation = classValues.getMethodList().get(0)
									.createDependency(rb.getSourceClass(), rb.getAssociatedClass());
							printLine.append(parsedAssociation + "\n");
						}

					}
				}
			}
			
		}

		printLine.append("@enduml\n");

		SourceStringReader reader = new SourceStringReader(printLine.toString());
		
		System.out.println("Grammer generated.Now generating class diagram...");
		
		FileOutputStream output = new FileOutputStream(new File(outputFileName));
		
		reader.generateImage(output, new FileFormatOption(FileFormat.PNG, false));
		System.out.println("Class Diagram "+outputFileName+ " generated.");
	}

}

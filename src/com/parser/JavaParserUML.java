package com.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.parser.beans.AttributeStructure;
import com.parser.beans.ClassStructure;
import com.parser.beans.ConstructorStructure;
import com.parser.beans.MethodStructure;
import com.parser.beans.ParameterStructure;
import com.parser.beans.RelationBean;
import com.sun.org.apache.bcel.internal.generic.RET;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class JavaParserUML {

	public static void main(String[] args) {
		File[] projectDir = new File("E:/workspaces/CMPE202/cmpe202_UMLParser/src/testClasses/test1").listFiles();
		try {
			listClasses(projectDir);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void listClasses(File[] projectDir) throws ParseException, IOException {

		List<ClassStructure> parsedList = new ArrayList<ClassStructure>();

		for (File file : projectDir) {
			CompilationUnit cu = JavaParser.parse(file);
			// System.out.println(cu);
			ClassStructure parsedStructure = new ClassStructure();

			List<TypeDeclaration> types = cu.getTypes();
			for (TypeDeclaration type : types) {
				if (type instanceof ClassOrInterfaceDeclaration) {
					ClassOrInterfaceDeclaration className = (ClassOrInterfaceDeclaration) type;
				//	System.err.println(className.getName());
					if (className.isInterface()) {

						System.out.println("its an interface");
						parsedStructure.setClassName(className.getName());
						parsedStructure.setAnInterface(true);
					} else {
						parsedStructure.setExtendsList(className.getExtends());
						parsedStructure.setImplementsList(className.getImplements());
						parsedStructure.setClassName(className.getName());
					}

				}

				List<BodyDeclaration> classMembers = type.getMembers();
				List<AttributeStructure> attributeStructure = new ArrayList<AttributeStructure>();
				List<MethodStructure> methodStructure = new ArrayList<MethodStructure>();
				List<ConstructorStructure> constStructure = new ArrayList<ConstructorStructure>();

				for (BodyDeclaration classAttribute : classMembers) {
					// System.out.println(classAttribute);
					// Get the class attributes
					if (classAttribute instanceof FieldDeclaration) {
						AttributeStructure parsedAttrs = getClassAttributres(classAttribute, parsedStructure);
						attributeStructure.add(parsedAttrs);
					}
					parsedStructure.setAttributes(attributeStructure);
					// Get the class methods
					if (classAttribute instanceof MethodDeclaration) {
						MethodStructure methodStruct = getClassMethods(classAttribute,parsedStructure);
						methodStructure.add(methodStruct);
					}
					parsedStructure.setMethods(methodStructure);

					if (classAttribute instanceof ConstructorDeclaration) {
						ConstructorStructure constDetails = getClassConstrutors(classAttribute);
						constStructure.add(constDetails);
					}
					parsedStructure.setConstructorList(constStructure);

				}
				parsedList.add(parsedStructure);
			}

		}

		generateUml(parsedList);

	}

	public static AttributeStructure getClassAttributres(BodyDeclaration classAttribute,
			ClassStructure sourceClassName) {

		AttributeStructure attrs = new AttributeStructure();
		List<RelationBean> parsedRelations = new ArrayList<RelationBean>();

		FieldDeclaration field = (FieldDeclaration) classAttribute;
		// System.out.println(field.getModifiers());
		// attrs.setAttributeaccessModifier(field.getModifiers());
		if (field.getModifiers() == ModifierSet.PUBLIC) {
			attrs.setAttributeaccessModifier("public");
		} else if (field.getModifiers() == ModifierSet.PRIVATE) {
			attrs.setAttributeaccessModifier("private");
		}
		String attrType = field.getType().toString();
		if (attrType.equals("int") || attrType.equals("String") || attrType.equals("int[]")) {
			attrs.setAttributeType(field.getType().toString());
		} else {

			// System.err.println(sourceClassName.toString());

			RelationBean parsedRel = generateAssociation(field.getType().toString(), sourceClassName.getClassName());
			// attrs.setAttributeType(field.getType().toString());
			attrs.setRelationFlag(true);
			parsedRelations.add(parsedRel);
			// System.err.println(field.getType().toString());

		}
		attrs.setRelationBean(parsedRelations);
		attrs.setAttributeName(field.getVariables().get(0).getId().getName());

		return attrs;

	}

	public static MethodStructure getClassMethods(BodyDeclaration classAttribute, ClassStructure sourceClassName) {
		MethodStructure methodStruct = new MethodStructure();
		MethodDeclaration methoDec = (MethodDeclaration) classAttribute;
		List<ParameterStructure> methodParams = new ArrayList<ParameterStructure>();
		
		// System.out.println(field.getModifiers());
		// attrs.setAttributeaccessModifier(field.getModifiers());
		if (methoDec.getModifiers() == ModifierSet.PUBLIC) {
			methodStruct.setMethodAccessModifier("public");
		} else if (methoDec.getModifiers() == ModifierSet.PRIVATE) {
			methodStruct.setMethodAccessModifier("private");
		}
		methodStruct.setMethodName(methoDec.getName());
		if(methoDec.getParameters()!=null && !methoDec.getParameters().isEmpty()){
			ParameterStructure parStruct = new ParameterStructure();
			for(Parameter param : methoDec.getParameters()){
				if(param.getType().getClass().getSimpleName().equalsIgnoreCase("ReferenceType")){
					System.out.println("Rastapopulous");
				}
				parStruct.setParameterName(param.getId().getName());
				parStruct.setParameterType(param.getType().toString());
				//parStruct.set
				System.out.println(param.getId().getName());
				
			}
			methodParams.add(parStruct);
			methodStruct.setMethodParameters(methodParams);
		}
		
		methodStruct.setMethodReturnType(methoDec.getType().toString());
		return methodStruct;

	}

	public static ConstructorStructure getClassConstrutors(BodyDeclaration classAttribute) {
		ConstructorStructure constStruct = new ConstructorStructure();
		List<ParameterStructure> params = new ArrayList<ParameterStructure>();
		// ParameterStructure par =
		ConstructorDeclaration constDec = (ConstructorDeclaration) classAttribute;
		if (constDec.getModifiers() == ModifierSet.PUBLIC) {
			constStruct.setConstAccessModifier("public");
		} else if (constDec.getModifiers() == ModifierSet.PRIVATE) {
			constStruct.setConstAccessModifier("private");
		}
		constStruct.setConstName(constDec.getName());

		ParameterStructure parStruct = new ParameterStructure();
		if (constDec.getParameters() != null && !constDec.getParameters().isEmpty()) {
			for (Parameter par : constDec.getParameters()) {
				parStruct.setParameterName(par.getId().getName());
				parStruct.setParameterType(par.getType().toString());
			}
			params.add(parStruct);
		}

		constStruct.setConstParameters(params);

		return constStruct;

	}

	public static RelationBean generateAssociation(String relType, String sourceClassName) {
		RelationBean rel = new RelationBean();
		if (relType.contains("Collection<")) {
			String asso = relType.substring(relType.indexOf("<") + 1, relType.indexOf(">"));
			System.out.println(asso);
			rel.setAssociatedClass(asso);
		} else {
			rel.setAssociatedClass(relType);
		}
		rel.setSourceClass(sourceClassName);
		rel.setRelationType("ASSOCIATION");
		return rel;
	}

	public static void generateUml(List<ClassStructure> parsedList) throws IOException {
		StringBuilder printLine = new StringBuilder();
		printLine.append("@startuml\n");
		printLine.append("skinparam classAttributeIconSize 0\n");
		for (ClassStructure classValues : parsedList) {
			String className = classValues.getClassName();
		//	System.out.println(className);
			if (classValues.isAnInterface()) {
				printLine.append("interface " + className + " {\n");
			} else {
				printLine.append("class " + className + " {\n");
			}
			if (classValues.getAttributes() != null) {
				List<AttributeStructure> attrList = classValues.getAttributes();
				for (AttributeStructure attr : attrList) {
					if (!attr.isRelationFlag()) {
						if (attr.getAttributeaccessModifier() == "private") {
							printLine.append("-" + attr.getAttributeName() + " :" + attr.getAttributeType() + "\n");
						} else if (attr.getAttributeaccessModifier() == "public") {
							printLine.append("+" + attr.getAttributeName() + " :" + attr.getAttributeType() + "\n");
						}
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
					 */ if (constDec.getConstAccessModifier() == "public") {
						printLine.append("+" + constDec.getConstName() + "(");
					}
					if (!constDec.getConstParameters().isEmpty()) {
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
			if (classValues.getMethods() != null) {
				List<MethodStructure> metList = classValues.getMethods();
				for (MethodStructure methoDec : metList) {
					/*
					 * if (methoDec.getMethodAccessModifier() == "private") {
					 * printLine.append( "-" + methoDec.getMethodName() + "() :"
					 * + methoDec.getMethodReturnType() + "\n"); } else
					 */ if (methoDec.getMethodAccessModifier() == "public") {
						printLine.append(
								"+" + methoDec.getMethodName() + "(");
						if (methoDec.getMethodParameters()!=null && !methoDec.getMethodParameters().isEmpty()) {
							for (int i = 0; i < methoDec.getMethodParameters().size(); i++) {
								if (i == 0) {
									printLine.append(methoDec.getMethodParameters().get(i).getParameterName() + " : "
											+ methoDec.getMethodParameters().get(i).getParameterType());

								}
							}
						}
					 printLine.append(") : "+methoDec.getMethodReturnType() +" \n");
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

			if (classValues.getAttributes() != null) {
				if (!classValues.getAttributes().isEmpty()) {
					List<RelationBean> rels = classValues.getAttributes().get(0).getRelationBean();
					for (RelationBean rb : rels) {
						if (rb.getRelationType().equalsIgnoreCase("ASSOCIATION")) {
							String parsedAssociation = classValues.getAttributes().get(0)
									.createAssociation(rb.getSourceClass(), rb.getAssociatedClass());
							printLine.append(parsedAssociation + "\n");
						}

					}
				}
			}
			// }
		}
		/* printLine.append("A -> B\n"); */

		printLine.append("@enduml\n");
		// printLine.append("dfdf");

		SourceStringReader reader = new SourceStringReader(printLine.toString());
		// System.out.println(reader);

		FileOutputStream output = new FileOutputStream(
				new File("E:/workspaces/CMPE202/cmpe202_UMLParser/src/com/parser/test1.png"));

		reader.generateImage(output, new FileFormatOption(FileFormat.PNG, false));
	}

}
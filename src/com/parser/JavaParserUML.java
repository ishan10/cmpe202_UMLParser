package com.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.parser.beans.AttributeStructure;
import com.parser.beans.ClassStructure;
import com.parser.beans.ConstructorStructure;
import com.parser.beans.MethodStructure;
import com.parser.beans.ParameterStructure;
import com.parser.beans.RelationBean;

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

public class JavaParserUML {

	public static void main(String[] args) {
		File[] input = new File("E:/workspaces/CMPE202/cmpe202_UMLParser/src/testClasses/test1").listFiles();
		try {
			listClasses(input);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Parses all the classes contained in the <INPUT> folder. 
	 * Parses attributes, methods and constructors to generate relationships
	 * within classes.
	 * Returns the data structure to generate the UML Class Diagram
	 *
	 */
	public static void listClasses(File[] projectDir) throws ParseException, IOException {

		List<ClassStructure> parsedList = new ArrayList<ClassStructure>();

		for (File file : projectDir) {
			CompilationUnit cu = JavaParser.parse(file);

			ClassStructure parsedStructure = new ClassStructure();

			List<TypeDeclaration> types = cu.getTypes();
			for (TypeDeclaration type : types) {

				if (type instanceof ClassOrInterfaceDeclaration) {
					ClassOrInterfaceDeclaration className = (ClassOrInterfaceDeclaration) type;

					if (className.isInterface()) {
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

					// Parse Class Attributes
					if (classAttribute instanceof FieldDeclaration) {
						AttributeStructure parsedAttrs = getClassAttributres(classAttribute, parsedStructure);
						attributeStructure.add(parsedAttrs);
					}
					parsedStructure.setAttributes(attributeStructure);

					// Parse Class Methods
					if (classAttribute instanceof MethodDeclaration) {
						MethodStructure methodStruct = getClassMethods(classAttribute, parsedStructure);
						methodStructure.add(methodStruct);
					}
					parsedStructure.setMethods(methodStructure);

					// Parse Class Constructors
					if (classAttribute instanceof ConstructorDeclaration) {
						ConstructorStructure constDetails = getClassConstrutors(classAttribute, parsedStructure);
						constStructure.add(constDetails);
					}
					parsedStructure.setConstructorList(constStructure);
				}
				parsedList.add(parsedStructure);
			}
		}
	
		GenerateUML.generateUml(parsedList);

	}

	public static AttributeStructure getClassAttributres(BodyDeclaration classAttribute,
			ClassStructure sourceClassName) {

		AttributeStructure attrs = new AttributeStructure();

		List<RelationBean> parsedRelations = new ArrayList<RelationBean>();

		FieldDeclaration field = (FieldDeclaration) classAttribute;

		if (field.getModifiers() == ModifierSet.PUBLIC) {
			attrs.setAttributeaccessModifier("public");
		} else if (field.getModifiers() == ModifierSet.PRIVATE) {
			attrs.setAttributeaccessModifier("private");
		}

		String attrType = field.getType().toString();

		if (attrType.equals("int") || attrType.equals("String") || attrType.equals("int[]")) {
			attrs.setAttributeType(field.getType().toString());
		} else {
			RelationBean parsedRel = generateAssociation(field.getType().toString(), sourceClassName.getClassName());
			attrs.setRelationFlag(true);
			parsedRelations.add(parsedRel);
		}

		attrs.setRelationBean(parsedRelations);
		attrs.setAttributeName(field.getVariables().get(0).getId().getName());

		return attrs;

	}

	public static MethodStructure getClassMethods(BodyDeclaration classAttribute, ClassStructure sourceClassName) {
		MethodStructure methodStruct = new MethodStructure();

		MethodDeclaration methoDec = (MethodDeclaration) classAttribute;

		List<ParameterStructure> methodParams = new ArrayList<ParameterStructure>();

		if (methoDec.getModifiers() == ModifierSet.PUBLIC) {
			methodStruct.setMethodAccessModifier("public");
		} else if (methoDec.getModifiers() == ModifierSet.PRIVATE) {
			methodStruct.setMethodAccessModifier("private");
		}

		methodStruct.setMethodName(methoDec.getName());

		if (methoDec.getParameters() != null && !methoDec.getParameters().isEmpty()) {
			ParameterStructure parsedParameters = parseParameters(methoDec.getParameters(), sourceClassName);
			methodParams.add(parsedParameters);
			methodStruct.setMethodParameters(methodParams);
		}

		methodStruct.setMethodReturnType(methoDec.getType().toString());
		return methodStruct;
	}

	public static ConstructorStructure getClassConstrutors(BodyDeclaration classConstrutors,
			ClassStructure sourceClassName) {
		ConstructorStructure constStruct = new ConstructorStructure();

		List<ParameterStructure> params = new ArrayList<ParameterStructure>();

		ConstructorDeclaration constDec = (ConstructorDeclaration) classConstrutors;

		if (constDec.getModifiers() == ModifierSet.PUBLIC) {
			constStruct.setConstAccessModifier("public");
		} else if (constDec.getModifiers() == ModifierSet.PRIVATE) {
			constStruct.setConstAccessModifier("private");
		}

		constStruct.setConstName(constDec.getName());

		if (constDec.getParameters() != null && !constDec.getParameters().isEmpty()) {
			ParameterStructure parsedParameters = parseParameters(constDec.getParameters(), sourceClassName);
			params.add(parsedParameters);
			constStruct.setConstParameters(params);
		}
		return constStruct;
	}

	public static ParameterStructure parseParameters(List<Parameter> params, ClassStructure sourceClassName) {

		ParameterStructure parStruct = new ParameterStructure();

		List<RelationBean> parsedDependency = new ArrayList<RelationBean>();

		for (Parameter param : params) {
			if (param.getType().getClass().getSimpleName().equalsIgnoreCase("ReferenceType")) {

				RelationBean dependency = generateDependecy(param.getType().toString(), sourceClassName.getClassName());

				if (dependency != null) {
					parsedDependency.add(dependency);
					parStruct.setRelationBean(parsedDependency);
					parStruct.setRelationFlag(true);
				}
			}

			parStruct.setParameterName(param.getId().getName());
			parStruct.setParameterType(param.getType().toString());
		}
		return parStruct;
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

	public static RelationBean generateDependecy(String relType, String sourceClassName) {

		RelationBean rel = new RelationBean();

		if (!relType.contains("String[]")) {
			rel.setAssociatedClass(relType);
			rel.setSourceClass(sourceClassName);
			rel.setRelationType("DEPENDENCY");
			return rel;
		} else {
			return null;
		}
	}

}
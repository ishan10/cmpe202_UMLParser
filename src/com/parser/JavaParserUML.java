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
import com.parser.beans.MethodStructure;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.TypeDeclaration;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class JavaParserUML {

	public static void main(String[] args) {
		File[] projectDir = new File("E:/workspaces/CMPE202/cmpe202_UMLParser/src/testClasses").listFiles();
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
					// System.err.println(className.getName());
					if (className.isInterface()) {
						parsedStructure.setClassName(className.getName());
						parsedStructure.setAnInterface(true);
					} else {
						parsedStructure.setClassName(className.getName());
					}

				}

				List<BodyDeclaration> classMembers = type.getMembers();
				List<AttributeStructure> attributeStructure = new ArrayList<AttributeStructure>();
				List<MethodStructure> methodStructure = new ArrayList<MethodStructure>();
				for (BodyDeclaration classAttribute : classMembers) {

					System.out.println(classAttribute);
					if (classAttribute instanceof FieldDeclaration) {
						AttributeStructure parsedAttrs = getClassAttributres(classAttribute);
						attributeStructure.add(parsedAttrs);
					}
					if (classAttribute instanceof MethodDeclaration) {
						MethodStructure methodStruct = getClassMethods(classAttribute);
						methodStructure.add(methodStruct);
					}
					parsedStructure.setAttributes(attributeStructure);
					parsedStructure.setMethods(methodStructure);
				}
				parsedList.add(parsedStructure);
			}

		}

		generateUml(parsedList);

	}

	public static AttributeStructure getClassAttributres(BodyDeclaration classAttribute) {

		AttributeStructure attrs = new AttributeStructure();
		FieldDeclaration field = (FieldDeclaration) classAttribute;
		System.out.println(field.getModifiers());
		// attrs.setAttributeaccessModifier(field.getModifiers());
		if (field.getModifiers() == ModifierSet.PUBLIC) {
			attrs.setAttributeaccessModifier("public");
		} else if (field.getModifiers() == ModifierSet.PRIVATE) {
			attrs.setAttributeaccessModifier("private");
		}
		attrs.setAttributeName(field.getVariables().get(0).getId().getName());
		attrs.setAttributeType(field.getType().toString());
		return attrs;

	}

	public static MethodStructure getClassMethods(BodyDeclaration classAttribute) {
		MethodStructure methodStruct = new MethodStructure();
		MethodDeclaration methoDec = (MethodDeclaration) classAttribute;
		// System.out.println(field.getModifiers());
		// attrs.setAttributeaccessModifier(field.getModifiers());
		if (methoDec.getModifiers() == ModifierSet.PUBLIC) {
			methodStruct.setMethodAccessModifier("public");
		} else if (methoDec.getModifiers() == ModifierSet.PRIVATE) {
			methodStruct.setMethodAccessModifier("private");
		}
		methodStruct.setMethodName(methoDec.getName());
		methodStruct.setMethodReturnType(methoDec.getType().toString());
		return methodStruct;

	}

	public static void generateUml(List<ClassStructure> parsedList) throws IOException {
		StringBuilder printLine = new StringBuilder();
		printLine.append("@startuml\n");
		printLine.append("skinparam classAttributeIconSize 0\n");
		for (ClassStructure classValues : parsedList) {
			String className = classValues.getClassName();
			System.out.println(className);
			printLine.append("class " + className + " {\n");
			if (classValues.getAttributes() != null) {
				List<AttributeStructure> attrList = classValues.getAttributes();
				for (AttributeStructure attr : attrList) {
					if (attr.getAttributeaccessModifier() == "private") {
						printLine.append("-" + attr.getAttributeName() + " :" + attr.getAttributeType() + "\n");
					} else if (attr.getAttributeaccessModifier() == "public") {
						printLine.append("+" + attr.getAttributeName() + " :" + attr.getAttributeType() + "\n");
					}
				}
			}
			if (classValues.getMethods() != null) {
				List<MethodStructure> metList = classValues.getMethods();
				for (MethodStructure methoDec : metList) {
					if (methoDec.getMethodAccessModifier() == "private") {
						printLine.append(
								"-" + methoDec.getMethodName() + "() :" + methoDec.getMethodReturnType() + "\n");
					} else if (methoDec.getMethodAccessModifier() == "public") {
						printLine.append(
								"+" + methoDec.getMethodName() + "() :" + methoDec.getMethodReturnType() + "\n");
					}
				}
			}
			printLine.append("}\n");
			// }
		}
		/* printLine.append("A -> B\n"); */

		printLine.append("@enduml\n");
		// printLine.append("dfdf");

		SourceStringReader reader = new SourceStringReader(printLine.toString());
		// System.out.println(reader);

		FileOutputStream output = new FileOutputStream(
				new File("E:/workspaces/CMPE202/cmpe202_UMLParser/src/com/parser/test.png"));

		reader.generateImage(output, new FileFormatOption(FileFormat.PNG, false));
	}

}

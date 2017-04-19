package com.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.parser.beans.ClassStructure;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
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
				parsedList.add(parsedStructure);
			}
		}

		generateUml(parsedList);

	}

	public static void generateUml(List<ClassStructure> parsedList) throws IOException {
		StringBuilder printLine = new StringBuilder();
		printLine.append("@startuml\n");
		printLine.append("skinparam classAttributeIconSize 0\n");
		for (ClassStructure classValues : parsedList) {
			String className = classValues.getClassName();
			System.out.println(className);
			printLine.append("class " + className + " {\n");
			printLine.append("}\n");
			// }
		}
			/* printLine.append("A -> B\n"); */
			
			printLine.append("@enduml\n");
		//	printLine.append("dfdf");

			SourceStringReader reader = new SourceStringReader(printLine.toString());
			// System.out.println(reader);

			FileOutputStream output = new FileOutputStream(
					new File("E:/workspaces/CMPE202/cmpe202_UMLParser/src/com/parser/test.png"));

			reader.generateImage(output, new FileFormatOption(FileFormat.PNG, false));
		}
	
}

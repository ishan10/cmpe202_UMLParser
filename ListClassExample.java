package com.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;
import com.parser.ParseStructure;

public class ListClassExample {

	
	
	public static void listClasses(File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			System.out.println(path);
			System.out.println(Strings.repeat("=", path.length()));
			try {
				new VoidVisitorAdapter<Object>() {
					@Override
					public void visit(ClassOrInterfaceDeclaration n, Object arg) {
						ParseStructure parseStructure = new ParseStructure();
						super.visit(n, arg);

						parseStructure.setClassName(n.getName());
						Map<String,String> privateAttributes = parseStructure.getPrivateClassAttributes();
						System.out.println(" * " + n.getName());
					//	System.out.println(" * " + n.getMembers());
						List<BodyDeclaration> bd = n.getMembers();

						
						for(BodyDeclaration member: bd){
							FieldDeclaration field = (FieldDeclaration) member;
						//	Map<Integer, ArrayList<String>> classAttributes = parseStructure.getClassAttributes();
							
							System.out.print(field.getType() + " ");
							System.out.print(field.getModifiers()+ " ");
							System.out.println(field.getVariables().get(0).getId().getName());
							if( field.getModifiers()==2){
								privateAttributes.put(field.getType().toString(), field.getVariables().get(0).getId().getName());
							}
						}
						parseStructure.generateUML(privateAttributes);
					}
				}.visit(JavaParser.parse(file), null);
				System.out.println(); // empty line
			} catch (ParseException | IOException e) {
				new RuntimeException(e);
			}
		}).explore(projectDir);
	}


	public static void main(String[] args) {
		File projectDir = new File("E:/workspaces/CMPE202/JavaParser/src/com/parser");

		listClasses(projectDir);
		//listMethodCalls(projectDir);
	}
	
	
}

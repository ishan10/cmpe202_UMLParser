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
import com.parser.DirExplorer;
import com.parser.ParseStructure;

public class ListClassExample {

	
	
	public static void listClasses(File projectDir, ParseStructure parseStructure) {
		new DirExplorer((level, path, file) -> path.endsWith("A.java"), (level, path, file) -> {
			System.out.println(path);
			System.out.println(Strings.repeat("=", path.length()));
			try {
				new VoidVisitorAdapter<Object>() {
					@Override
					public void visit(ClassOrInterfaceDeclaration n, Object arg) {
						
						super.visit(n, arg);
						//String className = n.getName();
						parseStructure.setClassName(n.getName());
						Map<String,String> privateAttributes = parseStructure.getPrivateClassAttributes();
					
						List<BodyDeclaration> bd = n.getMembers();
						
						for(BodyDeclaration member: bd){
							
							if(member instanceof FieldDeclaration){
							Map<Integer, ArrayList<String>> classAttributes = parseStructure.getClassAttributes();
								FieldDeclaration field = (FieldDeclaration) member;
							if( field.getModifiers()==2){
								privateAttributes.put(field.getType().toString(), field.getVariables().get(0).getId().getName());
							}
							}
						}
						try {
							parseStructure.generateUML(parseStructure);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.visit(JavaParser.parse(file), null);
				System.out.println(); // empty line
			} catch (ParseException | IOException e) {
				new RuntimeException(e);
			}
		}).explore(projectDir);
	}


	public static void main(String[] args) {
		ParseStructure parseStructure = new ParseStructure();
		File projectDir = new File("E:/workspaces/CMPE202/cmpe202_UMLParser/src/com/parser/");
		listClasses(projectDir , parseStructure);
	}
	
	
}

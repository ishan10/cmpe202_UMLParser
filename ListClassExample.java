package com.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;

public class ListClassExample {

	public static void listClasses(File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			System.out.println(path);
			System.out.println(Strings.repeat("=", path.length()));
			try {
				new VoidVisitorAdapter<Object>() {
					@Override
					public void visit(ClassOrInterfaceDeclaration n, Object arg) {
						super.visit(n, arg);
						System.out.println(" * " + n.getName());
						System.out.println(" * " + n.getMembers());
						
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
		//listClasses(projectDir);
		listMethodCalls(projectDir);
	}

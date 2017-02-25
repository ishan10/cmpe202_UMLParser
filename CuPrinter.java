package com.parser;

import java.io.FileInputStream;
import java.util.List;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.TypeDeclaration;

public class CuPrinter {

    public static void main(String[] args) throws Exception {
        // creates an input stream for the file to be parsed
        FileInputStream in = new FileInputStream("E:/workspaces/CMPE202/JavaParser/src/com/parser/A.java");

        // parse the file
        CompilationUnit cu = JavaParser.parse(in);

        List<TypeDeclaration> li = cu.getTypes();
        for (int i = 0;i < li.size();i++){
        	System.out.println(i + " "+ li.get(i));
        }
        
        // prints the resulting compilation unit to default system output
       // System.out.println(cu.toString());
    }
}
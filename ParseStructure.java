package com.parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;


public class ParseStructure {
	private String className;
	 private List<String> classNames;
	 public ParseStructure() {
			this.classNames = new ArrayList<String>();
			this.classAttributes = new TreeMap<Integer, ArrayList<String>>();
			this.privateClassAttributes = new TreeMap<String, String>();
		}
	 
	 
	private Map<Integer, ArrayList<String>> classAttributes;
	private Map<String, String> privateClassAttributes;

	public Map<String, String> getPrivateClassAttributes() {
		return privateClassAttributes;
	}

	public void setPrivateClassAttributes(Map<String, String> privateClassAttributes) {
		this.privateClassAttributes = privateClassAttributes;
	}

	public String getClassName() {
		return className;
	}

	/*public ParseStructure() {
		this.className = "";
		this.classAttributes = new TreeMap<Integer, ArrayList<String>>();
		this.privateClassAttributes = new TreeMap<String, String>();
	}*/

	public void setClassName(String className) {
		this.className = className;
	}

	public Map<Integer, ArrayList<String>> getClassAttributes() {
		return classAttributes;
	}

	public void setClassAttributes(Map<Integer, ArrayList<String>> classAttributes) {
		this.classAttributes = classAttributes;
	}

	/*public List<String> getClassNames() {
		return classNames;
	}



	public void setClassNames(List<String> classNames) {
		this.classNames = classNames;
	}   
*/


	public void generateUML(ParseStructure parseStructure) throws IOException{
		/*for (Map.Entry<String, String> entry : privateAttributes.entrySet()) {
		     System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());
		}*/
		
		String className = parseStructure.getClassName();
		Map<String, String> finalAttributes = parseStructure.getPrivateClassAttributes();
		FileWriter write = new FileWriter("E:/workspaces/CMPE202/JavaParser/src/com/parser/output.txt");
		StringBuilder printLine = new StringBuilder();
		printLine.append("@startuml\n");
		printLine.append("skinparam classAttributeIconSize 0\n");
		/*for(Iterator<String> i = finalClassList.iterator();i.hasNext();){
			String className = i.next();*/
			printLine.append("class "+className +" {\n");
	//	}
		for(Entry<String, String> entry : finalAttributes.entrySet()) {
			  String key = entry.getKey();
			  String value = entry.getValue();

			  printLine.append("- "+key +" "+ value +"\n");
			}
		/*printLine.append("A -> B\n");*/
		printLine.append("}\n");
		printLine.append("@enduml\n");
		printLine.append("dfdf");
		
		SourceStringReader reader = new SourceStringReader(printLine.toString());
		//System.out.println(reader);

        FileOutputStream output = new FileOutputStream(new File("E:/workspaces/CMPE202/JavaParser/src/com/parser/test.png"));

        reader.generateImage(output, new FileFormatOption(FileFormat.PNG, false));

   
	}
	
}

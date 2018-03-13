package Group1;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;

//import org.eclipse.core.runtime.IProgressMonitor 
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;

public class ProcessFile {

	private String type = "default";
	private String path = "default";
	private int declarations = 0;
	private int references = 0;
	
	// FileParserConstructor
	public ProcessFile(String pathName, String type) {
		this.path = pathName;
		this.type = type;
	}
	
	public void parseFile() throws IOException {
		File absPath = new File(path); //converts path to abstract path
		
		if (!absPath.isDirectory()) {
			throw new IOException();
		}
		File[] file = absPath.listFiles();
		String filePath = "";
		
		for (File fi : file) { //loop through file
			filePath = fi.getAbsolutePath();
			if (fi.isFile()) {
				parseIt(fileToString(path));
			}else {
				throw new IOException();
			}
		}
		
		
	}

	private void parseIt(String path) {
		// TODO Auto-generated method stub
		ASTParser parser = ASTParser.newParser(AST.JLS3);					//Creating the AST with the given string.
		parser.setSource(path.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		
		ASTNode node = parser.createAST(null);	//node to look through
		
		switch (type) {
		case "annotation":
			doAnnotation(node);
			break;
		case "class":
			doClass(node);
			break;
		case "enum":
			doEnum(node);
			break;
		case "interface":
			doInterface(node);
			break;
		}
		
		
	}

	private void doInterface(ASTNode node) {
		// TODO Auto-generated method stub
		countIntDec(node);
		countIntRef(node);
	}

	private void doEnum(ASTNode node) {
		// TODO Auto-generated method stub
		countEnumClassDec(node);
		countEnumClassRef(node);
	}

	private void doClass(ASTNode node) {
		// TODO Auto-generated method stub
		countClassDec(node);
		countClassRef(node);
	}

	private void doAnnotation(ASTNode node) {
		// TODO Auto-generated method stub
		countAnnotDec(node);
		countAnnotRef(node);
	}

	private void countIntRef(ASTNode node) {
		// TODO Auto-generated method stub
		
	}

	private void countIntDec(ASTNode node) {
		// TODO Auto-generated method stub
		// want to instantiate ASTVisistor
		int num  = 0;
		ASTVisitor visitor = new ASTVisitor();
		node.accept(visitor);
		if (node.is) {}
	}

	private void countEnumClassRef(ASTNode node) {
		// TODO Auto-generated method stub
		
	}

	private void countEnumClassDec(ASTNode node) {
		// TODO Auto-generated method stub
		
	}

	private void countClassRef(ASTNode node) {
		// TODO Auto-generated method stub
		
	}

	private void countClassDec(ASTNode node) {
		// TODO Auto-generated method stub
		
	}

	private void countAnnotRef(ASTNode node) {
		// TODO Auto-generated method stub
		Types type = new Types();
		node.accept(type);
		references = type.getAnnotRef();
	}

	private void countAnnotDec(ASTNode node) {
		// TODO Auto-generated method stub
		Types type = new Types();
		node.accept(type);
		references = type.getAnnotDec();
		
	}

	private String fileToString(String filePathToString) throws IOException{
		// TODO Auto-generated method stub
		StringBuilder inFile = new StringBuilder();							//Opening the file to read it
		BufferedReader reader = new BufferedReader(new FileReader(filePathToString));
		
		char[] ch = new char[10];
		int num = 0;
		
		while ((num = reader.read(ch)) != -1) {
			String readData = String.valueOf(ch, 0, num);
			inFile.append(readData);
			ch = new char[1024];
		}
		reader.close();
		return inFile.toString();
	}

	public int getRef() {
		// TODO Auto-generated method stub
		return references;
	}

	public int getDec() {
		// TODO Auto-generated method stub
		return declarations;
	}
	
}

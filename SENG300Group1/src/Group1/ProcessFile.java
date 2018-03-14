package Group1;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
//import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;

/*
 * import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;

import javax.swing.ListCellRenderer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
//import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
 */

//import org.eclipse.core.runtime.IProgressMonitor 
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class ProcessFile {

	private String type = "default";
	private String path = "default";
	private boolean hasPackage = false;
	private int declarations = 0;
	private int references = 0;
	
	// FileParserConstructor
	public ProcessFile(String pathName, String type, boolean hasPackage) {
		this.path = pathName;
		this.type = type;
		this.hasPackage = hasPackage;
	}
	
	public void parseFile() throws IOException {
		File home = new File(path);
		File[] files = null;
		try {
			files = home.listFiles(); 
			if (files == null) throw new NullPointerException();
		} catch (NullPointerException e) {
			System.out.println("'" + home + "' does not exist.");
			System.exit(0);  
		}

		for (File f: files) {
			String currentFilePath = f.getAbsolutePath();
			if (f.isFile()) {
				Parser parser = new Parser();
				parser.parseIt(fileToString(currentFilePath), getType(), hasPackage);
				declarations = parser.getDec();
				references = parser.getRef();
			}
		} 		
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
	
	public String getType() {
		return type;
	}
	
}

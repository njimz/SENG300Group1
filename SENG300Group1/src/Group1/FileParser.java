package Group1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
public class FileParser {

	private String type = "default";
	private String path = "default";
	private int declarations = 0;
	private int references = 0;
	
	// FileParserConstructor
	public FileParser(String pathName, String type) {
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
			}
		}
		
		
	}

	private void parseIt(Object fileToString) {
		// TODO Auto-generated method stub
		
	}

	private String fileToString(String path2) throws IOException{
		// TODO Auto-generated method stub
		return null;
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

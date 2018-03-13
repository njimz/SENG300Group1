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
	private int decalarations = 0;
	private int references = 0;
	
	// FileParserConstructor
	public FileParser(String pathName, String type) {
		this.path = pathName;
		this.type = type;
	}
	
	public void parseFile() {
		try {}catch() {}
	}
	
}

package Group1;
import java.io.IOException;
import java.util.Scanner;

import org.xml.sax.Parser;

public class Main {

	public static void main(String[] args) throws IllegalArgumentException {
		String pathName = "";
		String type = "";
		
		boolean  validInput = false;
		while (!validInput) {
			try { // check if user gives 2 arguments
				pathName = args[0];
				type = args[1];

				Scanner reader = new Scanner(System.in);
				System.out.println("Enter a path: ");
				pathName = reader.next();
				System.out.println("Enter a java type: ");
				type = reader.next();
				reader.close();
				if (type.length()==0) {
					System.out.println("Did not enter a Java type!");
					continue;
				}
				validInput = true;
			}
			//throws out of bounds exception if user does not input 2 arguments
			catch (java.lang.ArrayIndexOutOfBoundsException e){ 
				System.out.println("Invalid argument count. Arguments are PATHNAME TYPE!");
			}
		}
		
		ProcessFile fileToProcess = new ProcessFile(pathName, type.toLowerCase());
		try {
			fileToProcess.parseFile();
			System.out.println(type + ". Declarations found: " + fileToProcess.getDec() + 
			"; References found: " + fileToProcess.getRef() + ".");
		}catch (IOException e){
			System.out.println("Parsing Error!");
			System.exit(0);
		}

	}
}



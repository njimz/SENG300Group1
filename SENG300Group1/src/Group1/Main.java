package Group1;
import java.io.IOException;
import java.util.Scanner;

import org.xml.sax.Parser;

public class Main {

	public static void main(String[] args) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		String pathName = "";
		String type = "";
		
		try {
			pathName = args[0];
			type = args[1];
			/*
			Scanner reader = new Scanner(System.in);
			System.out.println("Enter a path: ");
			pathName = reader.next();
			System.out.println("Enter a java type: ");
			type = reader.next();
			reader.close();
			if (type.length()==0) {
				System.out.println("ERROR: Invalid Java type!");
				System.exit(0);
			}*/
		}catch (java.lang.ArrayIndexOutOfBoundsException e){
			System.out.println("INVALID ENTRY!");
			Scanner reader = new Scanner(System.in);
			System.out.println("Enter a path: ");
			pathName = reader.next();
			System.out.println("Enter a java type: ");
			type = reader.next();
			reader.close();
			if (type.length()==0) {
				System.out.println("ERROR: Invalid Java type!");
				System.exit(0);
			}
		}
		FileParser parser = new FileParser(pathName, type);
		try {
			parser.parseFile();
		}catch (IOException e){
			System.out.println("Parsing Error!");
		}
		System.out.println(type + ". Declarations found: " + parser.getDec() + 
				"; References found: " + parser.getRef() + ".");
		}
	}



package Group1;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;

public class Types extends ASTVisitor {
	public String type = "default";
	public String count = "default";
	private static int numRef = 0;
	private static int numDec = 0;
	/*
	public Types(String type, String whatToCount) {
		switch(type) {
		case "annot":
			annotToCount(whatToCount);
			break;
		case "class":
			classToCount(whatToCount);
			break;
		case "enum":
			enumToCount(whatToCount);
			break;
		case "int":
			intToCount(whatToCount);
			break;
			
		}
	}
	
	
	private void intToCount(String whatToCount) {
		// TODO Auto-generated method stub
		
	}

	private void enumToCount(String whatToCount) {
		// TODO Auto-generated method stub
		
	}

	private void classToCount(String whatToCount) {
		// TODO Auto-generated method stub
		
	}

	private void annotToCount(String whatToCount) {
		// TODO Auto-generated method stub
		switch (whatToCount) {
		case"":
		case"":
		}
	}*/

	

	public boolean visitAnnotRef(NormalAnnotation node) {
		System.out.println("Visited a Annotation Ref");		//When finding a node of this type, print
		numRef ++;															//this message and increment the total.
		return false;				 // skip children of this node
	}
	
	public int getAnnotRef() {
		// TODO Auto-generated method stub
		return numRef;
	}
	
	public boolean visitAnnotDec(AnnotationTypeDeclaration node) {
		System.out.println("Visited a Annotation Dec");		//When finding a node of this type, print
		numDec ++;															//this message and increment the total.
		return false;	
	}

	public int getAnnotDec() {
		// TODO Auto-generated method stub
		return numDec;
	}

	public void toDo() {
		// TODO Auto-generated method stub
		
	}

}

package Group1;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;

public class Reference extends ASTVisitor {
	private String type = "";
	private int num = 0;
	
	public Reference(String typePass) {
		this.type = typePass;
		
		switch(type) {
		case "annot":
			visitAnnot();
			break;
		case "class":
			break;
		case "enum":
			break;
		case "int":
			break;
		}
	}
	
	public boolean visitAnnot(NormalAnnotation node) {
		System.out.println("Visited a Annotation Ref");		//When finding a node of this type, print
		num ++;															//this message and increment the total.
		return false;				 // skip children of this node
	}
}

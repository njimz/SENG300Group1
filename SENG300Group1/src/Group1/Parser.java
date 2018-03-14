package Group1;

import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
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

/**
* Parser Class, takes the String file Path and String type to recursively go through nodes to 
* search for occurances of type references and type declarations.
**/
public class Parser {
	private int references = 0;
	private int declarations = 0;
	
	public void parseIt(String path, String type) {
		Map map = JavaCore.getOptions();
		map.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_5);
		map.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_5);
		map.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);
		
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setCompilerOptions(map);
		parser.setSource(path.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setEnvironment(null, null, null, true);
		parser.setUnitName("doesThisMatter.java");
		
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
	 
		cu.accept(new ASTVisitor() {
	 

			public boolean visit(TypeDeclaration node) {
				String name = node.getName().getFullyQualifiedName();	
				if (type.equals(name)) declarations++;
				if (node.getSuperclassType() != null) {
					if (type.equals(node.getSuperclassType().toString())) references++;						
				}
				ITypeBinding nodeBinding = node.resolveBinding();
				if (nodeBinding.getInterfaces() != null) {
					ITypeBinding[] interfaces = nodeBinding.getInterfaces();
					for (ITypeBinding i : interfaces) {
						if (type.equals(i.getQualifiedName())) references++;							
					}
				}
					
				return super.visit(node); 
			}	
			public boolean visit(VariableDeclarationFragment node) {	
				String name = node.resolveBinding().getType().getName();
				if (type.equals(name)) references++;
				return super.visit(node);
			}	
			public boolean visit(MethodDeclaration node) {	
				for (Object o : node.parameters()) {
					SingleVariableDeclaration svd = (SingleVariableDeclaration) o;
					if (type.equals(svd.getType().toString())) references++;
				}
					
				return super.visit(node);
			}
			public boolean visit(MethodInvocation node) {
				return super.visit(node);
			}
			public boolean visit(ClassInstanceCreation node) {
				String name = node.getType().toString();
				if (type.equals(name)) references++;	
				return false; // do not continue 
			}
			public boolean visit(AnnotationTypeDeclaration node) {
				String name = node.getName().getFullyQualifiedName();
				if (type.equals(name)) declarations++;					
				return false; // do not continue 
			}
			public boolean visit(EnumDeclaration node) {
				String name = node.getName().getFullyQualifiedName();
				if (type.equals(name)) declarations++;					
				ITypeBinding e = node.resolveBinding();
				if (e.getInterfaces() != null) {
					ITypeBinding[] interfaces = e.getInterfaces();
					for (ITypeBinding i : interfaces) {
						if (type.equals(i.getQualifiedName())) references++;
					}
				}
				return false; // do not continue 
			}
		});
	}
	
	public int getDec() {
		return declarations;
	}
	
	public int getRef() {
		return references;
	}

}

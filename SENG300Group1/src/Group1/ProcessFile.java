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
		/*
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
		default: // if not valid type, program ends
			System.out.println("Invalid Type!");
			System.exit(0);
		}*/
		
		Map map = JavaCore.getOptions();
		map.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_5);
		map.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_5);
		map.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);


		  

		  
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setCompilerOptions(map);
			parser.setSource(path.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setResolveBindings(true);
			//parser.setEnvironment(null, null, null, true);
			parser.setUnitName("doesThisMatter.java");
			

			
			
			 
			 
			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
	 
			cu.accept(new ASTVisitor() {
	 

				public boolean visit(TypeDeclaration node) {
					String name = node.getName().getFullyQualifiedName();
					
					if (type.equals(name)) declarations++;
					//if (DEBUG) System.out.println("Declaration: " +name);
			
					
					
					if (node.getSuperclassType() != null) {
						if (type.equals(node.getSuperclassType().toString())) references++;
						//if (DEBUG) System.out.println("This class extends " + node.getSuperclassType());
						
					}
					
					ITypeBinding nodeBinding = node.resolveBinding();
					if (nodeBinding.getInterfaces() != null) {
						ITypeBinding[] interfaces = nodeBinding.getInterfaces();
						for (ITypeBinding i : interfaces) {
							if (type.equals(i.getQualifiedName())) references++;
							//if (DEBUG) System.out.println("implements Reference: " + i.getName());
							
						}
					}
					
					return super.visit(node); 
				}
				

				
				
				public boolean visit(VariableDeclarationFragment node) {
					
					String name = node.resolveBinding().getType().getName();
					if (type.equals(name)) references++;

					//if (DEBUG) System.out.println("Variable Reference: " + name);


					return super.visit(node);
				}
				
				

				
				public boolean visit(MethodDeclaration node) {
				
					for (Object o : node.parameters()) {
						SingleVariableDeclaration svd = (SingleVariableDeclaration) o;
						//System.out.println(javaType.equals(svd.getType().toString()));
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
					
					//if (DEBUG) System.out.println("Reference: " + name);
	
					return false; // do not continue 
			}
				

				
				
				public boolean visit(AnnotationTypeDeclaration node) {
					String name = node.getName().getFullyQualifiedName();
					if (type.equals(name)) declarations++;
					
					//if (DEBUG) System.out.println("Declaration: " + name);
					
					return false; // do not continue 
				}
				
				
				public boolean visit(EnumDeclaration node) {
					String name = node.getName().getFullyQualifiedName();
					if (type.equals(name)) declarations++;
					//if (DEBUG) System.out.println("Declaration: " + name);
					
					ITypeBinding e = node.resolveBinding();
					if (e.getInterfaces() != null) {
						ITypeBinding[] interfaces = e.getInterfaces();
						for (ITypeBinding i : interfaces) {
							if (type.equals(i.getQualifiedName())) references++;
							//if (DEBUG) System.out.println("Implements Reference: " + i.getName());
						}
					}
					

					
					return false; // do not continue 
				}
				


			});
		
		
	}
/*
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
		//Types type = new Types();
		Reference ref = new Reference("annot");
		//ref.toDo("annot");
		//type.visitAnnotRef(node);
		node.accept(ref);
		references = type.getAnnotRef();
	}

	private void countAnnotDec(ASTNode node) {
		// TODO Auto-generated method stub
		Types type = new Types();
		node.accept(type);
		references = type.getAnnotDec();
		
	}*/

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

package com.smartgxt.core.rebind;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JConstructor;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.dev.javac.typemodel.JAnnotationType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.smartgxt.core.client.Constants;
import com.smartgxt.core.client.prototypes.Processable;
import com.smartgxt.core.client.prototypes.Prototype;
import com.smartgxt.core.client.prototypes.Prototypeble;
import com.smartgxt.core.client.security.Secured;

/**
 * Prototype Generator.
 * 
 * Only one construtor supported. Field action accessor process only o public
 * fields.
 * 
 * @author Anton Alexeyev
 */

public class PrototypeGenerator extends Generator {

	private TypeOracle oracle;

	private JClassType entryPointType;

	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {

		oracle = context.getTypeOracle();

		entryPointType = oracle.findType(EntryPoint.class.getName());

		List<JClassType> initialClasses = new ArrayList<JClassType>();
		List<JClassType> defferedClasses = new ArrayList<JClassType>();

		@SuppressWarnings("unused")
		PropertyOracle propertyOracle = context.getPropertyOracle();
		String entryPoint = null;

		for (JClassType classType : oracle.getTypes()) {
			if (!classType.equals(entryPointType)
					&& classType.isAssignableTo(entryPointType)
					&& (classType.getName().intern() != Constants.SmartGXT)) {
				entryPoint = classType.getPackage().getName();
			}

		}

		final String genClassName = "PrototypeImpl";

		ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(
				entryPoint, genClassName);
		composer.addImplementedInterface(Prototype.class.getCanonicalName());

		composer.addImport("com.google.gwt.core.client.GWT");
		composer.addImport("com.google.gwt.core.client.RunAsyncCallback");
		composer.addImport("com.smartgxt.core.client.prototypes.*");
		composer.addImport("com.smartgxt.core.client.prototypes.processing.*");
		composer.addImport("com.smartgxt.core.client.security.*");
		composer.addImport("java.util.*");

		for (JClassType classType : oracle.getTypes()) {
			Prototypeble prototypeble = classType
					.getAnnotation(Prototypeble.class);
			if (prototypeble != null) {
				composer.addImport(classType.getQualifiedSourceName());
				if (prototypeble.deffered())
					defferedClasses.add(classType);
				else
					initialClasses.add(classType);
			}

		}

		PrintWriter printWriter = context.tryCreate(logger, entryPoint,
				genClassName);

		if (printWriter != null) {
			SourceWriter sourceWriter = composer.createSourceWriter(context,
					printWriter);
			sourceWriter.println("PrototypeImpl() {");
			sourceWriter.indent();
			sourceWriter.outdent();

			sourceWriter.println("}");

			printInitialMethod(initialClasses, sourceWriter);
			printDefferedMethod(defferedClasses, sourceWriter);

			sourceWriter.commit(logger);

			sourceWriter.outdent();
			sourceWriter.println();
			sourceWriter.println();
			sourceWriter.outdent();

		}
		return composer.getCreatedClassName();
	}

	private void printInitialMethod(List<JClassType> clazzes,
			SourceWriter sourceWriter) {

		sourceWriter.println();
		sourceWriter
				.println("public <T> T newInstance( Class<T> classLiteral, final Object... args ) {");

		sourceWriter.println();
		sourceWriter.indent();

		sourceWriter
				.println("return newInstance(classLiteral.getName(),  args );");
		sourceWriter.outdent();

		sourceWriter.println("}");
		sourceWriter.println();

		sourceWriter.println();
		sourceWriter
				.println("public <T> T newInstance( String classLiteral, final Object... args ) {");

		for (JClassType classType : clazzes) {
			if (classType.isAbstract())
				continue;
			printObjectMethod(sourceWriter, classType, false);
		}

		sourceWriter.println();
		sourceWriter.indent();

		sourceWriter
				.println("assert false : \"Prototyping \" + classLiteral + \" requires Prototypeble annotation!!\";");
		sourceWriter.println("return null;");
		sourceWriter.outdent();

		sourceWriter.println("}");
		sourceWriter.println();

	}

	private void printDefferedMethod(List<JClassType> clazzes,
			SourceWriter sourceWriter) {
		
		sourceWriter.println();
		sourceWriter
				.println("public <T> T newInstance( Class<T>  classLiteral, final AsyncCommand command, final Object... args ) {");

		for (JClassType classType : clazzes) {
			if (classType.isAbstract())
				continue;
			printObjectMethod(sourceWriter, classType, true);
		}

		sourceWriter.println();
		sourceWriter.indent();
		sourceWriter.println("return newInstance(classLiteral.getName(), command, args);");
		sourceWriter.outdent();

		sourceWriter.println("}");
		sourceWriter.println();
		
		
		sourceWriter.println();
		sourceWriter
				.println("public <T> T newInstance( String classLiteral, final AsyncCommand command, final Object... args ) {");

		for (JClassType classType : clazzes) {
			if (classType.isAbstract())
				continue;
			printObjectMethod(sourceWriter, classType, true);
		}

		sourceWriter.println();
		sourceWriter.indent();
		sourceWriter
				.println("assert false : \"Prototyping \" + classLiteral + \" requires Prototypeble annotation!!\";");
		sourceWriter.println("return null;");
		sourceWriter.outdent();

		sourceWriter.println("}");
		sourceWriter.println();

	}

	private void printObjectMethod(SourceWriter sourceWriter,
			JClassType classType, boolean isAsync) {
		sourceWriter.println();
		sourceWriter.indent();

		// if (isAsync)
		// sourceWriter.println("if (classLiteral.equals(\""
		// + classType.getQualifiedSourceName() + "\")) {");
		// else
		sourceWriter.println("if (classLiteral.equals(\""
				+ classType.getQualifiedSourceName() + "\")) {");
		sourceWriter.indent();

		sourceWriter.println("ProcessingUtils.beforeAnyPrototype();");

		if (isAsync) {
			sourceWriter.println("GWT.runAsync(new RunAsyncCallback() {");
			sourceWriter.indent();
			sourceWriter.println("@Override");
			sourceWriter.println("public void onSuccess() {");
			sourceWriter.indent();
		}

		String constructorArgs = "";
		// classType.getConstructor(paramTypes)
		JConstructor[] contrs = classType.getConstructors();
		// TODO multiplay constructors support

		JParameter[] prms = contrs[0].getParameters();

		if (prms != null) {
			for (int i = 0; i < prms.length; i++) {
				if (i == 0) {
					constructorArgs = "("
							+ prms[i].getType().getQualifiedSourceName() + ")"
							+ "args[" + i + "]";

				} else {
					constructorArgs = constructorArgs + ", ("
							+ prms[i].getType().getQualifiedSourceName() + ")"
							+ "args[" + i + "]";
				}
			}
		}

		sourceWriter.println(classType.getQualifiedSourceName()
				+ " obj = null;");

		if (constructorArgs == "") {
			sourceWriter.println("obj = " + "new "
					+ classType.getQualifiedSourceName() + "();");
		} else {
			sourceWriter.println("obj = " + "new "
					+ classType.getQualifiedSourceName() + "("
					+ constructorArgs + ");");

		}

		sourceWriter
				.println("ProcessingUtils.afterObjectCreate(new ProtoClassType(obj, \""
						+ classType.getQualifiedSourceName()
						+ "\", "
						+ classType.getQualifiedSourceName() + ".class));");
		sourceWriter.println("List<String> hiddenForList = new ArrayList();");
		sourceWriter.println("List<String> disabledForList = new ArrayList();");
		for (JField field : classType.getFields()) {
			if (field.isAnnotationPresent(Processable.class))
				if (field.isPublic()) {
					// ProcessingUtils
					sourceWriter
							.println("ProcessingUtils.onFiledsInterator(new ProtoClassType(obj, \""
									+ classType.getQualifiedSourceName()
									+ "\", "
									+ classType.getQualifiedSourceName()
									+ ".class), new ProtoFieldType(obj."
									+ field.getName()
									+ ", \""
									+ field.getName() + "\"));");
				}
			if (field.isAnnotationPresent(Secured.class)) {
				Secured secured = field.getAnnotation(Secured.class);

				sourceWriter.println("hiddenForList = new ArrayList();");
				for (String s : secured.hiddenFor()) {
					sourceWriter.println("hiddenForList.add(\"" + s + "\");");

				}
				sourceWriter.println("disabledForList = new ArrayList();");
				for (String s : secured.disabledFor()) {
					sourceWriter.println("disabledForList.add(\"" + s + "\");");

				}
				sourceWriter
						.println("com.smartgxt.core.client.security.SecurityManager."
								+ "applySecurityRules(obj."
								+ field.getName()
								+ ", hiddenForList, disabledForList); ");
			}
		}

		for (JMethod method : classType.getMethods()) {
			// TODO another way to get is JType is void
			if (method.isAnnotationPresent(Processable.class))
				if (method.isPublic()
						&& (!method.getReturnType().toString().equals("void"))) {
					// ProcessingUtils
					sourceWriter
							.println("ProcessingUtils.onMethodsInterator(new ProtoClassType(obj, \""
									+ classType.getQualifiedSourceName()
									+ "\", "
									+ classType.getQualifiedSourceName()
									+ ".class), new ProtoMethodType(obj."
									+ method.getName()
									+ "(), \""
									+ method.getName() + "\"));");
					// TODO security

				}

		}

		if (isAsync) {
			sourceWriter.println("command.setObject((Object) obj);");
			sourceWriter.println("command.execute();");
			// sourceWriter.println("box.close();");
			sourceWriter.outdent();
			sourceWriter.println("}");

			// TODO !!! add a error message here
			sourceWriter.println("@Override");
			sourceWriter.println("public void onFailure(Throwable reason) {");
			sourceWriter.indent();
			// sourceWriter.println("box.close();");
			sourceWriter.outdent();
			sourceWriter.println("}");

			sourceWriter.outdent();
			sourceWriter.println("});");
		}

		sourceWriter.println("ProcessingUtils.afterAnyPrototype();");

		if (isAsync)
			sourceWriter.println("return null;");
		else {
			// sourceWriter.println("box.close();");
			sourceWriter.println("return (T) obj;");
		}

		sourceWriter.outdent();

		sourceWriter.println("}");
		sourceWriter.outdent();
	}
}

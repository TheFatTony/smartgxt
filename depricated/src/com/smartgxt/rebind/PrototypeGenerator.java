package com.smartgxt.rebind;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.smartgxt.client.commons.Params;
import com.smartgxt.client.prototypes.Processable;
import com.smartgxt.client.prototypes.Prototype;
import com.smartgxt.client.prototypes.Prototypeble;

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
					&& (classType.getName().intern() != Params.NAME)) {
				entryPoint = classType.getPackage().getName();
			}

		}

		final String genClassName = "PrototypeImpl";

		ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(
				entryPoint, genClassName);
		composer.addImplementedInterface(Prototype.class.getCanonicalName());

		composer.addImport("com.google.gwt.core.client.GWT");
		composer.addImport("com.google.gwt.core.client.RunAsyncCallback");
		composer.addImport("com.extjs.gxt.ui.client.widget.Component");
		composer.addImport("com.extjs.gxt.ui.client.widget.MessageBox");
		composer.addImport("com.smartgxt.client.prototypes.*");

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

		for (JClassType classType : clazzes) {
			if (classType.isAbstract())
				continue;
			printObjectMethod(sourceWriter, classType, false);
		}

		sourceWriter.println();
		sourceWriter.indent();

		sourceWriter
				.println("assert false : \"Prototyping \" + classLiteral.toString() + \" requires Prototypeble annotation!!\";");
		sourceWriter.println("return null;");
		sourceWriter.outdent();

		sourceWriter.println("}");
		sourceWriter.println();

	}

	private void printDefferedMethod(List<JClassType> clazzes,
			SourceWriter sourceWriter) {
		sourceWriter.println();

		sourceWriter
				.println("public <T> T newInstance( Class<T> classLiteral, final AsyncCommand command, final Object... args ) {");

		for (JClassType classType : clazzes) {
			if (classType.isAbstract())
				continue;
			printObjectMethod(sourceWriter, classType, true);
		}

		sourceWriter.println();
		sourceWriter.indent();
		sourceWriter
				.println("assert false : \"Prototyping \" + classLiteral.toString() + \" requires Prototypeble annotation!!\";");
		sourceWriter.println("return null;");
		sourceWriter.outdent();

		sourceWriter.println("}");
		sourceWriter.println();

	}

	private void printObjectMethod(SourceWriter sourceWriter,
			JClassType classType, boolean isAsync) {
		sourceWriter.println();
		sourceWriter.indent();

		sourceWriter.println("if (classLiteral.getName().equals(\""
				+ classType.getQualifiedSourceName() + "\")) {");
		sourceWriter.indent();

		// TODO internalization
		sourceWriter
				.println("final MessageBox box = MessageBox.progress(\"Подождите пожалуйста\",");
		sourceWriter.println("\"Загрузка...\", \"Загрузка...\");");

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
				.println("PrototypeUtils.afterObjectCreate(new ProtoClassType(obj, \""
						+ classType.getQualifiedSourceName()
						+ "\", "
						+ classType.getQualifiedSourceName() + ".class));");

		for (JField field : classType.getFields()) {
			if (field.isAnnotationPresent(Processable.class))
				if (field.isPublic()) {
					sourceWriter
							.println("PrototypeUtils.onFiledsInterator(new ProtoClassType(obj, \""
									+ classType.getQualifiedSourceName()
									+ "\", "
									+ classType.getQualifiedSourceName()
									+ ".class), new ProtoFieldType(obj."
									+ field.getName()
									+ ", \""
									+ field.getName() + "\"));");
				}
		}

		for (JMethod method : classType.getMethods()) {
			// TODO another way to get is JType is void
			if (method.isAnnotationPresent(Processable.class))
				if (method.isPublic()
						&& (!method.getReturnType().toString().equals("void"))) {
					sourceWriter
							.println("PrototypeUtils.onMethodsInterator(new ProtoClassType(obj, \""
									+ classType.getQualifiedSourceName()
									+ "\", "
									+ classType.getQualifiedSourceName()
									+ ".class), new ProtoMethodType(obj."
									+ method.getName()
									+ "(), \""
									+ method.getName() + "\"));");
				}
		}

		if (isAsync) {
			sourceWriter.println("command.setObject((Object) obj);");
			sourceWriter.println("command.execute();");
			sourceWriter.println("box.close();");
			sourceWriter.outdent();
			sourceWriter.println("}");

			// TODO !!! add a error message here
			sourceWriter.println("@Override");
			sourceWriter.println("public void onFailure(Throwable reason) {");
			sourceWriter.indent();
			sourceWriter.println("box.close();");
			sourceWriter.outdent();
			sourceWriter.println("}");

			sourceWriter.outdent();
			sourceWriter.println("});");
		}

		if (isAsync)
			sourceWriter.println("return null;");
		else {
			sourceWriter.println("box.close();");
			sourceWriter.println("return (T) obj;");
		}

		sourceWriter.outdent();

		sourceWriter.println("}");
		sourceWriter.outdent();
	}
}

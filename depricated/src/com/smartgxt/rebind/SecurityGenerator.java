package com.smartgxt.rebind;

import java.io.PrintWriter;

import com.extjs.gxt.ui.client.widget.Component;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.smartgxt.client.EntryPointPrototype;
import com.smartgxt.client.prototypes.SecurityPrototype;

/**
 * @author Anton Alexeyev
 * 
 */
public class SecurityGenerator extends Generator {

	// TODO bug with classes with a same name

	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {

		TypeOracle oracle = context.getTypeOracle();

		JClassType entryPointType = oracle.findType(EntryPointPrototype.class
				.getName());
		JClassType componentType = oracle.findType(Component.class.getName());

		String genPackageName = null;

		for (JClassType classType : oracle.getTypes()) {
			if (classType.isAssignableTo(entryPointType)
					&& (!entryPointType.equals(classType)))
				genPackageName = classType.getPackage().getName();
		}

		final String genClassName = "SecurityImpl";

		ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(
				genPackageName, genClassName);
		composer.addImplementedInterface(SecurityPrototype.class
				.getCanonicalName());

		composer.addImport("com.google.gwt.core.client.GWT");
		composer.addImport("com.extjs.gxt.ui.client.widget.Component");
		composer.addImport("com.extjs.gxt.ui.client.GXT");
		composer.addImport("com.smartgxt.client.prototypes.SecurityPrototype");
		composer.addImport("com.smartgxt.client.security.SecurityRules");

		PrintWriter printWriter = context.tryCreate(logger, genPackageName,
				genClassName);

		if (printWriter != null) {
			SourceWriter sourceWriter = composer.createSourceWriter(context,
					printWriter);

			sourceWriter.println("SecurityImpl() {");
			sourceWriter.indent();

			sourceWriter.println("if (!GWT.isScript()) {");
			sourceWriter.indent();

			for (JClassType classType : oracle.getTypes()) {
				if (classType.getQualifiedSourceName().indexOf(genPackageName) != -1) {
					if (componentType.isAssignableFrom(classType)) {
						sourceWriter.println("SecurityRules.preDefineClass(\""
								+ classType.getQualifiedSourceName() + "\",\""
								+ classType.getQualifiedSourceName() + "\");");
					}

					String pkg = classType.getQualifiedSourceName();
					for (JField f : classType.getFields()) {
						if (f.isPublic()) {
							JClassType jClass = f.getType().isClass();
							if ((jClass != null)
									&& componentType.isAssignableFrom(jClass)) {
								sourceWriter
										.println("SecurityRules.preDefineClass(\""
												+ pkg
												+ "."
												+ f.getName()
												+ "\",\""
												+ jClass.getQualifiedSourceName()
												+ "\");");
							}
						}
					}
				}
			}

			sourceWriter.outdent();
			sourceWriter.println("}");

			sourceWriter.outdent();
			sourceWriter.println("}");

			sourceWriter.commit(logger);
		}

		return composer.getCreatedClassName();

	}
}

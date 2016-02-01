package org.agilemicroservices.java2js.emit.extjs;

import org.agilemicroservices.java2js.emit.*;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/**
 * <code>ExtJSEmitter</code> is an <code>Emitter</code> implementation targeting ExtJS, generating
 * <code>Ext.data.Model</code> subclasses for service interfaces and JavaBeans.
 */
public class ExtJsEmitter implements Emitter
{

    // defines the mappings between java types and Ext.data.Types.
    private static final Map<TypeModel, String> typeModelToExtTypeNameMap = new HashMap<>();

    static
    {
        typeModelToExtTypeNameMap.put(NamespaceModel.BOOLEAN, "bool");
        typeModelToExtTypeNameMap.put(NamespaceModel.BOOLEAN_BOXED, "bool");

        typeModelToExtTypeNameMap.put(NamespaceModel.BYTE, "int");
        typeModelToExtTypeNameMap.put(NamespaceModel.BYTE_BOXED, "int");
        typeModelToExtTypeNameMap.put(NamespaceModel.SHORT, "int");
        typeModelToExtTypeNameMap.put(NamespaceModel.SHORT_BOXED, "int");
        typeModelToExtTypeNameMap.put(NamespaceModel.INT, "int");
        typeModelToExtTypeNameMap.put(NamespaceModel.INT_BOXED, "int");
        typeModelToExtTypeNameMap.put(NamespaceModel.LONG, "int");
        typeModelToExtTypeNameMap.put(NamespaceModel.LONG_BOXED, "int");

        typeModelToExtTypeNameMap.put(NamespaceModel.FLOAT, "float");
        typeModelToExtTypeNameMap.put(NamespaceModel.FLOAT_BOXED, "float");
        typeModelToExtTypeNameMap.put(NamespaceModel.DOUBLE, "float");
        typeModelToExtTypeNameMap.put(NamespaceModel.DOUBLE_BOXED, "float");

        typeModelToExtTypeNameMap.put(NamespaceModel.CHAR, "string");
        typeModelToExtTypeNameMap.put(NamespaceModel.CHAR_BOXED, "string");
        typeModelToExtTypeNameMap.put(NamespaceModel.STRING, "string");

        typeModelToExtTypeNameMap.put(NamespaceModel.BIG_INTEGER, "int");
        typeModelToExtTypeNameMap.put(NamespaceModel.BIG_DECIMAL, "float");

        typeModelToExtTypeNameMap.put(NamespaceModel.DATE, "date");
        typeModelToExtTypeNameMap.put(NamespaceModel.LOCAL_DATE, "date");
        typeModelToExtTypeNameMap.put(NamespaceModel.LOCAL_TIME, "date");
        typeModelToExtTypeNameMap.put(NamespaceModel.LOCAL_DATE_TIME, "date");
    }

    private String indent = "    ";
    private String outputPackageName;


    /**
     * Creates an initialized instance ready to emit code.
     *
     * @param writer            the write used to generate output.
     * @param outputPackageName the destination package for generated code, or <code>null</code> to use the Java package
     *                          names.
     */
    public ExtJsEmitter(String outputPackageName)
    {
        this.outputPackageName = outputPackageName;
    }


    @Override
    public void emit(NamespaceModel namespaceModel, PrintWriter writer)
    {
        writer.println("// generated by java2js on " + LocalDateTime.now().toString());
        writer.println();
        namespaceModel.getTypeModels().forEach(typeModel -> emitType(typeModel, writer));
    }

    private void emitType(TypeModel typeModel, PrintWriter writer)
    {
        if (!typeModel.isBuiltIn() && !typeModel.getJavaClass().isArray())
        {
            writer.println("// generated from " + typeModel.getName());

            String packageName = outputPackageName != null ? outputPackageName : typeModel.getPackageName();
            String qualifiedName = QualifiedName.format(typeModel.getSimpleName(), packageName);
            writer.println("Ext.define('" + qualifiedName + "', {");

            // superclass
            String baseClass = "Ext.data.Model";
            TypeModel superclassEntity = typeModel.getSuperclassModel();
            if (superclassEntity != null)
            {
                String superclassPackageName =
                        outputPackageName != null ? outputPackageName : superclassEntity.getPackageName();
                baseClass = QualifiedName.format(superclassEntity.getSimpleName(), superclassPackageName);
            }
            writer.println(indent + "extend: '" + baseClass + "',");

            // properties
            writer.println(indent + "fields: [");
            typeModel.getProperties().forEach(propertyModel -> emitProperty(propertyModel, writer));
            writer.println(indent + "],");

            writer.println("});");
            writer.println();
        }
    }

    private void emitProperty(PropertyModel propertyModel, PrintWriter writer)
    {
        if (propertyModel.getPropertyType().isBuiltIn())
        {
            String indent = this.indent + this.indent;
            writer.println(indent + "{ name: '" + propertyModel.getName()
                                   + "', type: '" + typeModelToExtName(propertyModel.getPropertyType()) + "' },");
        }
    }

    private String typeModelToExtName(TypeModel typeModel)
    {
        if (typeModel.getJavaClass().isArray()) {
            return "auto";
        }

        String typeName = typeModelToExtTypeNameMap.get(typeModel);
        if (typeName == null)
        {
            typeName = QualifiedName.format(typeModel.getSimpleName(), outputPackageName);
        }

        return typeName;
    }
}
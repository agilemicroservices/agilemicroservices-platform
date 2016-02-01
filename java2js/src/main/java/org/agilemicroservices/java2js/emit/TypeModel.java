package org.agilemicroservices.java2js.emit;

import java.util.ArrayList;
import java.util.List;


/**
 * <code>TypeModel</code> describes the properties of a JavaBean agnostic of a specific code generation target, i.e.
 * ExtJS JavaScript.
 *
 * @see NamespaceModel
 * @see PropertyModel
 */
public class TypeModel
{
    private TypeModel superclassModel;
    private List<PropertyModel> properties = new ArrayList<>();
    private String packageName;
    private String simpleName;
    private boolean builtIn;
    private Class<?> javaClass;


    public TypeModel(String simpleName, String packageName, Class<?> javaClass, TypeModel superclassModel)
    {
        this(simpleName, packageName, javaClass, superclassModel, false);
    }

    public TypeModel(String simpleName, String packageName, Class<?> javaClass, boolean builtIn)
    {
        this(simpleName, packageName, javaClass, null, builtIn);
    }

    public TypeModel(String simpleName, String packageName, Class<?> javaClass, TypeModel superclassModel,
                     boolean builtIn)
    {
        this.simpleName = simpleName;
        this.packageName = packageName;
        this.javaClass = javaClass;
        this.superclassModel = superclassModel;
        this.builtIn = builtIn;
    }


    public PropertyModel newProperty(String propertyName, TypeModel propertyType)
    {
        PropertyModel propertyModel = new PropertyModel(propertyName, propertyType);
        properties.add(propertyModel);
        return propertyModel;
    }


    public String getName()
    {
        return QualifiedName.format(simpleName, packageName);
    }

    public String getSimpleName()
    {
        return simpleName;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public List<PropertyModel> getProperties()
    {
        return properties;
    }

    public boolean isBuiltIn()
    {
        return builtIn;
    }

    public Class<?> getJavaClass()
    {
        return javaClass;
    }

    public TypeModel getSuperclassModel() {
        return superclassModel;
    }
}
package org.agilemicroservices.java2js.emit;


/**
 * <code>PropertyModel</code> defines a JavaBean property agnostic of a specific code generation target, i.e. ExtJS
 * JavaScript.
 *
 * @see TypeModel
 * @see NamespaceModel
 */
public class PropertyModel
{
    private String propertyName;
    private TypeModel propertyType;


    public PropertyModel(String propertyName, TypeModel propertyType)
    {
        this.propertyName = propertyName;
        this.propertyType = propertyType;
    }


    public String getName()
    {
        return propertyName;
    }

    public TypeModel getPropertyType()
    {
        return propertyType;
    }
}
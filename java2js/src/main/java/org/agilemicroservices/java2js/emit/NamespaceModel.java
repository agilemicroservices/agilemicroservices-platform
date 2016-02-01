package org.agilemicroservices.java2js.emit;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * <code>NamespaceModel</code> describes a collection of types, agnostic of a specific code generation target, i.e.
 * ExtJS.
 *
 * @see TypeModel
 * @see PropertyModel
 */
public class NamespaceModel
{
    public static final TypeModel BOOLEAN = new TypeModel("boolean", null, boolean.class, true);
    public static final TypeModel BOOLEAN_BOXED = new TypeModel("Boolean", "java.lang", Boolean.class, true);
    public static final TypeModel CHAR = new TypeModel("char", null, char.class, true);
    public static final TypeModel CHAR_BOXED = new TypeModel("Character", "java.lang", Character.class, true);

    public static final TypeModel BYTE = new TypeModel("byte", null, byte.class, true);
    public static final TypeModel BYTE_BOXED = new TypeModel("Byte", "java.lang", Byte.class, true);
    public static final TypeModel SHORT = new TypeModel("short", null, short.class, true);
    public static final TypeModel SHORT_BOXED = new TypeModel("Short", "java.lang", Short.class, true);
    public static final TypeModel INT = new TypeModel("int", null, int.class, true);
    public static final TypeModel INT_BOXED = new TypeModel("Integer", "java.lang", Integer.class, true);
    public static final TypeModel LONG = new TypeModel("long", null, long.class, true);
    public static final TypeModel LONG_BOXED = new TypeModel("Long", "java.lang", Long.class, true);

    public static final TypeModel FLOAT = new TypeModel("float", null, float.class, true);
    public static final TypeModel FLOAT_BOXED = new TypeModel("Float", "java.lang", Float.class, true);
    public static final TypeModel DOUBLE = new TypeModel("double", null, double.class, true);
    public static final TypeModel DOUBLE_BOXED = new TypeModel("Double", "java.lang", Double.class, true);

    public static final TypeModel STRING = new TypeModel("String", "java.lang", String.class, true);

    public static final TypeModel BIG_INTEGER = new TypeModel("BigInteger", "java.math", BigInteger.class, true);
    public static final TypeModel BIG_DECIMAL = new TypeModel("BigDecimal", "java.math", BigDecimal.class, true);

    public static final TypeModel DATE = new TypeModel("Date", "java.util", Date.class, true);
    public static final TypeModel LOCAL_DATE = new TypeModel("LocalDate", "java.time", LocalDate.class, true);
    public static final TypeModel LOCAL_TIME = new TypeModel("LocalTime", "java.time", LocalTime.class, true);
    public static final TypeModel
            LOCAL_DATE_TIME = new TypeModel("LocalDateTime", "java.time", LocalDateTime.class, true);

    private Map<String, TypeModel> qualifiedNameToTypeModelMap = new HashMap<>();


    public NamespaceModel()
    {
        qualifiedNameToTypeModelMap.put(BOOLEAN.getName(), BOOLEAN);
        qualifiedNameToTypeModelMap.put(BOOLEAN_BOXED.getName(), BOOLEAN_BOXED);
        qualifiedNameToTypeModelMap.put(CHAR.getName(), BOOLEAN);
        qualifiedNameToTypeModelMap.put(CHAR_BOXED.getName(), BOOLEAN_BOXED);

        qualifiedNameToTypeModelMap.put(BYTE.getName(), BYTE);
        qualifiedNameToTypeModelMap.put(BYTE_BOXED.getName(), BYTE_BOXED);
        qualifiedNameToTypeModelMap.put(SHORT.getName(), SHORT);
        qualifiedNameToTypeModelMap.put(SHORT_BOXED.getName(), SHORT_BOXED);
        qualifiedNameToTypeModelMap.put(INT.getName(), INT);
        qualifiedNameToTypeModelMap.put(INT_BOXED.getName(), INT_BOXED);
        qualifiedNameToTypeModelMap.put(LONG.getName(), LONG);
        qualifiedNameToTypeModelMap.put(LONG_BOXED.getName(), LONG_BOXED);

        qualifiedNameToTypeModelMap.put(FLOAT.getName(), FLOAT);
        qualifiedNameToTypeModelMap.put(FLOAT_BOXED.getName(), FLOAT_BOXED);
        qualifiedNameToTypeModelMap.put(DOUBLE.getName(), DOUBLE);
        qualifiedNameToTypeModelMap.put(DOUBLE_BOXED.getName(), DOUBLE_BOXED);

        qualifiedNameToTypeModelMap.put(STRING.getName(), STRING);

        qualifiedNameToTypeModelMap.put(BIG_INTEGER.getName(), BIG_INTEGER);
        qualifiedNameToTypeModelMap.put(BIG_DECIMAL.getName(), BIG_DECIMAL);

        qualifiedNameToTypeModelMap.put(DATE.getName(), DATE);
        qualifiedNameToTypeModelMap.put(LOCAL_DATE.getName(), LOCAL_DATE);
        qualifiedNameToTypeModelMap.put(LOCAL_TIME.getName(), LOCAL_TIME);
        qualifiedNameToTypeModelMap.put(LOCAL_DATE_TIME.getName(), LOCAL_DATE_TIME);
    }


    // TODO only class is needed, eliminate other arguments
    public TypeModel newType(String typeName, String packageName, Class<?> clazz, TypeModel superclassModel)
    {
        TypeModel typeModel = new TypeModel(typeName, packageName, clazz, superclassModel);
        String qualifiedName = QualifiedName.format(typeName, packageName);
        qualifiedNameToTypeModelMap.put(qualifiedName, typeModel);
        return typeModel;
    }

    public TypeModel getTypeModel(String qualifiedTypeName)
    {
        return qualifiedNameToTypeModelMap.get(qualifiedTypeName);
    }

    public Collection<TypeModel> getTypeModels()
    {
        return qualifiedNameToTypeModelMap.values();
    }
}
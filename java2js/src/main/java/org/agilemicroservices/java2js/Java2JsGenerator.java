package org.agilemicroservices.java2js;

import org.agilemicroservices.java2js.emit.Emitter;
import org.agilemicroservices.java2js.emit.NamespaceModel;
import org.agilemicroservices.java2js.emit.TypeModel;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import javax.xml.bind.annotation.XmlRootElement;
import java.beans.PropertyDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;


/**
 * <code>Java2JsGenerator</code> generates serialization-compatible definitions of JavaBeans for foreign language targets,
 * such as ExtJS.
 */
public class Java2JsGenerator
{
    private final NamespaceModel namespaceModel = new NamespaceModel();
    private final String[] inputPackageNames;
    private final Emitter emitter;


    /**
     * Creates a fully initialized instance ready to to generate code.
     *
     * @param inputPackageNames the input package(s) containing the classes to generated code from.
     *                          names.
     * @throws NullPointerException if input packages is <code>null</code>.
     * @see #generate(String)
     * @see #generate(PrintWriter)
     */
    public Java2JsGenerator(String[] inputPackageNames, Emitter emitter)
    {
        if (inputPackageNames == null)
        {
            throw new NullPointerException("Import package names may not be null.");
        }
        this.inputPackageNames = inputPackageNames;
        this.emitter = emitter;
    }


    /**
     * Generates a JavaScript file from the configured input and output packages.
     *
     * @param fileName the name of the file to write to.
     * @throws IOException if the file can't be created or written.
     */
    public void generate(String fileName) throws IOException
    {
        if (fileName == null)
        {
            throw new NullPointerException("File name may not be null.");
        }
        PrintWriter writer = new PrintWriter(new FileWriter(fileName));
        generate(writer);
        writer.close();
    }

    /**
     * Generates a stream of JavaScript from the configured input and output packages.
     *
     * @param writer
     * @throws IOException if thrown by the writer.
     */
    public void generate(PrintWriter writer) throws IOException
    {
        if (writer == null)
        {
            throw new NullPointerException("Writer may not be null.");
        }
        loadClasses().forEach(this::buildType);
        emitter.emit(namespaceModel, writer);
    }

    private List<Class<?>> loadClasses() throws IOException
    {
        List<Class<?>> classes = new ArrayList<>();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);

        for (String o : inputPackageNames)
        {
            String packageResourceName = ClassUtils.convertClassNameToResourcePath(o);
            Resource resources[] = resolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                                                                 packageResourceName + "/**/*.class");
            for (Resource j : resources)
            {
                if (j.isReadable())
                {
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(j);
                    if (isCandidateClass(metadataReader.getClassMetadata()))
                    {
                        try
                        {
                            Class<?> clazz = Class.forName(metadataReader.getClassMetadata().getClassName());
                            if (clazz.isAnnotationPresent(XmlRootElement.class))
                            {
                                classes.add(clazz);
                            }
                        }
                        catch (ClassNotFoundException e)
                        {
                            // TODO should this be handled?
                            // ignore
                        }
                    }
                }
            }
        }

        return classes;
    }

    private boolean isCandidateClass(ClassMetadata classMetadata)
    {
        // allow abstract classes as java beans, javascript has no analog
        return !classMetadata.isInterface() && !classMetadata.isAnnotation();
    }

    private TypeModel buildType(Class<?> beanClass)
    {
        TypeModel typeModel = namespaceModel.getTypeModel(beanClass.getName());
        if (typeModel == null)
        {
            Class<?> superclass = beanClass.getSuperclass();
            TypeModel superclassModel = null;
            if (superclass != null && superclass != Object.class)
            {
                superclassModel = buildType(superclass);
            }

            if (beanClass.isArray())
            {
                TypeModel componentType = buildType(beanClass.getComponentType());
                typeModel = namespaceModel.newType(beanClass.getSimpleName(), null, beanClass, superclassModel,
                                                   componentType.isBuiltIn());
            }
            else
            {
                typeModel = namespaceModel.newType(beanClass.getSimpleName(), beanClass.getPackage().getName(),
                                                   beanClass, superclassModel);
                buildPropertiesFromFields(typeModel);
                buildPropertiesFromMethods(typeModel);
            }
        }

        return typeModel;
    }

    private void buildPropertiesFromFields(TypeModel typeModel)
    {
        Class<?> beanClass = typeModel.getJavaClass();
        for (Field o : beanClass.getDeclaredFields())
        {
            if (isVisible(typeModel, o))
            {
                TypeModel propertyType = buildType(o.getType());
                typeModel.newProperty(o.getName(), propertyType);
            }
        }
    }

    private void buildPropertiesFromMethods(TypeModel typeModel)
    {
        PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(typeModel.getJavaClass());
        for (PropertyDescriptor o : descriptors)
        {
            if (isVisible(typeModel, o))
            {
                TypeModel propertyType = buildType(o.getPropertyType());
                typeModel.newProperty(o.getName(), propertyType);
            }
        }
    }

    private boolean isVisible(TypeModel typeModel, PropertyDescriptor property)
    {
        boolean visible = false;

        if (property.getName().equals("class"))
        {
            return false;
        }

        Method method = property.getReadMethod();
        if (method == null)
        {
            method = property.getWriteMethod();
        }

        if ((method.getModifiers() & Modifier.PUBLIC) != 0 && method.getDeclaringClass() == typeModel.getJavaClass())
        {
            visible = true;
        }

        return visible;
    }

    private boolean isVisible(TypeModel typeModel, Field field)
    {
        boolean visible = false;
        if ((field.getModifiers() & Modifier.PUBLIC) != 0 && field.getDeclaringClass() == typeModel.getJavaClass())
        {
            visible = true;
        }
        return visible;
    }
}
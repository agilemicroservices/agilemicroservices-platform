package org.agilemicroservices.java2js.emit;

import org.springframework.util.StringUtils;


public final class QualifiedName
{

    private QualifiedName()
    {
        // static class
    }

    /**
     * Creates a qualified name from a simple name and package, properly handling null/empty packages.
     *
     * @param simpleTypeName the simple name of the type.
     * @param packageName    the name of the package.
     * @return a qualified name from a simple name and package, properly handling null/empty packages.
     */
    public static String format(String simpleTypeName, String packageName)
    {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.hasText(packageName))
        {
            builder.append(packageName);
            builder.append('.');
        }
        builder.append(simpleTypeName);

        return builder.toString();
    }
}
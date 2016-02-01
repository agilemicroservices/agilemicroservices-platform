package org.agilemicroservices.java2js.servlet;

import org.agilemicroservices.java2js.Java2JsGenerator;
import org.agilemicroservices.java2js.emit.Emitter;
import org.agilemicroservices.java2js.emit.extjs.ExtJsEmitter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


/**
 * <code>Java2JsServlet</code> provides a mechanism for accessing <code>java2js</code> generated code over the web.
 * Using this servlet, JavaScript clients can load <code>java2js</code> generated code like any other JavaScript file
 * available on the web.
 * <p>
 */
public class Java2JsServlet extends HttpServlet
{
    public static final String ALLOWED_PACKAGE_NAMES_PARAM = "packages";
    private String[] allowedPackageNames = new String[0];


    /**
     * Initializes the servlet with configuration parameters.  The <code>packages</code> initialization parameter must
     * specify allowed packages, requests for unlisted packages will be denied.  Any classes referenced by a class in an
     * allowed package is also allowed, although its package may not be requested directly.
     *
     * @param config the parameters used to configure the instance.
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        String allowedPackageNamesStr = config.getInitParameter(ALLOWED_PACKAGE_NAMES_PARAM);
        if (allowedPackageNamesStr != null)
        {
            allowedPackageNames = allowedPackageNamesStr.split("\\s*,\\s*");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String[] inputPackageNames = req.getParameterValues("i");
        String outputPackageName = req.getParameter("o");

        if (inputPackageNames == null || inputPackageNames.length <= 0)
        {
            inputPackageNames = allowedPackageNames;
        }
        else
        {
            // verify requested packages are allowed
            for (String o : inputPackageNames)
            {
                if (Arrays.binarySearch(allowedPackageNames, o) < 0)
                {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            }

        }

        // generate javascript
        Emitter emitter = new ExtJsEmitter(outputPackageName);
        Java2JsGenerator generator = new Java2JsGenerator(inputPackageNames, emitter);
        generator.generate(resp.getWriter());
    }
}
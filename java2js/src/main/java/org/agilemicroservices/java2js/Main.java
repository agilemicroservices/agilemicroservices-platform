package org.agilemicroservices.java2js;

import org.agilemicroservices.java2js.emit.extjs.ExtJsEmitter;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.io.PrintWriter;


/**
 * <code>Main</code> implements the command line interface for <code>java2js</code>.
 * <p>
 * The following example illustrates basic usage.  The <code>myclasses</code>, <code>com.mypackage</code>,
 * <code>com.anotherpackage</code>, <code>jspackage</code> and <code>domain.js</code> should be replaced with
 * application specific values.
 *
 * <pre>
 * <code>
 * java -cp "java2js.jar:myclasses" org.agilemicroservices.java2js.Main -i com.mypackage -i com.anotherpackage -o jspackage domain.js
 * </code>
 * </pre>
 */
public class Main
{
    private static final String TOOL_NAME = "java2js";
    private static final Options options;


    static {
        options = new Options();
        options.addOption("h", "help", false, "Display help information.");
        options.addOption("p", "output-package", true, "Destination package for generated code, defaults to Java package.");
        options.addOption("f", "output-file", true, "Destination file for generated code, defaults to domain.js.");
    }


    private static void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        PrintWriter writer = new PrintWriter(System.err);
        formatter.printUsage(writer, 80, TOOL_NAME, options);
        writer.close();
    }

    private static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -cp \"" + TOOL_NAME + ".jar:classes\" " + Main.class.getName(), options);
    }

    public static void main(String[] args) {
        String[] inputPackageNames = null;
        String outputPackageName = null;
        String outputFileName = "domain.js";

        try
        {
            CommandLineParser parser = new DefaultParser();
            CommandLine commandLine = parser.parse(options, args);

            if (commandLine.hasOption('h')) {
                printHelp();
                System.exit(0);
            }

            if (commandLine.hasOption('p')) {
                outputPackageName = commandLine.getOptionValue('p');
            }

            if (commandLine.hasOption('f')) {
                outputFileName = commandLine.getOptionValue('f');
            }

            inputPackageNames = commandLine.getArgs();
            if (inputPackageNames.length != 1) {
                System.err.println("At least one source package must be specified.");
                printUsage();
                System.exit(1);
            }
        }
        catch (ParseException e) {
            System.err.println(e.getMessage());
            printUsage();
            System.exit(1);
        }

        try
        {
            ExtJsEmitter emitter = new ExtJsEmitter(outputPackageName);
            Java2JsGenerator generator = new Java2JsGenerator(inputPackageNames, emitter);
            generator.generate(outputFileName);
        }
        catch (IOException e) {
            System.err.println("Unable to write to file '" + outputFileName + "'.");
            System.exit(1);
        }
    }
}

package org.agilemicroservices.java2js;

import org.agilemicroservices.java2js.emit.extjs.ExtJsEmitter;
import org.junit.Test;

import java.io.IOException;


public class Java2JsGeneratorTest
{

    @Test
    public void test() throws IOException
    {
        String[] packageNames = new String[]{"org.agilemicroservices.java2js.mock"};
        ExtJsEmitter emitter = new ExtJsEmitter("example");
        Java2JsGenerator generator = new Java2JsGenerator(packageNames, emitter);
        generator.generate("/tmp/test.dom.js");
    }
}

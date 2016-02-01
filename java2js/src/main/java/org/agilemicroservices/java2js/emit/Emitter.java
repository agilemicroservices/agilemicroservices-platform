package org.agilemicroservices.java2js.emit;

import java.io.PrintWriter;


/**
 * <code>Emitter</code> defines the interface for objects that generate a target syntax given an
 * <code>NamespaceModel</code>.
 */
public interface Emitter
{

    void emit(NamespaceModel namespaceModel, PrintWriter writer);
}

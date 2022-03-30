package de.jpp.io;

import de.jpp.model.XYNode;
import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.Graph;

import org.jdom2.Element;

public abstract class GXLWriterTemplate {

    public GXLWriterTemplate(){}
    public abstract String write(Graph g);
    public abstract Element writeNode(XYNode x);
    public abstract Element writeEdge(Edge x);
    public abstract String calculateId(XYNode x);
    public abstract String calculateId(Edge x);

}

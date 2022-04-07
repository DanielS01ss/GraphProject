package de.jpp.io;

import de.jpp.io.interfaces.ParseException;
import de.jpp.model.XYNode;
import de.jpp.model.interfaces.Graph;

import javax.swing.text.Element;
import java.util.HashMap;
import java.util.Optional;

public abstract class GXLReaderTemplate {

    public  GXLReaderTemplate(){};
    public abstract Graph read(String a) throws ParseException;
    protected abstract void addEdge(Graph g, Element e, HashMap<String, XYNode> map);
    public abstract Graph createGraph();
    public abstract String readNodeId(XYNode x,Element e);
    public abstract XYNode readNode(Element e);
    public abstract <N> Optional<N> readAnnotation(Element e);


}

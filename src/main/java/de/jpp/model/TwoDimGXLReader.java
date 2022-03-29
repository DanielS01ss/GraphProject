package de.jpp.model;

import de.jpp.GXLReaderTemplate;
import de.jpp.model.interfaces.Graph;
import org.jdom2.Attribute;
import org.jdom2.Element;


import javax.print.attribute.AttributeSet;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class TwoDimGXLReader extends GXLReaderTemplate {

    TwoDimGXLReader(){}

    public String getAttrValue(Element el, String s)
    {
        Attribute attr = el.getAttribute(s);

        return attr.toString();
    }

    @Override
    public Graph read(String a) {
        return null;
    }

    @Override
    protected void addEdge(Graph g, javax.swing.text.Element e, HashMap<String, XYNode> map) {

    }


    @Override
    public Graph createGraph() {
        return null;
    }

    @Override
    public String readNodeId(XYNode x, javax.swing.text.Element e) {
        return null;
    }

    @Override
    public XYNode readNode(javax.swing.text.Element e) {
        return null;
    }

    @Override
    public <N> Optional<N> readAnnotation(javax.swing.text.Element e) {
        return Optional.empty();
    }


}

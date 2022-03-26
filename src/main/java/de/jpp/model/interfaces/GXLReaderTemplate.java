package de.jpp.model.interfaces;

import de.jpp.io.interfaces.GraphReader;
import de.jpp.io.interfaces.ParseException;
import de.jpp.model.GraphImpl;
import de.jpp.model.TwoDimGraph;
import de.jpp.model.XYNode;
import net.sourceforge.gxl.GXLGraph;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GXLReaderTemplate implements GraphReader {

    public GXLReaderTemplate() {
    }

    @Override
    public Graph read(Object input) throws ParseException {
        GXLGraph graph = new GXLGraph((String) input);
        return null;
    }

    public Graph read(String input)
    {
        return new TwoDimGraph();
    }

    private void addEdge(TwoDimGraph graph, Element element, HashMap<String, XYNode> map)
    {
        graph.addEdge(new XYNode(readNode(element)), new XYNode(readNode(element)), readAnnotation(element));
    }

    public String readNodeId(XYNode node, Element element) throws Exception
    {
        Attribute attribute = element.getAttribute("id");
        if (attribute.toString().equals(node.getLabel()))
             return attribute.toString();
        else
            throw new Exception("The ids doesn't match!");
    }

    public XYNode readNode(Element element)
    {
        String label;
        double x = 0;
        double y = 0;
        label = element.getAttributeValue("id");
        if(element.getChild("x") != null)
        {
            x = Double.parseDouble(element.getChildText("x"));
        }
        if(element.getChild("y") != null)
        {
            y = Double.parseDouble(element.getChildText("y"));
        }
        if(element.getChild("value") != null)
        {
            x = Double.parseDouble(element.getChildText("x"));
        }

        if(y != 0)
        {
            try {
                return new XYNode(label,x,y);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
            return new XYNode(label,x);
        return new XYNode();
    }

    public Optional readAnnotation(Element element)
    {
        if(element.getName().equals("TwoDimGraph"))
        {
            return Optional.of(element.getAttributeValue("weight"));
        }
        else
            return Optional.of(element.getAttributeValue("annotation"));
    }

    public Graph createGraph(boolean ok)
    {
        if(ok)
            return new GraphImpl();
        else
            return new TwoDimGraph();
    }
}

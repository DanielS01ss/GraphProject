package de.jpp.io;

import de.jpp.io.interfaces.ParseException;
import de.jpp.model.TwoDimGraph;
import de.jpp.model.XYNode;
import de.jpp.model.interfaces.Graph;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TwoDimGXLReader extends GXLReaderTemplate {

    TwoDimGXLReader(){}

    public String getAttrValue(Element el, String s)
    {
        Attribute attr = el.getAttribute(s);

        return attr.toString();
    }

    @Override
    public Graph read(String a) throws ParseException {
        TwoDimGraph gr = new TwoDimGraph();
        List<XYNode> nodes = new ArrayList<XYNode>();
        try{
            SAXBuilder sax = new SAXBuilder();
            InputStream stream = new ByteArrayInputStream(a.getBytes(StandardCharsets.UTF_8));
            Document doc = sax.build(stream);
            Element rootNode = doc.getRootElement();
            List<Element> list = rootNode.getChildren("graph");

            for(Element  target:list)
            {
                boolean labelFound = false;
                boolean xFound = false;
                boolean yFound = false;
                List<Element> el = target.getChildren("node");
                double x=-1.0,y=-1.0;
                String label;
                String textLabel;
                for(Element it:el)
                {
                    //elements that are child of a node element
                    List<Element> nodeChilds = it.getChildren();
                    label = it.getAttributeValue("id");

                    for(Element nodeChild:nodeChilds)
                    {
                        if(nodeChild.getAttributeValue("name").equals("x"))
                        {
                            if(nodeChild.getChildren().size() ==0)
                            {
                                throw new ParseException();

                            }
                            x = Double.parseDouble(nodeChild.getChildren().get(0).getText());
                            xFound = true;
                        } else if (nodeChild.getAttributeValue("name").equals("y"))
                        {
                            if(nodeChild.getChildren().size() ==0)
                            {
                                throw new ParseException();

                            }
                            y = Double.parseDouble(nodeChild.getChildren().get(0).getText());
                            yFound = true;
                        } else if(nodeChild.getAttributeValue("name").equals("description"))
                        {
                            if(nodeChild.getChildren().size() ==0)
                            {
                                throw new ParseException();

                            }
                            textLabel = nodeChild.getChildren().get(0).getText();
                            labelFound = true;
                        }

                    }
                    if(xFound && yFound && labelFound)
                    {
                        XYNode newNode = new XYNode(label,x,y);
                        gr.addNode(newNode);
                        nodes.add(newNode);
                    } else{
                        throw new ParseException();
                    }


                }

                List<Element> el2 = target.getChildren("edge");
                for (Element elem:el2)
                {
                    boolean isInteger = false;
                    String startStr="";
                    String endStr="";
                    int start = -1;
                    int end = -1;
                    try{
                        start = Integer.parseInt(elem.getAttributeValue("from"));
                        isInteger = true;
                    }catch (NumberFormatException ex)
                    {
                        startStr = elem.getAttributeValue("from");

                    }

                    if(isInteger)
                    {
                        end = Integer.parseInt(elem.getAttributeValue("to"));
                    } else {
                        endStr = elem.getAttributeValue("to");
                    }

                    Double cost = -1.0;
                    Collection<Element> attrList = elem.getChildren();
                    for(Element attrElem:attrList)
                    {
                        if(attrElem.getAttributeValue("name").equals("cost"))
                        {
                            cost = Double.parseDouble(attrElem.getChildText("float"));
                        }
                    }

                    XYNode s = new XYNode();
                    XYNode e = new XYNode();
                    for(int i=0;i<nodes.size();i++)
                    {
                        if(isInteger)
                        {
                            if(nodes.get(i).getLabel().equals(String.valueOf(start)))
                            {
                                s = nodes.get(i);
                            }
                            if(nodes.get(i).getLabel().equals(String.valueOf(end)))
                            {
                                e = nodes.get(i);
                            }
                        } else {
                            if(nodes.get(i).getLabel().equals(startStr))
                            {
                                s = nodes.get(i);
                            }
                            if(nodes.get(i).getLabel().equals(endStr))
                            {
                                e = nodes.get(i);
                            }
                        }

                    }
                    Optional<Double> op = Optional.of(cost);
                    gr.addEdge(s,e,op);
                }
            }

        }catch(IOException | JDOMException |NumberFormatException e)
        {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return gr;

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

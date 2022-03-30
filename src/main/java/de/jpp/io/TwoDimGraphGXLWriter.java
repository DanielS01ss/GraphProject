package de.jpp.io;

import com.example.graphen.ApplicationResources;
import de.jpp.io.GXLWriterTemplate;
import de.jpp.io.interfaces.ParseException;
import de.jpp.model.XYNode;
import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.Graph;

import org.jdom2.Attribute;
import org.jdom2.Element;

import java.util.*;

public class TwoDimGraphGXLWriter extends GXLWriterTemplate {
    HashMap<Object,String> idMap;
    int maxId;

    TwoDimGraphGXLWriter() { }

    public Element createAttribute(String s1,String s2,String s3)
    {
        //s1 name la attribute
        ///s2 tipul copilului
        ///s3 continutul copilului
        Element el = new Element("attr");
        el.setAttribute("name",s1);
        Element childAttr = new Element(s2);
        childAttr.setText(s3);
        el.addContent(childAttr);

        return el;
    }
    public String assignId(Object o)
    {
        return null;
    }

    public  void idMap(Element el)
    {
        ArrayList<Attribute> attrList = new ArrayList<>(el.getAttributes());
        for(int i=0;i<attrList.size();i++)
        {
            if(attrList.get(i).toString().contains("from"))
            {
                int startId = Integer.parseInt(el.getAttributeValue("from"));
                int endId = Integer.parseInt(el.getAttributeValue("to"));

                Element childAttr = el.getChild("attr");
                double cost = Double.parseDouble(childAttr.getChildText("float"));
                XYNode start = ApplicationResources.getNodeById(startId);
                XYNode end = ApplicationResources.getNodeById(endId);
                Optional<Double> opt = Optional.of(cost);
                Edge e = new Edge(start,end,opt);
                String edgeID = calculateId(e);
                idMap.put(e,edgeID);

            } else {

                System.out.println(attrList.get(i).toString());
                XYNode node = null;
                String res = attrList.get(i).toString().split("=")[1];
                res = res.substring(0,res.length()-1);
                int id = Integer.parseInt(res.substring(1,res.length()-1));
                List<Element> elems = el.getChildren();
                int index=0;
                double x=-1,y=-1;
                String label="";
                for(Element elInd: elems)
                {
                    if(index<2)
                    {
                        if(x==-1)
                        {
                            x = Double.parseDouble(elInd.getChildText("float"));
                        }
                        else{
                            y = Double.parseDouble(elInd.getChildText("float"));
                        }

                    }
                    else{
                        label = elInd.getChildText("string");
                    }
                    index++;
                }
                try{
                    node = new XYNode(label,x,y);
                } catch (ParseException ex)
                {
                    System.out.println(ex);
                }
                idMap.put(node,String.valueOf(id));
            }
        }
    }
    @Override
    public String write(Graph g) {
        return null;
    }

    @Override
    public Element writeNode(XYNode x) {
        Element elNode = new Element("node");
        elNode.setAttribute("id",x.getLabel());
        Element attr = new Element("attr");
        attr.setAttribute("name","x");
        Element attr2 = new Element("attr");
        attr2.setAttribute("name","y");
        Element floatAttr = new Element("float");
        floatAttr.setText(String.valueOf(x.getX()));
        attr.setContent(floatAttr);
        floatAttr = new Element("float");
        floatAttr.setText(String.valueOf(x.getY()));
        attr2.setContent(floatAttr);
        elNode.setContent(attr);
        elNode.setContent(attr2);

        return elNode;
    }

    @Override
    public  Element writeEdge(Edge x)
    {
        Element edgeElem = new Element("edge");
        XYNode startNode = (XYNode)x.getStart();
        XYNode endNode = (XYNode)x.getDestination();
        edgeElem.setAttribute("from",startNode.getLabel());
        edgeElem.setAttribute("to",endNode.getLabel());
        Element attr = new Element("attr");
        attr.setAttribute("name","cost");
        Element floatVal = new Element("float");
        floatVal.setText((String) x.getAnnotation().get());
        attr.setContent(floatVal);
        edgeElem.setContent(attr);
        return edgeElem;
    }

    public static  Element writeEdge1(Edge x)
    {
        Element edgeElem = new Element("edge");
        XYNode startNode = (XYNode)x.getStart();
        XYNode endNode = (XYNode)x.getDestination();
        edgeElem.setAttribute("from",startNode.getLabel());
        edgeElem.setAttribute("to",endNode.getLabel());
        Element attr = new Element("attr");
        attr.setAttribute("name","cost");
        Element floatVal = new Element("float");
        floatVal.setText((String) x.getAnnotation().get());
        attr.setContent(floatVal);
        edgeElem.setContent(attr);
        return edgeElem;
    }

    @Override
    public String calculateId(XYNode x) {
        Random rand = new Random();
        int int_random = rand.nextInt(100);
        double xNode = x.getX();
        double yNode = x.getY();
        return String.valueOf((int_random+xNode)*yNode);
    }

    @Override
    public String calculateId(Edge x) {

        XYNode start = (XYNode) x.getStart();
        XYNode end = (XYNode) x.getDestination();
        int id = start.hashCode()+end.hashCode();
        return String.valueOf(id);
    }

}

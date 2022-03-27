package de.jpp.factory;


import de.jpp.io.interfaces.GraphReader;
import de.jpp.io.interfaces.GraphWriter;
import de.jpp.io.interfaces.ParseException;
import de.jpp.model.LabelMapGraph;
import de.jpp.model.ObservableGraphimpl;
import de.jpp.model.TwoDimGraph;
import de.jpp.model.XYNode;
import de.jpp.model.interfaces.Edge;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.CDATA;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.IOException;
import java.io.OutputStream;
import net.sourceforge.gxl.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.image.BufferedImage;

public class IOFactory {

    /**
     * Returns a new GraphReader instance which parses a TwoDimGraph from a GXL-String
     *
     * @return a new GraphReader instance which parses a TwoDimGraph from a GXL-String
     */
    public GraphReader<XYNode, Double, TwoDimGraph, String> getTwoDimGxlReader() {
        GraphReader<XYNode,Double,TwoDimGraph,String> graphReader = new GraphReader<XYNode, Double, TwoDimGraph, String>() {
            @Override
            public TwoDimGraph read(String input) throws ParseException {
                String filePath = "C:\\Users\\stanc\\Desktop\\Java Challenge\\2022.3\\TestFiles\\gxl\\valid\\maze.gxl";
                TwoDimGraph gr = new TwoDimGraph();
                List<XYNode> nodes = new ArrayList<XYNode>();
                try{
                    SAXBuilder sax = new SAXBuilder();
                    Document doc = sax.build(new File(filePath));
                    Element rootNode = doc.getRootElement();
                    List<Element> list = rootNode.getChildren("graph");

                    for(Element  target:list)
                    {
                        List<Element> el = target.getChildren("node");
                        double x,y;
                        String label;
                        for(Element it:el)
                        {
                            label = it.getAttributeValue("id");
                            x = Double.parseDouble(((it.getChildren().get(0)).getChildText("float")));
                            y = Double.parseDouble((it.getChildren().get(1)).getChildText("float"));
                            XYNode newNode = new XYNode(label,x,y);
                            gr.addNode(newNode);
                            nodes.add(newNode);
                        }

                        List<Element> el2 = target.getChildren("edge");
                        for (Element elem:el2)
                        {
                            int start = Integer.parseInt(elem.getAttributeValue("from"));
                            int end = Integer.parseInt(elem.getAttributeValue("to"));
                            Double cost = Double.parseDouble(elem.getChildren().get(0).getChildText("float"));
                            XYNode s = new XYNode();
                            XYNode e = new XYNode();
                            for(int i=0;i<nodes.size();i++)
                            {

                                if(nodes.get(i).getLabel().equals(String.valueOf(start)))
                                {
                                    s = nodes.get(i);
                                }
                                if(nodes.get(i).getLabel().equals(String.valueOf(end)))
                                {
                                    e = nodes.get(i);
                                }
                            }
                            Optional<Double> op = Optional.of(cost);
                            gr.addEdge(s,e,op);
                        }
                    }

                }catch(IOException | JDOMException e)
                {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return gr;
            }
        };
        return graphReader;
    }

    /**
     * Returns a new GraphReader instance which parses a TwoDimGraph from a DOT-String
     *
     * @return a new GraphReader instance which parses a TwoDimGraph from a DOT-String
     */
    public GraphReader<XYNode, Double, TwoDimGraph, String> getTwoDimDotReader() {
        throw new UnsupportedOperationException("not supported yet!");
    }

    /**
     * Returns a new GraphReader instance which parses a TwoDimGraph from a BufferedImage-Maze
     *
     * @return a new GraphReader instance which parses a TwoDimGraph from a BufferedImage-Maze
     */
    public GraphReader<XYNode, Double, TwoDimGraph, BufferedImage> getTwoDimImgReader() {
        throw new UnsupportedOperationException("not supported yet!");
    }

    /**
     * Returns a new GraphReader instance which parses a LabelMapGraph from a GXL-String
     *
     * @return a new GraphReader instance which parses a LabelMapGraph from a GXL-String
     */
    public GraphReader<String, Map<String, String>, LabelMapGraph, String> getLabelMapGraphGxlReader() {
        throw new UnsupportedOperationException("not supported yet!");
    }

    /**
     * Returns a new GraphWriter instances which outputs a TwoDimGraph as a GXL-String
     *
     * @return a new GraphWriter instances which outputs a TwoDimGraph as a GXL-String
     */
    public GraphWriter<XYNode, Double, TwoDimGraph, String> getTwoDimGxlWriter() {
        ///tragem toate nodurile
        ///tragem toate edge-urile
        GraphWriter<XYNode,Double,TwoDimGraph,String> graphW = new GraphWriter<XYNode, Double, TwoDimGraph, String>() {
            @Override
            public String write(TwoDimGraph graph) {
                Document doc = new Document();
                doc.setRootElement(new Element("gxl"));
                Element gr = new Element("graph");

                Collection<XYNode> nodes = graph.getNodes();

                int i=1;
                for(XYNode x : nodes)
                {
                    Element node = new Element("node");
                    node.setAttribute("id",String.valueOf(i));

                    Element attr = new Element("attr");
                    attr.setAttribute("name","x");
                    Element floatAt = new Element("float");
                    floatAt.setText(String.valueOf(x.getX()));
                    attr.addContent(floatAt);
                    node.addContent(attr);

                    attr = new Element("attr");
                    attr.setAttribute("name","y");
                    floatAt = new Element("float");
                    floatAt.setText(String.valueOf(x.getY()));
                    attr.addContent(floatAt);
                    node.addContent(attr);

                    attr = new Element("attr");
                    attr.setAttribute("name","description");
                    floatAt = new Element("string");
                    floatAt.setText(String.valueOf(x.getLabel()));
                    attr.addContent(floatAt);
                    node.addContent(attr);

                    gr.addContent(node);
                    i++;
                }

                Collection<Edge<XYNode,Double>> edges = graph.getEdges();

                for(Edge<XYNode,Double> ed:edges)
                {
                    Element edgeNode = new Element("edge");
                    edgeNode.setAttribute("id",String.valueOf(i));
                    XYNode st = ed.getStart();
                    XYNode end = ed.getDestination();
                    edgeNode.setAttribute("from",st.getLabel());
                    edgeNode.setAttribute("to",end.getLabel());
                    Element attr = new Element("attr");
                    attr = new Element("attr");
                    attr.setAttribute("name","cost");
                    Element floatAt = new Element("float");
                    Double val = ed.getAnnotation().get();
                    floatAt.setText( String.valueOf(val));
                    attr.addContent(floatAt);
                    edgeNode.addContent(attr);
                    gr.addContent(edgeNode);
                    i++;
                }

                doc.getRootElement().addContent(gr);

                XMLOutputter xmlOutputter = new XMLOutputter();

                // pretty print
                xmlOutputter.setFormat(Format.getPrettyFormat());
                OutputStream out = System.out;
                String xmlString;
                try{
                    xmlOutputter.output(doc,out);
                    xmlString = out.toString();
                }catch(IOException e)
                {
                    System.out.println(e);
                    return null;
                }
                return  xmlString;
            }
        };
        return graphW;
    }

    /**
     * Returns a new GraphWriter instance which outputs a TwoDimGraph as DOT-String
     *
     * @return a new GraphWriter instance which outputs a TwoDimGraph as DOT-String
     */
    public GraphWriter<XYNode, Double, TwoDimGraph, String> getTwoDimDotWriter() {
        throw new UnsupportedOperationException("not supported yet!");
    }

    /**
     * Returns a new GraphWriter instance which outputs a LabelMapGraph as GXL-String
     *
     * @return a new GraphWriter instance which outputs a LabelMapGraph as GXL-String
     */
    public GraphWriter<String, Map<String, String>, LabelMapGraph, String> getLabelMapGraphGxlWriter() {
        throw new UnsupportedOperationException("not supported yet!");
    }


}

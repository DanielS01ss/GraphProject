package de.jpp.factory;


import de.jpp.io.interfaces.GraphReader;
import de.jpp.io.interfaces.GraphWriter;
import de.jpp.io.interfaces.ParseException;
import de.jpp.model.LabelMapGraph;
import de.jpp.model.TwoDimGraph;
import de.jpp.model.XYNode;
import de.jpp.model.interfaces.Edge;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IOFactory {

    /**
     * Returns a new GraphReader instance which parses a TwoDimGraph from a GXL-String
     *
     * @return a new GraphReader instance which parses a TwoDimGraph from a GXL-String
     */
    public  GraphReader<XYNode, Double, TwoDimGraph, String> getTwoDimGxlReader() {
        GraphReader<XYNode,Double,TwoDimGraph,String> graphReader = new GraphReader<XYNode, Double, TwoDimGraph, String>() {
            @Override
            public TwoDimGraph read(String input) throws ParseException {

                TwoDimGraph gr = new TwoDimGraph();
                List<XYNode> nodes = new ArrayList<XYNode>();
                try{
                    SAXBuilder sax = new SAXBuilder();
                    InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
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
                                        throw new JDOMException();

                                    }
                                    x = Double.parseDouble(nodeChild.getChildren().get(0).getText());
                                    xFound = true;
                                } else if (nodeChild.getAttributeValue("name").equals("y"))
                                {
                                    if(nodeChild.getChildren().size() ==0)
                                    {
                                        throw new JDOMException();

                                    }
                                    y = Double.parseDouble(nodeChild.getChildren().get(0).getText());
                                    yFound = true;
                                } else if(nodeChild.getAttributeValue("name").equals("description"))
                                {
                                    if(nodeChild.getChildren().size() ==0)
                                    {
                                        throw new JDOMException();

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
                                throw new JDOMException();
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
//                ApplicationResources.appGraph = gr;
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
        GraphReader<XYNode,Double,TwoDimGraph,String> graph = new GraphReader<XYNode, Double, TwoDimGraph, String>() {
            @Override
            public TwoDimGraph read(String input) throws ParseException {
                TwoDimGraph graph1 = new TwoDimGraph();
                try(Scanner scanner = new Scanner(input)) {
                    String line = scanner.nextLine();
                    Pattern nodePattern1 = Pattern.compile("(([0-9]*){1})(\s*)\\[((label=n[0-9]*){1})(\s*)((x=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1})(\s*)((y=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1})(\s*)\\]");
                    Pattern nodePattern2 = Pattern.compile("([0-9]*)(\s*)\\[((x=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1})(\s*)((y=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1})(\s*)((label=n[0-9]*){1})(\s*)\\]");
                    Pattern edgePattern = Pattern.compile("(([0-9]*){1})(\s*)((->){1})(\s*)(([0-9]*){1})(\s*)(\\[)(\s*)((dist=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1})(\s*)(\\])");
                    Matcher nodeMatcher1;
                    Matcher nodeMatcher2;
                    Matcher edgeMatcher;
                    Matcher matcher;
                    String label = " ";
                    double x = 0;
                    double y = 0;
                    HashMap<Integer,XYNode> map = new HashMap<>();
                    int id = 0;
                    while (scanner.hasNextLine())
                    {
                        line = scanner.nextLine();
                        if (line.equals("}"))
                        {
                            break;
                        }
                        nodeMatcher1 = nodePattern1.matcher(line);
                        nodeMatcher2 = nodePattern2.matcher(line);
                        edgeMatcher = edgePattern.matcher(line);
                        if(nodeMatcher1.matches() || nodeMatcher2.matches())
                        {
                            Pattern labelPattern = Pattern.compile("(label=n[0-9]*){1}");
                            Pattern xPattern = Pattern.compile("(x=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1}");
                            Pattern yPattern = Pattern.compile("(y=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1}");
                            matcher = labelPattern.matcher(line);
                            while (matcher.find())
                            {
                                label = matcher.group().split("=")[1];
                            }
                            matcher = xPattern.matcher(line);
                            while (matcher.find())
                            {
                                x = Double.parseDouble(matcher.group().split("=")[1]);
                            }
                            matcher = yPattern.matcher(line);
                            while (matcher.find())
                            {
                                y = Double.parseDouble(matcher.group().split("=")[1]);
                            }
                            graph1.addNode(new XYNode(label,x,y));
                            Pattern idPattern = Pattern.compile("(label=n[0-9]*)");
                            matcher = idPattern.matcher(line);
                            while (matcher.find())
                            {
                                id = Integer.parseInt(matcher.group().split("=n")[1]);
                            }
                            map.put(id,new XYNode(label,x,y));
                        }
                        else
                            throw new ParseException("Invalid Node Format!");
                        if(edgeMatcher.matches())
                        {
                            Pattern distPattern = Pattern.compile("(dist=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1}");
                            matcher = distPattern.matcher(line);
                            int id1 = 0, id2 = 0;
                            String annotation = " ";
                            while (matcher.find())
                            {
                                annotation = matcher.group().split("=")[1];
                            }
                            Pattern nodesPattern = Pattern.compile("(([0-9]*){1})(\s*)((->){1})(\s*)(([0-9]*){1})(\s*)");
                            matcher = nodesPattern.matcher(line);
                            while (matcher.find())
                            {
                                String ln = matcher.group().split("->")[0];
                                ln = ln.replaceAll("\s","");
                                id1 = Integer.parseInt(ln);
                                ln = matcher.group().split("->")[1];
                                ln = ln.replaceAll("\s","");
                                id2 = Integer.parseInt(ln);
                            }
                            graph1.addEdge(map.get(id1),map.get(id2),Optional.of(Double.parseDouble(annotation)));
                        }
                        else
                            throw new ParseException("Invalid Edge Format!");
                    }
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
                return graph1;
            }
        };
        return graph;
    }

    /**
     * Returns a new GraphReader instance which parses a TwoDimGraph from a BufferedImage-Maze
     *
     * @return a new GraphReader instance which parses a TwoDimGraph from a BufferedImage-Maze
     */
    public GraphReader<XYNode, Double, TwoDimGraph, BufferedImage> getTwoDimImgReader() {
       GraphReader<XYNode,Double,TwoDimGraph,BufferedImage> graph = new GraphReader<XYNode, Double, TwoDimGraph, BufferedImage>() {
           @Override
           public TwoDimGraph read(BufferedImage input) throws ParseException {
               TwoDimGraph twoDimGraph = new TwoDimGraph();
               ArrayList<XYNode> nodeList = new ArrayList<>();
               ArrayList<Edge<XYNode,Double>> edgeList = new ArrayList<>();
               int[][] result = convertTo2D(input);
               int width = input.getWidth();
               int height = input.getHeight();
               int counter = 1;
               String label = "";
               XYNode currentNode = new XYNode();
               for (int col = 1; col < width-1; col++)
               {
                   while (counter < height-1)
                   {
                       if(counter == height-2 && col > 1)
                       {
                           if(result[counter][col-1] == -1)
                           {
                               label = "(" + (col) + "|" + counter + ")";
                               nodeList.add(new XYNode(label,col,counter));
                               label = "(" + (col-1) + "|" + counter + ")";
                               try {
                                   twoDimGraph.addEdge(currentNode, new XYNode(label, col - 1, counter), Optional.of(1.0));
                                   twoDimGraph.addEdge(new XYNode(label, col - 1, counter), currentNode, Optional.of(1.0));
                               }
                               catch (Exception e)
                               {
                                   e.printStackTrace();
                               }
                           }
                           break;
                       }
                       if(result[counter][col] == -1)
                       {
                           label = "(" + col + "|" + counter + ")";
                           currentNode = new XYNode(label,col,counter);
                           nodeList.add(currentNode);
                           if(result[counter+1][col] == -1)
                           {
                               label = "(" + col + "|" + (counter+1) + ")";
                               try {
                                   twoDimGraph.addEdge(currentNode, new XYNode(label, col, counter+1), Optional.of(1.0));
                                   twoDimGraph.addEdge(new XYNode(label, col, counter+1), currentNode, Optional.of(1.0));
                               } catch (Exception e) {
                                   e.printStackTrace();
                               }
                           }
                       }
                       if(col > 1)
                       {
                           if(result[counter][col-1] == -1)
                           {
                               label = "(" + (col-1) + "|" + counter + ")";
                               try {
                                   twoDimGraph.addEdge(currentNode, new XYNode(label, col - 1, counter), Optional.of(1.0));
                                   twoDimGraph.addEdge(new XYNode(label, col - 1, counter), currentNode, Optional.of(1.0));
                               }
                               catch (Exception e)
                               {
                                   e.printStackTrace();
                               }
                           }
                       }
                       counter++;
                   }
               }
               twoDimGraph.addNodes(nodeList);
               if(twoDimGraph == null)
                   throw new ParseException();
               return twoDimGraph;
           }
       };
       return graph;
    }

    public static int[][] convertTo2D(BufferedImage image)
    {
        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int[][] result = new int[height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }

        return result;
    }

    /**
     * Returns a new GraphReader instance which parses a LabelMapGraph from a GXL-String
     *
     * @return a new GraphReader instance which parses a LabelMapGraph from a GXL-String
     */

//    public GraphReader<String, Map<String, String>, LabelMapGraph, String> getLabelMapGraphGxlReader() {
//        throw new UnsupportedOperationException("not supported yet!");
//    }


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
        GraphWriter<XYNode,Double,TwoDimGraph,String> graph = new GraphWriter<XYNode, Double, TwoDimGraph, String>() {
            @Override
            public String write(TwoDimGraph graph) {
                ArrayList<XYNode> list = new ArrayList<>(graph.getNodes());
                ArrayList<Edge<XYNode,Double>> list1 = new ArrayList<>(graph.getEdges());
                StringBuilder line = new StringBuilder();
                line.append("digraph{\n");
                String ln = "";
                Integer counter = 1;
                for (XYNode n : list)
                {
                    ln = counter.toString() + "[label=" + n.getLabel() + " x=" + n.getX() + " y=" + n.getY() + "]\n";
                    counter++;
                    line.append(ln);
                }
                for(Edge<XYNode,Double> e : list1)
                {
                    ln = e.getStart().getLabel().split("n")[1] + "->" + e.getDestination().getLabel().split("n")[1] + " [dist=" + e.getAnnotation() + "]\n";
                    line.append(ln);
                }
                line.append("}");
                return new String(line);
            }
        };
        return graph;
    }

    /**
     * Returns a new GraphWriter instance which outputs a LabelMapGraph as GXL-String
     *
     * @return a new GraphWriter instance which outputs a LabelMapGraph as GXL-String
     */
    public GraphWriter<String, Map<String, String>, LabelMapGraph, String> getLabelMapGraphGxlWriter() {

        GraphWriter<String,Map<String,String>,LabelMapGraph,String> gWrite = new GraphWriter<String, Map<String, String>, LabelMapGraph, String>() {
            @Override
            public String write(LabelMapGraph graph) {
               String result="";
               Collection<String> nodes = graph.getNodes();
               Collection<Edge<String,Map<String,String>>> edges = graph.getEdges();

               return result;
            }
        };

        return gWrite;
    }

    public GraphReader<String, Map<String, String>, LabelMapGraph,String> getLabelMapGraphGxlReader()
    {
        GraphReader<String, Map<String, String>, LabelMapGraph,String> grReader = new GraphReader<String, Map<String, String>, LabelMapGraph, String>() {
            @Override
            public LabelMapGraph read(String input) throws ParseException {
                ///public GraphImpl(ArrayList<N> nodes, HashMap<N, ArrayList<Edge>> edges)
                ///ArrayList<String> Nodes
                ///HashMap <String,ArrayList<Edge>>
                ///Deci la node <String>
                ///la edges HashMap<String,ArrayList<Edge>> edges;
                LabelMapGraph lGraph = new LabelMapGraph();
                InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
                SAXBuilder sax = new SAXBuilder();
                Document doc;
                try{
                    doc = sax.build(stream);
                    Element rootNode = doc.getRootElement();
                    List<Element> target = rootNode.getChildren("node");
                    for(Element el:target)
                    {
                        String nodeData = el.getAttributeValue("id");
                        lGraph.addNode(nodeData);
                    }

                    target = rootNode.getChildren("edge");

                    for(Element el:target)
                    {
                        ///start dest annotation
                        String start = el.getAttributeValue("from");
                        String end = el.getAttributeValue("to");
                        Edge eg = new Edge(start,end);
                        lGraph.addEdge(start,end);
                    }

                } catch (JDOMException | IOException ex)
                {
                    System.out.println(ex);
                }

                return lGraph;
            }
        };

        return grReader;
    }
}

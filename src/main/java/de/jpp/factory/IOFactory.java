package de.jpp.factory;


import de.jpp.io.interfaces.GraphReader;
import de.jpp.io.interfaces.GraphWriter;
import de.jpp.io.interfaces.ParseException;
import de.jpp.model.LabelMapGraph;
import de.jpp.model.TwoDimGraph;
import de.jpp.model.XYNode;
import de.jpp.model.interfaces.Edge;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IOFactory {


    /**
     * Returns a new GraphReader instance which parses a TwoDimGraph from a GXL-String
     *
     * @return a new GraphReader instance which parses a TwoDimGraph from a GXL-String
     */
    public GraphReader<XYNode, Double, TwoDimGraph, String> getTwoDimGxlReader() {
        throw new UnsupportedOperationException("not supported yet!");
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
    public GraphReader<String, Map<String, String>, LabelMapGraph, String> getLabelMapGraphGxlReader() {
        throw new UnsupportedOperationException("not supported yet!");
    }

    /**
     * Returns a new GraphWriter instances which outputs a TwoDimGraph as a GXL-String
     *
     * @return a new GraphWriter instances which outputs a TwoDimGraph as a GXL-String
     */
    public GraphWriter<XYNode, Double, TwoDimGraph, String> getTwoDimGxlWriter() {
        throw new UnsupportedOperationException("not supported yet!");
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
        throw new UnsupportedOperationException("not supported yet!");
    }


}

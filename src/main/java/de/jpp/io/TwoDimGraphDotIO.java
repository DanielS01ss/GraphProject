package de.jpp.io;

import de.jpp.io.interfaces.GraphReader;
import de.jpp.io.interfaces.GraphWriter;
import de.jpp.io.interfaces.ParseException;
import de.jpp.model.TwoDimGraph;
import de.jpp.model.XYNode;
import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.Graph;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TwoDimGraphDotIO  implements GraphWriter, GraphReader {
    @Override
    public Graph read(Object input) throws ParseException {
        TwoDimGraph graph1 = new TwoDimGraph();
        try(Scanner scanner = new Scanner((String) input)) {
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
            HashMap<Integer, XYNode> map = new HashMap<>();
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
                if(edgeMatcher.matches())
                {
                    Pattern distPattern = Pattern.compile("(dist=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1}");
                    matcher = distPattern.matcher(line);
                    int id1 = 0, id2 = 0;
                    String annotation = "";
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
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return graph1;
    }

    @Override
    public Object write(Graph graph) {
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

    public void parseNode(TwoDimGraph graph, HashMap<Integer, XYNode> map, String line) throws ParseException
    {
        Pattern nodePattern1 = Pattern.compile("(([0-9]*){1})(\s*)\\[((label=n[0-9]*){1})(\s*)((x=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1})(\s*)((y=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1})(\s*)\\]");
        Pattern nodePattern2 = Pattern.compile("([0-9]*)(\s*)\\[((x=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1})(\s*)((y=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1})(\s*)((label=n[0-9]*){1})(\s*)\\]");
        Matcher nodeMatcher1 = nodePattern1.matcher(line);
        Matcher nodeMatcher2 = nodePattern2.matcher(line);
        Matcher matcher;
        boolean b = nodeMatcher1.matches() || nodeMatcher2.matches();
        if(b)
        {
            Pattern labelPattern = Pattern.compile("(label=n[0-9]*){1}");
            Pattern xPattern = Pattern.compile("(x=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1}");
            Pattern yPattern = Pattern.compile("(y=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1}");
            String label = "";
            double x = 0;
            double y = 0;
            int id = 0;
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
            try {
                graph.addNode(new XYNode(label,x,y));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Pattern idPattern = Pattern.compile("(label=n[0-9]*)");
            matcher = idPattern.matcher(line);
            while (matcher.find())
            {
                id = Integer.parseInt(matcher.group().split("=n")[1]);
            }
            try {
                map.put(id,new XYNode(label,x,y));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else throw new ParseException();
    }

    public void parseEdge(TwoDimGraph graph, HashMap<Integer, XYNode> map, String line) throws ParseException
    {
        Pattern edgePattern = Pattern.compile("(([0-9]*){1})(\s*)((->){1})(\s*)(([0-9]*){1})(\s*)(\\[)(\s*)((dist=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1})(\s*)(\\])");
        Matcher edgeMatcher = edgePattern.matcher(line);
        Matcher matcher;
        if(edgeMatcher.matches())
        {
            Pattern distPattern = Pattern.compile("(dist=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1}");
            matcher = distPattern.matcher(line);
            int id1 = 0, id2 = 0;
            String annotation = "";
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
            graph.addEdge(map.get(id1),map.get(id2),Optional.of(Double.parseDouble(annotation)));
        }
        else
            throw new ParseException("Invalid Format!");
    }

    public void parseLine(TwoDimGraph graph, HashMap<Integer, XYNode> map, String line)
    {
        try(Scanner scanner = new Scanner((String) line)) {
            String string = scanner.nextLine();
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
            int id = 0;
            while (scanner.hasNextLine())
            {
                string = scanner.nextLine();
                if (string.equals("}"))
                {
                    break;
                }
                nodeMatcher1 = nodePattern1.matcher(string);
                nodeMatcher2 = nodePattern2.matcher(string);
                edgeMatcher = edgePattern.matcher(string);
                if(nodeMatcher1.matches() || nodeMatcher2.matches())
                {
                    Pattern labelPattern = Pattern.compile("(label=n[0-9]*){1}");
                    Pattern xPattern = Pattern.compile("(x=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1}");
                    Pattern yPattern = Pattern.compile("(y=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1}");
                    matcher = labelPattern.matcher(string);
                    while (matcher.find())
                    {
                        label = matcher.group().split("=")[1];
                    }
                    matcher = xPattern.matcher(string);
                    while (matcher.find())
                    {
                        x = Double.parseDouble(matcher.group().split("=")[1]);
                    }
                    matcher = yPattern.matcher(string);
                    while (matcher.find())
                    {
                        y = Double.parseDouble(matcher.group().split("=")[1]);
                    }
                    graph.addNode(new XYNode(label,x,y));
                    Pattern idPattern = Pattern.compile("(label=n[0-9]*)");
                    matcher = idPattern.matcher(string);
                    while (matcher.find())
                    {
                        id = Integer.parseInt(matcher.group().split("=n")[1]);
                    }
                    map.put(id,new XYNode(label,x,y));
                }
                if(edgeMatcher.matches())
                {
                    Pattern distPattern = Pattern.compile("(dist=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1}");
                    matcher = distPattern.matcher(string);
                    int id1 = 0, id2 = 0;
                    String annotation = "";
                    while (matcher.find())
                    {
                        annotation = matcher.group().split("=")[1];
                    }
                    Pattern nodesPattern = Pattern.compile("(([0-9]*){1})(\s*)((->){1})(\s*)(([0-9]*){1})(\s*)");
                    matcher = nodesPattern.matcher(string);
                    while (matcher.find())
                    {
                        String ln = matcher.group().split("->")[0];
                        ln = ln.replaceAll("\s","");
                        id1 = Integer.parseInt(ln);
                        ln = matcher.group().split("->")[1];
                        ln = ln.replaceAll("\s","");
                        id2 = Integer.parseInt(ln);
                    }
                    graph.addEdge(map.get(id1),map.get(id2),Optional.of(Double.parseDouble(annotation)));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public HashMap<String, String> parseAnnotation(String line) throws ParseException
    {
        HashMap<String, String> map = new HashMap<>();
        Pattern p = Pattern.compile("\\[(\s*)([a-zA-Z]*)([0-9]*)=(((-?)(0|([1-9][0-9]*))(.[0-9]+)?){1})(\s*)\\]");
        Matcher m = p.matcher(line);
        if(m.matches())
        {
            while (m.find())
            {
                map.put(m.group().split("=")[0],m.group().split("=")[1]);
            }
        }
        else
            throw new ParseException("Invalid Format!");
        return map;
    }
}

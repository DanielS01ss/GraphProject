package de.jpp.model;

import de.jpp.io.interfaces.GraphReader;
import de.jpp.io.interfaces.GraphWriter;
import de.jpp.io.interfaces.ParseException;
import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.Graph;

import javax.xml.stream.XMLStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TwoDimGraphDotIO  implements GraphWriter, GraphReader {
    @Override
    public Graph read(Object input) throws ParseException {
        return null;
    }

    @Override
    public Object write(Graph graph) {
        return null;
    }

    public void parseNode(TwoDimGraph graph, HashMap<Integer, XYNode> map, String line) throws ParseException
    {
        Pattern p = Pattern.compile("([a-zA-Z]*[0-9]*){1}");
        Pattern p1 = Pattern.compile("(^(-?)(0|([1-9][0-9]*))(.[0-9]+)?$){1}");
        Pattern p2 = Pattern.compile("(^(-?)(0|([1-9][0-9]*))(.[0-9]+)?$){1}");
        Matcher matcher = p.matcher(line.split(",")[0]);
        Matcher m1 = p1.matcher(line.split(",")[1]);
        Matcher m2 = p2.matcher(line.split(",")[2]);
        boolean b = matcher.matches() && m1.matches() && m2.matches();
        if(b)
        {
            try {
                graph.addNode(new XYNode(line.split(",")[0],Double.parseDouble(line.split(",")[1]),Double.parseDouble(line.split(",")[2])));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else throw new ParseException();
    }

    public void parseEdge(TwoDimGraph graph, HashMap<Integer, XYNode> map, String line) throws ParseException
    {
        String FirstNode = line.split(",")[0];
        Pattern p = Pattern.compile("([a-zA-Z]*[0-9]*){1}\s(^(-?)(0|([1-9][0-9]*))(.[0-9]+)?$){1}\s(^(-?)(0|([1-9][0-9]*))(.[0-9]+)?$){1}");
        Matcher m = p.matcher(FirstNode);
        String SecondNode = line.split(",")[1];
        Pattern p1 = Pattern.compile("([a-zA-Z]*[0-9]*){1}\s(^(-?)(0|([1-9][0-9]*))(.[0-9]+)?$){1}\s(^(-?)(0|([1-9][0-9]*))(.[0-9]+)?$){1}");
        Matcher m1 = p1.matcher(SecondNode);
        String Annotation = line.split(",")[2];
        Pattern p2 = Pattern.compile("(^(-?)(0|([1-9][0-9]*))(.[0-9]+)?$){1}");
        Matcher m2 = p2.matcher(Annotation);
        boolean b = m.matches() && m1.matches() && m2.matches();

        if(b)
        {
            try {
                graph.addEdge(new XYNode(FirstNode.split(" ")[0], Double.parseDouble(FirstNode.split(" ")[1]), Double.parseDouble(FirstNode.split(" ")[2])),new XYNode(SecondNode.split(" ")[0], Double.parseDouble(SecondNode.split(" ")[1]), Double.parseDouble(SecondNode.split(" ")[2])), Optional.of(Double.parseDouble(Annotation)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
            throw new ParseException();
    }
}

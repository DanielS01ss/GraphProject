package de.jpp;

import de.jpp.factory.IOFactory;
import de.jpp.io.TwoDimGraphDotIO;
import de.jpp.io.interfaces.GraphReader;
import de.jpp.io.interfaces.ParseException;
import de.jpp.model.TwoDimGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestClass {
    public static void main(String[] args) throws ParseException {
        IOFactory ioFactory = new IOFactory();
        GraphReader graphReader = ioFactory.getTwoDimDotReader();
        TwoDimGraph graph = new TwoDimGraph();

        String input = new String();
        try(Scanner cin = new Scanner(new File("SampleGraph.dot"))) {
            StringBuilder line = new StringBuilder();
            while (cin.hasNextLine())
            {
                line.append(cin.nextLine());
                line.append("\n");
            }
            input = line.toString();
        }
        catch (FileNotFoundException e)
        {
            e.getMessage();
        }

        Pattern ptr = Pattern.compile("(([0-9]*){1})(\\s*).(\\s*)((label=((n[0-9]*))){1})((\\s)*)((x=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1})(\\s*)((y=((-?)(0|([1-9][0-9]*))(.[0-9]+)?)){1})(\\s*).");

        String ln = "1       [         label=n10       x=10.0            y=40.0             ]";
        Matcher matcher = ptr.matcher(ln);

        System.out.println(matcher.matches());

        graph = (TwoDimGraph)  new TwoDimGraphDotIO().read(input);

        System.out.println(graph.equals( new TwoDimGraphDotIO().read(input)));

    }
}

package de.jpp;


import de.jpp.model.TwoDimGraph;
import de.jpp.model.TwoDimGraphDotIO;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestClass {
    public static void main(String[] args)
    {
        TwoDimGraphDotIO graphDotIO = new TwoDimGraphDotIO();

        Pattern p = Pattern.compile("(XYNode)?,[a-zA-Z]*");
        Matcher m = p.matcher("XYNode,node");
        boolean b = m.matches();
        System.out.println(b);
    }
}

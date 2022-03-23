package de.jpp;


import de.jpp.factory.GraphFactory;
import de.jpp.model.interfaces.Graph;

import java.util.Optional;

public class TestClass {
    public static void main(String[] args)
    {
        Graph<String,Double> graph = GraphFactory.createNewGraph();

        graph.addNode("Start");
        graph.addNode("End");

        Double weight = 10.0;
        graph.addEdge("Start", "End", Optional.of(weight));

        System.out.println(graph);
    }
}

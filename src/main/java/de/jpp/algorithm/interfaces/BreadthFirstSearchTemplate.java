package de.jpp.algorithm.interfaces;


import de.jpp.algorithm.NodeInformation;
import de.jpp.algorithm.SearchResultImpl;
import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.Graph;

public abstract class BreadthFirstSearchTemplate<N> implements SearchAlgorithm {
    private boolean stopped;
    private Graph graph;
    private N start;
    private SearchResultImpl result;

    public BreadthFirstSearchTemplate(boolean stopped, Graph graph, N start, SearchResultImpl result) {
        this.stopped = stopped;
        this.graph = graph;
        this.start = start;
        this.result = result;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public N getStart() {
        return start;
    }

    public void setStart(N start) {
        this.start = start;
    }

    public SearchResultImpl getResult() {
        return result;
    }

    public void setResult(SearchResultImpl result) {
        this.result = result;
    }

    protected NodeInformation<Edge,Double> getNodeInformation(Edge edge, double value)
    {
        return new NodeInformation(edge,value);
    }

    protected void openIfShorter(N node, NodeInformation info)
    {
        if(result.getInformation(node).getDistance() > info.getDistance())
        {
            result.open(node,info);
        }
    }
}

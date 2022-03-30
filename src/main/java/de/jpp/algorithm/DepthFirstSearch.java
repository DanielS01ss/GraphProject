package de.jpp.algorithm;

import de.jpp.algorithm.interfaces.*;
import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.Graph;

import java.util.ArrayList;
import java.util.Stack;
import java.util.function.BiConsumer;

public class DepthFirstSearch<N> implements SearchAlgorithm {

    private boolean stopped;
    private Graph graph;
    private N start;
    private SearchResultImpl result;

    public DepthFirstSearch(boolean stopped, Graph graph, N start, SearchResultImpl result) {
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

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public void setStart(N start) {
        this.start = start;
    }

    public SearchResultImpl getResult() {
        return result;
    }

    private void expand(N node, SearchStopStrategy stopStrategy)
    {

    }

    public void setResult(SearchResultImpl result) {
        this.result = result;
    }

    @Override
    public SearchResult findPaths(SearchStopStrategy type) {
        StartToDestStrategy tp = (StartToDestStrategy) type;
        ArrayList<N> nodeList = new ArrayList<>(graph.getNodes());
        for (int i = 0; i < nodeList.size(); i++)
        {
            result.setOpen(nodeList.get(i));
        }
        Stack<N> stack = new Stack<>();
        N currentNode = start;
        stack.push(start);
        while (!stack.isEmpty() || !tp.stopSearch(currentNode) || !stopped)
        {
            currentNode = stack.pop();
            if (result.getNodeStatus(currentNode) == NodeStatus.OPEN || result.getNodeStatus(currentNode) == NodeStatus.UNKOWN )
            {
                result.setClosed(currentNode);
            }
            ArrayList<Edge> neighbors = new ArrayList<>(graph.getNeighbours(currentNode));
            for (int i = 0; i < neighbors.size(); i++)
            {
                N node = (N) neighbors.get(i).getDestination();
                if(node != null && result.getNodeStatus(node) != NodeStatus.CLOSED)
                {
                    result.close(currentNode,new NodeInformation<>(new Edge<>(currentNode,node),0));
                    stack.push(node);
                }
            }
        }
        return result;
    }

    @Override
    public SearchResult findAllPaths() {
        ArrayList<N> nodeList = new ArrayList<>(graph.getNodes());
        for (int i = 0; i < nodeList.size(); i++)
        {
            result.setOpen(nodeList.get(i));
        }
        Stack<N> stack = new Stack<>();
        N currentNode = start;
        stack.push(start);
        while (!stack.isEmpty() || !stopped)
        {
            currentNode = stack.pop();
            if (result.getNodeStatus(currentNode) == NodeStatus.OPEN || result.getNodeStatus(currentNode) == NodeStatus.UNKOWN )
            {
                result.setClosed(currentNode);
            }
            ArrayList<Edge> neighbors = new ArrayList<>(graph.getNeighbours(currentNode));
            for (int i = 0; i < neighbors.size(); i++)
            {
                N node = (N) neighbors.get(i).getDestination();
                if(node != null && result.getNodeStatus(node) != NodeStatus.CLOSED)
                {
                    result.close(currentNode,new NodeInformation<>(new Edge<>(currentNode,node),0));
                    stack.push(node);
                }
            }
        }
        return result;
    }

    @Override
    public ObservableSearchResult getSearchResult() {
        ArrayList<N> nodeList = new ArrayList<>(graph.getNodes());
        for (int i = 0; i < nodeList.size(); i++)
        {
            result.setOpen(nodeList.get(i));
            BiConsumer<N,SearchResult> listener = (val,val2) -> {val2.setOpen(val);};
            result.addNodeOpenedListener(listener);
        }
        Stack<N> stack = new Stack<>();
        N currentNode = start;
        stack.push(start);
        while (!stack.isEmpty())
        {
            currentNode = stack.pop();
            if (result.getNodeStatus(currentNode) == NodeStatus.OPEN || result.getNodeStatus(currentNode) == NodeStatus.UNKOWN )
            {
                result.setClosed(currentNode);
                BiConsumer<N,SearchResult> listener = (val,val2) -> {val2.setClosed(val);};
                result.addNodeClosedListener(listener);
            }
            ArrayList<Edge> neighbors = new ArrayList<>(graph.getNeighbours(currentNode));
            for (int i = 0; i < neighbors.size(); i++)
            {
                N node = (N) neighbors.get(i).getDestination();
                if(node != null && result.getNodeStatus(node) != NodeStatus.CLOSED)
                {
                    result.close(currentNode,new NodeInformation<>(new Edge<>(currentNode,node),0));
                    stack.push(node);
                }
            }
        }
        return result;
    }

    @Override
    public Object getStart() {
        return start;
    }

    @Override
    public Graph getGraph() {
        return graph;
    }

    @Override
    public void stop() {
        stopped = true;
    }
}

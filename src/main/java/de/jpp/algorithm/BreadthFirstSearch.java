package de.jpp.algorithm;

import de.jpp.algorithm.interfaces.*;
import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiConsumer;

public class BreadthFirstSearch<N>  extends BreadthFirstSearchTemplate<N> {

    public BreadthFirstSearch(boolean stopped, Graph graph, N start, SearchResultImpl result) {
        super(stopped, graph, start, result);
    }

    @Override
    public SearchResult findPaths(SearchStopStrategy type) {
        ArrayList<N> nodeList = new ArrayList<>(getGraph().getNodes());
        for(int i = 0; i < nodeList.size(); i++)
        {
            getResult().setOpen(nodeList.get(i));
        }
        Queue<N> queue = new LinkedList<>();
        N currentNode = getStart();
        getResult().setClosed(currentNode);
        queue.add(currentNode);

        while (!queue.isEmpty() || type.stopSearch(currentNode) || !isStopped())
        {
            currentNode = queue.poll();
            ArrayList<Edge> neighbors = new ArrayList<>(getGraph().getNeighbours(currentNode));
            for (int i = 0; i < neighbors.size(); i++)
            {
                N node = (N) neighbors.get(i).getDestination();
                if(getResult().getNodeStatus(node) != NodeStatus.CLOSED)
                {
                    getResult().close(currentNode,new NodeInformation<>(neighbors.get(i),0));
                    queue.add(node);
                }
            }
        }
        return getResult();
    }

    @Override
    public SearchResult findAllPaths() {
       ArrayList<N> nodeList = new ArrayList<>(getGraph().getNodes());
        for(int i = 0; i < nodeList.size(); i++)
        {
            getResult().setOpen(nodeList.get(i));
        }
        Queue<N> queue = new LinkedList<>();
        N currentNode = getStart();
        getResult().setClosed(currentNode);
        queue.add(currentNode);

        while (!queue.isEmpty() || !isStopped())
        {
            currentNode = queue.poll();
            ArrayList<Edge> neighbors = new ArrayList<>(getGraph().getNeighbours(currentNode));
            for (int i = 0; i < neighbors.size(); i++)
            {
                N node = (N) neighbors.get(i).getDestination();
                if(getResult().getNodeStatus(node) != NodeStatus.CLOSED)
                {
                    getResult().close(currentNode,new NodeInformation<>(neighbors.get(i),0));
                    queue.add(node);
                }
            }
        }
        return getResult();
    }

    @Override
    public ObservableSearchResult getSearchResult() {
        ArrayList<N> nodeList = new ArrayList<>(getGraph().getNodes());
        for(int i = 0; i < nodeList.size(); i++)
        {
            getResult().setOpen(nodeList.get(i));
            BiConsumer<N,SearchResult> listener = (val, val2) -> {val2.setClosed(val);};
            getResult().addNodeClosedListener(listener);
        }
        Queue<N> queue = new LinkedList<>();
        N currentNode = getStart();
        getResult().setClosed(currentNode);
        queue.add(currentNode);

        while (!queue.isEmpty() || !isStopped())
        {
            currentNode = queue.poll();
            ArrayList<Edge> neighbors = new ArrayList<>(getGraph().getNeighbours(currentNode));
            for (int i = 0; i < neighbors.size(); i++)
            {
                N node = (N) neighbors.get(i).getDestination();
                if(getResult().getNodeStatus(node) != NodeStatus.CLOSED)
                {
                    getResult().close(currentNode,new NodeInformation<>(neighbors.get(i),0));
                    queue.add(node);
                }
            }
        }
        return getResult();
    }

    @Override
    public void stop() {
        setStopped(true);
    }
}

package de.jpp.algorithm;

import de.jpp.algorithm.interfaces.*;
import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.Graph;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class DijkstraSearch<N> extends BreadthFirstSearchTemplate {
    public DijkstraSearch(boolean stopped, Graph graph, Object start, SearchResultImpl result) {
        super(stopped, graph, start, result);
    }

    @Override
    public SearchResult findPaths(SearchStopStrategy type) {
        ArrayList<N> list = new ArrayList<>(getGraph().getNodes());
        ArrayList<Edge> edgeList = new ArrayList<>(getGraph().getEdges());
        for (int i = 0; i <  list.size(); i++)
        {
            getResult().open(list.get(i),new NodeInformation<>(new Edge<>(),100000000));
        }
        double minDistance = 100000000;
        double overallDistance = 0;
        int smallestNodeIndex = 0;
        getResult().open(getStart(), new NodeInformation<>(new Edge<>(),0));
        N currentNode = (N) getStart();
        ArrayList<Edge> neighbor = new ArrayList<>(getGraph().getNeighbours(currentNode));
        while (!type.stopSearch(currentNode) || !isStopped()) {

            for (int i = 0; i < neighbor.size(); i++) {
                if (getResult().getNodeStatus(neighbor.get(i).getDestination()) == NodeStatus.OPEN || getResult().getNodeStatus(neighbor.get(i).getDestination()) == NodeStatus.UNKOWN) {
                    getResult().close(neighbor.get(i).getDestination(), new NodeInformation<>(neighbor.get(i), (double) neighbor.get(i).getAnnotation().get()));
                }
            }

            for (int i = 0; i < neighbor.size(); i++)
            {
                if(getResult().getInformation(neighbor.get(i).getDestination()).getDistance() < minDistance)
                {
                    minDistance = getResult().getInformation(neighbor.get(i).getDestination()).getDistance();
                    smallestNodeIndex = i;
                }
            }
            overallDistance+=minDistance;
            currentNode = (N) neighbor.get(smallestNodeIndex).getDestination();
            getResult().close(neighbor.get(smallestNodeIndex).getDestination(),new NodeInformation<>(neighbor.get(smallestNodeIndex), overallDistance));
        }
        return getResult();
    }

    @Override
    public SearchResult findAllPaths() {
        ArrayList<N> list = new ArrayList<>(getGraph().getNodes());
        ArrayList<Edge> edgeList = new ArrayList<>(getGraph().getEdges());
        for (int i = 0; i <  list.size(); i++)
        {
            getResult().open(list.get(i),new NodeInformation<>(new Edge<>(),100000000));
        }
        double minDistance = 100000000;
        double overallDistance = 0;
        int smallestNodeIndex = 0;
        getResult().open(getStart(), new NodeInformation<>(new Edge<>(),0));
        N currentNode = (N) getStart();
        ArrayList<Edge> neighbor = new ArrayList<>(getGraph().getNeighbours(currentNode));
        while (getResult().getAllOpenNodes() != null || !isStopped()) {

            for (int i = 0; i < neighbor.size(); i++) {
                if (getResult().getNodeStatus(neighbor.get(i).getDestination()) == NodeStatus.OPEN || getResult().getNodeStatus(neighbor.get(i).getDestination()) == NodeStatus.UNKOWN) {
                    getResult().close(neighbor.get(i).getDestination(), new NodeInformation<>(neighbor.get(i), (double) neighbor.get(i).getAnnotation().get()));
                }
            }

            for (int i = 0; i < neighbor.size(); i++)
            {
                if(getResult().getInformation(neighbor.get(i).getDestination()).getDistance() < minDistance)
                {
                    minDistance = getResult().getInformation(neighbor.get(i).getDestination()).getDistance();
                    smallestNodeIndex = i;
                }
            }
            overallDistance+=minDistance;
            currentNode = (N) neighbor.get(smallestNodeIndex).getDestination();
            getResult().close(neighbor.get(smallestNodeIndex).getDestination(),new NodeInformation<>(neighbor.get(smallestNodeIndex), overallDistance));
        }
        return getResult();
    }

    @Override
    public ObservableSearchResult getSearchResult() {
        ArrayList<N> list = new ArrayList<>(getGraph().getNodes());
        ArrayList<Edge> edgeList = new ArrayList<>(getGraph().getEdges());
        for (int i = 0; i <  list.size(); i++)
        {
            getResult().open(list.get(i),new NodeInformation<>(new Edge<>(),100000000));
            BiConsumer<N,SearchResult> listener = new BiConsumer<N, SearchResult>() {
                @Override
                public void accept(N n, SearchResult searchResult) {
                    searchResult.setOpen(n);
                }
            };
            getResult().addNodeOpenedListener(listener);
        }
        double minDistance = 100000000;
        double overallDistance = 0;
        int smallestNodeIndex = 0;
        getResult().open(getStart(), new NodeInformation<>(new Edge<>(),0));
        N currentNode = (N) getStart();
        ArrayList<Edge> neighbor = new ArrayList<>(getGraph().getNeighbours(currentNode));
        while (!isStopped()) {

            for (int i = 0; i < neighbor.size(); i++) {
                if (getResult().getNodeStatus(neighbor.get(i).getDestination()) == NodeStatus.OPEN || getResult().getNodeStatus(neighbor.get(i).getDestination()) == NodeStatus.UNKOWN) {
                    getResult().close(neighbor.get(i).getDestination(), new NodeInformation<>(neighbor.get(i), (double) neighbor.get(i).getAnnotation().get()));
                    BiConsumer<N,SearchResult> listener = new BiConsumer<N, SearchResult>() {
                        @Override
                        public void accept(N n, SearchResult searchResult) {
                            searchResult.setClosed(n);
                        }
                    };
                    getResult().addNodeClosedListener(listener);
                }
            }

            for (int i = 0; i < neighbor.size(); i++)
            {
                if(getResult().getInformation(neighbor.get(i).getDestination()).getDistance() < minDistance)
                {
                    minDistance = getResult().getInformation(neighbor.get(i).getDestination()).getDistance();
                    smallestNodeIndex = i;
                }
            }
            overallDistance+=minDistance;
            currentNode = (N) neighbor.get(smallestNodeIndex).getDestination();
            getResult().close(neighbor.get(smallestNodeIndex).getDestination(),new NodeInformation<>(neighbor.get(smallestNodeIndex), overallDistance));
            BiConsumer<N,SearchResult> listener = new BiConsumer<N, SearchResult>() {
                @Override
                public void accept(N n, SearchResult searchResult) {
                    searchResult.setClosed(n);
                }
            };
            getResult().addNodeClosedListener(listener);
        }
        return getResult();
    }

    @Override
    public void stop() {
        setStopped(true);
    }
}

package de.jpp.algorithm;

import de.jpp.algorithm.interfaces.BreadthFirstSearchTemplate;
import de.jpp.algorithm.interfaces.ObservableSearchResult;
import de.jpp.algorithm.interfaces.SearchResult;
import de.jpp.algorithm.interfaces.SearchStopStrategy;
import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.Graph;

import java.util.ArrayList;

public class BreadthFirstSearch<N>  extends BreadthFirstSearchTemplate<N> {

    public BreadthFirstSearch(boolean stopped, Graph graph, N start, SearchResultImpl result) {
        super(stopped, graph, start, result);
    }

    @Override
    public SearchResult findPaths(SearchStopStrategy type) {

        return getResult();
    }

    @Override
    public SearchResult findAllPaths() {
        ArrayList<Edge> neighbors = new ArrayList<>(getGraph().getNeighbours(getStart()));
        N currentNode = getStart();
        getResult().close(currentNode,new NodeInformation<>(new Edge<>(),0));
        while (!getResult().getAllOpenNodes().isEmpty())
        {
            for (int i = 0; i < neighbors.size(); i++)
            {

            }
        }
        return getResult();
    }

    @Override
    public ObservableSearchResult getSearchResult() {
        return new SearchResultImpl();
    }

    @Override
    public void stop() {

    }
}

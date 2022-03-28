package de.jpp.algorithm;

import de.jpp.algorithm.interfaces.BreadthFirstSearchTemplate;
import de.jpp.algorithm.interfaces.ObservableSearchResult;
import de.jpp.algorithm.interfaces.SearchResult;
import de.jpp.algorithm.interfaces.SearchStopStrategy;
import de.jpp.model.interfaces.Graph;

public class DijkstraSearch extends BreadthFirstSearchTemplate {
    public DijkstraSearch(boolean stopped, Graph graph, Object start, SearchResultImpl result) {
        super(stopped, graph, start, result);
    }

    @Override
    public SearchResult findPaths(SearchStopStrategy type) {
        return null;
    }

    @Override
    public SearchResult findAllPaths() {
        return null;
    }

    @Override
    public ObservableSearchResult getSearchResult() {
        return null;
    }

    @Override
    public void stop() {

    }
}

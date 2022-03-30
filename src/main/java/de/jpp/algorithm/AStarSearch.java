package de.jpp.algorithm;

import de.jpp.algorithm.interfaces.*;
import de.jpp.model.interfaces.Graph;

public class AStarSearch<N> extends BreadthFirstSearchTemplate {
    private N destination;
    private EstimationFunction estToDest;

    public AStarSearch(boolean stopped, Graph graph, Object start, Object dest, SearchResultImpl result, EstimationFunction estimationFunction) {
        super(stopped, graph, start, result);
        this.destination = (N) dest;
        this.estToDest = estimationFunction;
    }

    public N getDestination() {
        return destination;
    }

    public void setDestination(N destination) {
        this.destination = destination;
    }

    public EstimationFunction getEstToDest() {
        return estToDest;
    }

    public void setEstToDest(EstimationFunction estToDest) {
        this.estToDest = estToDest;
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
        setStopped(true);
    }
}

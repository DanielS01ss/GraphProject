package de.jpp.factory;

import de.jpp.algorithm.interfaces.*;
import de.jpp.model.interfaces.Graph;
import de.jpp.model.interfaces.WeightedGraph;

import java.util.ArrayList;

/**
 * A class which is collecting several methods to create new SearchAlgorithms on Graphs with specified types
 *
 * @param <N> the type of nodes in graphs on which this search should operate
 * @param <A> the type of annotation of edges in graphs on which this search should operate
 */
public class SearchFactory<N, A> {


    /**
     * Returns a new SearchAlgorithm instance which searches the specified Graph with a DepthFirstSearch starting at the specified start node
     *
     * @param graph the graph
     * @param start the start node
     * @param <G>   the exact type of the Graph
     * @return a new SearchAlgorithm instance which searches the specified Graph with a DepthFirstSearch starting at the specified start node
     */
    public <G extends Graph<N, A>> SearchAlgorithm<N, A, G> getDepthFirstSearch(G graph, N start) {
        return null;
    }

    /**
     * Returns a new SearchAlgorithm instance which searches the specified Graph with a BreadthFirstSearch starting at the specified start node
     *
     * @param graph the graph
     * @param start the start node
     * @param <G>   the exact type of the Graph
     * @return a new SearchAlgorithm instance which searches the specified Graph with a BreadthFirstSearch starting at the specified start node
     */
    public <G extends Graph<N, A>> SearchAlgorithm<N, A, G> getBreadthFirstSearch(G graph, N start) {
        throw new UnsupportedOperationException("not supported yet!");
    }

    /**
     * Returns a new SearchAlgorithm instance which searches the specified WeightedGraph with a BreadthFirstSearch starting at the specified start node
     *
     * @param graph the graph
     * @param start the start node
     * @param <G>   the exact type of the WeightedGraph
     * @return a new SearchAlgorithm instance which searches the specified Graph with a BreadthFirstSearch starting at the specified start node
     */
    public <G extends WeightedGraph<N, A>> SearchAlgorithm<N, A, G> getDijkstra(G graph, N start) {
        throw new UnsupportedOperationException("not supported yet!");
    }

    /**
     * Returns a new SearchAlgorithm instance which searches the specified WeightedGraph with a A*Search starting at the specified start node aiming at the specified destination node
     *
     * @param graph              the graph
     * @param start              the start node
     * @param dest               the destination node
     * @param estimationFunction the heuristic function h estimated the distance from the current node to the destination
     * @param <G>                the exact type of the WeightedGraph
     * @return a new SearchAlgorithm instance which searches the specified WeightedGraph with a A*Search starting at the specified start node aiming at the specified destination node
     */

    public <G extends WeightedGraph<N, A>> SearchAlgorithm<N, A, G> getAStar(G graph, N start, N dest, EstimationFunction<N> estimationFunction) {
        throw new UnsupportedOperationException("not supported yet!");
    }


}

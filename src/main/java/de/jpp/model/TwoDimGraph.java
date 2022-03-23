package de.jpp.model;

import de.jpp.model.interfaces.Edge;

import java.util.Optional;

/**
 * A Two Dimensional graph. <br>
 * The abstract-tag is only set because the tests will not compile otherwise. You should remove it!
 */
public class TwoDimGraph extends ObservableGraphimpl<XYNode,Double>{
    /**
     * Adds an edge to the graph which is automatically initialised with the euclidian distance of the nodes <br>
     * Returns the newly created edge
     *
     * @param start the start node of the edge
     * @param dest  the destination node of the edge
     * @return the newly created edge
     */
    public Edge<XYNode, Double> addEuclidianEdge(XYNode start, XYNode dest) {
        Double weight = start.euclidianDistTo(dest);
        addEdge(start,dest,Optional.of(weight));
        return new Edge<>(start,dest,Optional.of(weight));
    }


}


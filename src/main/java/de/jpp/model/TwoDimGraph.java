package de.jpp.model;

import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.ObservableGraph;
import de.jpp.model.interfaces.WeightedGraph;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * A Two Dimensional graph. <br>
 * The abstract-tag is only set because the tests will not compile otherwise. You should remove it!
 */
public class TwoDimGraph implements WeightedGraph<XYNode,Double>, ObservableGraph<XYNode,Double> {
    /**
     * Adds an edge to the graph which is automatically initialised with the euclidian distance of the nodes <br>
     * Returns the newly created edge
     *
     * @param start the start node of the edge
     * @param dest  the destination node of the edge
     * @return the newly created edge
     */

    private ObservableGraphimpl graph;

    public TwoDimGraph(ObservableGraphimpl graph) {
        this.graph = graph;
    }

    public TwoDimGraph() {
        graph = new ObservableGraphimpl();
    }

    public Edge<XYNode, Double> addEuclidianEdge(XYNode start, XYNode dest) {
        Double weight = start.euclidianDistTo(dest);
        addEdge(start,dest,Optional.of(weight));
        return new Edge<>(start,dest,Optional.of(weight));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TwoDimGraph graph1 = (TwoDimGraph) o;
        return graph.equals(graph1.graph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph);
    }

    @Override
    public boolean addNode(XYNode node) {
        return graph.addNode(node);
    }

    @Override
    public boolean addNodes(Collection<? extends XYNode> nodes) {
        return graph.addNodes(nodes);
    }

    @Override
    public boolean addNodes(XYNode... nodes) {
        return graph.addNodes(nodes);
    }

    @Override
    public Collection<XYNode> getNodes() {
        return graph.getNodes();
    }

    @Override
    public Edge<XYNode, Double> addEdge(XYNode start, XYNode destination, Optional<Double> annotation) {
        graph.addEdge(start,destination,annotation);
        return new Edge<>(start,destination,annotation);
    }

    @Override
    public boolean removeEdge(Edge<XYNode, Double> edge) {
        return graph.removeEdge(edge);
    }

    @Override
    public Collection<Edge<XYNode, Double>> getNeighbours(XYNode node) {
        return graph.getNeighbours(node);
    }

    @Override
    public Collection<Edge<XYNode, Double>> getReachableFrom(XYNode node) {
        return graph.getReachableFrom(node);
    }

    @Override
    public Collection<Edge<XYNode, Double>> getEdges() {
        return graph.getEdges();
    }

    @Override
    public boolean removeNode(XYNode node) {
        return graph.removeNode(node);
    }

    @Override
    public boolean removeNodes(Collection<? extends XYNode> nodes) {
        return graph.removeNodes(nodes);
    }

    @Override
    public boolean removeNodes(XYNode... nodes) {
        return graph.removeNodes(nodes);
    }

    @Override
    public void clear() {
        graph.clear();
    }

    @Override
    public void addNodeAddedListener(Consumer<XYNode> listener) {
        graph.addNodeAddedListener(listener);
    }

    @Override
    public void addNodeRemovedListener(Consumer<XYNode> listener) {
        graph.addNodeRemovedListener(listener);
    }

    @Override
    public void addEdgeAddedListener(Consumer<Edge<XYNode, Double>> listener) {
        graph.addEdgeAddedListener(listener);
    }

    @Override
    public void addEdgeRemovedListener(Consumer<Edge<XYNode, Double>> listener) {
        graph.addEdgeRemovedListener(listener);
    }

    @Override
    public void removeNodeAddedListener(Consumer<XYNode> listener) {
        graph.removeNodeAddedListener(listener);
    }

    @Override
    public void removeNodeRemovedListener(Consumer<XYNode> listener) {
        graph.removeNodeRemovedListener(listener);
    }

    @Override
    public void removeEdgeAddedListener(Consumer<Edge<XYNode, Double>> listener) {
        graph.removeEdgeAddedListener(listener);
    }

    @Override
    public void removeEdgeRemovedListener(Consumer<Edge<XYNode, Double>> listener) {
        graph.removeEdgeRemovedListener(listener);
    }

    @Override
    public void addNeighboursListedListener(Consumer<Collection<Edge<XYNode, Double>>> listener) {
        graph.addNeighboursListedListener(listener);
    }

    @Override
    public void addReachableListedListener(Consumer<Collection<Edge<XYNode, Double>>> listener) {
        graph.addReachableListedListener(listener);
    }

    @Override
    public void addNodesListedListener(Consumer<Collection<XYNode>> listener) {
        graph.addNodesListedListener(listener);
    }

    @Override
    public void addEdgesListedListener(Consumer<Collection<Edge<XYNode, Double>>> listener) {
        graph.addEdgesListedListener(listener);
    }

    @Override
    public void removeNeighboursListedListener(Consumer<Collection<Edge<XYNode, Double>>> listener) {
        graph.removeNeighboursListedListener(listener);
    }

    @Override
    public void removeReachableListedListener(Consumer<Collection<Edge<XYNode, Double>>> listener) {
        graph.removeReachableListedListener(listener);
    }

    @Override
    public void removeNodesListedListener(Consumer<Collection<XYNode>> listener) {
        graph.removeNodesListedListener(listener);
    }

    @Override
    public void removeEdgesListedListener(Consumer<Collection<Edge<XYNode, Double>>> listener) {
        graph.removeEdgesListedListener(listener);
    }

    @Override
    public double getDistance(Edge<XYNode, Double> edge) {
        return graph.getDistance(edge);
    }
}


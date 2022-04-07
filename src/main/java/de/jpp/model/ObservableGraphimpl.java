package de.jpp.model;

import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.Graph;
import de.jpp.model.interfaces.ObservableGraph;
import de.jpp.model.interfaces.WeightedGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class ObservableGraphimpl<N extends XYNode,A> implements ObservableGraph, WeightedGraph {

    protected GraphImpl graph;

    protected ArrayList<Consumer<N>> nodeAddedListner;
    protected ArrayList<Consumer<N>> nodeRemovedListner;
    protected ArrayList<Consumer<Edge>> edgeAddedListner;
    protected ArrayList<Consumer<Edge>> edgeRemovedListner;

    protected ArrayList<Consumer<N>> neighborsListedListner;
    protected ArrayList<Consumer<N>> reachableListedListner;
    protected ArrayList<Consumer<Edge>> edgesListedListner;
    protected ArrayList<Consumer<N>> nodesListedListner;

    public ObservableGraphimpl() {
        graph = new GraphImpl();
        nodeAddedListner = new ArrayList<>();
        nodeRemovedListner = new ArrayList<>();
        edgeAddedListner = new ArrayList<>();
        edgeRemovedListner = new ArrayList<>();
        neighborsListedListner = new ArrayList<>();
        reachableListedListner = new ArrayList<>();
        edgesListedListner = new ArrayList<>();
        nodesListedListner = new ArrayList<>();
    }

    public ObservableGraphimpl(GraphImpl graph, ArrayList<Consumer<N>> nodeAddedListner, ArrayList<Consumer<N>> nodeRemovedListner, ArrayList<Consumer<Edge>> edgeAddedListner, ArrayList<Consumer<Edge>> edgeRemovedListner, ArrayList<Consumer<N>> neighborsListedListner, ArrayList<Consumer<N>> reachableListedListner, ArrayList<Consumer<Edge>> edgesListedListner, ArrayList<Consumer<N>> nodesListedListner) {
        this.graph = graph;
        this.nodeAddedListner = nodeAddedListner;
        this.nodeRemovedListner = nodeRemovedListner;
        this.edgeAddedListner = edgeAddedListner;
        this.edgeRemovedListner = edgeRemovedListner;
        this.neighborsListedListner = neighborsListedListner;
        this.reachableListedListner = reachableListedListner;
        this.edgesListedListner = edgesListedListner;
        this.nodesListedListner = nodesListedListner;
    }

    public GraphImpl getGraphImpl(){ return graph;}
    public Graph getGraph() {
        return graph;
    }

    public ArrayList<Consumer<N>> getNodeAddedListner() {
        return nodeAddedListner;
    }

    public ArrayList<Consumer<N>> getNodeRemovedListner() {
        return nodeRemovedListner;
    }

    public ArrayList<Consumer<Edge>> getEdgeAddedListner() {
        return edgeAddedListner;
    }

    public ArrayList<Consumer<Edge>> getEdgeRemovedListner() {
        return edgeRemovedListner;
    }

    public ArrayList<Consumer<N>> getNeighborsListedListner() {
        return neighborsListedListner;
    }

    public ArrayList<Consumer<N>> getReachableListedListner() {
        return reachableListedListner;
    }

    public ArrayList<Consumer<Edge>> getEdgesListedListner() {
        return edgesListedListner;
    }

    public ArrayList<Consumer<N>> getNodesListedListner() {
        return nodesListedListner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObservableGraphimpl<?,?> that = (ObservableGraphimpl<?,?>) o;
        return graph.equals(that.graph) && nodeAddedListner.equals(that.nodeAddedListner) && nodeRemovedListner.equals(that.nodeRemovedListner) && edgeAddedListner.equals(that.edgeAddedListner) && edgeRemovedListner.equals(that.edgeRemovedListner) && neighborsListedListner.equals(that.neighborsListedListner) && reachableListedListner.equals(that.reachableListedListner) && edgesListedListner.equals(that.edgesListedListner) && nodesListedListner.equals(that.nodesListedListner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph, nodeAddedListner, nodeRemovedListner, edgeAddedListner, edgeRemovedListner, neighborsListedListner, reachableListedListner, edgesListedListner, nodesListedListner);
    }

    @Override
    public boolean addNode(Object node) {
        return graph.addNode((N) node);
    }

    @Override
    public boolean addNodes(Collection nodes) {
        return graph.addNodes(nodes);
    }

    @Override
    public boolean addNodes(Object[] nodes) {
        return graph.addNodes(nodes);
    }

    @Override
    public Collection getNodes() {
        return graph.getNodes();
    }

    @Override
    public Edge addEdge(Object start, Object destination, Optional annotation) {
        graph.addEdge(start,destination,annotation);
        return new Edge<>(start,destination,annotation);
    }

    @Override
    public boolean removeEdge(Edge edge) {
        return graph.removeEdge(edge);
    }

    @Override
    public Collection<Edge<N,A>> getNeighbours(Object node) {
        return graph.getNeighbours(node);
    }

    @Override
    public Collection<Edge<N,A>> getReachableFrom(Object node) {
        return graph.getReachableFrom(node);
    }

    @Override
    public Collection<Edge<N,A>> getEdges() {
        return graph.getEdges();
    }

    @Override
    public boolean removeNode(Object node) {
        return graph.removeNode(node);
    }

    @Override
    public boolean removeNodes(Collection nodes) {
        return graph.removeNodes(nodes);
    }

    @Override
    public boolean removeNodes(Object[] nodes) {
        return graph.removeNodes(nodes);
    }

    @Override
    public void clear() {
        graph.clear();
    }

    @Override
    public void addNodeAddedListener(Consumer listener) {
        nodeAddedListner.add(listener);
    }

    @Override
    public void addNodeRemovedListener(Consumer listener) {
        nodeRemovedListner.add(listener);
    }

    @Override
    public void removeNodeAddedListener(Consumer listener) {
        nodeAddedListner.remove(listener);
    }

    @Override
    public void removeNodeRemovedListener(Consumer listener) {
        nodeRemovedListner.remove(listener);
    }

    @Override
    public void removeEdgeRemovedListener(Consumer listener) {
        edgeRemovedListner.remove(listener);
    }

    @Override
    public void removeEdgeAddedListener(Consumer listener) {
        edgeAddedListner.remove(listener);
    }

    @Override
    public void addEdgeRemovedListener(Consumer listener) {
        listener = (val) -> {graph.removeEdge((Edge) val);};
        listener.accept(new Edge<N,A>());
        edgeRemovedListner.add(listener);
    }

    @Override
    public void addEdgeAddedListener(Consumer listener) {
        listener = (val) -> {graph.addEdge(val, val, Optional.of(val));};
        listener.accept(new Edge<>());
        edgeAddedListner.add(listener);
    }

    @Override
    public void addNeighboursListedListener(Consumer listener) {
        listener = (val) -> {graph.getNeighbours(val);};
        listener.accept(new XYNode());
        neighborsListedListner.add(listener);
    }

    @Override
    public void addReachableListedListener(Consumer listener) {
        listener = (val) -> {graph.getReachableFrom(val);};
        listener.accept(new XYNode());
        reachableListedListner.add(listener);
    }

    @Override
    public void addNodesListedListener(Consumer listener) {
        listener = (val) -> {graph.getNodes();};
        listener.accept(new XYNode());
        nodesListedListner.add(listener);
    }

    @Override
    public void addEdgesListedListener(Consumer listener) {
        listener = (val) -> {graph.getEdges();};
        listener.accept(new Edge<>());
        edgesListedListner.add(listener);
    }

    @Override
    public void removeNeighboursListedListener(Consumer listener) {
        neighborsListedListner.remove(listener);
    }

    @Override
    public void removeReachableListedListener(Consumer listener) {
        reachableListedListner.remove(listener);
    }

    @Override
    public void removeNodesListedListener(Consumer listener) {
        nodesListedListner.remove(listener);
    }

    @Override
    public void removeEdgesListedListener(Consumer listener) {
        edgesListedListner.remove(listener);
    }

    @Override
    public double getDistance(Edge edge) {
        return (double) edge.getAnnotation().get();
    }
}

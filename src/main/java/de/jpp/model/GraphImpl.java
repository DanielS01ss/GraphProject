package de.jpp.model;

import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.Graph;
import org.w3c.dom.Node;

import java.util.*;

public class GraphImpl<N, A> implements Graph<N,A> {

    private ArrayList<N> nodes;
    private HashMap<N,ArrayList<Edge>> edges;

    public GraphImpl() {
        nodes = new ArrayList<>();
        edges = new HashMap<>();
    }

    public GraphImpl(ArrayList<N> nodes, HashMap<N, ArrayList<Edge>> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    private ArrayList<Edge> EnsureEdgeListNotNull(Node node)
    {
        ArrayList<Edge> list = new ArrayList<>();
        for(Map.Entry<N,ArrayList<Edge>> e : edges.entrySet())
        {
            if(e.getKey().equals(node))
            {
                if(e.getValue() == null)
                    return new ArrayList<>();
                else
                    list.addAll(e.getValue());
            }

        }
        return list;
    }

    @Override
    public String toString() {
        return "GraphImpl{" +
                "nodes=" + nodes +
                ", edges=" + edges +
                '}';
    }

    @Override
    public boolean addNode(Object node) {
        edges.put((N) node,new ArrayList<>());
        return nodes.add((N) node);
    }

    @Override
    public boolean addNodes(Collection nodes) {
        for(Object s : nodes)
        {
            edges.put((N) s, new ArrayList<>());
        }
        ArrayList<N> list = new ArrayList<>(new HashSet<>(nodes));
        for(int i = 0; i < list.size(); i++)
        {
            this.nodes.add(list.get(i));
        }
        return nodes.size() != 0;
    }

    @Override
    public boolean addNodes(Object[] nodes) {
        ArrayList<N> node = new ArrayList<>(new HashSet<>(Arrays.asList((N[]) nodes)));
        for(Object s : node)
        {
            edges.put((N) s, new ArrayList<>());
        }
        for(int i = 0; i < node.size(); i++)
        {
            this.nodes.add(node.get(i));
        }
        return true;
    }

    @Override
    public Collection getNodes() {
        return nodes;
    }

    @Override
    public Edge addEdge(Object start, Object destination, Optional annotation) {
        Edge edge = new Edge(start,destination,annotation);
        for(Map.Entry<N,ArrayList<Edge>> s : edges.entrySet())
        {
            if(s.getKey().equals(start))
            {
                s.getValue().add(edge);
            }
        }
        return edge;
    }

    @Override
    public boolean removeEdge(Edge edge) {
        for(Map.Entry<N,ArrayList<Edge>> s : edges.entrySet())
        {
            if(s.getValue().contains(edge))
            {
                s.getValue().remove(edge);
            }
        }
        return false;
    }

    @Override
    public Collection<Edge> getNeighbours(Object node) {
        ArrayList<Edge> list = new ArrayList<>();
        for(Map.Entry<N,ArrayList<Edge>> s : edges.entrySet())
        {
            if(s.getKey().equals(node))
            {
                list.addAll(s.getValue());
            }
        }
        return list;
    }

    @Override
    public Collection<Edge> getReachableFrom(Object node) {
        ArrayList<Edge> list = new ArrayList<>();
        for(Map.Entry<N,ArrayList<Edge>> s : edges.entrySet())
        {
            for(Edge e : s.getValue())
            {
                if(e.getDestination().equals(node))
                {
                    list.add(e);
                }
            }
        }
        return list;
    }

    @Override
    public Collection<Edge<N,A>> getEdges() {

        ArrayList<Edge> list = new ArrayList<>();
        for(Map.Entry<N,ArrayList<Edge>> s : edges.entrySet())
        {
            list.addAll(s.getValue());
        }


        return (Collection)list;

    }

    @Override
    public boolean removeNode(Object node) {
        edges.remove(node);
        return nodes.remove(node);
    }

    @Override
    public boolean removeNodes(Collection nodes) {
        for(Object s : nodes)
        {
            edges.remove(s);
        }
        return this.nodes.removeAll(nodes);
    }

    @Override
    public boolean removeNodes(Object[] nodes) {
        ArrayList<N> node = new ArrayList<>(Arrays.asList((N)nodes));
        for(Object s : node)
        {
            edges.remove(s);
        }
        return this.nodes.removeAll(node);
    }

    @Override
    public void clear() {
        this.nodes.clear();
        this.edges.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphImpl<?, ?> graph = (GraphImpl<?, ?>) o;
        return nodes.equals(graph.nodes) && edges.equals(graph.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes, edges);
    }
}

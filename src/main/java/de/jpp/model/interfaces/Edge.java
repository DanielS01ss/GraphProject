package de.jpp.model.interfaces;

import java.util.Objects;
import java.util.Optional;

public class Edge<N, A> {


    private N start;
    private N dest;
    private Optional<A> annotation;
    /**
     * Creates a new edge with the specified start node, destination node and annotation
     *
     * @param start      the start node
     * @param dest       the destination node
     * @param annotation the annotation
     */
    public Edge(N start, N dest, Optional<A> annotation) {
        if(start != null && dest != null)
        {
            this.start = start;
            this.dest = dest;
        }
        this.annotation = annotation;
    }

    public Edge() {
    }

    public Edge(N start, N dest)
    {
        if(start != null && dest != null)
        {
            this.start = start;
            this.dest = dest;
        }
    }

    /**
     * Returns the start node of this edge
     *
     * @return the start node of this edge
     */
    public N getStart() {
        return start;
    }

    /**
     * Returns the destination node of this edge
     *
     * @return the destination node of this edge
     */
    public N getDestination() {
        return dest;
    }

    /**
     * Returns the annotation of this edge
     *
     * @return the annotation of this edge
     */
    public Optional<A> getAnnotation() {
        return annotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?, ?> edge = (Edge<?, ?>) o;
        return start.equals(edge.start) && dest.equals(edge.dest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, dest);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "start=" + start +
                ", dest=" + dest +
                ", weight=" + annotation.get() +
                '}';
    }
}

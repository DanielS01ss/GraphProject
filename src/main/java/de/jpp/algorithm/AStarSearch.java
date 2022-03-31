package de.jpp.algorithm;

import de.jpp.algorithm.interfaces.*;
import de.jpp.model.interfaces.Edge;
import de.jpp.model.interfaces.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

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
        PriorityQueue<N> queue = new PriorityQueue<>(20, new Comparator<N>() {
            @Override
            public int compare(N o1, N o2) {
                if(getResult().getInformation(o1).getDistance() > getResult().getInformation(o2).getDistance())
                {
                    return 1;
                }
                else
                    if(getResult().getInformation(o1).getDistance() < getResult().getInformation(o2).getDistance())
                    {
                        return -1;
                    }
                    else
                        return 0;
            }
        });
        getResult().open(getStart(),new NodeInformation<>(new Edge<>(),0));
        N current = (N) getStart();
        queue.add((N) getStart());

        while (!queue.isEmpty() || type.stopSearch(current) || !isStopped())
        {
            current = queue.poll();
            getResult().setOpen(current);

            ArrayList<Edge> list = new ArrayList<>(getGraph().getNeighbours(current));
            for(int i = 0; i < list.size(); i++)
            {
                N child = (N) list.get(i).getDestination();
                double cost = (double) list.get(i).getAnnotation().get();
                double temp_g_score = estToDest.getEstimatedDistance(current,child) + cost;
                double temp_f_score = temp_g_score + getResult().getInformation(child).getDistance();

                if(getResult().getNodeStatus(child) == NodeStatus.CLOSED && temp_f_score >= estToDest.getEstimatedDistance(current,child))
                {
                    continue;
                }
                else
                    if(queue.contains(child) || temp_f_score <= estToDest.getEstimatedDistance(current,child))
                    {
                        if(queue.contains(child)){
                            queue.remove(child);
                        }

                        queue.add(child);
                    }
            }
        }
        return getResult();
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

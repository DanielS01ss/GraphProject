package de.jpp.factory;

import de.jpp.algorithm.SearchResultImpl;
import de.jpp.algorithm.StartToDestStrategy;
import de.jpp.algorithm.interfaces.SearchStopStrategy;

import java.util.ArrayList;

public class SearchStopFactory {

    /**
     * Returns a SearchStopStrategy which never stops on its own
     *
     * @param <N> the exact types of nodes in the graph underlying the search
     * @return a SearchStopStrategy which never stops on its own
     */
    public <N> SearchStopStrategy<N> expandAllNodes() {
        SearchStopStrategy<N> searchStopStrategy = new SearchStopStrategy<N>() {
            @Override
            public boolean stopSearch(N lastClosedNode) {
                return false;
            }
        };
        return searchStopStrategy;
    }

    /**
     * Returns a SearchStopStrategy which stops after a specified number of nodes are CLOSED
     *
     * @param maxCount the maximum number of nodes which may be CLOSED
     * @param <N>      the exact type of nodes in the graph underlying the search
     * @return a SearchStopStrategy which stops after a specified number of nodes are CLOSED
     */
    public <N> SearchStopStrategy<N> maxNodeCount(int maxCount) {
        SearchStopStrategy<N> searchStopStrategy = new SearchStopStrategy<N>() {
            private ArrayList<N> list = new ArrayList<>(new SearchResultImpl<N>().getAllClosedNodes());
            private int node = list.size();
            @Override
            public boolean stopSearch(N lastClosedNode) {
                if(node >= maxCount)
                    return true;
                return false;
            }
        };
        return searchStopStrategy;
    }

    /**
     * Returns a SearchStopStrategy which stops after a specified destination has been reached
     *
     * @param dest the destination
     * @param <N>  the exact type of nodes in the graph underlying the search
     * @return a SearchStopStrategy which stops after a specified destination has been reached
     */
    public <N> StartToDestStrategy<N> startToDest(N dest) {
        StartToDestStrategy<N>  startToDestStrategy = new StartToDestStrategy<>(dest);
        return startToDestStrategy;
    }

}

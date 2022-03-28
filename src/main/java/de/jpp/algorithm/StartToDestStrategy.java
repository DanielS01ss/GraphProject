package de.jpp.algorithm;

import de.jpp.algorithm.interfaces.SearchStopStrategy;


public class StartToDestStrategy<N> implements SearchStopStrategy<N> {

    private N dest;

    public StartToDestStrategy() {
    }

    public StartToDestStrategy(N dest) {
        this.dest = dest;
    }

    @Override
    public boolean stopSearch(N lastClosedNode) {
        if(dest.equals(lastClosedNode))
            return true;
        return false;
    }

    /**
     * Returns the destination node of this search
     *
     * @Returns the destination node of this search
     */
    public N getDest() {
        return dest;
    }

}

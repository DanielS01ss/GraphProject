package de.jpp.algorithm;

import de.jpp.algorithm.interfaces.SearchStopStrategy;


public class StartToDestStrategy<N> implements SearchStopStrategy<N> {

    @Override
    public boolean stopSearch(N lastClosedNode) {
        throw new UnsupportedOperationException("not supported yet!");
    }

    /**
     * Returns the destination node of this search
     *
     * @Returns the destination node of this search
     */
    public N getDest() {
        throw new UnsupportedOperationException("not supported yet!");
    }

}

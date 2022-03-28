package com.example.graphen;

import de.jpp.model.ObservableGraphimpl;
import de.jpp.model.TwoDimGraph;
import de.jpp.model.XYNode;

import java.util.Collection;

public class ApplicationResources {
     public static TwoDimGraph appGraph;

     public static XYNode getNodeById(int id)
     {
          XYNode node = null;
          Collection<XYNode> nodes = appGraph.getNodes();
          for(XYNode nodeInd:nodes)
          {
               if(nodeInd.getLabel().equals(String.valueOf(id)))
               {
                    node = nodeInd;
                    return nodeInd;
               }
          }
          return null;
     }
}

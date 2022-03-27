package de.jpp.algorithm;

import de.jpp.algorithm.interfaces.NodeStatus;
import de.jpp.algorithm.interfaces.ObservableSearchResult;
import de.jpp.algorithm.interfaces.SearchResult;
import de.jpp.model.XYNode;
import de.jpp.model.interfaces.Edge;

import java.util.*;
import java.util.function.BiConsumer;

public class SearchResultImpl<N> implements ObservableSearchResult {

    private HashMap<N,NodeStatus> statusMap;
    private HashMap<N,NodeInformation> infoMap;
    private ArrayList<BiConsumer> onOpen;
    private ArrayList<BiConsumer> onClose;

    public SearchResultImpl() {
    }

    public SearchResultImpl(HashMap<N, NodeStatus> statusMap, HashMap<N, NodeInformation> infoMap, ArrayList<BiConsumer> onOpen, ArrayList<BiConsumer> onClose) {
        this.statusMap = statusMap;
        this.infoMap = infoMap;
        this.onOpen = onOpen;
        this.onClose = onClose;
    }

    public HashMap<N, NodeStatus> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(HashMap<N, NodeStatus> statusMap) {
        this.statusMap = statusMap;
    }

    public HashMap<N, NodeInformation> getInfoMap() {
        return infoMap;
    }

    public void setInfoMap(HashMap<N, NodeInformation> infoMap) {
        this.infoMap = infoMap;
    }

    public ArrayList<BiConsumer> getOnOpen() {
        return onOpen;
    }

    public void setOnOpen(ArrayList<BiConsumer> onOpen) {
        this.onOpen = onOpen;
    }

    public ArrayList<BiConsumer> getOnClose() {
        return onClose;
    }

    public void setOnClose(ArrayList<BiConsumer> onClose) {
        this.onClose = onClose;
    }

    public void open(N node, NodeInformation information)
    {
        statusMap.put(node,NodeStatus.OPEN);
        infoMap.put(node,information);
    }

    public void close(N node, NodeInformation information)
    {
        statusMap.put(node,NodeStatus.CLOSED);
        infoMap.put(node,information);
    }

    public NodeInformation getInformation(N node)
    {
        if(infoMap.get(node) == null)
        {
            return null;
        }
        return infoMap.get(node);
    }

    @Override
    public void addNodeOpenedListener(BiConsumer onOpen) {
        onOpen = (val,val2) -> { setOpen(val); };
        N node = (N) new XYNode();
        onOpen.accept(node, new SearchResultImpl<>());
        this.onOpen.add(onOpen);
    }

    @Override
    public void removeNodeOpenedListener(BiConsumer onOpen) {
        this.onOpen.remove(onOpen);
    }

    @Override
    public void addNodeClosedListener(BiConsumer onClose) {
        onClose = (val,val2) -> { setClosed(val); };
        N node = (N) new XYNode();
        onClose.accept(node, new SearchResultImpl<>());
        this.onClose.add(onClose);
    }

    @Override
    public void removeNodeClosedListener(BiConsumer onClose) {
        this.onClose.remove(onClose);
    }

    @Override
    public NodeStatus getNodeStatus(Object node) {
        return statusMap.get(node);
    }

    @Override
    public Optional<Edge> getPredecessor(Object node) {
        if(infoMap.get(node).getPredecessor() != null)
        {
            return Optional.of(infoMap.get(node).getPredecessor());
        }
        return Optional.empty();
    }

    @Override
    public Collection getAllKnownNodes() {
        ArrayList<N> list = new ArrayList<>();
        for (Map.Entry<N,NodeStatus> m : statusMap.entrySet())
        {
            if(m.getValue() != NodeStatus.UNKOWN)
            {
                list.add(m.getKey());
            }
        }
        return list;
    }

    @Override
    public Collection getAllOpenNodes() {
        ArrayList<N> list = new ArrayList<>();
        for (Map.Entry<N,NodeStatus> m : statusMap.entrySet())
        {
            if(m.getValue() == NodeStatus.OPEN)
            {
                list.add(m.getKey());
            }
        }
        return list;
    }

    @Override
    public void setClosed(Object node) {
        statusMap.put((N) node, NodeStatus.CLOSED);
    }

    @Override
    public void setOpen(Object node) {
        statusMap.put((N) node, NodeStatus.OPEN);
    }

    @Override
    public void clear() {
        statusMap.clear();
        infoMap.clear();
    }

    @Override
    public Optional<List<Edge>> getPathTo(Object dest) {
        return Optional.empty();
    }

}

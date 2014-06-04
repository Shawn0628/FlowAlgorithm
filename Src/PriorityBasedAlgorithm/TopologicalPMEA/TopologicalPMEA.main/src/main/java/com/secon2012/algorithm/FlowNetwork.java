package com.secon2012.algorithm;

import java.util.ArrayList;
import com.secon2012.model.FlowEdge;
import com.secon2012.model.RegularNode;

public interface FlowNetwork {
	public ArrayList<RegularNode> getV();
	public void setV(ArrayList<RegularNode> v);
	public int V();
    // add edge e in both v's and w's adjacency lists
    public void addEdge(FlowEdge e);

    // return list of edges incident to  v
    public Iterable<FlowEdge> adj(int v);

    // return list of all edges
    public Iterable<FlowEdge> edges();

    // string representation of Graph - takes quadratic time
    public String toString();
    public ArrayList<RegularNode> getSourceNodeList();
	public void setSourceNodeList(ArrayList<RegularNode> sourceNodeList);
	public ArrayList<FlowEdge> getSortedAdjEdge (int v);
}

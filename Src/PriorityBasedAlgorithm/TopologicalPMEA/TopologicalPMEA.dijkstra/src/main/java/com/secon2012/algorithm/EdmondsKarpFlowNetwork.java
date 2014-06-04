package com.secon2012.algorithm;

import com.secon2012.model.FlowEdge;
import com.secon2012.model.RegularNode;
import com.secon2012.util.Bag;

import java.util.ArrayList;
import java.util.Collections;

public class EdmondsKarpFlowNetwork implements FlowNetwork{
    private ArrayList<RegularNode> V;
    private int E;
    private Bag<FlowEdge>[] adj;
    private ArrayList<RegularNode> sourceNodeList;
	private ArrayList<RegularNode> splitNonDGNodeList;
	private double transimitionRange;
    
	@SuppressWarnings("unchecked")
	public EdmondsKarpFlowNetwork(int p, ArrayList<RegularNode> array, double transimitionRange) {
		// TODO Auto-generated constructor stub
		this.V = array;
		this.E = 0;
		this.sourceNodeList = getSrcNodeList(p, array);
		this.splitNonDGNodeList = getSplitNonDGNodeList(p, array);
		this.transimitionRange = transimitionRange;
        adj = (Bag<FlowEdge>[]) new Bag[V.size()];
        for (int i = 0; i < V.size(); i++){
            adj[i] = new Bag<FlowEdge>();
        }
        
        //Add edge between i' and i''
        for(int i = 0; i<V.size()-2; i=i+2){
        	addEdge(new FlowEdge(V.get(i), V.get(i+1), V.get(i).getEnergy(), true, false, 0));
        }
        
        //Add edge between s and DG
        for(int i=0; i<this.getSourceNodeList().size(); i++){
        	addEdge(new FlowEdge(getV().get(V.size()-2), getSourceNodeList().get(i), getSourceNodeList().get(i).getDataItem(), false, false, 0));
        }
        
        //Add edge between non-DG and t
        for(int i = 0; i<splitNonDGNodeList.size(); i++){
        	addEdge(new FlowEdge(splitNonDGNodeList.get(i), V.get(V.size()-1),splitNonDGNodeList.get(i).getStorageCapacity() , false, false, 0));
        }
        
        //Add edge between two nodes if they are in transmission range.
        for(int i=0; i < V.size()-2; i=i+2){
        	for(int j=i+2; j<V.size()-2; j=j+2){
        		double distance = Math.sqrt(Math.pow(V.get(i).getX()-V.get(j).getX(), 2) + Math.pow(V.get(i).getY()-V.get(j).getY(), 2));
        		if(distance <= this.transimitionRange){
        			addEdge(new FlowEdge(V.get(j+1), V.get(i),Double.POSITIVE_INFINITY, false, true, Math.pow(distance, 2) * 0.00032 + 0.32));
        			addEdge(new FlowEdge(V.get(i+1), V.get(j),Double.POSITIVE_INFINITY, false, true, Math.pow(distance, 2) * 0.00032 + 0.32) );
        		}                                                                    
        	}
        }
	}

	private ArrayList<RegularNode> getSplitNonDGNodeList(int p, ArrayList<RegularNode> array) {
		// TODO Auto-generated method stub
		ArrayList<RegularNode> a = new ArrayList<RegularNode>();
		for(int i = p*2+1; i<array.size()-2; i=i+2){
			a.add(array.get(i));
		}
		return a;
	}

	private ArrayList<RegularNode> getSrcNodeList(int p,
			ArrayList<RegularNode> array) {
		// TODO Auto-generated method stub
		ArrayList<RegularNode> a = new ArrayList<RegularNode>();
		for(int i = 0; i<2*p; i=i+2){
			a.add(array.get(i));
		}
		return a;
	}

	public ArrayList<RegularNode> getV() {
		return V;
	}

	public void setV(ArrayList<RegularNode> v) {
		V = v;
	}
	
	public int V() {
    	return V.size();
    }
    
    // add edge e in both v's and w's adjacency lists
    public void addEdge(FlowEdge e) {
        E++;
        int v = e.from();
        int w = e.to();
        adj[v].add(e);
        adj[w].add(e);
    }

    // return list of edges incident to  v
    public Iterable<FlowEdge> adj(int v) {
        return adj[v];
    }

    // return list of all edges
    public Iterable<FlowEdge> edges() {
        Bag<FlowEdge> list = new Bag<FlowEdge>();
        for (int i = 0; i < V.size(); i++)
            for (FlowEdge e : adj(i))
                list.add(e);
        return list;
    }

    // string representation of Graph - takes quadratic time
    public String toString() {
        String NEWLINE = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int i = 0; i < V.size(); i++) {
            s.append(i + ":  ");
            for (FlowEdge e : adj[i]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
    
    public ArrayList<RegularNode> getSourceNodeList() {
		return sourceNodeList;
	}

	public void setSourceNodeList(ArrayList<RegularNode> sourceNodeList) {
		this.sourceNodeList = sourceNodeList;
	}
	
	public ArrayList<FlowEdge> getSortedAdjEdge (int v){
		ArrayList<FlowEdge> a = new ArrayList<FlowEdge>();
		for (FlowEdge e : adj(v)) {
			a.add(e);
		}
		Collections.sort(a);
		return a;
	}
}
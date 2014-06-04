package com.secon2012.algorithm;

import com.secon2012.model.FlowEdge;
import com.secon2012.model.RegularNode;

public class EdmondsKarpFordFulkerson implements Cloneable {
    private boolean[] marked;     // marked[v] = true iff s->v path in residual graph
    private FlowEdge[] edgeTo;    // edgeTo[v] = last edge on shortest residual s->v path
    private double value;         // current value of max flow
    private double priorityValue;         // current value of max flow
    public EdmondsKarpFlowNetwork G;
    private double totalEnergyConsumption;
    private double transmissionCost;
    private double nextTransmissionCost;
    private double receivingEnergy;
    
    // max flow in flow network G from s to t
    public EdmondsKarpFordFulkerson(EdmondsKarpFlowNetwork G, RegularNode source, RegularNode sink, double receivingEnergy) {
        int s = source.getID();
        int t = sink.getID();
    	value = excess(G, t);
    	this.nextTransmissionCost = receivingEnergy;
    	this.receivingEnergy = receivingEnergy;
    	this.G = G;
        if (!isFeasible(G, s, t)) {
            throw new RuntimeException("Initial flow is infeasible");
        }

        // while there exists an augmenting path, use it
        
        while (hasAugmentingPath(G, s, t)) {
        	// double edge capacity
        	int secondEdge = 0, penultEdge = 0, totalCount=0, count=0;
        	for (int v = t; v != s; v = edgeTo[v].other(v)) {
        		totalCount++;
        		//System.out.print(v + " ");
        	}
        	for (int v = t; v != s; v = edgeTo[v].other(v)) {
        		if(count == 1){
        			secondEdge = v;
        		}
        		if(totalCount-2 == count){
        			penultEdge = v;
        		}
        		count++;
        	}
        	RegularNode dg = G.getV().get(edgeTo[penultEdge].other(penultEdge));
        	
        	for (int v = t; v != s; v = edgeTo[v].other(v)) {
        		if( edgeTo[v].isEnergyEdge()==true)
        		{
        		transmissionCost = nextTransmissionCost;
        		if (v==secondEdge)
        			{transmissionCost = receivingEnergy;}
        		else if (v==penultEdge)
        			{transmissionCost = nextTransmissionCost;}
        		else 
        			{transmissionCost = nextTransmissionCost + receivingEnergy;}
        		nextTransmissionCost = edgeTo[edgeTo[v].other(v)].getLength();
            	edgeTo[v].setCapacity((edgeTo[v].capacity()- edgeTo[v].flow())/transmissionCost + edgeTo[v].flow());
        		}
        	}
        	
            // compute bottleneck capacity
            double bottle = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            }
            // augment flow
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
            	edgeTo[v].addResidualFlowTo(v, bottle);
                //if(edgeTo[v].isEnergyEdge()) totalEnergyConsumption += bottle;
            }

            value += bottle;
            //System.out.println(bottle);
            
            priorityValue += bottle*dg.getValue();
        	for (int v = t; v != s; v = edgeTo[v].other(v)) {
        		if( edgeTo[v].isEnergyEdge()==true) {
	        		transmissionCost = nextTransmissionCost;
	        		
	        		if (v==secondEdge)
	        			{transmissionCost = receivingEnergy;}
	        		else if (v==penultEdge)
	        			{transmissionCost = nextTransmissionCost;}
	        		else 
	        			{transmissionCost = nextTransmissionCost + receivingEnergy;}
	        		nextTransmissionCost = edgeTo[edgeTo[v].other(v)].getLength();
	        		totalEnergyConsumption += bottle*transmissionCost;
	        		//System.out.println(totalEnergyConsumption +" " + bottle + " "+ transmissionCost);
	        		edgeTo[v].setCapacity(edgeTo[v].flow() + (edgeTo[v].capacity()- edgeTo[v].flow())*transmissionCost);
        		}
        	}           
        }
        // check optimality conditions
        assert check(G, s, t);
    }

	// return value of max flow
    public double value()  {
        return value;
    }
    public double priorityValue()  {
        return priorityValue;
    }
    // is v in the s side of the min s-t cut?
    public boolean inCut(int v)  {
        return marked[v];
    }

    // return an augmenting path if one exists, otherwise return null
    private boolean hasAugmentingPath(EdmondsKarpFlowNetwork g2, int s, int t) {
        edgeTo = new FlowEdge[g2.V()];
        marked = new boolean[g2.V()];
        DijkstraSP d = new DijkstraSP(g2, s, edgeTo);
        
        // is there an augmenting path?
        if(d.hasPathTo(t)){
        	return true;
        }else{
        	return false;
        }
    }
	
	// return excess flow at vertex v
    private double excess(EdmondsKarpFlowNetwork g2, int v) {
        double excess = 0.0;
        for (FlowEdge e : g2.adj(v)) {
            if (v == e.from()) excess -= e.flow();
            else               excess += e.flow();
        }
        return excess;
    }

    // return excess flow at vertex v
    private boolean isFeasible(EdmondsKarpFlowNetwork g2, int s, int t) {
        double EPSILON = 1E-11;

        // check that capacity constraints are satisfied
        for (int v = 0; v < g2.V(); v++) {
            for (FlowEdge e : g2.adj(v)) {
                if (e.flow() < 0 || e.flow() > e.capacity()) {
                    System.err.println("Edge does not satisfy capacity constraints: " + e);
                    return false;
                }
            }
        }

        // check that net flow into a vertex equals zero, except at source and sink
        if (Math.abs(value + excess(g2, s)) > EPSILON) {
            System.err.println("Excess at source = " + excess(g2, s));
            System.err.println("Max flow         = " + value);
            return false;
        }
        if (Math.abs(value - excess(g2, t)) > EPSILON) {
            System.err.println("Excess at sink   = " + excess(g2, t));
            System.err.println("Max flow         = " + value);
            return false;
        }
        for (int v = 0; v < g2.V(); v++) {
            if (v == s || v == t) continue;
            else if (Math.abs(excess(g2, v)) > EPSILON) {
                System.err.println("Net flow out of " + v + " doesn't equal zero");
                return false;
            }
        }
        return true;
    }

    // check optimality conditions
    private boolean check(EdmondsKarpFlowNetwork G, int s, int t) {

        // check that flow is feasible
        if (!isFeasible(G, s, t)) {
            System.err.println("Flow is infeasible");
            return false;
        }

        // check that s is on the source side of min cut and that t is not on source side
        if (!inCut(s)) {
            System.err.println("source " + s + " is not on source side of min cut");
            return false;
        }
        if (inCut(t)) {
            System.err.println("sink " + t + " is on source side of min cut");
            return false;
        }

        // check that value of min cut = value of max flow
        double mincutValue = 0.0;
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if ((v == e.from()) && inCut(e.from()) && !inCut(e.to()))
                    mincutValue += e.capacity();
            }
        }

        double EPSILON = 1E-11;
        if (Math.abs(mincutValue - value) > EPSILON) {
            System.err.println("Max flow value = " + value + ", min cut value = " + mincutValue);
            return false;
        }
        return true;
    }

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	public void round(){
		value = Math.round(value);
		priorityValue = Math.round(priorityValue);
	}

	public double getTotalEnergyConsumption() {
		return totalEnergyConsumption;
	}

	public void setTotalEnergyConsumption(double totalEnergyConsumption) {
		this.totalEnergyConsumption = totalEnergyConsumption;
	}

	public boolean[] getMarked() {
		return marked;
	}

	public void setMarked(boolean[] marked) {
		this.marked = marked;
	}

	public FlowEdge[] getEdgeTo() {
		return edgeTo;
	}

	public void setEdgeTo(FlowEdge[] edgeTo) {
		this.edgeTo = edgeTo;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getPriorityValue() {
		return priorityValue;
	}

	public void setPriorityValue(double priorityValue) {
		this.priorityValue = priorityValue;
	}

	public EdmondsKarpFlowNetwork getG() {
		return G;
	}

	public void setG(EdmondsKarpFlowNetwork g) {
		G = g;
	}

	public double getTransmissionCost() {
		return transmissionCost;
	}

	public void setTransmissionCost(double transmissionCost) {
		this.transmissionCost = transmissionCost;
	}

	public double getNextTransmissionCost() {
		return nextTransmissionCost;
	}

	public void setNextTransmissionCost(double nextTransmissionCost) {
		this.nextTransmissionCost = nextTransmissionCost;
	}

	public double getReceivingEnergy() {
		return receivingEnergy;
	}

	public void setReceivingEnergy(double receivingEnergy) {
		this.receivingEnergy = receivingEnergy;
	}
}
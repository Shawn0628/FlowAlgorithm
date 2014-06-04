
/*************************************************************************
 *  Compilation:  javac FlowEdge.java
 *  Execution:    java FlowEdge
 *  Dependencies: Node.java
 *
 *  Capacitated edge with a flow in a flow network.
 *
 *************************************************************************/

/**
 *  The <tt>FlowEdge</tt> class represents a capacitated edge with a flow
 *  in a digraph.
 *  <p>
 *  For additional documentation, see <a href="/algs4/74or">Section 7.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */

public class FlowEdge implements Comparable<FlowEdge>{
    private final RegularNode v;             // from
    private final RegularNode w;             // to 
    private double capacity;   				 // capacity
	private double flow;            		 // flow
	private boolean isEnergyEdge;            // flag
	private boolean isTransmisionEdge;            // flag
	
	public void setTransmisionEdge(boolean isTransmisionEdge) {
		this.isTransmisionEdge = isTransmisionEdge;
	}

	public boolean isTransmisionEdge() {
		return isTransmisionEdge;
	}

	public boolean isEnergyEdge() {
		return isEnergyEdge;
	}

	public void setEnergyEdge(boolean isEnergyEdge) {
		this.isEnergyEdge = isEnergyEdge;
	}

	private double length;            		 // length
	
    public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public FlowEdge(RegularNode v, RegularNode w, double capacity, boolean isEnergyEdge, boolean isTransmisionEdge, double length) {
        if (capacity < 0) throw new RuntimeException("Negative edge capacity");
        this.v         = v;
        this.w         = w;  
        this.capacity  = capacity;
        this.flow      = 0;
        this.isEnergyEdge = isEnergyEdge;
        this.isTransmisionEdge = isTransmisionEdge;   
        this.length = length;
    }

    // accessor methods
    public int from()         { return v.getID();        }  
    public int to()           { return w.getID();        }  
    public double capacity()  { return capacity; }
    public double flow()      { return flow;     }
    public FlowEdge getFlowEdge() {return this;}
    
    public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

    public int other(int vertex) {
        if      (vertex == v.getID()) return w.getID();
        else if (vertex == w.getID()) return v.getID();
        else throw new RuntimeException("Illegal endpoint");
    }

    public double residualCapacityTo(int vertex) {
        if      (vertex == v.getID()) return flow;
        else if (vertex == w.getID()) return capacity - flow;
        else throw new RuntimeException("Illegal endpoint");
    }

    public void addResidualFlowTo(int vertex, double delta) {
        if      (vertex == v.getID()) flow -= delta;
        else if (vertex == w.getID()) flow += delta;
        else throw new RuntimeException("Illegal endpoint");
    }

	public double getFlow() {
		return flow;
	}

	public void setFlow(double flow) {
		this.flow = flow;
	}

	@Override
	public int compareTo(FlowEdge e) {
		// TODO Auto-generated method stub
		if(this.length > e.length){
			return 1;
		}else 
			if(this.length < this.length){
			return -1;
		}
		else return 0; 
	}
}
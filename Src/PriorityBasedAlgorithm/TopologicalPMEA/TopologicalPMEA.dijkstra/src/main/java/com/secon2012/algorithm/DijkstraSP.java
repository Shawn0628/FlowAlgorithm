package com.secon2012.algorithm;
import com.secon2012.model.FlowEdge;
import com.secon2012.util.IndexMinPQ;
import com.secon2012.util.Stack;

public class DijkstraSP {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private FlowEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices

    public DijkstraSP(FlowNetwork G, int s, FlowEdge[] edgeTo) {
        distTo = new double[G.V()];
        this.edgeTo = edgeTo;
        for (int v = 0; v < G.V(); v++)
        distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        
        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (FlowEdge e : G.adj(v))
            
            	relax(e);
            
        }
        // check optimality conditions
        assert check(G, s);
    }

    // relax edge e and update pq if changed
    private void relax(FlowEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.getLength() && e.residualCapacityTo(w)>0) {
            distTo[w] = distTo[v] + e.getLength();
            edgeTo[w] = e;
            //System.out.println(v+"->"+w + "Forward residualCpacityTo: " + e.residualCapacityTo(w));
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else pq.insert(w, distTo[w]);
        }
        else if (distTo[v] > distTo[w] + e.getLength() && e.residualCapacityTo(v)>0) {
            distTo[v] = distTo[w] + e.getLength();
            edgeTo[v] = e;
           // System.out.println(w+"->"+v + "Backward residualCpacityTo: " + e.residualCapacityTo(v));
            if (pq.contains(v)) pq.decreaseKey(v, distTo[v]);
            else pq.insert(v, distTo[v]);
        }
    }

    // length of shortest path from s to v
    public double distTo(int v) {
        return distTo[v];
    }

    // is there a path from s to v?
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    // shortest path from s to v as an Iterable, null if no such path
    public Iterable<FlowEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<FlowEdge> path = new Stack<FlowEdge>();
        for (FlowEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }


    // check optimality conditions:
    // (i) for all edges e:            distTo[e.to()] <= distTo[e.from()] + e.weight()
    // (ii) for all edge e on the SPT: distTo[e.to()] == distTo[e.from()] + e.weight()
    private boolean check(FlowNetwork g, int s) {

        // check that edge weights are nonnegative
        for (FlowEdge e : g.edges()) {
            if (e.getLength() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < g.V(); v++) {
            if (v == s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < g.V(); v++) {
            for (FlowEdge e : g.adj(v)) {
                int w = e.to();
                if (distTo[v] + e.getLength() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < g.V(); w++) {
            if (edgeTo[w] == null) continue;
            FlowEdge e = edgeTo[w];
            int v = e.from();
            if (w != e.to()) return false;
            if (distTo[v] + e.getLength() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }
}
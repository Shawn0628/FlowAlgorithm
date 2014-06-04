import java.io.Serializable;
import java.util.ArrayList;


public class FrameWorld implements Serializable{
	private ArrayList<RegularNode> array; // store all nodes
	private int x;
	private int y;
	private int p;
	
	public FrameWorld(int p, int x, int y) {
		super();
		this.p = p;
		this.array = new ArrayList<RegularNode>();
		this.x = x;
		this.y = y;
	}

	public ArrayList<RegularNode> getArray() {
		return array;
	}

	public void setArray(ArrayList<RegularNode> array) {
		this.array = array;
	}
	
	public boolean isOccupied(int x, int y){
		for(int i=0; i<array.size(); i++){
			int a = array.get(i).getX();
			int b = array.get(i).getY();
		
			if(a == x && b == y){
				return true;
			} 
		}
		return false;
	}
	
	public void addNode(RegularNode n){
		array.add(n);
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}
}

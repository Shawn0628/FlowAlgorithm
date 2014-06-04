
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.NoSuchElementException;

public class GenerateGraphController {
	private static int count = 0;
	static final int DATAITEMS = 500;
	private static final int STORAGE = 1280;
	private static final int NONDGSENERGY = 500;
	static final int DGSENERGY = 500;
	public static final int TRNMISONRANGE = 150;
	public static final double REVEIVINGENERGY = 0.32;
	private static final int X = 2000; // x axis [0, 99]
	private static final int Y = 2000; // y axis [0, 99]
	static final int UPPERBONDOFPRIORITYVALUE = 100; // randomly create priority value for DG [1,10]
	static final int LOWERBONDOFPRIORITYVALUE = 1;  
	private static final int p = 0;  //the number of source nodes;
	private static final int q = 1000; // the number of non-DG nodes;
	
	//Main client that creates random network, solves max flow, and prints results
    public static void main(String[] args) {
    	try{
        	FrameWorld fw = new FrameWorld(p, X, Y);
        	//create p DGs randomly on Frame world
        	randomlyCreateDG(p, fw);
        	//create q non-DGs randomly on Frame world
        	randomlyCreateNonDG(q, fw);
        	//Add super source node s
        	RegularNode superSource = new RegularNode(0,0, fw.getArray().size(), 0,0,0,0,"s", false);
        	fw.addNode(superSource);
        	//Add super source node t
        	RegularNode superSink = new RegularNode(0,0, fw.getArray().size(), 0,0,0,0,"t", false);
        	fw.addNode(superSink);
        	
        	outputFile(fw);
        	
    	} catch(NoSuchElementException e) {
    		System.out.println("Input invaild, p should not be greater than n square");
    		System.exit(-1);
    	}catch(NumberFormatException e) {
    		e.printStackTrace();
    		System.out.println("Input invaild, Please input two numbers");
    		System.out.println("java Command <how big for your grid network> <how many DGs>");
    		System.exit(-1);
    	}catch(ArrayIndexOutOfBoundsException e) {
    		e.printStackTrace();
    		System.out.println("Input invaild");
    		System.out.println("java Command <how big for your grid network> <how many DGs>");
    		System.exit(-1);
    	}
    }
	
	private static void outputFile(FrameWorld fw) {
		// TODO Auto-generated method stub
		try {
		    ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("FrameWorld.data"));
		    output.writeObject(fw);
		    output.close();
		    System.out.println("Successfuly create graph!");
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}

	private static void randomlyCreateNonDG(int n, FrameWorld fw) {
		// TODO Auto-generated method stub
		while (count < 2*n){
			int x = StdRandom.uniform(0, X);
			int y = StdRandom.uniform(0, Y);
			if(!fw.isOccupied(x, y)){
				//(x,y, ID, energy, storageCapacity, dataItem, value, type)
				fw.addNode(new RegularNode(x, y, count, NONDGSENERGY, STORAGE, 0, 0, "non-DG", false));
				count++;
				fw.addNode(new RegularNode(x, y, count, NONDGSENERGY, STORAGE, 0, 0, "splitednon-DG", false));
				count++;
			}
		}
	}
	
	private static void randomlyCreateDG(int n, FrameWorld fw) {
		// TODO Auto-generated method stub
		while (count < 2*n){
			int x = StdRandom.uniform(0, X);
			int y = StdRandom.uniform(0, Y);
			int z = StdRandom.uniform(LOWERBONDOFPRIORITYVALUE, UPPERBONDOFPRIORITYVALUE);
			if(!fw.isOccupied(x, y)){
				//(x,y, ID, energy, storageCapacity, dataItem, value, type) control dataItem
				fw.addNode(new RegularNode(x, y, count, DGSENERGY, 0, DATAITEMS, z, "DG", false));
				count++;
				fw.addNode(new RegularNode(x, y, count, DGSENERGY, 0, DATAITEMS, z, "splitedDG", false));
				count++;
			}
		}
	}

	public static int getDataitems() {
		return DATAITEMS;
	}
}

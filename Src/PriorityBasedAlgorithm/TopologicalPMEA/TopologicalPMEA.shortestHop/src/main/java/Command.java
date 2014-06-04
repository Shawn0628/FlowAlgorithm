import java.io.*;
import java.util.NoSuchElementException;


public class Command {
	static final int p = 10;  //the number of source nodes;
	static final int NONDGSENERGY = 500;
	static final int DGSENERGY = 500;
	static final int STORAGE = 30;
	public static final int TRANSMITIONRANGE = 110;
	//Main client that creates random network, solves max flow, and prints results
    public static void main(String[] args) {
    	try{
        	FrameWorld fw = readFile();
        	/**********************************/
        	changeParameters(fw);
        	/**********************************/
        	RegularNode superSource = fw.getArray().get(fw.getArray().size()-2);
        	RegularNode superSink = fw.getArray().get(fw.getArray().size()-1);
            // create flow network with V vertices and E edges
        	FlowNetwork G = new FlowNetwork(p, fw.getArray());
        	FordFulkerson maxflow = new FordFulkerson(G, superSource, superSink);
        	maxflow.round();//round to the nearest integer value
        	System.out.println("Max Flow: " + maxflow.value());
        	System.out.println("Priority: " + maxflow.priorityValue());
        	System.out.println("totalEnergyConsumption: " + maxflow.getTotalEnergyConsumption());
        	System.out.println("ratio: " + (maxflow.getTotalEnergyConsumption()/maxflow.priorityValue()));
        	
        	outputResults(maxflow.value(), maxflow.priorityValue(), maxflow.getTotalEnergyConsumption()/maxflow.priorityValue());
        	outputFileAllNodes(fw);
        	
    	} catch(NoSuchElementException e) {
    		e.printStackTrace();
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
	
	private static void outputResults(double value, double priorityValue,
			double ratio) {
		// TODO Auto-generated method stub
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(".//maxflow.data", true));
			bw.write(p + " " + value + " " + "0");
			bw.flush();
			bw.close();
			
			BufferedWriter bw1 = new BufferedWriter(new FileWriter(".//priority.data", true));
			bw1.write(p +" " + priorityValue+ " " + "0");
			bw1.flush();
			bw1.close();
			
			BufferedWriter bw2 = new BufferedWriter(new FileWriter(".//ratio.data", true));
			bw2.write(p +" " + ratio+ " " + "0");
			bw2.flush();
			bw2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void changeParameters(FrameWorld fw) {
		// TODO Auto-generated method stub
		for(int i=0; i<p*2; i++){
			int z = StdRandom.uniform(GenerateGraphController.LOWERBONDOFPRIORITYVALUE, GenerateGraphController.UPPERBONDOFPRIORITYVALUE);
			fw.getArray().get(i).setDataItem(GenerateGraphController.DATAITEMS);
			fw.getArray().get(i).setEnergy(GenerateGraphController.DGSENERGY);
			fw.getArray().get(i).setStorageCapacity(0);
			fw.getArray().get(i).setValue(i);
			if(i%2 == 0){
				fw.getArray().get(i).setType("DG");
			} else {
				fw.getArray().get(i).setType("splitedDG");
			}
		}
		
		for(int i=0; i<fw.getArray().size(); i++){
			if(fw.getArray().get(i).getType().equals("DG") || fw.getArray().get(i).getType().equals("splitedDG")){
				fw.getArray().get(i).setEnergy(DGSENERGY);
			}
			if(fw.getArray().get(i).getType().equals("non-DG") || fw.getArray().get(i).getType().equals("splitednon-DG")){
				fw.getArray().get(i).setEnergy(NONDGSENERGY);
			}
			if(fw.getArray().get(i).getType().equals("non-DG") || fw.getArray().get(i).getType().equals("splitednon-DG")){
				fw.getArray().get(i).setStorageCapacity(STORAGE);
			}
		}
	}

	private static FrameWorld readFile() {
		// TODO Auto-generated method stub
		ObjectInputStream input = null;
		FrameWorld fw = null;
		try {
		    input = new ObjectInputStream(new FileInputStream("FrameWorld.data"));
		    fw = (FrameWorld) input.readObject();
		    input.close();
		    if(fw != null){
		    	return fw;
		    } else {
		    	System.out.println("Read file error: FrameWorld returns null");
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}finally {
			fw = null;
		}
		return null;
	}
	
	private static void outputFileAllNodes(FrameWorld fw) {
		// TODO Auto-generated method stub
		try {
		    ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("AllNodes.data"));
		    output.writeObject(fw);
		    output.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}

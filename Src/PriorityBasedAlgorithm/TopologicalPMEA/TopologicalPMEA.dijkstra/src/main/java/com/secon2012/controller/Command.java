package com.secon2012.controller;

import com.secon2012.model.FrameWorld;

import java.io.*;

public class Command {
	private int dataItems;
	private int storage;
	private int nonDgEnergy;
	private int dgEnergy;
	private int transimitionRange;
	private double receivingEnergy;
	private int p; // the number of DG
	private String maxflowPath;
	private String priorityPath;
	private String ratioPath;

	public void outputResults(double value, double priorityValue, double ratio,
			int p) {
		// TODO Auto-generated method stub
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(maxflowPath,
					true));
			bw.write(p + " " + value + " " + "0");
			bw.flush();
			bw.close();

			BufferedWriter bw1 = new BufferedWriter(new FileWriter(
					priorityPath, true));
			bw1.write(p + " " + priorityValue + " " + "0");
			bw1.flush();
			bw1.close();

			BufferedWriter bw2 = new BufferedWriter(new FileWriter(ratioPath,
					true));
			bw2.write(p + " " + ratio + " " + "0");
			bw2.flush();
			bw2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void changeParameters(FrameWorld fw, int p, int dataItems,
			int dgEnergy, int nonDgEnergy, int storage) {
		// TODO Auto-generated method stub
		for (int i = 0; i < p * 2; i++) {
			fw.getArray().get(i).setDataItem(dataItems);
			fw.getArray().get(i).setEnergy(dgEnergy);
			fw.getArray().get(i).setStorageCapacity(0);
			fw.getArray().get(i).setValue(i);
			if (i % 2 == 0) {
				fw.getArray().get(i).setType("DG");
			} else {
				fw.getArray().get(i).setType("splitedDG");
			}
		}

		for (int i = 0; i < fw.getArray().size(); i++) {
			if (fw.getArray().get(i).getType().equals("DG")
					|| fw.getArray().get(i).getType().equals("splitedDG")) {
				fw.getArray().get(i).setEnergy(dgEnergy);
			}
			if (fw.getArray().get(i).getType().equals("non-DG")
					|| fw.getArray().get(i).getType().equals("splitednon-DG")) {
				fw.getArray().get(i).setEnergy(nonDgEnergy);
			}
			if (fw.getArray().get(i).getType().equals("non-DG")
					|| fw.getArray().get(i).getType().equals("splitednon-DG")) {
				fw.getArray().get(i).setStorageCapacity(storage);
			}
		}
	}

	public FrameWorld readFile() {
		// TODO Auto-generated method stub
		ObjectInputStream input = null;
		FrameWorld fw = null;
		try {
			input = new ObjectInputStream(
					new FileInputStream("FrameWorld.data"));
			fw = (FrameWorld) input.readObject();
			input.close();
			if (fw != null) {
				return fw;
			} else {
				System.out.println("Read file error: FrameWorld returns null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fw = null;
		}
		return null;
	}

	public int getDataItems() {
		return dataItems;
	}

	public void setDataItems(int dataItems) {
		this.dataItems = dataItems;
	}

	public int getStorage() {
		return storage;
	}

	public void setStorage(int storage) {
		this.storage = storage;
	}

	public int getNonDgEnergy() {
		return nonDgEnergy;
	}

	public void setNonDgEnergy(int nonDgEnergy) {
		this.nonDgEnergy = nonDgEnergy;
	}

	public int getDgEnergy() {
		return dgEnergy;
	}

	public void setDgEnergy(int dgEnergy) {
		this.dgEnergy = dgEnergy;
	}

	public int getTransimitionRange() {
		return transimitionRange;
	}

	public void setTransimitionRange(int transimitionRange) {
		this.transimitionRange = transimitionRange;
	}

	public double getReceivingEnergy() {
		return receivingEnergy;
	}

	public void setReceivingEnergy(double receivingEnergy) {
		this.receivingEnergy = receivingEnergy;
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public String getMaxflowPath() {
		return maxflowPath;
	}

	public void setMaxflowPath(String maxflowPath) {
		this.maxflowPath = maxflowPath;
	}

	public String getPriorityPath() {
		return priorityPath;
	}

	public void setPriorityPath(String priorityPath) {
		this.priorityPath = priorityPath;
	}

	public String getRatioPath() {
		return ratioPath;
	}

	public void setRatioPath(String ratioPath) {
		this.ratioPath = ratioPath;
	}
}

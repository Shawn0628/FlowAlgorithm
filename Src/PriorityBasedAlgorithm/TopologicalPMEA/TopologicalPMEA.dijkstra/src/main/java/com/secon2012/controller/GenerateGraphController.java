package com.secon2012.controller;

import com.secon2012.model.FrameWorld;
import com.secon2012.model.RegularNode;
import com.secon2012.util.StdRandom;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GenerateGraphController {
	private int dataItems;
	private int storage;
	private int nonDgEnergy;
	private int dgEnergy;
	private int X; // x axis [0, 99]
	private int Y; // y axis [0, 99]
	private int upperBondOfPriorityValue; // randomly create priority value for
											// DG [1,10]
	private int lowerBondOfPriorityValue;
	private int q; // the number of non-DG nodes;
	private int p; // the number of DG
	private int transimitionRange;

	public void outputFile(FrameWorld fw) {
		// TODO Auto-generated method stub
		try {
			ObjectOutputStream output = new ObjectOutputStream(
					new FileOutputStream("FrameWorld.data"));
			output.writeObject(fw);
			output.close();
			System.out.println("Successfuly create graph!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void randomlyCreateNonDG(int n, FrameWorld fw, int nonDGEnergy,
			int storage, int x, int y) {
		// TODO Auto-generated method stub
		int count = 0;
		// insert first 1 nodes
		int a = StdRandom.uniform(0, x);
		int b = StdRandom.uniform(0, y);
		fw.addNode(new RegularNode(a, b, count, nonDGEnergy, storage, 0, 0,
				"non-DG", false));
		count++;
		fw.addNode(new RegularNode(a, b, count, nonDGEnergy, storage, 0, 0,
				"splitednon-DG", false));
		count++;

		while (count < 2 * n) {
			int x1 = StdRandom.uniform(0, x);
			int y1 = StdRandom.uniform(0, y);
			if (!fw.isOccupied(x1, y1) && fw.hasNeighbour(x1, y1)) {
				// (x,y, ID, energy, storageCapacity, dataItem, value, type)
				fw.addNode(new RegularNode(x1, y1, count, nonDGEnergy, storage,
						0, 0, "non-DG", false));
				count++;
				fw.addNode(new RegularNode(x1, y1, count, nonDGEnergy, storage,
						0, 0, "splitednon-DG", false));
				count++;
			}
		}
		System.out.println(count/2 + " nodes in total");
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

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public int getUpperBondOfPriorityValue() {
		return upperBondOfPriorityValue;
	}

	public void setUpperBondOfPriorityValue(int upperBondOfPriorityValue) {
		this.upperBondOfPriorityValue = upperBondOfPriorityValue;
	}

	public int getLowerBondOfPriorityValue() {
		return lowerBondOfPriorityValue;
	}

	public void setLowerBondOfPriorityValue(int lowerBondOfPriorityValue) {
		this.lowerBondOfPriorityValue = lowerBondOfPriorityValue;
	}

	public int getQ() {
		return q;
	}

	public void setQ(int q) {
		this.q = q;
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}
}

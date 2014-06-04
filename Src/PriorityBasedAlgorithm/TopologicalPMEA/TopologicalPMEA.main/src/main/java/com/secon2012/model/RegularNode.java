package com.secon2012.model;

import java.io.Serializable;

public class RegularNode implements Comparable<RegularNode>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6094821036159861949L;
	private int ID;
	private int x;
	private int y;
	private double energy;
	private int StorageCapacity;
	private int dataItem;
	private int value;
	private double flow;
	private String type;
	private boolean visit;

	public RegularNode(int x, int y, int iD, double energy,
			int storageCapacity, int dataItem, int value, String type,
			boolean visit) {
		super();
		this.x = x;
		this.y = y;
		this.ID = iD;
		this.energy = energy;
		this.StorageCapacity = storageCapacity;
		this.dataItem = dataItem;
		this.value = value;
		this.type = type;
		this.visit = visit;
	}

	public double getFlow() {
		return flow;
	}

	public void setFlow(double d) {
		this.flow = d;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public int getStorageCapacity() {
		return StorageCapacity;
	}

	public void setStorageCapacity(int storageCapacity) {
		StorageCapacity = storageCapacity;
	}

	public String toString() {
		return this.ID + " " + this.energy + " " + this.value;
	}

	public int getDataItem() {
		return dataItem;
	}

	public void setDataItem(int dataItem) {
		this.dataItem = dataItem;
	}

	@Override
	public int compareTo(RegularNode o) {
		if (this.value > o.value) {
			return 1;
		} else if (this.value < o.value) {
			return -1;
		} else
			return 0;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isVisit() {
		return visit;
	}

	public void setVisit(boolean visit) {
		this.visit = visit;
	}

}

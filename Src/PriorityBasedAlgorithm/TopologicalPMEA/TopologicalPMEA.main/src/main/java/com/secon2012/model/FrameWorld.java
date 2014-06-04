package com.secon2012.model;

import java.io.Serializable;
import java.util.ArrayList;

public class FrameWorld implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2745224197864624855L;
	private ArrayList<RegularNode> array; // store all nodes
	private int x;
	private int y;
	private int p;
	private int transimitionRange;

	public FrameWorld() {
	}

	public FrameWorld(int p, int x, int y, int transimitionRange) {
		super();
		this.p = p;
		this.array = new ArrayList<RegularNode>();
		this.x = x;
		this.y = y;
		this.transimitionRange = transimitionRange;
	}

	public ArrayList<RegularNode> getArray() {
		return array;
	}

	public void setArray(ArrayList<RegularNode> array) {
		this.array = array;
	}

	public boolean isOccupied(int x, int y) {
		for (int i = 0; i < array.size(); i++) {
			int a = array.get(i).getX();
			int b = array.get(i).getY();

			if (a == x && b == y) {
				return true;
			}
		}
		return false;
	}

	public void addNode(RegularNode n) {
		array.add(n);
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
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

	public boolean hasNeighbour(int x1, int y1) {
		for (int i = 0; i < array.size(); i++) {
			double distance = Math.sqrt(Math.pow(array.get(i).getX() - x1, 2)
					+ Math.pow(array.get(i).getY() - y1, 2));
			if (distance <= this.transimitionRange) {
				return true;
			}
		}
		return false;
	}
}

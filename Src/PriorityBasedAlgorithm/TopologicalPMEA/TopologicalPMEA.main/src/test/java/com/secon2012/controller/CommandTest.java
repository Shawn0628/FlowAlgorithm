package com.secon2012.controller;

import java.util.NoSuchElementException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.secon2012.algorithm.EdmondsKarpFlowNetwork;
import com.secon2012.algorithm.EdmondsKarpFordFulkerson;
import com.secon2012.algorithm.HeuristicFordFulkerson;
import com.secon2012.algorithm.OptimalFlowNetwork;
import com.secon2012.algorithm.OptimalFordFulkerson;
import com.secon2012.model.FrameWorld;
import com.secon2012.model.RegularNode;

public class CommandTest {
	private static FrameWorld fw;
	private static RegularNode superSource;
	private static RegularNode superSink;
	private static Command command;

	@BeforeClass
	public static void setUp() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "beans.xml");
		command = (Command) applicationContext.getBean("command");

		fw = command.readFile();
		/**********************************/
		command.changeParameters(fw, command.getP(), command.getDataItems(), command.getDgEnergy(),
				command.getNonDgEnergy(), command.getStorage());
		/**********************************/
		superSource = fw.getArray().get(fw.getArray().size() - 2);
		superSink = fw.getArray().get(fw.getArray().size() - 1);
	}

	public static void main(String[] args) {
		try {
			setUp();
			CommandTest ct = new CommandTest();
			ct.optimalAlgorithm();
			ct.heuristicAlgorithm();
			ct.edmondsKarpAlgorithm();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			System.out
					.println("Input invaild, p should not be greater than n square");
			System.exit(-1);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("Input invaild, Please input two numbers");
			System.out
					.println("java Command <how big for your grid network> <how many DGs>");
			System.exit(-1);
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("Input invaild");
			System.out
					.println("java Command <how big for your grid network> <how many DGs>");
			System.exit(-1);
		}
	}

	@Test
	public void edmondsKarpAlgorithm() {
		// TODO Auto-generated method stub
		// create flow network with V vertices and E edges
		EdmondsKarpFlowNetwork G = new EdmondsKarpFlowNetwork(command.getP(),
				fw.getArray(), command.getTransimitionRange());
		EdmondsKarpFordFulkerson maxflow = new EdmondsKarpFordFulkerson(G,
				superSource, superSink, command.getReceivingEnergy());
		maxflow.round();// round to the nearest integer value
		System.out
				.println("---------------------------------------------------");
		System.out.println("Edmonds-Karp:");
		System.out.println("Max Flow: " + maxflow.value());
		System.out.println("Priority: " + maxflow.priorityValue());
		System.out.println("totalEnergyConsumption: "
                + maxflow.getTotalEnergyConsumption());
		System.out.println("ratio: "
				+ (maxflow.getTotalEnergyConsumption() / maxflow
						.priorityValue()));
		command.outputResults(maxflow.value(), maxflow.priorityValue(),
                maxflow.getTotalEnergyConsumption() / maxflow.priorityValue(),
                command.getP());
	}

	@Test
	public void heuristicAlgorithm() {
		// TODO Auto-generated method stub
		// create flow network with V vertices and E edges
		OptimalFlowNetwork G = new OptimalFlowNetwork(command.getP(), fw.getArray(),
				command.getTransimitionRange());
		HeuristicFordFulkerson maxflow = new HeuristicFordFulkerson(G,
				superSource, superSink, command.getReceivingEnergy());
		maxflow.round();// round to the nearest integer value
		System.out
				.println("---------------------------------------------------");
		System.out.println("Heuristic:");
		System.out.println("Max Flow: " + maxflow.value());
		System.out.println("Priority: " + maxflow.priorityValue());
		System.out.println("totalEnergyConsumption: "
                + maxflow.getTotalEnergyConsumption());
		System.out.println("ratio: "
				+ (maxflow.getTotalEnergyConsumption() / maxflow
						.priorityValue()));
		command.outputResults(maxflow.value(), maxflow.priorityValue(),
                maxflow.getTotalEnergyConsumption() / maxflow.priorityValue(),
                command.getP());
	}

	@Test
	public void optimalAlgorithm() {
		// TODO Auto-generated method stub
		// create flow network with V vertices and E edges
		OptimalFlowNetwork G = new OptimalFlowNetwork(command.getP(), fw.getArray(),
				command.getTransimitionRange());
		OptimalFordFulkerson maxflow = new OptimalFordFulkerson(G, superSource,
				superSink, command.getReceivingEnergy());
		maxflow.round();// round to the nearest integer value
		System.out.println("Optimal:");
		System.out.println("Max Flow: " + maxflow.value());
		System.out.println("Priority: " + maxflow.priorityValue());
		System.out.println("totalEnergyConsumption: "
                + maxflow.getTotalEnergyConsumption());
		System.out.println("ratio: "
				+ (maxflow.getTotalEnergyConsumption() / maxflow
						.priorityValue()));
		command.outputResults(maxflow.value(), maxflow.priorityValue(),
                maxflow.getTotalEnergyConsumption() / maxflow.priorityValue(),
                command.getP());
	}
}

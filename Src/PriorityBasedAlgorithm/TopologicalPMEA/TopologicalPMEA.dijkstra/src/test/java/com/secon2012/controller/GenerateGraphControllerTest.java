package com.secon2012.controller;

import com.secon2012.model.FrameWorld;
import com.secon2012.model.RegularNode;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.NoSuchElementException;

public class GenerateGraphControllerTest {

	// Main client that creates random network, solves max flow, and prints
	// results
	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                    "beans.xml");
			GenerateGraphController ggc = (GenerateGraphController) applicationContext
					.getBean("ggc");
			FrameWorld fw = new FrameWorld(0, ggc.getX(), ggc.getY(),
					ggc.getTransimitionRange());
			// create q non-DGs randomly on Frame world
			ggc.randomlyCreateNonDG(ggc.getQ(), fw, ggc.getNonDgEnergy(),
					ggc.getStorage(), ggc.getX(), ggc.getY());
			// Add super source node s
			RegularNode superSource = new RegularNode(0, 0, fw.getArray()
					.size(), 0, 0, 0, 0, "s", false);
			fw.addNode(superSource);
			// Add super source node t
			RegularNode superSink = new RegularNode(0, 0, fw.getArray().size(),
					0, 0, 0, 0, "t", false);
			fw.addNode(superSink);

			ggc.outputFile(fw);

		} catch (NoSuchElementException e) {
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
}

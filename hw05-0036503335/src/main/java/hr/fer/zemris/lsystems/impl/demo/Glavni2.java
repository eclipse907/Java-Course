package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * This class represents a demo for the drawing of Lindenmayer systems
 * using the classes from the hr.fer.zemris.lsystems.impl package.
 *  
 * @author Tin Reiter
 * @version 1.0
 */
public class Glavni2 {

	/**
	 * The method from which the program starts.
	 * 
	 * @param args command line arguments. Not used in this
	 *             program.
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
	}
	
	/**
	 * This method creates a new Lindenmayer system.
	 * 
	 * @param provider an object that configures the
	 *                 Lindenmayer system.
	 * @return a new Lindenmayer system.
	 */
	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		String[] data = new String[] { "origin                 0.05 0.4",
				"angle                  0",
				"unitLength             0.9",
				"unitLengthDegreeScaler 1.0 / 3.0",
				"",
				"command F draw 1",
				"command + rotate 60",
				"command - rotate -60",
				"",
				"axiom F",
				"",
				"production F F+F--F+F"
				};
		return provider.createLSystemBuilder().configureFromText(data).build();
	}
	
}

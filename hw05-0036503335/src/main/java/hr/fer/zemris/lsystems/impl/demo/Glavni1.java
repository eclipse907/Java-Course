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
public class Glavni1 {

	/**
	 * The method from which the program starts.
	 * 
	 * @param args command line arguments. Not used in this
	 *             program.
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new)); 
	}

	/**
	 * This method creates a new Lindenmayer system.
	 * 
	 * @param provider an object that configures the
	 *                 Lindenmayer system.
	 * @return a new Lindenmayer system.
	 */
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder()
				.registerCommand('F', "draw 1")
				.registerCommand('+', "rotate 60")
				.registerCommand('-', "rotate -60")
				.setOrigin(0.05, 0.4)
				.setAngle(0)
				.setUnitLength(0.9)
				.setUnitLengthDegreeScaler(1.0/3.0)
				.registerProduction('F', "F+F--F+F")
				.setAxiom("F")
				.build();
	}
	
}

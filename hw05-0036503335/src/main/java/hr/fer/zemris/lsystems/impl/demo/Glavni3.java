package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * This class represents a demo for the drawing of Lindenmayer systems
 * using the classes from the hr.fer.zemris.lsystems.impl package.
 *  
 * @author Tin Reiter
 * @version 1.0
 */
public class Glavni3 {

	/**
	 * The method from which the program starts.
	 * 
	 * @param args command line arguments. Not used in this
	 *             program.
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
	
}

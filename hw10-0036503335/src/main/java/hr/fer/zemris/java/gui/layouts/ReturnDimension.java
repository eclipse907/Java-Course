package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Dimension;

/**
 * This interface represents an object that can return
 * either the preferred, maximum or minimum dimension of
 * a component.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
@FunctionalInterface
public interface ReturnDimension {

	/**
	 * Returns either the preferred, maximum or minimum dimension 
	 * of the component given.
	 * 
	 * @param comp the component for which to return the desired
	 *             dimension.
	 * @return either the preferred, maximum or minimum dimension 
	 *         of the component given.
	 */
	Dimension getDimension(Component comp);

}

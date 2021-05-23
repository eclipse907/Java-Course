package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import hr.fer.zemris.math.Vector2D;

/**
 * This class represents a state in the drawing of a
 * Lindenmayer system.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class TurtleState {

	private Vector2D currentPosition;
	private Vector2D currentOrientation;
	private Color currentColor;
	private double currentEffectiveStepLength;
	
	/**
	 * Creates a new state in the drawing of a Lindenmayer system
	 * with the given values.
	 * 
	 * @param currentPosition the position of the state.
	 * @param currentOrientation the orientation of the state.
	 * @param currentColor the drawing color of the state.
	 * @param currentEffectiveStepLength the effective step length of the
	 *                                   state.
	 */
	public TurtleState(Vector2D currentPosition, Vector2D currentOrientation, Color currentColor, double currentEffectiveStepLength) {
		this.currentPosition = currentPosition;
		this.currentOrientation = currentOrientation;
		this.currentColor = currentColor;
		this.currentEffectiveStepLength = currentEffectiveStepLength;
	}

	/**
	 * Returns the current position of the state.
	 * 
	 * @return the current position of the state.
	 */
	public Vector2D getCurrentPosition() {
		return currentPosition;
	}
    
	/**
	 * Sets the current position of the state.
	 * 
	 * @param currentPosition the position to set.
	 */
	public void setCurrentPosition(Vector2D currentPosition) {
		this.currentPosition = currentPosition;
	}

	/**
	 * Returns the current orientation of the state.
	 * 
	 * @return the current orientation of the state.
	 */
	public Vector2D getCurrentOrientation() {
		return currentOrientation;
	}

	/**
	 * Returns the current drawing color of the state.
	 * 
	 * @return the current drawing color of the state.
	 */
	public Color getCurrentColor() {
		return currentColor;
	}

	/**
	 * Sets the drawing color of the state to the given
	 * value.
	 * 
	 * @param newColor the new color.
	 */
	public void setCurrentColor(Color newColor) {
		this.currentColor = newColor;
	}
	
	/**
	 * Returns the effective step length of the state.
	 * 
	 * @return the effective step length of the state.
	 */
	public double getCurrentEffectiveStepLength() {
		return currentEffectiveStepLength;
	}

	/**
	 * Sets the effective step length of the state to the
	 * given value.
	 * 
	 * @param currentEffectiveStepLength the new effective step
	 *                                   length.
	 */
	public void setCurrentEffectiveStepLength(double currentEffectiveStepLength) {
		this.currentEffectiveStepLength = currentEffectiveStepLength;
	}

	/**
	 * Returns a copy of this state.
	 * 
	 * @return a copy of this state.
	 */
	public TurtleState copy() {
		return new TurtleState(currentPosition.copy(), currentOrientation.copy(), currentColor, currentEffectiveStepLength);
	}
	
}

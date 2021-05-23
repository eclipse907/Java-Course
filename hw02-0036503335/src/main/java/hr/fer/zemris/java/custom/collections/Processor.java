package hr.fer.zemris.java.custom.collections;

/**
 * This class is a model of an object which can perform
 * some kind of a process on the object passed to it.
 * This class is a template and should not be initialized,
 * only inherited.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class Processor {
	
	/**
	 * This method accepts an object and performs some kind
	 * of a process on it. Classes inheriting this class
	 * should implement their own version of this method.
	 * 
	 * @param value the object to be processed
	 */
	public void process(Object value) {
	}
	
}

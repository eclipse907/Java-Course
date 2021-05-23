package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.Tester;

/**
 * This class tests if the given object is an instance
 * of the Integer class with an even value. This class
 * implements the Tester interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
class EvenIntegerTester implements Tester {
	
	/**
	 * Tests if the given object is an instance
     * of the Integer class with an even value.
	 */
	@Override
	public boolean test(Object obj) {    
		if(!(obj instanceof Integer)) return false;    
		Integer i = (Integer)obj;    
		return i % 2 == 0;  
	}
	
} 

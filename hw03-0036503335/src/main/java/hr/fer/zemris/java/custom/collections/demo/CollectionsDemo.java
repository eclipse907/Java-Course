package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.List;
import hr.fer.zemris.java.custom.collections.Tester;

/**
 * This class provides a demonstration of the common use cases
 * for the usage of the collections from the hr.fer.zemris.java.custom.collections
 * package.
 *   
 * @author Tin Reiter
 * @version 1.0
 */
public class CollectionsDemo {

	/**
	 * The method from which the program starts.
	 * 
	 * @param args command line arguments. Not used in
	 *             this program.
	 */
	public static void main(String[] args) { 
		Collection col1 = new ArrayIndexedCollection(); 
		Collection col2 = new LinkedListIndexedCollection(); 
		col1.add("Ivo"); 
		col1.add("Ana"); 
		col1.add("Jasna"); 
		col2.add("Jasmina"); 
		col2.add("Štefanija"); 
		col2.add("Karmela"); 
		ElementsGetter getter1 = col1.createElementsGetter(); 
		ElementsGetter getter2 = col1.createElementsGetter(); 
		ElementsGetter getter3 = col2.createElementsGetter(); 
		System.out.println("Jedan element: " + getter1.getNextElement()); 
		System.out.println("Jedan element: " + getter1.getNextElement()); 
		System.out.println("Jedan element: " + getter2.getNextElement()); 
		System.out.println("Jedan element: " + getter3.getNextElement()); 
		System.out.println("Jedan element: " + getter3.getNextElement());
		
		getter1 = col1.createElementsGetter(); 
		getter1.getNextElement(); 
		getter1.processRemaining(System.out::println);
		
		Tester t = new EvenIntegerTester(); 
		System.out.println(t.test("Ivo")); 
		System.out.println(t.test(22)); 
		System.out.println(t.test(3));
		
		col1 = new LinkedListIndexedCollection(); 
		col2 = new ArrayIndexedCollection(); 
		col1.add(2); 
		col1.add(3); 
		col1.add(4); 
		col1.add(5); 
		col1.add(6); 
		col2.add(12); 
		col2.addAllSatisfying(col1, new EvenIntegerTester()); 
		col2.forEach(System.out::println);
		
		List col3 = new ArrayIndexedCollection(); 
		List col4 = new LinkedListIndexedCollection(); 
		col3.add("Ivana"); 
		col4.add("Jasna"); 
		Collection col5 = col3; 
		Collection col6 = col4; 
		col3.get(0); 
		col4.get(0);  
		col3.forEach(System.out::println); // Ivana 
		col4.forEach(System.out::println); // Jasna 
		col5.forEach(System.out::println); // Ivana 
		col6.forEach(System.out::println); // Jasna
	}

}

package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * This class represents a program that displays a fractal derived from 
 * the Newton-Raphson iteration. It uses the FractalViewer class from
 * the fractal-viewer-1.0.jar to display the fractal calculated. This
 * class uses parallelization to accelerate the fractal calculation.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class Newton {

	/**
	 * The method from which the program starts.
	 * 
	 * @param args command line arguments. Not used in this
	 *             program.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<Complex> roots = new ArrayList<>();
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		while (true) {
			System.out.print("Root " + Integer.toString(roots.size() + 1) + "> ");
			if (sc.hasNext()) {
				String input = sc.nextLine().trim();
				if (input.equalsIgnoreCase("done")) {
					if (roots.size() < 2) {
						System.out.println("Not enough roots entered. Please enter at least 2 roots.");
						continue;
					}
					break;
				}
				try {
					Complex root = parseComplexNumber(input);
					roots.add(root);
				} catch (NumberFormatException ex) {
					System.out.println(ex.getLocalizedMessage());
				}
			}
		}
		System.out.println("Image of fractal will appear shortly. Thank you.");
		sc.close();
		FractalViewer.show(new FractalProducer(roots));
	}
	
	/**
	 * This method parses a single complex number from the given string.
	 * 
	 * @param input the string from which to parse the complex number.
	 * @return a new complex number parsed from the string.
	 * @throws NumberFormatException if the given string contains a wrong
	 *                               complex number.
	 */
	private static Complex parseComplexNumber(String input) {
		if (input.matches("[+-]?\\d+")) {
			double re = Double.parseDouble(input);
			double im = 0;
			return new Complex(re, im);
		} else if (input.matches("[+-]?i\\d*")) {
			String newInput = input.replaceAll("i", "").trim();
			double re = 0;
			double im;
			if (newInput.isBlank()) {
				im = 1;
			} else if (newInput.equals("+") || newInput.equals("-")) {
				newInput = newInput.concat("1");
				im = Double.parseDouble(newInput);
			} else {
				im = Double.parseDouble(newInput);
			}
			return new Complex(re, im);
		} else if (input.matches("[+-]?\\d+\\s*[+-]{1}\\s*i\\d*")) {
			char[] inputArray = input.trim().toCharArray();
			int i = 0;
			StringBuilder number = new StringBuilder();
			number.append(inputArray[i++]);
			while (inputArray[i] != '+' && inputArray[i] != '-') {
				if (!Character.isWhitespace(inputArray[i])) {
					number.append(inputArray[i]);
				}
				i++;
			}
			double re = Double.parseDouble(number.toString());
			number = new StringBuilder();
			while (i < inputArray.length) {
				if (inputArray[i] == 'i') {
					if (i + 1 >= inputArray.length) {
						number.append('1');
					}
					i++;
					continue;
				}
				if (!Character.isWhitespace(inputArray[i])) {
					number.append(inputArray[i]);
				}
				i++;
			}
			double im = Double.parseDouble(number.toString());
			return new Complex(re, im);
		} else {
			throw new NumberFormatException("Given complex number has wrong format.");
		}
	}
	
	/**
	 * This class represents a fractal producer that generates the data necessary
	 * to display a fractal using the FractalViewer class. It implements the
	 * IFractalProducer interface. This producer uses parallelization to accelerate 
	 * the data calculation.
	 *  
	 * @author Tin Reiter
	 * @version 1.0
	 */
	private static class FractalProducer implements IFractalProducer {
		
		private ComplexPolynomial polynomial;
		private ComplexRootedPolynomial rootedPolynomial;
		private ExecutorService pool;
		
		/**
		 * Creates a new fractal producer that generates the necessary data to display
		 * a fractal with the given complex roots.
		 * 
		 * @param roots the complex roots of the fractal to display.
		 */
		public FractalProducer(List<Complex> roots) {
			this.rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, roots.toArray(new Complex[roots.size()]));
			this.polynomial = rootedPolynomial.toComplexPolynom();
			this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new DaemonicThreadFactory());
		}

		/**
		 * Generates the data necessary to display a fractal for the given values.
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			short[] data = new short[width * height];
			final int numOfRows = 8 * Runtime.getRuntime().availableProcessors();
			int ysPerRow = height / numOfRows;
			List<Future<?>> results = new ArrayList<>();
			
			for (int i = 0; i < numOfRows; i++) {
				int startY = i * ysPerRow;
				int endY = (i + 1) * ysPerRow;
				if(i == numOfRows - 1) {
					endY = height;
				}
				results.add(pool.submit(new PartOfFractalCalculator(startY, endY, data, width, height, reMin, reMax, imMin, imMax)));
			}
			
			for (Future<?> task : results) {
				try {
					task.get();
				} catch (InterruptedException | ExecutionException ignorable) {
				}
			}
			
			observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);
		}
		
		/**
		 * This class represents part of a calculation necessary to
		 * display a fractal and is used by the FractalProducer class
		 * to parallelize the fractal calculation.
		 * 
		 * @author Tin Reiter
		 * @version 1.0
		 */
		private class PartOfFractalCalculator implements Runnable {

			private int startY;
			private int endY;
			private short[] data;
			private int width;
			private int height;
			private double reMin;
			private double reMax;
			private double imMin;
			private double imMax;
			
			/**
			 * Creates a new part of a fractal calculation for the
			 * given values.
			 * 
			 * @param startY the start of this part of the calculation.
			 * @param endY the end of this part of the calculation.
			 * @param data the array in which to store the result of
			 *             the calculation.
			 */
			public PartOfFractalCalculator(int startY, int endY, short[] data, int width, 
					                       int height, double reMin, double reMax, double imMin,
					                       double imMax
					                      ) {
				this.startY = startY;
				this.endY = endY;
				this.data = data;
				this.width = width;
				this.height = height;
				this.reMin = reMin;
				this.reMax = reMax;
				this.imMin = imMin;
				this.imMax = imMax;
			}
			
			/**
			 * Calculates this part of a fractal calculation.
			 */
			@Override
			public void run() {
				int m = 16*16*16;
				int offset = startY * width;
				for (int y = startY; y < endY; y++) {
					for (int x = 0; x < width; x++) {
						double cre = x / (width-1.0) * (reMax - reMin) + reMin;
						double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
						Complex zn = new Complex(cre, cim);
						int iter = 0;
						double module;
						do {
							Complex numerator = polynomial.apply(zn);
							Complex denominator = polynomial.derive().apply(zn);
							Complex znold = zn;
							Complex fraction = numerator.divide(denominator);
							zn = zn.sub(fraction);
							module = znold.sub(zn).module();
							iter++;
						} while (module > 1E-3 && iter < m);
						int index = rootedPolynomial.indexOfClosestRootFor(zn, 0.002);
						data[offset++] = (short)(index + 1);
					}
				}
			}
			
		}

	}
	
	/**
	 * This class represents a thread factory that produces daemonic threads
	 * for the tasks given.
	 * 
	 * @author Tin Reiter
	 * @version 1.0
	 */
	private static class DaemonicThreadFactory implements ThreadFactory {

		/**
		 * Creates a new daemonic thread that runs the task
		 * given.
		 * 
		 * @return a new daemonic thread that runs the task
		 *         given.
		 */
		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			return thread;
		}
		
	}

}

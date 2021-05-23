package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * This class represents a program that loads the data of a bar chart from
 * a text file given as a command line argument and displays it on the screen.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 8001783544740936988L;
	
	/**
	 * Creates a new bar chart that can display its data on the screen.
	 * 
	 * @param barChart the data of the bar chart.
	 * @param filePath the file path of the file from which the data
	 *                 was loaded.
	 */
	public BarChartDemo(BarChart barChart, String filePath) {
		setLocation(200, 200);
		setSize(500, 500);
		setTitle("Bar Chart Demo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI(barChart, filePath);
	}
	
	/**
	 * Initializes the graphical user interface of this
	 * bar chart.
	 */
	private void initGUI(BarChart barChart, String filePath) {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(new JLabel(filePath, SwingConstants.LEFT), BorderLayout.PAGE_START);
		cp.add(new BarChartComponent(barChart), BorderLayout.CENTER);
	}
	
	/**
	 * The method from which the program starts.
	 * 
	 * @param args command line arguments.
	 * @throws IllegalArgumentException if no command line argument is given
	 *                                  or wrong number of command line arguments
	 *                                  is given.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Wrong number of command line arguments given.");
		}
		try {
			Path file = Paths.get(args[0]);
			if (!Files.exists(file)) {
				throw new IllegalArgumentException("The given file does not exist.");
			}
			String xAxisName;
			String yAxisName;
			List<XYValue> values = new ArrayList<>();
			int minY;
			int maxY;
			int gapSize;
			try (BufferedReader br = Files.newBufferedReader(file)) {
				xAxisName = br.readLine();
				yAxisName = br.readLine();
				String valuesLine = br.readLine();
				if (yAxisName == null || xAxisName == null || valuesLine == null) {
					throw new IllegalArgumentException("The given file is wrong.");
				}
				String[] splitted = valuesLine.split("\\s+");
				for (String value : splitted) {
					String[] splittedValues = value.split(",");
					if (splittedValues.length != 2) {
						throw new IllegalArgumentException("The given file is wrong.");
					}
					values.add(new XYValue(Integer.parseInt(splittedValues[0]), Integer.parseInt(splittedValues[1])));
				}
				String yMin = br.readLine();
				String yMax = br.readLine();
				String gap = br.readLine();
				if (yMin == null || yMax == null || gap == null) {
					throw new IllegalArgumentException("The given file is wrong.");
				}
				minY = Integer.parseInt(yMin);
				maxY = Integer.parseInt(yMax);
				gapSize = Integer.parseInt(gap);
			}
			BarChart barChart = new BarChart(values, xAxisName, yAxisName, minY, maxY, gapSize);
			SwingUtilities.invokeLater(() -> {
				new BarChartDemo(barChart, file.toString()).setVisible(true);
			});
		} catch (InvalidPathException ex) {
			System.out.println("Wrong path given");
			System.exit(1);
		} catch (IOException ex) {
			System.out.println("There was an error while trying to open the given file.");
			System.exit(1);
		} catch (NumberFormatException ex) {
			System.out.println("The given file contains a wrong number: " + ex.getMessage());
			System.exit(1);
		} catch (IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
			System.exit(1);
		}
	}

}

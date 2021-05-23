package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * This class represents a program that displays two lists that
 * display prime numbers and a button which when preset adds a 
 * new prime number to the lists.
 *  
 * @author Tin Reiter
 * @version 1.0
 */
public class PrimDemo extends JFrame {

	private static final long serialVersionUID = -1305147476023758530L;

	/**
	 * Creates a new prime number displayer.
	 */
	public PrimDemo() {
		setLocation(200, 200);
		setSize(600, 600);
		setTitle("Prim Demo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}
	
	/**
	 * Initializes the graphical user interface of this
	 * prime number displayer.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		PrimListModel prim = new PrimListModel();
		JList<Integer> leftList = new JList<>(prim);
		JList<Integer> rightList = new JList<Integer>(prim);
		panel.add(new JScrollPane(leftList));
		panel.add(new JScrollPane(rightList));
		cp.add(panel, BorderLayout.CENTER);
		JButton next = new JButton("Next");
		next.addActionListener(a -> {
			prim.next();
		});
		cp.add(next, BorderLayout.PAGE_END);
	}
	
	/**
	 * The method from which the program starts.
	 * 
	 * @param args command line arguments. Not used
	 *             in this program.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}

}

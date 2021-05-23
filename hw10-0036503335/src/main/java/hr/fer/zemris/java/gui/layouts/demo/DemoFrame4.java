package hr.fer.zemris.java.gui.layouts.demo;

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * This class represents a demo for the custom layout manager
 * from the hr.fer.zemris.java.gui.layouts package.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class DemoFrame4 extends JFrame {

	private static final long serialVersionUID = 6401299313372377027L;

	/**
	 * Creates a new demo window.
	 */
	public DemoFrame4() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        initGUI();
    }
    
	/**
	 * Initializes the graphical components of the demo window.
	 */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new CalcLayout(3));
        cp.add(new JLabel("x"), "1,1");
        cp.add(new JLabel("y"), "2,3");
        cp.add(new JLabel("z"), "2,7");
        cp.add(new JLabel("w"), "4,2");
        cp.add(new JLabel("a"), "4,5");
        cp.add(new JLabel("b"), "4,7");
    }
    
    /**
     * The method from which the program starts.
     * 
     * @param args command line arguments. Not used
     *             in this program.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
              new DemoFrame4().setVisible(true);
        });
    }

}

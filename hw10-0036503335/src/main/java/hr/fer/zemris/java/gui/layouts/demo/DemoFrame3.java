package hr.fer.zemris.java.gui.layouts.demo;

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * This class represents a demo for the custom layout manager
 * from the hr.fer.zemris.java.gui.layouts package.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class DemoFrame3 extends JFrame {
	
	private static final long serialVersionUID = -3489722101711792221L;

	/**
	 * Creates a new demo window.
	 */
	public DemoFrame3() {
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
        cp.add(new JLabel("x"), new RCPosition(1,1));
        cp.add(new JLabel("y"), new RCPosition(2,3));
        cp.add(new JLabel("z"), new RCPosition(2,7));
        cp.add(new JLabel("w"), new RCPosition(4,2));
        cp.add(new JLabel("a"), new RCPosition(4,5));
        cp.add(new JLabel("b"), new RCPosition(4,7));
    }
    
    /**
     * The method from which the program starts.
     * 
     * @param args command line arguments. Not used
     *             in this program.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
              new DemoFrame3().setVisible(true);
        });
    }

}

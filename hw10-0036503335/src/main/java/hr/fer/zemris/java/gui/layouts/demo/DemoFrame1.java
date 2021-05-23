package hr.fer.zemris.java.gui.layouts.demo;

import java.awt.Color;
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
public class DemoFrame1 extends JFrame {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new demo window.
	 */
	public DemoFrame1() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();
        pack();
    }
    
	/**
	 * Initializes the graphical components of the demo window.
	 */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new CalcLayout(3));
        cp.add(l("tekst 1"), new RCPosition(1,1));
        cp.add(l("tekst 2"), new RCPosition(2,3));
        cp.add(l("tekst stvarno najdulji"), new RCPosition(2,7));
        cp.add(l("tekst kraći"), new RCPosition(4,2));
        cp.add(l("tekst srednji"), new RCPosition(4,5));
        cp.add(l("tekst"), new RCPosition(4,7));
    }
    
    /**
     * Creates a new JLabel with the text given.
     * 
     * @param text the text of the JLabel.
     * @return a new JLabel with the text given.
     */
    private JLabel l(String text) {
        JLabel l = new JLabel(text);
        l.setBackground(Color.YELLOW);
        l.setOpaque(true);
        return l;
    }
    
    /**
     * The method from which the program starts.
     * 
     * @param args command line arguments. Not used
     *             in this program.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
              new DemoFrame1().setVisible(true);
        });
    }

}

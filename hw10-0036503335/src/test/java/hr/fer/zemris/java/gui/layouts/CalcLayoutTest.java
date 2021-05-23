package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.junit.jupiter.api.Test;

class CalcLayoutTest {

	@Test
	void testWrongRCPosition() {
		JPanel p = new JPanel(new CalcLayout(3));
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel("x"), new RCPosition(0, 2));
		});
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel("x"), new RCPosition(7, 2));
		});
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel("x"), new RCPosition(3, 0));
		});
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel("x"), new RCPosition(3, 9));
		});
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel("x"), new RCPosition(-3, -2));
		});
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel("x"), new RCPosition(1, 3));
		});
		p.add(new JLabel("x"), new RCPosition(2, 1));
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel("y"), new RCPosition(2, 1));
		});
	}

	@Test
	void testPreferredSize1() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
	
	@Test
	void testPreferredSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
}

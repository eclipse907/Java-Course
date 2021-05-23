package hr.fer.zemris.java.main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import hr.fer.zemris.java.models.Line;
import hr.fer.zemris.java.models.Oval;
import hr.fer.zemris.java.models.PictureToDraw;
import hr.fer.zemris.java.models.Shape;
import hr.fer.zemris.java.models.ShapeType;

public class App extends JFrame {


	private static final long serialVersionUID = 7512648050256434959L;
	private Image toDraw;
	
	public App() {
		setLocation(100, 100);
		setSize(800, 800);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("App");
		initGUI();
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new GridLayout(2, 1));
		JPanel mainPanel = new JPanel(new GridLayout(2, 1));
		JTextArea textArea = new JTextArea();
		mainPanel.add(textArea);
		JPanel image = new JPanel() {
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (toDraw != null) {
					g.drawImage(toDraw, 0, 0, null);
				}
			};
			
		};
		mainPanel.add(image);
		cp.add(mainPanel);
		JPanel controlBar = new JPanel(new GridLayout(1, 2));
		JButton draw = new JButton("Draw");
		draw.addActionListener(a -> {
			try {
				PictureToDraw picture = parseShapes(textArea.getText());
				toDraw = drawImage(picture);
				image.repaint();
			} catch (IllegalArgumentException ex) {
				JOptionPane.showOptionDialog(this, ex.getMessage(), "Error",
	                     JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null
	                    );
			}
		});
		controlBar.add(draw);
		JButton exit = new JButton("Exit");
		exit.addActionListener(a -> {
			System.exit(0);
		});
		controlBar.add(exit);
		cp.add(controlBar);
	}
	
	private PictureToDraw parseShapes(String text) throws NumberFormatException, IllegalArgumentException {
		String[] lines = text.split("\n");
		int i = 0;
		while (i < lines.length && (lines[i].isBlank()) || lines[i].trim().startsWith("#")) {
			i++;
		}
		String[] firstLine = lines[i].trim().split("\\s+", 3);
		if (!firstLine[0].equals("SIZE")) {
			throw new IllegalArgumentException("Error in text.");
		}
		PictureToDraw picture = parseFirstLine(Arrays.copyOfRange(firstLine, 1, firstLine.length));
		for (i += 1; i < lines.length; i++) {
			if (lines[i].isBlank() || lines[i].trim().startsWith("#")) {
				continue;
			}
			String[] splitedLine = lines[i].split("\\s+");
			if (splitedLine[0].equals("LINE")) {
				picture.addShape(parseLine(Arrays.copyOfRange(splitedLine, 1, splitedLine.length)));
			} else if (splitedLine[0].equals("OVAL")) {
				picture.addShape(parseOval(Arrays.copyOfRange(splitedLine, 1, splitedLine.length)));
			} else {
				throw new IllegalArgumentException("Error in text.");
			}
		}
		return picture;
	}
	
	private PictureToDraw parseFirstLine(String[] firstLineArguments) {
		Point pictureDimension = null;
		Color backroundColor = null;
		if (firstLineArguments.length != 2) {
			throw new IllegalArgumentException("Error in text.");
		}
		for (String attribute : firstLineArguments) {
			String[] splitedAttribute = attribute.split("=", 2);
			if (splitedAttribute.length != 2) {
				throw new IllegalArgumentException("Error in text.");
			}
			if (splitedAttribute[0].equals("dim")) {
				String[] dim = splitedAttribute[1].split(",", 2);
				if (dim.length != 2) {
					throw new IllegalArgumentException("Error in text.");
				}
				pictureDimension = new Point(Integer.parseInt(dim[0]), Integer.parseInt(dim[1]));
			} else if (splitedAttribute[0].equals("background:rgb")) {
				String[] rgb = splitedAttribute[1].split(",", 3);
				if (rgb.length != 3) {
					throw new IllegalArgumentException("Error in text.");
				}
				backroundColor = new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
 			} else {
 				throw new IllegalArgumentException("Error in text.");
 			}
		}
		if (pictureDimension == null || backroundColor == null) {
			throw new IllegalArgumentException("Error in text.");
		}
		return new PictureToDraw(pictureDimension, backroundColor);
	}
	
	private Line parseLine(String[] lineArguments) {
		if (lineArguments.length != 3) {
			throw new IllegalArgumentException("Error in text.");
		}
		Point start = null;
		Point end = null;
		Color lineColor = null;
		for (String attribute : lineArguments) {
			String[] splitedAttribute = attribute.split("=", 2);
			if (splitedAttribute.length != 2) {
				throw new IllegalArgumentException("Error in text.");
			}
			if (splitedAttribute[0].equals("start")) {
				String[] dim = splitedAttribute[1].split(",", 2);
				if (dim.length != 2) {
					throw new IllegalArgumentException("Error in text.");
				}
				start = new Point(Integer.parseInt(dim[0]), Integer.parseInt(dim[1]));
			} else if (splitedAttribute[0].equals("end")) {
				String[] dim = splitedAttribute[1].split(",", 2);
				if (dim.length != 2) {
					throw new IllegalArgumentException("Error in text.");
				}
				end = new Point(Integer.parseInt(dim[0]), Integer.parseInt(dim[1]));
			} else if (splitedAttribute[0].equals("stroke:rgb")) {
				String[] rgb = splitedAttribute[1].split(",", 3);
				if (rgb.length != 3) {
					throw new IllegalArgumentException("Error in text.");
				}
				lineColor = new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
			} else {
				throw new IllegalArgumentException("Error in text.");
			}
		}
		if (start == null || end == null || lineColor == null) {
			throw new IllegalArgumentException("Error in text.");
		}
		return new Line(start, end, lineColor);
	}
	
	private Oval parseOval(String[] ovalArguments) {
		if (ovalArguments.length != 5) {
			throw new IllegalArgumentException("Error in text.");
		}
		Point center = null;
		Integer rx = null;
		Integer ry = null;
		Color lineColor = null;
		Color fillColor = null;
		for (String attribute : ovalArguments) {
			String[] splitedAttribute = attribute.split("=", 2);
			if (splitedAttribute.length != 2) {
				throw new IllegalArgumentException("Error in text.");
			}
			if (splitedAttribute[0].equals("center")) {
				String[] dim = splitedAttribute[1].split(",", 2);
				if (dim.length != 2) {
					throw new IllegalArgumentException("Error in text.");
				}
				center = new Point(Integer.parseInt(dim[0]), Integer.parseInt(dim[1]));
			} else if (splitedAttribute[0].equals("rx")) {
				rx = Integer.valueOf(splitedAttribute[1]);
			} else if (splitedAttribute[0].equals("ry")) {
				ry = Integer.valueOf(splitedAttribute[1]);
			} else if (splitedAttribute[0].equals("stroke:rgb")) {
				String[] rgb = splitedAttribute[1].split(",", 3);
				if (rgb.length != 3) {
					throw new IllegalArgumentException("Error in text.");
				}
				lineColor = new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
			} else if (splitedAttribute[0].equals("fill:rgb")) {
				String[] rgb = splitedAttribute[1].split(",", 3);
				if (rgb.length != 3) {
					throw new IllegalArgumentException("Error in text.");
				}
				fillColor = new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
			} else {
				throw new IllegalArgumentException("Error in text.");
			}
		}
		if (center == null || rx == null || ry == null || lineColor == null || fillColor == null) {
			throw new IllegalArgumentException("Error in text.");
		}
		return new Oval(center, rx, ry, lineColor, fillColor);
	}
	
	private Image drawImage(PictureToDraw picture) {
		BufferedImage bim = new BufferedImage((int)picture.getSize().getX(), (int)picture.getSize().getY(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bim.createGraphics();
		g2d.setColor(picture.getBackroundColor());
		g2d.fillRect(0, 0, (int)picture.getSize().getX(), (int)picture.getSize().getY());
		for (Shape shape : picture.getShapes()) {
			if (shape.getType().equals(ShapeType.LINE)) {
				Line line = (Line)shape;
				g2d.setColor(line.getLineColor());
				g2d.drawLine(line.getStart().x, line.getStart().y, line.getEnd().x, line.getEnd().y);
			} else {
				Oval oval = (Oval)shape;
				g2d.setColor(oval.getLineColor());
				g2d.drawOval(oval.getCenter().x - oval.getRx(), oval.getCenter().y - oval.getRy(), 2 * oval.getRx(), 2 * oval.getRy());
				g2d.fillOval(oval.getCenter().x - oval.getRx(), oval.getCenter().y - oval.getRy(), 2 * oval.getRx(), 2 * oval.getRy());
			}
		}
		g2d.dispose();
		return bim;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new App().setVisible(true);
      });
	}

}

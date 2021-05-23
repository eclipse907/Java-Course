package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a layout manager that places components in a
 * 5 x 7 grid. The component at position 1 x 1 is stretched to occupy
 * the first 5 columns of the first row. The user can specify the size of 
 * the space between the components. This class implements the LayoutManager2
 * interface.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class CalcLayout implements LayoutManager2 {
	
	private final int maxRows = 5;
	private final int maxColumns = 7;
	private int distanceBetweenComponents;
	private Map<Integer, Map<Integer, Component>> rows;
	
	/**
	 * Creates a new CalcLayout manager with the given space
	 * between the components.
	 * 
	 * @param distanceBetweenComponents the space between the components.
	 */
	public CalcLayout(int distanceBetweenComponents) {
		this.distanceBetweenComponents = distanceBetweenComponents;
		this.rows = new HashMap<>();
		for (int i = 1; i <= maxRows; i++) {
			rows.put(i, new HashMap<Integer, Component>());
		}
		for (Map<Integer, Component> row : rows.values()) {
			for (int i = 1; i <= maxColumns; i++) {
				row.put(i, null);
			}
		}
	}
	
	/**
	 * Creates a new CalcLayout manager with the space between
	 * the components set to 0.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * {@inheritDoc} This method is unsupported.
	 * @throws UnsupportedOperationException if this method is
	 *                                       called.
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		for (Map<Integer, Component> row : rows.values()) {
			for (int i = 1; i < maxColumns; i++) {
				if (comp.equals(row.get(i))) {
					row.put(i, null);
					return;
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getDesiredDimension(parent, Component::getPreferredSize);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getDesiredDimension(parent, Component::getMinimumSize);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void layoutContainer(Container parent) {
		int widthAvailable = (parent.getWidth() - parent.getInsets().left - parent.getInsets().right - (maxColumns - 1) * distanceBetweenComponents);
		int heightAvailable = (parent.getHeight() - parent.getInsets().top - parent.getInsets().bottom - (maxRows - 1) * distanceBetweenComponents);
		int componentWidth = widthAvailable / maxColumns;
		int componentHeight = heightAvailable / maxRows; 
		int[] componentWidths = new int[maxColumns];
		int[] componentHeights = new int[maxRows];
		Arrays.fill(componentWidths, componentWidth);
		Arrays.fill(componentHeights, componentHeight);
		int numOfWidthIndexesToAdd = widthAvailable - maxColumns * componentWidth;
		int numOfHeightIndexesToAdd = heightAvailable - maxRows * componentHeight;
		if (numOfWidthIndexesToAdd > 0) {
			int widthIndexesToAdd = Math.round(maxColumns / (float)(numOfWidthIndexesToAdd));
			for (int i = 0; i < maxColumns; i += widthIndexesToAdd) {
				componentWidths[i]++;
			}
		}
		if (numOfHeightIndexesToAdd > 0) {
			int heightIndexesToAdd = Math.round(maxRows / (float)(numOfHeightIndexesToAdd));
			for (int i = 0; i < maxRows; i += heightIndexesToAdd) {
				componentHeights[i]++;
			}
		}
		int startX = parent.getInsets().left;
		int startY = parent.getInsets().top;
		if (rows.get(1).get(1) != null) {
			rows.get(1).get(1).setBounds(startX, startY, 5 * componentWidths[0] + 4 * distanceBetweenComponents, componentHeights[0]);
		}
		for (int i = 6; i <= maxColumns; i++) {
			if (rows.get(1).get(i) != null) {
				rows.get(1).get(i).setBounds(startX + (i - 1) * (componentWidth + distanceBetweenComponents), startY,
						                     componentWidths[i - 1], componentHeights[0]
						                    );
			}
		}
		for (int i = 2; i <= maxRows; i++) {
			for (int j = 1; j <= maxColumns; j++) {
				if (rows.get(i).get(j) != null) {
					rows.get(i).get(j).setBounds(startX + (j - 1) * (componentWidth + distanceBetweenComponents),
							                     startY + (i - 1) * (componentHeight + distanceBetweenComponents),
							                     componentWidths[j - 1], componentHeights[i - 1]);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if the given component or
	 *                              constraint is null.
	 * @throws IllegalArgumentException if the given constraint
	 *                                  is wrong.
	 * @throws CalcLayoutException if the given position is wrong or
	 *                             the component is already present in
	 *                             the manager or the position given is
	 *                             already taken.
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (comp == null || constraints == null) {
			throw new NullPointerException();
		}
		RCPosition position;
		if (constraints instanceof RCPosition) {
			position = (RCPosition)constraints;
		} else if (constraints instanceof String) {
			position = RCPosition.parse((String)constraints);
		} else {
			throw new IllegalArgumentException("Wrong constraint given.");
		}
		if (position.getRow() < 1 || position.getColumn() < 1 || position.getRow() > maxRows || position.getColumn() > maxColumns) {
			throw new CalcLayoutException("Wrong position given.");
		}
		if (position.getRow() == 1 && position.getColumn() >=2 && position.getColumn() <= 5) {
			throw new CalcLayoutException("Wrong position given.");
		}
		for (Map<Integer, Component> row : rows.values()) {
			for (Component cp : row.values()) {
				if (comp.equals(cp)) {
					throw new CalcLayoutException("Given component is already in this layout.");
				}
			}
		}
		if (rows.get(position.getRow()).get(position.getColumn()) == null) {
			rows.get(position.getRow()).put(position.getColumn(), comp);
		} else {
			throw new CalcLayoutException("A component is already present at the given position.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getDesiredDimension(target, Component::getMaximumSize);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invalidateLayout(Container target) {
	}

	/**
	 * This method returns the container dimension specified by the dimensionGetter 
	 * parameter. The method returns either the preferred, minimum or maximum dimension of
	 * the given container.
	 * 
	 * @param target the container for which to return the desired dimension.
	 * @param dimensionGetter specifies for what dimension to query the components
	 *                        of this layout. 
	 * @return either the preferred, minimum or maximum dimension of
	 *         the given container as specified by the dimensionGetter
	 *         parameter.
	 */
	private Dimension getDesiredDimension(Container target, ReturnDimension dimensionGetter) {
		int componentWidth = 0;
		int componentHeight = 0;
		if (rows.get(1).get(1) != null) {
			Dimension dim = dimensionGetter.getDimension(rows.get(1).get(1));
			if (dim != null) {
				componentWidth = (dim.width - 4 * distanceBetweenComponents) / 5;
				componentHeight = dim.height;
			}
		}
		for (int i = 1; i <= maxRows; i++) {
			for (int j = 1; j <= maxColumns; j++) {
				if (i == 1 && j == 1) {
					continue;
				}
				if (rows.get(i).get(j) != null) {
					Dimension dim = dimensionGetter.getDimension(rows.get(i).get(j));
					if (dim == null) {
						continue;
					}
					if (dim.width > componentWidth) {
						componentWidth = dim.width;
					}
					if (dim.height > componentHeight) {
						componentHeight = dim.height;
					}
				}
			}
		}
		int width = target.getInsets().left + (maxColumns - 1) * distanceBetweenComponents + maxColumns * componentWidth + target.getInsets().right;
		int height = target.getInsets().top + (maxRows - 1) * distanceBetweenComponents + maxRows * componentHeight + target.getInsets().bottom;
		return new Dimension(width, height);
	}

}

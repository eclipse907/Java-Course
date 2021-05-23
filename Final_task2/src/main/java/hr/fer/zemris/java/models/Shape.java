package hr.fer.zemris.java.models;

public class Shape {
	
	private ShapeType type;

	public Shape(ShapeType type) {
		super();
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public ShapeType getType() {
		return type;
	}

}

package hr.fer.zemris.java.raytracer.model;

/**
 * This class represents a sphere in a graphical
 * scene. It inherits the GraphicalObject class.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class Sphere extends GraphicalObject {

	private Point3D center;
	private double radius;
	private double kdr;
	private double kdg;
	private double kdb;
	private double krr;
	private double krg;
	private double krb;
	private double krn;
	
	/**
	 * Creates a new sphere with the given values.
	 * 
	 * @param center the center of the sphere.
	 * @param radius the radius of the sphere.
	 * @param kdr coefficient for the red color
	 *            of the diffuse component.
	 * @param kdg coefficient for the green color
	 *            of the diffuse component.
	 * @param kdb coefficient for the blue color
	 *            of the diffuse component.
	 * @param krr coefficient for the red color
	 *            of the reflective component.
	 * @param krg coefficient for the green color
	 *            of the reflective component.
	 * @param krb coefficient for the blue color
	 *            of the reflective component.
	 * @param krn coefficient n of the reflective
	 *            component.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb, double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		double b = ray.direction.scalarMultiply(2).scalarProduct(ray.start.sub(center));
		double c = ray.start.sub(center).scalarProduct(ray.start.sub(center)) - radius * radius;
		double discriminant = b * b - 4 * c;
		if (discriminant >= 0) {
			double t0 = (-b + Math.sqrt(discriminant)) / 2;
			double t1 = (-b - Math.sqrt(discriminant)) / 2;
			if (t0 > t1) {
				double pom = t0;
				t0 = t1;
				t1 = pom;
			}
			double t;
			boolean outer;
			if (t0 <= 0) {
				t = t1;
				outer = false;
				if (t <= 0) {
					return null;
				}
			} else {
				t = t0;
				outer = true;
			}
			Point3D intersection = ray.start.add(ray.direction.scalarMultiply(t));
			double distance = ray.start.difference(ray.start, intersection).norm();
			return new RayIntersection(intersection, distance, outer) {
				
				private double kdr = Sphere.this.kdr;
				private double kdg = Sphere.this.kdg;
				private double kdb = Sphere.this.kdb;
				private double krr = Sphere.this.krr;
				private double krg = Sphere.this.krg;
				private double krb = Sphere.this.krb;
				private double krn = Sphere.this.krn;
				private Point3D normal = intersection.sub(center).modifyNormalize();
				
				/**
				 * {@inheritDoc}
				 */
				@Override
				public Point3D getNormal() {
					return normal;
				}
				
				/**
				 * {@inheritDoc}
				 */
				@Override
				public double getKrr() {
					return krr;
				}
				
				/**
				 * {@inheritDoc}
				 */
				@Override
				public double getKrn() {
					return krn;
				}
				
				/**
				 * {@inheritDoc}
				 */
				@Override
				public double getKrg() {
					return krg;
				}
				
				/**
				 * {@inheritDoc}
				 */
				@Override
				public double getKrb() {
					return krb;
				}
				
				/**
				 * {@inheritDoc}
				 */
				@Override
				public double getKdr() {
					return kdr;
				}
				
				/**
				 * {@inheritDoc}
				 */
				@Override
				public double getKdg() {
					return kdg;
				}
				
				/**
				 * {@inheritDoc}
				 */
				@Override
				public double getKdb() {
					return kdb;
				}
			};
		} else {
			return null;
		}
	}

}

package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerAnimator;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This class represents a ray caster that casts rays on the graphical scene given
 * and displays it on the screen with a rotating animation. It uses the RayTracerViewer from 
 * the raytracer-1.0.jar to get and display the graphical scene. This ray caster uses 
 * parallelization to calculate the rgb values of the pixels in the graphical scene.
 * 
 * @author Tin Reiter
 * @version 1.0
 */
public class RayCasterParallel2 {
	
	/**
	 * The method from which the program starts.
	 * 
	 * @param args command line arguments. Not used in
	 *             this program.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(
			  getIRayTracerProducer(), getIRayTracerAnimator(), 30, 30
	    );
	}
	
	/**
	 * This method returns an object which implements the IRayTracerAnimator
	 * interface and can animate a graphical scene with the given parameters.
	 *  
	 * @return an object which implements the IRayTracerAnimator
	 *         interface.
	 */
	private static IRayTracerAnimator getIRayTracerAnimator() {
		return new IRayTracerAnimator() {
			long time;
			
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void update(long deltaTime) {
				time += deltaTime;
			}
			
			/**
			 * {@inheritDoc}
			 */
			@Override
			public Point3D getViewUp() { // fixed in time
				return new Point3D(0,0,10);
			}
			
			/**
			 * {@inheritDoc}
			 */
			@Override
			public Point3D getView() { // fixed in time
				return new Point3D(-2,0,-0.5);
			}
			
			/**
			 * {@inheritDoc}
			 */
			@Override
			public long getTargetTimeFrameDuration() {
				return 100; // redraw scene each 150 milliseconds
			}
			
			/**
			 * {@inheritDoc}
			 */
			@Override
			public Point3D getEye() { // changes in time
				double t = (double)time / 10000 * 2 * Math.PI;
				double t2 = (double)time / 5000 * 2 * Math.PI;
				double x = 50*Math.cos(t);
				double y = 50*Math.sin(t);
				double z = 30*Math.sin(t2);
				return new Point3D(x,y,z);
			}
		};
	}
	
	/**
	 * This method returns an object that implements the IRayTracerProducer
	 * interface and can calculate the color of all pixels in the graphical
	 * scene for the parameters given using ray casting. It uses parallelization
	 * to speed up the calculation.
	 *  
	 * @return an object that implements the IRayTracerProducer
	 *         interface and uses parallelization to speed up 
	 *         the calculation.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void produce(hr.fer.zemris.java.raytracer.model.Point3D eye,
					hr.fer.zemris.java.raytracer.model.Point3D view, hr.fer.zemris.java.raytracer.model.Point3D viewUp,
					double horizontal, double vertical, int width, int height, long requestNo,
					IRayTracerResultObserver observer, AtomicBoolean cancel) {
				
				System.out.println("Započinjem izračune...");
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				
				Point3D OG = view.difference(view, eye).normalize();
				Point3D VUV = viewUp.normalize();
				
				Point3D yAxis = VUV.sub(OG.scalarMultiply(OG.scalarProduct(VUV))).normalize();
				Point3D xAxis = OG.vectorProduct(yAxis).normalize();
				// Point3D zAxis = yAxis.vectorProduct(xAxis);
				
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2)).add(yAxis.scalarMultiply(vertical / 2));
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				ForkJoinPool pool = new ForkJoinPool();
				
				/**
				 * This class represents a recursive action that calculates the rgb values
				 * of the pixels from the given height range.
				 * 
				 * @author Tin Reiter
				 * @version 1.0
				 */
				class CalculatePixelColor extends RecursiveAction {

					private static final long serialVersionUID = -8172811700213889832L;
					private int startHeight;
					private int endHeight;
					
					/**
					 * Creates a new recursive action that calculates the rgb values
				     * of the pixels from the given height range.
				     * 
					 * @param startHeight the start of the height range.
					 * @param endHeight the end of the height range.
					 */
					public CalculatePixelColor(int startHeight, int endHeight) {
						this.startHeight = startHeight;
						this.endHeight = endHeight;
					}
					
					/**
					 * {@inheritDoc}
					 */
					@Override
					protected void compute() {
						if (endHeight - startHeight == 1) {
							computeDirect();
							return;
						}
						CalculatePixelColor cl1 = new CalculatePixelColor(startHeight, (startHeight + endHeight) / 2);
						CalculatePixelColor cl2 = new CalculatePixelColor((startHeight + endHeight) / 2, endHeight);
						invokeAll(cl1, cl2);
					}
					
					/**
					 * Directly computes this task without splitting it
					 * recursively into smaller tasks.
					 */
					private void computeDirect() {
						short[] rgb = new short[3];
						int offset = startHeight * width;
						for(int y = startHeight; y < endHeight; y++) {
							for(int x = 0; x < width; x++) {
								Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x * horizontal / (width - 1)))
										              .sub(yAxis.scalarMultiply(y * vertical / (height - 1)));
								Ray ray = Ray.fromPoints(eye, screenPoint);
								
								tracer(scene, ray, rgb);
								
								red[offset] = rgb[0] > 255 ? 255 : rgb[0];
								green[offset] = rgb[1] > 255 ? 255 : rgb[1];
								blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
								
								offset++;
							}
						}
					}
					
				}
				
				CalculatePixelColor cl = new CalculatePixelColor(0, height);
				pool.invoke(cl);
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

		};
	}
	
	/**
	 * This method calculates the color of the pixel associated with the
	 * ray given. The rgb values are set to 0 if the ray does not intersect 
	 * any objects in the scene. It stores the result as short values for red,
	 * green and blue in the short array given in the following order: 
	 * red, green, blue.
	 *  
	 * @param scene the graphical scene.
	 * @param ray the ray associated with the pixel.
	 * @param rgb a short array of size 3 in which to store the calculated rgb
	 *            values.
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {
    	rgb[0] = 0;
    	rgb[1] = 0;
    	rgb[2] = 0;
    	RayIntersection closest = findClosestIntersection(scene, ray);
    	if(closest==null) {
    		return;
    	}
    	rgb[0] = 15;
    	rgb[1] = 15;
    	rgb[2] = 15;
    	for (LightSource ls : scene.getLights()) {
    		Ray r = Ray.fromPoints(ls.getPoint(), closest.getPoint());
    		RayIntersection intersection = findClosestIntersection(scene, r);
    		if (intersection != null &&
    			Math.abs(intersection.getDistance() - closest.getPoint().difference(closest.getPoint(), ls.getPoint()).norm()) > 0.0001
    		   ) {
    			continue;
    		} else {
    			Point3D l = r.direction.negate().modifyNormalize();
    			Point3D reflectionVec = closest.getNormal().scalarMultiply(2 * closest.getNormal().scalarProduct(l)).modifySub(l);
    			Point3D v = ray.direction.negate();
    			double dotProduct = l.scalarProduct(closest.getNormal());
    			if (dotProduct > 0) {
    				rgb[0] += ls.getR() * closest.getKdr() * dotProduct;
    				rgb[1] += ls.getG() * closest.getKdg() * dotProduct;
    				rgb[2] += ls.getB() * closest.getKdb() * dotProduct;
    			}
    			dotProduct = reflectionVec.scalarProduct(v);
    			if (dotProduct > 0) {
    				rgb[0] += ls.getR() * closest.getKrr() * Math.pow(dotProduct, closest.getKrn());
    				rgb[1] += ls.getG() * closest.getKrg() * Math.pow(dotProduct, closest.getKrn());
    				rgb[2] += ls.getB() * closest.getKrb() * Math.pow(dotProduct, closest.getKrn());
    			}
			}
    	}
    }

	/**
	 * Finds the closest intersection between the ray given and the objects in the
	 * graphical scene provided.
	 * 
	 * @param scene the graphical scene.
	 * @param ray the ray for which to find the closest
	 *            intersection.
	 * @return a RayIntersection object which represents the closest intersection between
	 *         the given ray and the objects in the graphical scene, or null if no intersection
	 *         is found.
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection closest = null;
		for (GraphicalObject obj : scene.getObjects()) {
			RayIntersection intersection = obj.findClosestRayIntersection(ray);
			if (closest == null || intersection != null && intersection.getDistance() < closest.getDistance()) {
				closest = intersection;
			}
		}
		return closest;
	}

}

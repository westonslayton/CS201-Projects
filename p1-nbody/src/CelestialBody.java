import java.lang.Math.*;

/**
 * Celestial Body class for NBody
 * Modified from original Planet class
 * used at Princeton and Berkeley
 * @author ola
 *
 * If you add code here, add yourself as @author below
 *
 *
 */
public class CelestialBody {

	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName;

	/**
	 * Create a Body from parameters	
	 * @param xp initial x position
	 * @param yp initial y position
	 * @param xv initial x velocity
	 * @param yv initial y velocity
	 * @param mass of object
	 * @param filename of image for object animation
	 */
	public CelestialBody(double xp, double yp, double xv,
			             double yv, double mass, String filename){
		myXPos = xp;
		myYPos = yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;

	}

	/**
	 *
	 * @return
	 */
	public double getX() {
		return myXPos;
	}

	/**
	 *
	 * @return
	 */
	public double getY() {
		return myYPos;
	}

	/**
	 * Accessor for the x-velocity
	 * @return the value of this object's x-velocity
	 */
	public double getXVel() {
		return myXVel;
	}
	/**
	 * Return y-velocity of this Body.
	 * @return value of y-velocity.
	 */
	public double getYVel() {
		return myYVel;
	}

	/**
	 *
	 * @return
	 */
	public double getMass() {
		return myMass;
	}

	/**
	 *
	 * @return
	 */
	public String getName() {
		return myFileName;
	}

	/**
	 * Return the distance between this body and another
	 * @param b the other body to which distance is calculated
	 * @return distance between this body and b
	 */
	public double calcDistance(CelestialBody b) {
		double r;
		double x;
		double y;
		x = this.myXPos - b.myXPos;
		y = this.myYPos - b.myYPos;
		r = Math.sqrt((x * x) + (y * y));
		return r;
	}

	public double calcForceExertedBy(CelestialBody b) {
		double r = calcDistance(b);
		double g = 6.67*1e-11;
		double f = g * (this.myMass * b.myMass) / (r * r);
		return f;
	}

	public double calcForceExertedByX(CelestialBody b) {
		double f = calcForceExertedBy(b);
		double dx = b.getX() - this.getX();
		double r = calcDistance(b);
		return f*dx/r;
	}
	public double calcForceExertedByY(CelestialBody b) {
		double f = calcForceExertedBy(b);
		double dy = b.getY() - this.getY();
		double r = calcDistance(b);
		return f*dy/r;
	}

	public double calcNetForceExertedByX(CelestialBody[] bodies) {
		double sum = 0.0;
		for(CelestialBody b : bodies) {
			if (! b.equals (this)){
				sum += calcForceExertedByX(b);
			}
		}
		return sum;
	}

	public double calcNetForceExertedByY(CelestialBody[] bodies) {
		double sum = 0.0;
		for(CelestialBody b : bodies) {
			if (! b.equals (this)){
				sum += calcForceExertedByY(b);
			}
		}
		return sum;
	}

	public void update(double deltaT, 
			           double xforce, double yforce) {
		double ax = xforce / this.myMass;
		double ay = yforce / this.myMass;
		double nvx = myXVel + deltaT*ax;
		double nvy = myYVel + deltaT*ay;
		double nx = myXPos + deltaT*nvx;
		double ny = myYPos + deltaT*nvy;
		myXPos = nx;
		myYPos = ny;
		myXVel = nvx;
		myYVel = nvy;
	}

	/**
	 * Draws this planet's image at its current position
	 */
	public void draw() {
		StdDraw.picture(myXPos,myYPos,"images/"+myFileName);
	}
}

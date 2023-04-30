import java.math.*;

/**
 *  Tests calcPairwiseForce
 */
public class TestCalcForceExertedByXY {

    /**
     *  Tests calcForceExertedByXY.
     */
    public static void main(String[] args) {
        checkCalcForceExertedByXY();
    }

    /**
     *  Checks whether or not two Doubles are equal and prints the result.
     *
     *  @param  expected    Expected double
     *  @param  actual      Double received
     *  @param  label   Label for the 'test' case
     *  @param  eps     Tolerance for the double comparison.
     */
    private static void checkEquals(double actual, double expected, String label, double eps) {
        if (Math.abs(expected - actual) <= eps * Math.abs(Math.max(expected, actual))) {
            System.out.println("PASS: " + label + ": Expected " + expected + " and you gave " + actual);
        } else {
            System.out.println("FAIL: " + label + ": Expected " + expected + " and you gave " + actual);
        }
    }

    /**
     *  Checks the Body class to make sure calcForceExertedByXY works.
     */
    private static void checkCalcForceExertedByXY() {
        System.out.println("Checking calcForceExertedByX and calcForceExertedByY...");

        CelestialBody p1 = new CelestialBody(1.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
        CelestialBody p2 = new CelestialBody(2.0, 1.0, 3.0, 4.0, 4e11, "jupiter.gif");
        CelestialBody p3 = new CelestialBody(4.0, 5.0, 3.0, 4.0, 5.0, "jupiter.gif");

        checkEquals(p1.calcForceExertedByX(p2), 133.4, "calcForceExertedByX()", 0.01);
        checkEquals(p2.calcForceExertedByX(p1), -133.4, "calcForceExertedByX()", 0.01);
        checkEquals(p1.calcForceExertedByX(p3), 4.002e-11, "calcForceExertedByX()", 0.01);
        checkEquals(p1.calcForceExertedByY(p2), 0.0, "calcForceExertedByY()", 0.01);
        checkEquals(p1.calcForceExertedByY(p3), 5.336e-11, "calcForceExertedByY()", 0.01);

        //Added Spring 2022
        
        /*
        
        System.out.println("These tests check if you implemented these methods using F*dx/r and F*dy/r, not F/r*dx and F/r*dy");
        System.out.println("If you fail these tests AND you've made the appropriate change above, it might be because of your Java runtime. Post a question on Ed.");
        CelestialBody p4 =new CelestialBody(0.0, 0.0, 3.0, 4.0, 5*1e10, "jupiter.gif");
        CelestialBody p5 =new CelestialBody(3000.0, 4000.0, 3.0, 4.0, 5*1e10 * Double.MIN_VALUE, "jupiter.gif");

        //System.out.println(Double.MIN_VALUE);             4.9E-324
        //System.out.println(p4.calcForceExertedBy(p5));    2.9644E-320

        //Roundoff error causes next test to fail: Got 1.482E-320 and 1.9763E-320 respectively for F/r * dx and F/r * dy (which we don't want)
        checkEquals(p4.calcForceExertedByX(p5), 1.7786E-320, "calcForceExertedByX()", 0.01);
        checkEquals(p4.calcForceExertedByY(p5), 2.3715E-320, "calcForceExertedByY()", 0.01); 
        */
    }
}

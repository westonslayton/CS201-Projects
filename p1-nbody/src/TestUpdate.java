
import java.math.*;

/**
 *  Tests Body class' update() method
 */
public class TestUpdate {

    /**
     *  Tests update.
     */
    public static void main(String[] args) {
        checkUpdate();
    }

    /**
     *  Checks whether or not two Doubles are equal and prints the result.
     *
     *  @param  expected    Expected double
     *  @param  actual      Double received
     *  @param  label   Label for the 'test' case
     *  @param  eps     Tolerance for the double comparison.
     */
    private static void checkEquals(double expected, double actual, String label, double eps) {
        if (Math.abs(expected - actual) <= eps * Math.max(expected, actual)) {
            System.out.println("PASS: " + label + ": Expected " + expected + " and you gave " + actual);
        } else {
            System.out.println("FAIL: " + label + ": Expected " + expected + " and you gave " + actual);
        }
    }


    /**
     *  Checks the Body class to make sure update works.
     */
    private static void checkUpdate() {
        System.out.println("Checking update...");

        CelestialBody p1 = new CelestialBody(1.0, 2.0, 3.0, 4.0, 5.0, "jupiter.gif");

        p1.update(2.0, 1.0, -0.5);

        checkEquals(7.8, p1.getX(), "update()", 0.01);
        checkEquals(9.6, p1.getY(), "update()", 0.01);
        checkEquals(3.4, p1.getXVel(), "update()", 0.01);
        checkEquals(3.8, p1.getYVel(), "update()", 0.01);
    }
}

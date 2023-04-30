

import java.io.FileNotFoundException;

/**
 *  Tests Nbody.readBodies. Reads in ./data/planets.txt and checks output of
 *  readBodies().
 */
public class TestReadBodies {

    private static boolean doubleEquals(double actual, double expected, double eps) {
        return Math.abs(expected - actual) <= eps * Math.max(expected, actual);
    }

    /** Checks to make sure that readBodies() works perfectly. 
     * @throws FileNotFoundException */
    private static String checkReadBodies() throws FileNotFoundException {
        System.out.println("Checking readBodies...");
        String planetsTxtPath = "./data/planets.txt";
        /* If the following line fails to compile, you probably need to make
         * a certain method static! */
        CelestialBody[] actualOutput = NBody.readBodies(planetsTxtPath);

        /* Check the simple things: */
        if (actualOutput == null) {
            return "FAIL: readBodies(); null output";
        }
        if (actualOutput.length != 5) {
            return "FAIL: readBodies().length: Expected 5 and you gave " + actualOutput.length;
        }

        /* Check to make sure every body exists, plus random spot checks */
        boolean foundEarth = false;
        boolean foundMars = false;
        boolean foundMercury = false;
        boolean foundSun = false;
        boolean foundVenus = false;
        boolean randomChecksOkay = true;
        for (CelestialBody p : actualOutput) {
            if ("earth.gif".equals(p.getName())) {
                foundEarth = true;
                if (!doubleEquals(p.getX(), 1.4960e+11, 0.01)) {
                    System.out.println("Advice: Your Earth doesn't have the right xxPos!");
                    randomChecksOkay = false;
                }
            } else if ("mars.gif".equals(p.getName())) {
                foundMars = true;
            } else if ("mercury.gif".equals(p.getName())) {
                foundMercury = true;
                if (!doubleEquals(p.getY(), 0, 0.01)) {
                    System.out.println("Advice: Your Mercury doesn't have the right yyPos!");
                    randomChecksOkay = false;
                }
            } else if ("sun.gif".equals(p.getName())) {
                foundSun = true;
            } else if ("venus.gif".equals(p.getName())) {
                foundVenus = true;
                if (!doubleEquals(p.getMass(), 4.8690e+24, 0.01)) {
                    System.out.println("Advice: Your Venus doesn't have the right mass!");
                    randomChecksOkay = false;
                }
            }
            else {
                System.out.printf("unexpected planet file: '%s'\n",p.getName());
            }
        }

        /* Build up a nice list of missing bodies */
        String missingBodies = "";
        if (!foundEarth) {
            missingBodies += "Earth, ";
        }
        if (!foundMars) {
            missingBodies += "Mars, ";
        }
        if (!foundMercury) {
            missingBodies += "Mercury, ";
        }
        if (!foundSun) {
            missingBodies += "Sun, ";
        }
        if (!foundVenus) {
            missingBodies += "Venus, ";
        }
        if (missingBodies.length() > 0) {
            String answer = "FAIL: readBodies(); Missing these bodies: ";
            answer += missingBodies.substring(0, missingBodies.length() - 2);
            return answer;
        }
        if (!randomChecksOkay) {
            return "FAIL: readBodies(); Not all bodies have correct info!";
        }
        return "PASS: readBodies(); Congrats! This was the hardest test!";
    }

    public static void main(String[] args) throws FileNotFoundException {
        String testResult = checkReadBodies();
        System.out.println(testResult);
    }
}

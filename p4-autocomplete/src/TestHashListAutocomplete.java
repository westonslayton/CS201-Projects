import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;

/**
 * Testing HashListAutocomplete for correctness
 * @author Daniel Hwang
 * @author Matthew Giglio
 * @author Emily Du
 *
 */

public class TestHashListAutocomplete {
    private HashListAutocomplete bob;
    //private String[] n;
    //private double[] w;

    @BeforeEach
    public void setup () {
        //Simple test case
        //Yes, these are fruits that begin with the letter a. The things you learn in college.
        String[] n = {
            "apple","apricot","avocado","almond","alupug"};
        double[] w = {1.0, 3.0, 4.0, 2.0, 3.0};
        bob = new HashListAutocomplete(n, w);
        //If you're getting an error with initialization, check that you're adding up to the first MAX_PREFIX substrings, and
        //note that strings may be shorter than term length. You can consider this to be the secret test in a way.
    }

    /**
     * Tests that the constructor initializes prefixes properly given test words and weights
     * Should have every possible prefix up until number of characters specified
     */
    @Test
    public void testInitalizeKeys() {
        ArrayList<String> solution = new ArrayList<>(Arrays.asList(new String[] {
            "", "appl", "almon", "apri", "apple", "avocad", "avo",
            "app", "almo", "a", "avocado", "apr", "alm", "avoc",
            "alupu", "almond", "al", "alu", "alup", "ap",
            "apricot", "av", "apric", "avoca", "alupug", "aprico"
        }));
        //ArrayList<String> keys = new ArrayList<String>(bob.myMap.keySet());
        for (String key : solution){
            try{
                bob.topMatches(key, 10);
            }
            catch (Exception e){
                fail("Your HashListAutocomplete did not initialize key " + key + " properly.");
            }
        }
    }

    /**
     * Tests that constructor initializes Term values properly for the correct prefixes 
     */
    @Test
    public void testInitalizeValues() {
        // checks that Term values for the map were sorted correctly
        ArrayList<Term> solution = new ArrayList<>(Arrays.asList(new Term[]{
            new Term("avocado", 4.0),
            new Term("apricot", 3.0),
            new Term("alupug", 3.0),
            new Term("almond", 2.0),
            new Term("apple", 1.0)}));
        List<Term> termsBeginningWithA = bob.topMatches("a", 10);
        for (Term key : solution){
            assertTrue(termsBeginningWithA.contains(key), "Your HashListAutocomplete did not associate " + key + "to a simple prefix \"a\"");
        }
    }

    /**
     * Tests that code returns weightiest top matches when k > number of entries
     */
    @Test
    public void testTopMatches() {
        //top matches in bob for "ap" should be "apricot", "apple"
        ArrayList<Term> solution = new ArrayList<>(Arrays.asList(new Term[]{new Term(
            "apricot", 3.0), new Term("apple", 1.0)}));
        assertEquals(solution, bob.topMatches("ap", 3), "Your HashListAutocomplete "
            + "did not return the correct result for a simple case.");
    }

    /**
     * Tests that code returns weightiest top matches when k < number of entries
     */
    @Test
    public void testTopMatchesK() {
        //top matches in bob for "ap", 1 should be "apricot", "apple"
        ArrayList<Term> solution = new ArrayList<>(Arrays.asList(new Term[]{new Term("apricot",
            3.0)}));
        assertEquals(solution, bob.topMatches("ap", 1), "Your HashListAutocomplete"
            + " did not return the correct result for a simple case.");
    }

    /**
     * Tests that code returns correct sizeInBytes
     * Should return correct estimate of memory to store all keys and values of myMap
     * Accepts both sizeInBytes approaches implied in writeup and accepted on Gradescope
     */
    @Test
    public void testSizeInBytes() {
        assertTrue((bob.sizeInBytes() == 312 || bob.sizeInBytes() == 950), "Your HashListAutocomplete did not"
            + "yield the proper size in bytes for a simple case");
    }

    /**
     * Tests that constructor throws correct exceptions for different bad input scenarios
     */
    @Test
    public void testExceptions() {
      assertThrows(NullPointerException.class, ()-> new HashListAutocomplete(null, null));
      String[] terms = {"apple", "apple"};
      double[] weights = new double[]{1.0};
      assertThrows(IllegalArgumentException.class, ()->new HashListAutocomplete(terms, weights));
      double[] weights2 = new double[]{1.0, 2.0, 3.0};
        assertThrows(IllegalArgumentException.class, ()->new HashListAutocomplete(terms, weights2));
    }

    /**
     * Tests that code handles nonexistent prefix queries correctly
    */
    @Test
    public void testNonexistentPrefix(){
        assertTrue(bob.topMatches("banana", 3).size() == 0, "Your HashListAutocomplete gave a non-empty list for a prefix that shouldn't be in the map!");
    }

    /**
     * Tests that no extra keys are returned
     * Additionally ensures that no prefixes longer than supposed to are returned
     */
    @Test
    public void testGoUpToMaxPrefix() {
        HashListAutocomplete woo = new HashListAutocomplete(new String[]{"bananabanana", "banana"}, new double[]{2.0, 2.0});
        assertFalse(woo.topMatches("bananabanana", 1).contains("bananabanana"), "Your HashListAutocomplete EITHER accessed a key not in the map OR contains prefixes longer than MAX_PREFIX characters");
    }
}

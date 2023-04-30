import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;

/**
 * Testing BinarySearchLibrary for correctness
 * @author ola
 *
 */
public class TestBinarySearchLibrary {

	List<String> myList;
	
	/**
     * Setup for JUnit tests on BinarySearchLibrary
     */
	@BeforeEach
	public void setup () {
		String[] ss = {
				"apple","apple","apple","apple",
				"cherry","cherry","cherry","cherry","cherry","cherry",
				"orange",
				"zoo","zoo","zoo","zoo","zoo","zoo","zoo",
				"zoo","zoo","zoo","zoo","zoo"};
		
		myList = Arrays.asList(ss);
	}

	/**
     * Tests correctness of firstindex returned for target element
     */
	@Test
	public void testFirstIndex() {
		String[] keys = {"apple","cherry","lemon","orange","zoo"};
		int[] results = {0,4,-1,10,11};
		for(int k=0; k < keys.length; k++) {
			String target = keys[k];
			int index = BinarySearchLibrary.firstIndex(myList, target, Comparator.naturalOrder());
			if (index < 0) {
				assertEquals(Math.signum(results[k]), Math.signum(index), "wrong first index for " + target);
			}
			else {
				assertEquals(results[k], index, "wrong first index for " + target);
			}
		}
	}
	
	/**
     * Tests correctness of lastindex returned for target element
     */
	@Test
	public void testLastIndex() {
		String[] keys = {"apple","cherry","lemon","orange","zoo"};
		int[] results = {3,9,-1,10,22};
		for(int k=0; k < keys.length; k++) {
			String target = keys[k];
			int index = BinarySearchLibrary.lastIndex(myList, target, Comparator.naturalOrder());
			assertEquals(results[k],index,"wrong first index for "+target);
		}
	}

}

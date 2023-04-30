import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.Iterator;
import java.util.Random;

/**
 * Class for running JUNit tests with different implementations of IDnaStrand.
 * To use a different implementation alter the method <code>getNewStrand</code>
 * since that method is called by all JUnit tests to create the IDnaStrand
 * objects being tested.
 *
 * @author ola
 * @date January 2008, modified and commented in September 2008
 * @date January 2009, added splice testing
 * @date October 2015, added nodeList
 * @date October 2016, updated for iterator and no "" strings
 * @date September 2020, updated by Charles Lyu
 * @date October 2022, updated by Emily Du and Havish Malladi
 */

public class TestStrand {
	private static String[] strs = { "aggtccg", "aaagggtttcccaaagggtttccc", "a", "g",
			"aggtccgttccggttaaggagagagagagagttt" };

	/**
	 * Return a strand to test by other JUnit tests
	 *
	 * @param s
	 *            is the string modeled by an IDnaStrand implementation
	 * @return an IDnaStrand object for testing in this JUnit testing class.
	 */
	public IDnaStrand getNewStrand(String s) {
		//return new StringStrand(s);
        return new LinkStrand(s);
		//return new StringBuilderStrand(s);
	}

	/**
	 * This test checks if .size() returns the correct value for basic cases"
	 */
	@Test
	public void testSize() {
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				return str;
			});
			assertEquals(s.length(), strand.size(),"This test checks if .size() returns the correct value"
					+ " for basic cases. Your code did not return the correct .size() for strand " + s);
		}
	}

	@Test
	public void testRandomIndexes() {
		int NUM_NODES = 20000;
		Random rand = new Random(12356);
		char[] arr = {'c','g','a','t'};
		IDnaStrand ourStrand = getNewStrand("cgat");
		for(int k=0; k < NUM_NODES; k++){
			ourStrand.append("cgat");
		}

		for(int k=0; k < 30; k++) {
			int index = rand.nextInt((int)ourStrand.size());
			char ch = ourStrand.charAt(index);
			assertTrue(ch == arr[index % 4],k+"-th index is "+index);
		}
	}

	@Test
	public void testReverseSize() {
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				return str.reverse();
			});
			assertEquals(s.length(), strand.size(),"This test checks if .size() returns the correct value"
					+ " for reverse cases. Your code did not return the correct .size() for reversed strand " + s);
		}
	}

	/**
	 * This test checks if toString works correctly for basic cases.
	 */
	@Test
	public void testToString() {
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				return str;
			});
			assertEquals(s, strand.toString(),"This test checks if toString works correctly for "
					+ "basic cases. Your code did not return the correct .toString() for strand " + s);
		}
	}

	/** This test checks if initializeFrom works correctly for basic cases */
	@Test
	public void testInitialize() {
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand("");
				str.initialize(s);
				return str;
			});
			assertEquals(s.length(), strand.size(),"This test checks if initialize works correctly"
					+ " for basic cases. Your code did not give the correct size() after calling initialize(" + s + ")");
			assertEquals( s, strand.toString(),"This test checks if initialize works correctly"
					+ " for basic cases. Your code did not give the correct toString() after calling initialize(" + s + ")");
		}
	}

	/**
	 * This test checks if the number of appends is the same after reversing a IDnaStrand
	 */
	@Test
	public void testReverseAppends() {
		IDnaStrand str = getNewStrand(strs[0]);
		int appends = str.getAppendCount();
		IDnaStrand rev = str.reverse();
		assertEquals(appends, rev.getAppendCount(), "This checks that you didn't modify append during reverse."
				+ " Make sure not to update myAppends when reversing!");
	}

	/**
	 * This test checks if reverse works correctly for strands with a single node
	 */
	@Test
	public void testReverseSingle() {
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				IDnaStrand rev = str.reverse();
				return rev;
			});
			String rs = new StringBuilder(s).reverse().toString();
			assertEquals(rs, strand.toString(), "This test checks if reverse works correctly for "
					+ "strands with a single node. Your code did not give the correct toString() after reversing " + s);
		}
	}

	/**
	 * This test checks if reverse works correctly for strands with multiple nodes
	 */
	@Test
	public void testReverseMulti() {
		// Two nodes
		String a = "actgcaggttaag";
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				str.append(a);
				IDnaStrand rev = str.reverse();
				return rev;
			});
			String rs = new StringBuilder(s).append(a).reverse().toString();
			assertEquals(rs, strand.toString(), "This test checks if reverse works correctly for "
					+ "strands with two nodes. Your code did not give the correct toString() after " +
					"reversing a strand with nodes " + s + ", " + a);
		}

		// Three nodes
		String b = "tttttccgaaaggc";
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				str.append(a);
				str.append(b);
				IDnaStrand rev = str.reverse();
				return rev;
			});
			String rs = new StringBuilder(s).append(a).append(b).reverse().toString();
			assertEquals(rs, strand.toString(), "This test checks if reverse works correctly for "
					+ "strands with three nodes. Your code did not give the correct toString() after " +
					"reversing a strand with nodes " + s + ", " + a + ", " + b);
		}
	}

	@Test
	public void testReverseMultiSize() {
		// Two nodes
		String a = "actgcaggttaag";
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				str.append(a);
				IDnaStrand rev = str.reverse();
				return rev;
			});
			String rs = new StringBuilder(s).append(a).reverse().toString();
			assertEquals(rs.length(), strand.size(), "This test checks if reverse SIZE works correctly for "
					+ "strands with two nodes. Your code did not give the correct size() after " +
					"reversing a strand with nodes " + s + ", " + a);
		}

		// Three nodes
		String b = "tttttccgaaaggc";
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				str.append(a);
				str.append(b);
				IDnaStrand rev = str.reverse();
				return rev;
			});
			String rs = new StringBuilder(s).append(a).append(b).reverse().toString();
			assertEquals(rs.length(), strand.size(), "This test checks if reverse SIZE works correctly for "
					+ "strands with three nodes. Your code did not give the correct size() after " +
					"reversing a strand with nodes " + s + ", " + a + ", " + b);
		}
	}

	@Test
	/** This test checks if append works correctly for simple cases */
	public void testAppend() {
		String app = "gggcccaaatttgggcccaaattt";
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				str.append(app);
				return str;
			});
			assertEquals(s + app, strand.toString(),
					"This test checks if append works correctly for "
							+ "simple cases. Your code did not give the correct toString() after "
							+ "appending " + app + " to " + s
			);
			assertEquals(s.length() + app.length(), strand.size(),
					"This test checks if append works correctly for "
							+ "simple cases. Your code did not give the correct size() after "
							+ "appending " + app + " to " + s
			);
		}
	}

	@Test
	/** This test checks if append works correctly when called multiple times */
	public void testAppendMulti() {
		String a = "gggcccaaatttgggcccaaattt";
		String b = "acgacttcg";
		String c = "aaggttc";
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				str.append(a);
				str.append(b);
				str.append(c);
				return str;
			});
			assertEquals(s + a + b + c, strand.toString(),
					"This test checks if append works correctly when called multiple "
							+ "times. Your code did not give the correct toString() after appending "
							+ a + ", " + b + ", " + c + " to " + s
			);
			assertEquals(s.length() + a.length() + b.length() + c.length(), strand.size(),
					"This test checks if append works correctly when called multiple "
							+ "times. Your code did not give the correct size() after appending "
							+ a + ", " + b + ", " + c + " to " + s
			);
		}
	}

	
	/** This test checks if .addToFront() works correctly in cases of multiple calls*/
	/* TEST DEPRECATED
	@Test
	public void testAddToFront() {
		String a = "cccaaatttgggaaattt";
		String b = "catcatcat";
		String c = "tttaaaccc";

		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				if (str instanceof LinkStrand) {
					((LinkStrand) str).addToFront(a);
					((LinkStrand) str).addToFront(b);
					((LinkStrand) str).addToFront(c);
					return str;
				} else {
					return null;
				}
			});
			assertNotNull(strand, "This test checks if you commented out getNewStrand()'s"
					+ "StringStrand initialization and commented in its LinkStrand initialization."
					+ "You did not do so and this test cannot be correctly run"
			);
			assertEquals(c + b + a + s, strand.toString(),
					"This test checks if addToFront works correctly when called multiple "
							+ "times. Your code did not give the correct toString() after appending "
							+ c + ", " + b + ", " + a + " to " + s
			);
			assertEquals(c.length() + b.length() + a.length() + s.length(), strand.size(),
					"This test checks if append works correctly when called multiple "
							+ "times. Your code did not give the correct size() after appending "
							+ c + ", " + b + ", " + a + " to " + s
			);
		}
	}
	*/


	/**
	 *	Checks if iterator methods are implemented correctly
	 */
	@Test
	public void testIterator() {
		IDnaStrand test = getNewStrand(strs[0]);
		for (int i = 1; i < strs.length; i++) {
			test.append(strs[i]);
		}
		String all = test.toString();
		Iterator<Character> itc = test.iterator();

		for (int i = 0; i < all.length(); i++) {
			final int index = i;
			Assertions.assertAll("iterator output",
					()->assertTrue(itc.hasNext(),"hasNext() returned false when it should be true, when iterating through" +
							" index "+index+" of "+all.length() + ". This is typically due to errors with myIndex."),
					()->assertEquals(all.charAt(index), itc.next(),
							"charAt(" + index + ") does not match the expected character"));
		}

		assertFalse(itc.hasNext(),"hasNext() returned true when it should be false, after iterating " +
				"through the entire strand. This is typically due to errors with myIndex.");
	}

	/**
	 * Checks if index out of bounds exception error is thrown for charAt(-1) call
	 * If you fail this type, make sure you're throwing an IndexOutOfBoundsException if
	 * index is outside of the range of characters in LinkStrand.
	 * Test will automatically pass if getNewStrand returns StringStrand, StringBuilderStrand resolving issue found 2/9/22
	 */
	@Test
	public void testCharAtMinus1() {
		IDnaStrand test = getNewStrand(strs[0]);
		if (test instanceof StringStrand || test instanceof StringBuilderStrand){
			return ;
		}
		try{
			test.charAt(-1);
		}
		catch (Exception e){
			System.out.println("wu");
			assertTrue(e instanceof IndexOutOfBoundsException && !(e instanceof StringIndexOutOfBoundsException));
		}
	}
}

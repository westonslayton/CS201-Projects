import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.Comparator;
import java.util.List;

public class TestBinarySearchAutocomplete {

	private Term[] myTerms = new Term[] { new Term("ape", 0), new Term("apple", 0), new Term("bat", 0),
			new Term("bee", 0), new Term("cat", 0) };
	private String[] myNames = { "ape", "app", "ban", "bat", "bee", "car", "cat" };
	private double[] myWeights = { 6, 4, 2, 3, 5, 7, 1 };

	/** A comparator which considers all terms equal **/
	public class AllEqual implements Comparator<Term> {
		public int compare(Term o1, Term o2) {
			return 0;
		}
	}

	/**
	 * A comparator which is basically Term.PrefixOrder, but counts the number
	 * of compare calls made
	 */
	public class CompareCounter implements Comparator<Term> {

		private PrefixComparator comparator;
		private int count = 0;

		public CompareCounter(int r) {
			comparator = PrefixComparator.getComparator(r);
		}

		@Override
		public int compare(Term o1, Term o2) {
			count++;
			return comparator.compare(o1, o2);
		}

		public int compareCount() {
			return count;
		}

	}

	/**
	 * Sorts terms by ascending weight
	 */
	public class WeightSorter implements Comparator<Term> {

		@Override
		public int compare(Term o1, Term o2) {
			return (int) (100 * (o1.getWeight() - o2.getWeight()));
		}

	}

	public Autocompletor getInstance() {
		return getInstance(myNames, myWeights);
	}

	public Autocompletor getInstance(String[] names, double[] weights) {
		return new BinarySearchAutocomplete(names, weights);
	}
	
	private String[] terms2strings(List<Term> list) {
		String[] ret = new String[list.size()];
		for(int k=0; k < list.size(); k++) {
			ret[k] = list.get(k).getWord();
		}
		return ret;
	}
	
	/**
	 * Tests correctness of topKMatches() for a few simple cases
	 */
	@Test
	public void testTopKMatches() {
		Autocompletor test = getInstance();
		String[] queries = { "", "", "", "", "a", "ap", "b", "ba", "d" };
		int[] ks = { 8, 1, 2, 3, 1, 1, 2, 2, 100 };
		String[][] results = { { "car", "ape", "bee", "app", "bat", "ban", "cat" }, { "car" }, { "car", "ape" },
				{ "car", "ape", "bee" }, { "ape" }, { "ape" }, { "bee", "bat" }, { "bat", "ban" }, {} };
		for (int i = 0; i < queries.length; i++) {
			String query = queries[i];
			String[] reported = terms2strings(test.topMatches(query, ks[i]));
			String[] actual = results[i];
			assertArrayEquals(actual, reported,"wrong top matches for " + query + " " + ks[i]);
		}
	}

	/**
	 * Tests correctness of simple cases where firstIndexOf should find an index
	 */
	@Test
	public void testFirstIndexOfHits() {
		assertEquals(0, BinarySearchAutocomplete.firstIndexOf(myTerms, new Term("a", 0), PrefixComparator.getComparator(1)));

		assertEquals(0, BinarySearchAutocomplete.firstIndexOf(myTerms, new Term("a", 0), PrefixComparator.getComparator(1)));

		assertEquals(2, BinarySearchAutocomplete.firstIndexOf(myTerms, new Term("ba", 0), PrefixComparator.getComparator(2)));

		assertEquals(4, BinarySearchAutocomplete.firstIndexOf(myTerms, new Term("cat", 0), PrefixComparator.getComparator(3)));
	}

	@Test
	public void testLastIndexOfHits() {
		assertEquals(1, BinarySearchAutocomplete.lastIndexOf(myTerms, new Term("a", 0), PrefixComparator.getComparator(1)));

		assertEquals(1, BinarySearchAutocomplete.lastIndexOf(myTerms, new Term("ap", 0), PrefixComparator.getComparator(2)));

		assertEquals(0, BinarySearchAutocomplete.lastIndexOf(myTerms, new Term("ape", 0), PrefixComparator.getComparator(3)));

		assertEquals(3, BinarySearchAutocomplete.lastIndexOf(myTerms, new Term("b", 0), PrefixComparator.getComparator(1)));

		assertEquals(4, BinarySearchAutocomplete.lastIndexOf(myTerms, new Term("c", 0), PrefixComparator.getComparator(1)));
	}

	/**
	 * Tests correctness of simple cases where firstIndexOf and lastIndexOf
	 * should not find a valid index (and thus return -1)
	 */
	@Test
	public void testFirstIndexOfMisses() {
		assertEquals(-1, BinarySearchAutocomplete.firstIndexOf(myTerms, new Term("d", 0), PrefixComparator.getComparator(1)));
		assertEquals(-1, BinarySearchAutocomplete.firstIndexOf(myTerms, new Term("ab", 0), PrefixComparator.getComparator(2)));
		assertEquals(-1, BinarySearchAutocomplete.firstIndexOf(myTerms, new Term("car", 0), PrefixComparator.getComparator(3)));
		assertEquals(-1, BinarySearchAutocomplete.firstIndexOf(myTerms, new Term("cats", 0), PrefixComparator.getComparator(4)));
	}

	/**
	 * This test checks if lastIndexOf returns -1 for missing words"
	 */
	@Test
	public void testLastIndexOfMisses() {
		assertEquals(-1, BinarySearchAutocomplete.lastIndexOf(myTerms, new Term("d", 0), PrefixComparator.getComparator(1)));
		assertEquals(-1, BinarySearchAutocomplete.lastIndexOf(myTerms, new Term("ab", 0), PrefixComparator.getComparator(2)));
		assertEquals(-1, BinarySearchAutocomplete.lastIndexOf(myTerms, new Term("car", 0), PrefixComparator.getComparator(3)));
		assertEquals(-1, BinarySearchAutocomplete.lastIndexOf(myTerms, new Term("cats", 0), PrefixComparator.getComparator(4)));
	}

	/**
	 * Tests if firstIndexOf/lastIndexOf return the correct first/last index
	 * when duplicates exist.
	 */
	@Test
	public void testFirstIndexOfDuplicates() {
		Term[] terms = new Term[] { new Term("ape", 0), new Term("apple", 0), new Term("apple", 0),
				new Term("apple", 0), new Term("bat", 0), new Term("bat", 0), new Term("bat", 0), new Term("bee", 0),
				new Term("bee", 0), new Term("bee", 0), new Term("cat", 0) };
		assertEquals(0, BinarySearchAutocomplete.firstIndexOf(terms, new Term("a", 0), PrefixComparator.getComparator(1)));
		assertEquals(1, BinarySearchAutocomplete.firstIndexOf(terms, new Term("app", 0), PrefixComparator.getComparator(3)));
		assertEquals(4, BinarySearchAutocomplete.firstIndexOf(terms, new Term("b", 0), PrefixComparator.getComparator(1)));
		assertEquals(7, BinarySearchAutocomplete.firstIndexOf(terms, new Term("be", 0), PrefixComparator.getComparator(2)));
	}

	@Test
	public void testLastIndexOfDuplicates() {
		Term[] terms = new Term[] { new Term("ape", 0), new Term("apple", 0), new Term("apple", 0),
				new Term("apple", 0), new Term("bat", 0), new Term("bat", 0), new Term("bat", 0), new Term("bee", 0),
				new Term("bee", 0), new Term("bee", 0), new Term("cat", 0) };
		assertEquals(3, BinarySearchAutocomplete.lastIndexOf(terms, new Term("a", 0), PrefixComparator.getComparator(1)));
		assertEquals(3, BinarySearchAutocomplete.lastIndexOf(terms, new Term("app", 0), PrefixComparator.getComparator(3)));
		assertEquals(6, BinarySearchAutocomplete.lastIndexOf(terms, new Term("ba", 0), PrefixComparator.getComparator(2)));
		assertEquals(9, BinarySearchAutocomplete.lastIndexOf(terms, new Term("b", 0), PrefixComparator.getComparator(1)));
	}
	
	@Test
	public void testFirstIndexOfAllEqual(){
		for(int i = 2; i <= 256; i = i*2){
			Term[] terms = new Term[i];
			for(int j = 0; j < i; j++){
				terms[j] = new Term(""+(char)j, 0);
			}
			AllEqual comp = new AllEqual();
			assertEquals(0, BinarySearchAutocomplete.
					firstIndexOf(terms, new Term("a", 0), comp),"all equal "+i);
		}
	}
	
	@Test
	public void testLastIndexOfAllEqual(){
		AllEqual comp = new AllEqual();
		for(int i = 2; i <= 256; i = i*2){
			Term[] terms = new Term[i];
			for(int j = 0; j < i; j++){
				terms[j] = new Term(""+(char)j, 0);
			}
			assertEquals(i-1, BinarySearchAutocomplete.
					lastIndexOf(terms, new Term("a", 0), comp));
		}
	}
	
	/**Tests if firstIndexOf or lastIndexOf change the arrays passed in
	 */
	@Test
	public void testFirstIndexOfMutates(){
		for(int i = 2; i <= 256; i = i*2){
			Term[] terms = new Term[i];
			for(int j = 0; j < i; j++){
				terms[j] = new Term(""+(char)j, j);
			}
			Term[] terms2 = terms.clone();
			BinarySearchAutocomplete.firstIndexOf(
					terms2, new Term("a", 0), PrefixComparator.getComparator(1));
			assertArrayEquals(terms, terms2,"firstIndexOf mutates arguments");
		}
	}
	
	@Test
	public void testLastIndexOfMutates(){
		for(int i = 2; i <= 256; i = i*2){
			Term[] terms = new Term[i];
			for(int j = 0; j < i; j++){
				terms[j] = new Term(""+(char)j, j);
			}
			Term[] terms2 = terms.clone();
			BinarySearchAutocomplete.lastIndexOf(
					terms2, new Term("a", 0), PrefixComparator.getComparator(1)	);
			assertArrayEquals(terms, terms2,"firstIndexOf mutates arguments"
					);
		}
	}
	
	@Test
	public void testFirstIndexOfUsesEquals(){
		WeightSorter comp = new WeightSorter();
		for(int i = 2; i <= 256; i = i*2){
			Term[] terms = new Term[i];
			for(int j = 0; j < i; j++){
				terms[j] = new Term("a", j);
			}
			assertEquals(0, BinarySearchAutocomplete.
					firstIndexOf(terms, new Term("b", 0), comp),"first index of issue");
		}
	}
	

	@Test
	public void testLastIndexOfUsesEquals(){
		WeightSorter comp = new WeightSorter();
		for(int i = 2; i <= 256; i = i*2){
			Term[] terms = new Term[i];
			for(int j = 0; j < i; j++){
				terms[j] = new Term("b", j);
			}
			assertEquals(i-1, BinarySearchAutocomplete.
					lastIndexOf(terms, new Term("a", i-1), comp),"last index issue");
		}
	}

	/**
	 * Tests that constructor throws the correct exceptions
	 */
	@Test
	public void testConstructorException(){
		try{
			Autocompletor test = getInstance(null, myWeights);
			fail("No exception thrown");
		}
		catch(NullPointerException e){
		}
		catch(Throwable e){
			fail("Wrong throw");
		}
		try{
			Autocompletor test = getInstance(myNames, null);
			fail("No exception thrown");
		}
		catch(NullPointerException e){
		}
		catch(Throwable e){
			fail("Wrong throw");
		}
	}
}

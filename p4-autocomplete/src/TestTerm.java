import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;



public class TestTerm {
	private Random rng = new Random(1234);

	private String[] myNames = { "bhut jolokia", "capsaicin", "carolina reaper", "chipotle", "habanero", "jalapeno",
			"jalapeno membrane" };
	private double[] myWeights = { 855000, 16000000, 2200000, 3500, 100000, 3500, 10000 };

	public Term[] getTerms() {
		Term[] terms = new Term[myNames.length];
		for (int i = 0; i < terms.length; i++)
			terms[i] = new Term(myNames[i], myWeights[i]);
		return terms;
	}

	public int indexOf(Term[] arr, Term item) {
		for (int i = 0; i < arr.length; i++)
			if (arr[i].equals(item))
				return i;
		return -1;
	}


	/**
	 * Tests that sorting terms without comparator is the same as sorting
	 * lexicographically
	 */
	@Test
	public void testNativeSortingOrder() {
		Term[] terms = getTerms();
		Term[] sorted = terms.clone();
		for (int i = 0; i < 10; i++) {
			Collections.shuffle(Arrays.asList(terms));
			Arrays.sort(terms);
			assertArrayEquals(sorted, terms);
		}
	}

	/**
	 * Tests WeightOrder sorts correctly
	 */

	@Test
	public void testWeightSortingOrder() {
		Term[] terms = getTerms();
		Term[] sorted = { terms[3], terms[5], terms[6], terms[4], terms[0], terms[2], terms[1] };
		for (int i = 0; i < 10; i++) {
			// preserve chipotle and jalapeno's order
			Collections.shuffle(Arrays.asList(terms));
			if (indexOf(terms, sorted[0]) > indexOf(terms, sorted[1])) {
				int temp = indexOf(terms, sorted[0]);
				terms[indexOf(terms, sorted[1])] = sorted[0];
				terms[temp] = sorted[1];
			}
			Arrays.sort(terms,
					Comparator.comparing(Term::getWeight));
			assertArrayEquals(sorted, terms);
		}
	}


	@Test
	/**
	 * Tests PrefixOrder
	 */
	public void testPrefixOrder() {
		// Tests basic cases
		Term[] terms1 = getTerms();
		Term[] sorted1 = { terms1[0], terms1[3], terms1[2], terms1[1], terms1[4], terms1[6], terms1[5] };
		for (int i = 0; i < terms1.length / 2; i++) {
			Term temp = terms1[i];
			terms1[i] = terms1[terms1.length - 1 - i];
			terms1[terms1.length - 1 - i] = temp;
		}
		Arrays.sort(terms1, PrefixComparator.getComparator(1));
		assertArrayEquals(sorted1, terms1);

		Term[] terms2 = getTerms();
		Term[] sorted2 = { terms2[0], terms2[2], terms2[1], terms2[3], terms2[4], terms2[6], terms2[5] };
		for (int i = 0; i < terms2.length / 2; i++) {
			Term temp = terms2[i];
			terms2[i] = terms2[terms2.length - 1 - i];
			terms2[terms2.length - 1 - i] = temp;
		}
		Arrays.sort(terms2, PrefixComparator.getComparator(2));
		assertArrayEquals(sorted2, terms2);

		Term[] terms3 = getTerms();
		Term[] sorted3 = { terms3[0], terms3[1], terms3[2], terms3[3], terms3[4], terms3[6], terms3[5] };
		for (int i = 0; i < terms3.length / 2; i++) {
			Term temp = terms3[i];
			terms3[i] = terms3[terms3.length - 1 - i];
			terms3[terms3.length - 1 - i] = temp;
		}
		Arrays.sort(terms3, PrefixComparator.getComparator(3));
		assertArrayEquals(sorted3, terms3);

		// Test prefix case
		Term[] terms4 = getTerms();
		Term[] sorted4 = { terms4[0], terms4[1], terms4[2], terms4[3], terms4[4], terms4[5], terms4[6] };
		Collections.shuffle(Arrays.asList(terms4));
		Arrays.sort(terms4, PrefixComparator.getComparator(10));
		assertArrayEquals(sorted4, terms4);

		// Test zero case
		Term[] terms5 = getTerms();
		Collections.shuffle(Arrays.asList(terms5));
		Term[] sorted5 = terms5.clone();
		Arrays.sort(terms5, PrefixComparator.getComparator(0));
		assertArrayEquals(sorted5, terms5);
	}


	// These tests verify that your PrefixComparator correctly compares words with the same
	// myPrefixSize characters and also compares two words with the same first characters with
	// length less than myPrefixSize.
	@Test
	public void testPrefixPrefix() {
		Term a = new Term("bee",0);
		Term b = new Term("beeswax",0);

		Comparator<Term> c3 = PrefixComparator.getComparator(3);
		Comparator<Term> c4 = PrefixComparator.getComparator(4);

		int r3_bee = c3.compare(a,b);
		int r4_bee = c4.compare(a,b);
		assertEquals(0,r3_bee,a.getWord()+ " "+b.getWord()+" with prefix size 3 should be equal");
		assertEquals(-1,Math.signum(r4_bee),a.getWord()+ " should be less than "+b.getWord()+" with prefix size 4");

		Term c = new Term("cat", 0);
		Term d = new Term("cats", 0);

		int r3_cat = c3.compare(c, d);
		int r4_cat = c4.compare(c, d);
		assertEquals(0,r3_cat,c.getWord()+ " "+d.getWord()+" with prefix size 3 should be equal");
		assertEquals(-1,Math.signum(r4_cat),c.getWord()+ " should be less than "+d.getWord()+" with prefix size 4");
	}

	// This test performs additional tests according to the table provided in the writeup
	// outside of the ones above.
	@Test
	public void testPrefixAdditional(){
		Comparator<Term> c3 = PrefixComparator.getComparator(3); //new Term.PrefixOrder(3);
		Comparator<Term> c4 = PrefixComparator.getComparator(4); //new Term.PrefixOrder(4);

		Term a = new Term("bees", 0);
		Term b = new Term("beek", 0);
		Term c = new Term("bug", 0);
		Term d = new Term("beeswax", 0);
		Term e = new Term("beekeeper", 0);
		Term f = new Term("bee", 0);

		int r4_bee = c4.compare(f, e); //bee v beekeeper, 4
		int r4_bees = c4.compare(a, b); //bees v beek, 4
		int r4_bug = c4.compare(c, d); //bug v beeswax, 4
		int r4_beeswax = c4.compare(a, d); //bees v beeswax, 4
		int r3_beekeeper = c3.compare(e, d); //beekeeper v beeswax, 3
		int r3_bee = c3.compare(f, d); //bee v beeswax, 3

		assertEquals(-1,Math.signum(r4_bee),f.getWord()+ " should be less than "+e.getWord()+" with prefix size 4");
		assertEquals(1,Math.signum(r4_bees),a.getWord()+ " should be greater than "+b.getWord()+" with prefix size 4");
		assertEquals(1,Math.signum(r4_bug),c.getWord()+ " should be greater than "+d.getWord()+" with prefix size 4");
		assertEquals(0,Math.signum(r4_beeswax),a.getWord()+ " "+d.getWord()+" with prefix size 4 should be equal");
		assertEquals(0,Math.signum(r3_beekeeper),e.getWord()+ " "+d.getWord()+" with prefix size 3 should be equal");
		assertEquals(0,Math.signum(r3_bee),f.getWord()+ " "+b.getWord()+" with prefix size 4 should be equal");
	}
}

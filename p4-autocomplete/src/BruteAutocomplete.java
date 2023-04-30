import java.util.*;

/**
 * Implements Autocompletor by scanning through the entire array of terms for
 * every topKMatches or topMatch query.
 */
public class BruteAutocomplete implements Autocompletor {

	protected Term[] myTerms;
	protected int mySize;

	/**
	 * Create immutable instance with terms constructed from parameter
	 * @param terms words such that terms[k] is part of a word pair 0 <= k < terms.length
	 * @param weights weights such that weights[k] corresponds to terms[k]
	 * @throws NullPointerException if either parameter is null
	 * @throws IllegalArgumentException if terms.length != weights.length
	 * @throws IllegalArgumentException if any elements of weights is negative
	 * @throws IllegalArgumentException if any elements of terms is duplicate
	 */
	public BruteAutocomplete(String[] terms, double[] weights) {

		if (terms == null || weights == null) {
			throw new NullPointerException("One or more arguments null");
		}

		if (terms.length != weights.length) {
			throw new IllegalArgumentException("terms and weights are not the same length");
		}
		initialize(terms,weights);
	}

	@Override
	public List<Term> topMatches(String prefix, int k) {
		if (k < 0) {
			throw new IllegalArgumentException("Illegal value of k:"+k);
		}
		
		// maintain pq of size k
		PriorityQueue<Term> pq = 
				new PriorityQueue<>(Comparator.comparing(Term::getWeight));
		for (Term t : myTerms) {
			if (!t.getWord().startsWith(prefix)) {
				continue; // don't process if doesn't begin with prefix
			}
			if (pq.size() < k) {
				pq.add(t);
			} else if (pq.peek().getWeight() < t.getWeight()) {
				pq.remove();
				pq.add(t);
			}
		}
		// after loop, pq holds *at most* k Terms and
		// these are terms that are the "heaviest" based on code above
		// since pq is a min-pq, lightest/least-heavy is first to be removed

		int numResults = Math.min(k, pq.size());
		LinkedList<Term> ret = new LinkedList<>();
		for (int i = 0; i < numResults; i++) {
			ret.addFirst(pq.remove());
		}
		return ret;
	}
	public List<Term> topSort(String prefix, int k){
		ArrayList<Term> list = new ArrayList<>();
		for(Term t : myTerms) {
			if (t.getWord().startsWith(prefix)) {
				list.add(t);
			}
		}
		Collections.sort(list,Comparator.comparing(Term::getWeight)
				                        .reversed());
		return list.subList(0,Math.min(list.size(),k));
	}
	@Override
	public void initialize(String[] terms, double[] weights) {
		myTerms = new Term[terms.length];

		HashSet<String> words = new HashSet<>();

		for (int i = 0; i < terms.length; i++) {
			words.add(terms[i]);
			myTerms[i] = new Term(terms[i], weights[i]);
			if (weights[i] < 0) {
				throw new IllegalArgumentException("Negative weight "+ weights[i]);
			}
		}
		if (words.size() != terms.length) {
			throw new IllegalArgumentException("Duplicate input terms");
		}
	}
	
	@Override
	public int sizeInBytes() {
		if (mySize == 0) {
			
			for(Term t : myTerms) {
			    mySize += BYTES_PER_DOUBLE + 
			    		BYTES_PER_CHAR*t.getWord().length();	
			}
		}
		return mySize;
	}
}

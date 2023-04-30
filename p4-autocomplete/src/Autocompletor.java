import java.util.List;

/**
 * An Autocompletor supports returning either the top k best matches, or the
 * single top match, given a String prefix.
 * 
 * @author Austin Lu
 * @author Owen Astrachan changed API from Iterable to List
 * @author Owen Astrachan added size constants
 */
public interface Autocompletor {
	
	public static int BYTES_PER_DOUBLE = 8;
	public static int BYTES_PER_CHAR = 2;
	public static int BYTES_PER_INT = 4;
	public static int BYTES_PER_BOOL = 1;

	/**
	 * Returns the top k matching terms in descending order of weight. If there
	 * are fewer than k matches, return all matching terms in descending order
	 * of weight. If there are no matches, return an empty list.
	 */
	public List<Term> topMatches(String prefix, int k);
	
	/**
	 * Create internal state needed to store Term objects
	 * from the parameters. Should be called in implementing
	 * constructors
	 * @param terms is array of Strings for words in each Term
	 * @param weights is corresponding weight for word in terms
	 */
	public void initialize(String[] terms, double[] weights);
	
	/**
	 * Return size in bytes of all Strings and doubles
	 * stored in implementing class. To the extent that
	 * other types are used for efficiency, there size should
	 * be included too 
	 * @return number of bytes used after initialization
	 */
	public int sizeInBytes();

}

import java.util.Iterator;

/**
 * Interface for DNA/strand experiments
 * 
 * @author Owen Astrachan
 * @author Brandon Fain
 */
public interface IDnaStrand extends Iterable<Character>{
	/**
	 * Cut this strand at every occurrence of enzyme, essentially replacing
	 * every occurrence of enzyme with splicee.
	 * @param enzyme is the pattern/strand searched for and replaced
	 * @param splicee is the pattern/strand replacing each occurrence of enzyme
	 * @return the new strand leaving the original strand unchanged.
	 */
	default IDnaStrand cutAndSplice(String enzyme, String splicee) {
		String search = this.toString();
		IDnaStrand ret = getInstance("");
		// Splits dna strand by enzyme, leaving empty strings
		// in case of leading, repeating, or trailing enzymes
		String[] fragments = search.split(enzyme+"{1}", -1);
		for (int i=0; i<fragments.length-1; i++) {    // splicing in
			ret.append(fragments[i]);
			ret.append(splicee);
		}
		ret.append(fragments[fragments.length-1]);    // adding last fragment
		return ret;
	}

	/**
	 * Returns the number of elements/base-pairs/nucleotides in this strand.
	 * @return the number of base-pairs in this strand
	 */
	public long size();

	/**
	 * Initialize by copying DNA data from the string into this strand,
	 * replacing any data that was stored. The parameter should contain only
	 * valid DNA characters, no error checking is done by the this method.
	 * 
	 * @param source
	 *            is the string used to initialize this strand
	 */
	public void initialize(String source);

	/**
	 * Return this object, useful to obtain
	 * an object without knowing its type, e.g.,
	 * calling dna.getInstance() returns an IDnaStrand
	 * that will be the same concrete type as dna
	 * @param source is data from which object constructed
	 * @return an IDnaStrand whose .toString() method will be source
	 */
	public IDnaStrand getInstance(String source);
	
	/**
	 * Return some string identifying this class and
	 * internal tracked data, e.g., append calls.
	 * @return a string representing this strand and its characteristics
	 */
	default public String strandInfo() {
		return this.getClass().getName();
	}

	/**
	 * Append dna to the end of this strind.
	 * @param dna
	 *            is the string appended to this strand
	 * @return this strand after the data has been added
	 */
	public IDnaStrand append(String dna);

	/**
	 * Returns an IDnaStrand that is the reverse of this strand, e.g., for
	 * "CGAT" returns "TAGC"
	 * 
	 * @return reverse strand
	 */
	public IDnaStrand reverse();

	/**
	 * Returns the number of times append has been called.
	 * 
	 * @return
	 */
	public int getAppendCount();
	
	/**
	 * Returns character at a specified index, where 0 <= index < size()
	 * @param index specifies which character will be returned
	 * @return the character at index
	 * @throws IndexOutOfBoundsException if index < 0 or inde >= size()
	 */
	public char charAt(int index);
	
	/**
	 * Satisfy the Iterable<Character> interface
	 * @return an iterator over this DNA sequence
	 */
	default Iterator<Character> iterator(){
		return new CharDnaIterator(this);
	}
	
}

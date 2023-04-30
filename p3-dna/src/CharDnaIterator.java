import java.util.Iterator;

/**
 * This class serves to iterate over all the characters in
 * a strand of dna. Internally it uses the IDnaStrand.charAt
 * method, so performance will be "good" if sequential
 * access via consecutive indexes results in O(n)
 * performance for n calls of .charAt with indexes
 * in order
 * @author ola 
 *
 */
public class CharDnaIterator implements Iterator<Character> {
	
	private IDnaStrand myStrand;
	private int myIndex;
	
	/**
	 * Construct an iterator from a specified strand.
	 * @param strand will be iterated over
	 */
	public CharDnaIterator(IDnaStrand strand) {
		myStrand = strand;
		myIndex = 0;
	}
	
	/**
	 * Standard Iterator hasNext() method
	 * @return true if there is a next character,
	 * false otherwise
	 */
	@Override 
	public boolean hasNext(){
		return myIndex < myStrand.size();
	}
	
	/**
	 * Standard iterator next() functionality, i.e.,
	 * next() should return true before calling this
	 * method.
	 * @return the next character in the iteration
	 */
	@Override 
	public Character next(){
		char ch = myStrand.charAt(myIndex);
		myIndex++;
		return ch;
	}
}

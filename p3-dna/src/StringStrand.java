/**
 * Simple but somewhat efficient implementation of IDnaStrand. \ This
 * implementation uses StringBuilders to represent genomic/DNA data.
 * @author ola
 */

public class StringStrand implements IDnaStrand {
	
	private String myInfo;
	private int myAppends;

	public StringStrand(){
		this("");
	}
	/**
	 * Create a strand representing s. No error checking is done to see if s
	 * represents valid genomic/DNA data.
	 * 
	 * @param s
	 *            is the source of cgat data for this strand
	 */
	public StringStrand(String s) {
		initialize(s);
	}

	/**
	 * Initialize this strand so that it represents the value of source. No
	 * error checking is performed.
	 * 
	 * @param source
	 *            is the source of this enzyme
	 */
	@Override 
	public void initialize(String source) {
		myInfo = new String(source);
		myAppends = 0;
	}
	
	/**
	 * @return the number of base pairs in this strand
	 */
	
	@Override 
	public long size() {
		return myInfo.length();
	}

	@Override
	public String toString() {
		return myInfo;
	}

	/**
	 * Simply append a strand of dna data to this strand. No error checking is
	 * done. This method isn't efficient; it doesn't use a StringBuilder or a
	 * StringBuffer.
	 * 
	 * @param dna
	 *            is the String appended to this strand
	 */
	@Override 
	public IDnaStrand append(String dna) {
		myInfo = myInfo + dna;
		myAppends++;
		return this;
	}

	@Override 
	public IDnaStrand reverse() {
		StringBuilder copy = new StringBuilder(myInfo);
		copy.reverse();
		StringStrand ss = new StringStrand(copy.toString());
		return ss;
	}
	
	@Override 
	public IDnaStrand getInstance(String source) {

		return new StringStrand(source);
	}

	@Override 
	public int getAppendCount() {
		return myAppends;
	}
	
	@Override 
	public char charAt(int index){
		return myInfo.charAt(index);
	}
}

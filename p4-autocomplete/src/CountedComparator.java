import java.util.Comparator;

/**
 * Decorate a comparator so that number
 * of comparisons can be accessed. Wrap the comparator
 * supplied in constructor in this one, all calls passed
 * through to supplied comparator and counted.
 * 
 * @author ola
 *
 * @param <E>
 */
public class CountedComparator<E extends Comparable<E>> implements Comparator<E> {
	private Comparator<E> myComparator;
	private int myCount;
	
	public CountedComparator(){
		myComparator = Comparator.naturalOrder();
	}
	
	public CountedComparator(Comparator<E> comp) {
		myComparator = comp;
	}
	
	@Override
	public int compare(E o1, E o2) {
		myCount++;
		return myComparator.compare(o1,o2);
	}
	
	/**
	 * Return number of times compare is called
	 * @return
	 */
	public int getCount() {
		return myCount;
	}
	
	/**
	 * Reset internal counter to zero so that
	 * getCount() will return zero immediately after clear()
	 */
	public void reset() {
		myCount = 0;
	}
	
}

import java.util.*;

public class HashListAutocomplete implements Autocompletor {

    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;


    public HashListAutocomplete(String[] terms, double[] weights) {
        if(terms.length != weights.length){
            throw new IllegalArgumentException("terms and weights different length");
        }
        if(weights == null || terms == null){
            throw new NullPointerException("Null arguments");
        }

        initialize(terms, weights);
    
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        List list = new ArrayList<>();
        if(!(prefix.length() <= MAX_PREFIX))
            prefix = prefix.substring(0, MAX_PREFIX);
        
        if(myMap.containsKey(prefix)){
            List<Term> a = myMap.get(prefix);
            List<Term> b = a.subList(0, Math.min(k, a.size()));
            return b;
        }

        return list; 
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        myMap = new HashMap<String, List<Term>>();
		for (int i = 0; i < terms.length; i++) {
			int now = Math.min(MAX_PREFIX, terms[i].length());
			for (int k = 0; k <= now; k++) {
				if (!myMap.containsKey(terms[i].substring(0, k))) { 
					List<Term> n = new ArrayList<Term>();
					Term cur = new Term(terms[i], weights[i]);
					myMap.put(terms[i].substring(0, k), n);
					myMap.get(terms[i].substring(0, k)).add(cur);
				} 
                else{
                    Term cur = new Term(terms[i], weights[i]);
					myMap.get(terms[i].substring(0, k)).add(cur);
				}
				if(weights[i] < 0) {
					throw new IllegalArgumentException("Weight below 0");
				}
			}
		}
		for (String s : myMap.keySet()) {
			Collections.sort(myMap.get(s), Comparator.comparing(Term::getWeight).reversed());
		}
		mySize = 0;
    }

    @Override
    public int sizeInBytes() {
        if(mySize == 0){
			for (String s : myMap.keySet()) {
                int h = 0;
				mySize = mySize + s.length() * BYTES_PER_CHAR;
				List<Term> b = myMap.get(s);
				while (h < b.size()) {
					Term arb = b.get(h);
					mySize = mySize + BYTES_PER_DOUBLE + BYTES_PER_CHAR * arb.getWord().length();
					h++;
				}
			}
		}

		return mySize;
    }
    
}


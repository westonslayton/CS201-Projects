import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


public class BenchMarkForAutocomplete {
	public static final String CHARSET = "UTF-8";
	public static final Locale LOCALE = Locale.US;
	
	String[] myCompletorNames = {
		"BruteAutocomplete",
		"BinarySearchAutocomplete",
		"HashListAutocomplete"
	};
	Autocompletor[] myCompletors;
	
	public void setUp(String filename) throws FileNotFoundException {
		Scanner in = new Scanner(new File(filename), CHARSET);
		in.useLocale(LOCALE);
		int N = Integer.parseInt(in.nextLine());
		String[] terms = new String[N];
		double[] weights = new double[N];
		for (int i = 0; i < N; i++) {
			String line = in.nextLine();
			int tab = line.indexOf('\t');
			weights[i] = Double.parseDouble(line.substring(0, tab).trim());
			terms[i] = line.substring(tab + 1);
		}
		myCompletors = new Autocompletor[myCompletorNames.length];
		double[] times = new double[myCompletors.length];
		for(int k=0; k < myCompletors.length; k++) {
			String name = myCompletorNames[k];
			try {
				double start = System.nanoTime();
				Autocompletor a = (Autocompletor) Class.forName(name)
						.getDeclaredConstructor(String[].class, double[].class).newInstance(terms, weights);
				double end = System.nanoTime();
				myCompletors[k] = a;
				times[k] = (end-start)/1e9;
			} catch (Exception e) {
				throw new RuntimeException("can't create "+name+" "+e);
			}
		}
		for(int k=0; k < myCompletors.length; k++) {
			System.out.printf("init time: %2.4g\tfor %s\n",
					times[k],myCompletorNames[k]);
		}
	}
	
	public void runAM() {
		int matchSize = 100;
		String[] all = {"","", "a", "a", "b", "c", "g", "ga", "go", "gu", "x", "y", "z", "aa", "az", "za", "zz","zqzqwwx"};
		System.out.printf("search\tsize\t#match");
		for(int k=0; k < myCompletors.length; k++) {
			String name = myCompletorNames[k];
			int length = Math.min(10, name.length());
			System.out.printf("\t%s", name.substring(0,length));
		}
		System.out.println();
		
		for(String s : all) {
			ArrayList<List<Term>> results = new ArrayList<>();
			int allSize = myCompletors[0].topMatches(s, Integer.MAX_VALUE).size();
			double[] times = new double[myCompletors.length];
			System.gc();
			for(int k=0; k < myCompletors.length; k++) {				
				double start = System.nanoTime();
				results.add(myCompletors[k].topMatches(s, matchSize));
				double end = System.nanoTime();
				times[k] = (end-start)/1e9;
			}
			System.out.printf("%s\t%d\t%d", s,allSize,matchSize);
			for(int k=0; k < myCompletors.length; k++) {
				System.out.printf("\t%2.8f", times[k]);
			}
			System.out.println();
			for(int k=0; k < results.size()-1; k++) {
				if (! results.get(k).equals(results.get(k+1))) {
					System.out.printf("%s and %s differ\n",
							myCompletorNames[k],myCompletorNames[k+1]);
					List<Term> r1 = results.get(k);
					List<Term> r2 = results.get(k+1);
					for(int j=0; j < r1.size(); j++) {
						if (!r1.get(j).equals(r2.get(j))) {
							System.out.printf("%d\t%s\t%s\n",
									j,r1.get(j),r2.get(j));
						}
					}
				}
			}
		}
		for(int k=0; k < myCompletors.length; k++) {
			System.out.printf("size in bytes=%d\t for %s\n",
					myCompletors[k].sizeInBytes(),myCompletorNames[k]);
		}

	}
	public void doMark() throws FileNotFoundException {
		String fname = "data/threeletterwords.txt"; 
		fname = "data/fourletterwords.txt";
		fname = "data/alexa.txt";
		setUp(fname);
		runAM();
	}
	public static void main(String[] args) throws FileNotFoundException {
		
		BenchMarkForAutocomplete bmfb = new BenchMarkForAutocomplete();
		bmfb.doMark();
	}
}

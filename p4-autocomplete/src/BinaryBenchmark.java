import java.util.*;

public class BinaryBenchmark {
	public List<String> createList(int reps) {
		ArrayList<String> list = new ArrayList<>();
		for(char ch = 'a'; ch <= 'z'; ch++) {
			String s = ""+ch+ch+ch;
			for(int k=0; k < reps; k++) {
				list.add(s);
			}
		}
		Collections.sort(list);
		return list;
	}
	
	public void results() {
		List<String> list = createList(1000);
		System.out.printf("size of list = %d\n", list.size());
		System.out.println("prefix\tindex\tindex\tcslow\tcfast\n");
		for(char ch = 'a'; ch <= 'z'; ch += 5) {
			String prefix = ""+ch+ch+ch;
			CountedComparator<String> comp = new CountedComparator<>();
			int dex = BinarySearchLibrary.firstIndexSlow(list, prefix, comp);
			CountedComparator<String> comp2 = new CountedComparator<>();
			int dex2 = BinarySearchLibrary.firstIndex(list, prefix, comp2);
			System.out.printf("%s\t%6d\t%6d\t%6d\t%6d\n",
					   prefix,dex,dex2,comp.getCount(),comp2.getCount());
		}
	}
	public static void main(String[] args) {
		BinaryBenchmark bbm = new BinaryBenchmark();
		bbm.results();
	}
}

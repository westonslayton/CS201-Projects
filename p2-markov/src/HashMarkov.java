import java.util.*;

public class HashMarkov implements MarkovInterface{
    protected String[] myWords;		// Training text split into array of words 
	protected Random myRandom;		// Random number generator
	protected int myOrder;			// Length of WordGrams used
    private HashMap<WordGram, List<String>> myMap = new HashMap<>();

    public HashMarkov(int order){
		myOrder = order;
		myRandom = new Random();
	}

    @Override
	public void setTraining(String text){
		myWords = text.split("\\s+");
        myMap.clear();
        for(int i = 0; i < myWords.length - myOrder; i++){
           WordGram gram = new WordGram(myWords, i, myOrder);
		   if(myMap.containsKey(gram)){
				myMap.get(gram).add(myWords[i + myOrder]);
		   }
		   else{
			ArrayList<String> afterWords = new ArrayList<String>();
			afterWords.add(myWords[i + myOrder]);
			myMap.put(gram, afterWords);
		   }
        }
	}

    @Override
	public List<String> getFollows(WordGram wgram) {
        ArrayList<String> list = new ArrayList<>();
		if(myMap.containsKey(wgram)){
            return myMap.get(wgram);
        }
        else{return list;}
	}

    private String getNext(WordGram wgram) {
		List<String> follows = getFollows(wgram);
		if (follows.size() == 0) {
			int randomIndex = myRandom.nextInt(myWords.length);
			return myWords[randomIndex];
		}
		else {
			int randomIndex = myRandom.nextInt(follows.size());
			return follows.get(randomIndex);
		}
	}

    @Override
	public String getRandomText(int length){
		ArrayList<String> randomWords = new ArrayList<>(length);
		int index = myRandom.nextInt(myWords.length - myOrder + 1);
		WordGram current = new WordGram(myWords,index,myOrder);
		randomWords.add(current.toString());

		for(int k=0; k < length-myOrder; k += 1) {
			String nextWord = getNext(current);
			randomWords.add(nextWord);
			current = current.shiftAdd(nextWord);
		}
		return String.join(" ", randomWords);
	}

    @Override
	public void setSeed(long seed) {
		myRandom.setSeed(seed);
	}

    @Override
	public int getOrder() {
		return myOrder;
	}

}

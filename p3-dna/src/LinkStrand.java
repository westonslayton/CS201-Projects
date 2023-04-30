public class LinkStrand implements IDnaStrand {

    private class Node {
        String info;
        Node next;
        public Node(String x){
            info = x;
            next = null;
        }
    }

    private Node myFirst, myLast;
    private long mySize;
    private int myAppends;
    private int myIndex;
    private Node myCurrent;
    private int myLocalIndex;


    public LinkStrand(){
        this("");
    }

    public LinkStrand(String s) {
		initialize(s);
	}

    
    @Override
    public long size() {
        return mySize;
    }

    @Override
    public void initialize(String source) {
        myIndex = 0;
        myLocalIndex = 0;
        myFirst = new Node(source);
        myCurrent = myFirst;
        mySize = source.length();
        myLast = myFirst;
        myAppends = 0;
    }

    @Override
    public IDnaStrand getInstance(String source) {
        return new LinkStrand(source);
    }

    @Override
    public IDnaStrand append(String dna) {
        Node appending = new Node(dna);
        myLast.next = appending;
        myLast = appending;
        mySize += dna.length();
        myAppends += 1;
        return this;
    }

    public String toString(){
        Node current = myFirst;
        StringBuilder dna = new StringBuilder();
        while(current != null){
            dna.append(current.info);
            current = current.next;
        }
        return dna.toString();
    }

    @Override
    public IDnaStrand reverse() {
        LinkStrand reversed = new LinkStrand();
        Node current = myFirst;
        while(current != null){
            StringBuilder currentDna = new StringBuilder(current.info);
            String dnaReversed = currentDna.reverse().toString();
            reversed.append(dnaReversed);
            reversed.myAppends -= 1;
            current = current.next;
        }
        Node list = reversed.myFirst;
        Node rev = null;
        while (list != null) {
            Node temp = list.next;
            list.next = rev;
            rev = list;
            list = temp;
        }
        reversed.myFirst = rev;
        return reversed;
    }

    @Override
    public int getAppendCount() {
        return myAppends;
    }

    
    public char charAt(int index) {
        if(0 > index || index >= mySize){
            throw new IndexOutOfBoundsException();
        }
        if(myIndex > index){
            myIndex = 0;
            myLocalIndex = 0;
            myCurrent = myFirst;
        }

        while(index != myIndex){
            myIndex += 1;
            myLocalIndex += 1;
            if(myCurrent.info.length() == myLocalIndex){
                myCurrent = myCurrent.next;
                myLocalIndex = 0;
            }
        }
        return myCurrent.info.charAt(myLocalIndex);
    }
}

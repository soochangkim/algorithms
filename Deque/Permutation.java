import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
    
public class Permutation {
    
    public static void main(String[] args){
        
        int size = Integer.parseInt(args[0]);
        RandomizedQueue<String> rQueue = new RandomizedQueue<String>();
        
        while(!StdIn.isEmpty()){
            rQueue.enqueue(StdIn.readString());
        }
        
        for(int i = 0; i < size; i++){
            StdOut.printf(rQueue.dequeue() + " ");
        }
    }
}
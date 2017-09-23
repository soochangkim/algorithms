import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private final int INIT_SIZE = 16;
    private int top = 0;
    private Item[] ar;
    
    public RandomizedQueue() {
        ar = (Item[]) new Object[INIT_SIZE];
    }
    
    public boolean isEmpty() {
        return top == 0;
    }
    
    public int size(){
        return top;
    }
    
    public void enqueue(Item item){
        
        if(item == null) throw new IllegalArgumentException();
        if(top >= ar.length * 3 / 4) resize(ar.length << 1);
        
        ar[top++] = item;
        shuffle();
        
    }
    
    public Item dequeue(){
        
        Item item;
        
        validateQueue();
        
        if(top < ar.length * 1 / 4) resize(ar.length >> 1);
        
        item = ar[--top];
        ar[top] = null;
        if(!isEmpty()) shuffle();
        
        return item;

    }
    
    private void validateQueue(){
        if(isEmpty()) throw new NoSuchElementException();
    }
    private void resize(int size){
        Item[] newArray = (Item[]) new Object[size];
        int i;
        
        for(i = 0; i < top; i++){
            newArray[i] = ar[i];
        }
        
        ar = newArray;
    }
    
    private void shuffle(){
        int idx = StdRandom.uniform(top);
        
        Item temp = ar[top - 1];    
        ar[top - 1] = ar[idx];
        ar[idx] = temp;    
    }
    
    public Item sample(){
        
        validateQueue();
        
        int idx = StdRandom.uniform(top);
        return ar[idx];
    }
    
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item>{
        
        private boolean isInit = false;
        private Item[] clone;
        private int cursor;
               
        public boolean hasNext(){
            if(!isInit) init();
            return cursor > 0;            
        }
        
        public Item next(){
            Item item;
            
            if(!hasNext()) throw new NoSuchElementException();
            if(!isInit) init();
            
            item = clone[--cursor];
            clone[cursor] = null;
            return item;
        }
        
        public void remove(){
            throw new UnsupportedOperationException ();
        }
        
        private void init(){
            int i; 
            clone = (Item[]) new Object[top];
            
            for(i = 0; i < top; i++ ){
                clone[i] = ar[i];
            }
            cursor = top;
            StdRandom.shuffle(clone);
            isInit = true;
        }
    }
    
    public static void main(String[] args) {
        RandomizedQueue<String> rQueue = new RandomizedQueue<String>();
        
        rQueue.enqueue("12");
        Iterator<String> it = rQueue.iterator();
        it.hasNext();
        it.next();
        it.next();
        
    }
}
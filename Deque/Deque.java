import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    
    private class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> prev;
    }
    
    private int size;
    private Node<Item> begin;
    private Node<Item> end;
            
    public Deque() {
        begin = end = new Node<Item>();
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void addFirst(Item item) {
        
        Node<Item> newItem = new Node<Item>();
        
        validateInput(item);
        
        newItem.item = item;
        newItem.next = begin;
        begin.prev = newItem;
        begin = newItem;
        size++;        
    }
    
    public void addLast(Item item) {
     
        Node<Item> newItem = new Node<Item>();
        
        validateInput(item);
        
        newItem.item = item;
        newItem.prev = end;
        end.next = newItem;
        end = newItem;
        size++;        
    }
    
    public Item removeFirst() {
        
        Item item;
        
        validateDeque();
        
        item = begin.item;
        
        begin = begin.next;
        begin.prev.next = null;
        begin.prev = null;
        
        size-- ;
        
        return item;
    }
    
    public Item removeLast() {
        
        Item item;       
        
        validateDeque();
        
        item = end.item;
        end = end.prev;
        end.next.prev = null;
        end.next = null;        
        
        return item;
    }
       
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
   
    private class DequeIterator implements Iterator<Item>{
        
        private Node<Item> cursor;
        
        public boolean hasNext(){           
            return cursor.next != null;
        }
        
        public Item next(){
            Item item;
            
            if(!hasNext()){
                
                throw new NoSuchElementException ();
            }
            
            item = cursor.item;
            cursor = cursor.next;
            return item;
        }   
        
        public void remove(){
            throw new UnsupportedOperationException();
        }   
    }
    
    private void validateDeque () {
        if( isEmpty()) {
            throw new NoSuchElementException();
        }
    }
    
    private void validateInput(Item input){
        if(input == null){
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(0);
         deque.removeFirst();
    }
}
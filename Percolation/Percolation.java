import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
 
    private final WeightedQuickUnionUF uf;
    private boolean[] open;
    private final int len;
    private final int begin;//begin = 0;
    private final int end;
    private final int size;
    private boolean isPerlocate = false;
    private int numOpen;
    
    public Percolation(int n) {        
        
        if ( n <= 0) {
            throw new IllegalArgumentException ("row index i out of bounds");
        } 
        
        size = n;
        begin = 0;
        end = size * size + 1;
        len = end + 1;
        uf = new WeightedQuickUnionUF(len);
        open = new boolean[len];
        open[begin] = true;
        
        for (int i = 1; i < size + 1; i ++ ) uf.union(i, 0);       
        numOpen = 0;
    }
    
    public void open(int row, int col) { 
        
        if ( !isValidRowCol(row, col)) {
            throw new IllegalArgumentException ("row index i out of bounds");
        }
        
        int idx = (row - 1) * size + col;
        
        if(open[idx]){
            return;
        }
                
        if (row > 1 && open[idx - size]) { 
            uf.union(idx, idx - size);
        } 
        
        if (col < size && open[idx + 1] ) { 
            uf.union(idx, idx + 1);
        } 
        
        if (row < size && open[idx + size]) { 
            uf.union(idx, idx + size);
        } 
        
        if (col > 1 && open[idx - 1]) { 
            uf.union(idx, idx - 1);
        } 
        
        open[idx] = true;
        numOpen ++;
        
        if(!isPerlocate && uf.connected(begin, idx)){
            int temp = end - size - 1;
            for(int i = 1; i < size + 1; i ++){
                if(open[temp + i] &&  isFull(size, i)){
                    isPerlocate = true;
                }
            }
        }
    } 
    
    public boolean isOpen(int row, int col) { 
        
        if(!isValidRowCol(row, col)){
            throw new IllegalArgumentException ("row index i out of bounds");
        }
        
        return open[--row * size + col];
    } 
    
    public boolean isFull(int row, int col) { 
        
        if(!isValidRowCol(row, col)){
            throw new IllegalArgumentException ("row index i out of bounds");
        }
        
        int idx = (row - 1) * size + col;
        return uf.connected(begin, idx) && open[idx];
    } 
    
    public int numberOfOpenSites() { 
        return numOpen;
    } 
    
    public boolean percolates() { 
        return isPerlocate ;
    } 
    
    private boolean isValidRowCol(int row, int col) {
        return (row > 0) && (col > 0) && (row <= size) && (col <= size);
    }
    
    public static void main(String[] args) {
        
        Percolation p = new Percolation(3);
       
//        p.open(1,6);
        p.open(2,3);
        p.open(3,3);
        p.open(1,3);
        p.open(3, 1);
        StdOut.println(p.percolates());
//        StdOut.println(p.numberOfOpenSites());
//        StdOut.println(p.isFull(3,1));
//        p.open(5,6);
//        p.open(5,5);
//
//        p.open(4,4);
//        p.open(3,4);
//        p.open(2,4);
//        p.open(2,3);
//        p.open(2,2);
//        p.open(2,1);
//
//        p.open(3,1);
//        p.open(4,1);
//        p.open(5,1);
//        p.open(5,2);
//        p.open(6,2);
//        p.open(5,4);
    }
}
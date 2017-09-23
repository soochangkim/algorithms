import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {  
    
    private final double[] estimation;
    private double mean = -1;
    private double stddev = -1;
    private final double time;
    private final int times;
    
    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid           
        
        int count, row, col;
        
        if(n < 1 || trials < 1){
            throw new IllegalArgumentException ("row index i out of bounds");
        }
        times = trials;
        estimation = new double[trials];
        Stopwatch stopwatch = new Stopwatch();
        
        for(int i = 0; i < trials; i ++) {
            
            Percolation p = new Percolation(n);
            count = 0;
            
            while(!p.percolates()) {
                
                do{
                    row = StdRandom.uniform(1, n + 1);
                    col = StdRandom.uniform(1, n + 1);
                } while(p.isOpen(row, col));
                
                p.open(row, col);
                count ++;
            }
                       
            estimation[i] =  1.0 * count / (n * n);
        }
        time = stopwatch.elapsedTime();
        
    }
    public double mean() {
        if(mean == -1){
            mean = StdStats.mean(estimation);
        }
        return mean;
    }                          
   
    public double stddev() {
        if(stddev == -1){
            stddev = StdStats.stddev(estimation);
        }
        return stddev;
    }                        
   
    public double confidenceLo() {
       // low  endpoint of 95% confidence interval
       return mean() - 1.96 * stddev() / Math.sqrt(times);
   
    }                  
   
    public double confidenceHi() {
        
       return mean() + 1.96 * stddev() / Math.sqrt(times);
    }                  

   
    public static void main(String[] args) {
        // test client (described below)
       PercolationStats ps = new PercolationStats(200, 100);
      
       StdOut.println(ps.confidenceLo());
    }        
}

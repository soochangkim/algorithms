import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    
    private LineSegment[] segments;
    private Point[][] p;
    private int top = 0 ;
    private Comparator<Point> c;
    
    public FastCollinearPoints(Point[] points) {
        
        int i, j, k, len, count;
        
        if(points == null ) throw new java.lang.IllegalArgumentException ();
        
        validatePoint(points);
        len = points.length;  
        Point[] copy = new Point[len];
        Point[] clone = new Point[len];
        Point[] temp;
        segments = new LineSegment[len * len];
        p = new Point[len * len][2];
        
        for( i = 0; i < len; i ++){
            copy[i] = points[i];
            clone[i] = points[i];
        }
        
        Arrays.sort(copy);
        
        for ( i = 0; i < len; i++) {
            
            c = copy[i].slopeOrder();
            Arrays.sort(clone, c);
            
            for ( j = 1; j < len; ) {
                
                k = j + 1;
                count = 1;
                
                while( k < len && isSameLine( clone[j], clone[k] )) {
                    count++;
                    k++;
                }

                if ( count >= 3) {
                    temp = setSegment(clone, 0, j, k);
                    
                    if(!searchLineSegment(temp)){
                        insert(p, temp);
                        segments[top++] = new LineSegment(temp[0], temp[1]);
                    }   
                }
                
                j = k; 
            }
        }
    }
    
    private void insert(Point[][] points, Point[] p1){
        points[top] = p1;
        int i = top - 1;
       
        while(i >= 0 
             && points[i][0].compareTo(points[i+1][0]) >= 0
             && points[i][1].compareTo(points[i+1][1]) > 0){
            exch(points[i], points[i+1]);
            i--;
        }
    }
    
    private void exch(Point[] p1, Point[] p2){
        Point[] temp = p1;
        p1 = p2;
        p2 = temp;
    }
    
    private boolean searchLineSegment(Point[] sg){
        
        int lo = 0, hi = top;
        int mid=(lo + hi) /2;
        
        while ( lo < hi ) {
            if(p[mid][0].compareTo(sg[0]) > 0) hi = mid - 1;
            else if(p[mid][0].compareTo(sg[0]) < 0) lo = mid + 1;
            else {

                while(p[mid][0].compareTo(sg[0]) == 0){
                    if(p[mid][1].compareTo(sg[1]) > 0) mid--;
                    else if(p[mid][1].compareTo(sg[1]) < 0) mid++;
                    else return true;
                }
                return false;
            }
            mid=(lo + hi) /2;
        }
        return false;

    }
    
    private Point[] setSegment (Point[] p, int origin, int lo, int hi){
        
        int len = hi - lo;
        Point[] ps = new Point[len + 1];
        ps[0] = p[origin];
        Point[] poin = new Point[2];
        
        for(int i = 0; i < ps.length - 1; i++ ) {
            ps[i + 1] = p[lo + i];
        }
        
        for(int i = 1; i < ps.length; i++) {
            for(int j = i - 1; j >= 0; j--) {
                if(ps[j].compareTo(ps[j+1]) > 0 ) {
                    exch(ps, j, j+1);
                } else break;
            }
        }
        
        poin[0] = ps[0];
        poin[1] = ps[ps.length -1];
        return poin;
    }
    
    private void exch(Point[] ps, int le, int ri){
        Point temp = ps[le];
        ps[le] = ps[ri];
        ps[ri] = temp;
    }
    
    public           int numberOfSegments() {
        return top;
    }
    
    public LineSegment[] segments() {
        LineSegment[] sg = new LineSegment[top];
        
        for(int i = 0 ; i < top; i++ ){
            sg[i] = segments[i];
        }
        
        return sg;
    }
    
    private boolean isSameLine( Point p1, Point p2 ) {       
        return c.compare(p1, p2) == 0;
    }
    
    private void validatePoint(Point[] ps){
        int len = ps.length;
        
        for(int i = 0; i < len; i++){
            if(ps[i] == null ) throw new java.lang.IllegalArgumentException ();
        }

        for(int i = 0; i < len; i++) {
            for(int j = i + 1; j < len; j++) {
                if(ps[i].compareTo(ps[j]) == 0) 
                    throw new java.lang.IllegalArgumentException ();
            }
        }
    }
    
    public static void main(String[] args) {
        Point[] points = new Point[13];

        points[3] = new Point(1000, 1000);
        points[2] = new Point(3000, 3000);
        points[1] = new Point(4000, 4000);
        points[0] = new Point(2000, 2000);
        points[4] = new Point(6000, 6000);
        points[5] = new Point(5000, 5000);
        points[7] = new Point(7000, 7000);
        points[6] = new Point(8000, 8000);
        points[8] = new Point(9000, 9000);
        points[9] = new Point(14407, 10367);
        points[11] = new Point(14407, 17188);
        points[10] = new Point(14407, 17831);
        points[12] = new Point(14407, 19953);
        FastCollinearPoints fcp = new FastCollinearPoints(points);
        LineSegment[] sg = fcp.segments();
        for(int i = 0; i < sg.length; i++){
            StdOut.println(sg[i]);
        }
    }   
    
}
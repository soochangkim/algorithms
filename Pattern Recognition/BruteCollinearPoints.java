import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;


public class BruteCollinearPoints {
    
    private int top = 0;
    private LineSegment[] segments;
    
    public BruteCollinearPoints ( Point[] points ) {
        int i,j,k,l;
        
        if(points == null ) throw new java.lang.IllegalArgumentException ();

        validatePoint(points);
       
        LineSegment[] temp = new LineSegment[points.length];

        for ( i = points.length - 1; i >= 3; i-- ) {    
            
            for ( j = i - 1; j >= 2; j-- ) { 
                                        
                for ( k = j - 1; k >= 1; k-- ) {
                    
                    if( !isSameLine (points[i], points[j], points[k] )) continue;
                    
                    for ( l = k - 1; l >= 0; l-- ) { 
                        
                        if( !isSameLine (points[j], points[k], points[l] ) ) continue;
                        
                        temp[top++] = setSegment(points[i], points[l], points[k], points[j]);
                    }
                }
            }
        }
        
        segments = new LineSegment[top];
        
        for(i = 0; i < top ; i ++){
            segments[i] = temp[i];
        }
    } 
    
    private LineSegment setSegment (Point p1, Point p2, Point p3, Point p4){
        LineSegment sg;
        Point[] ps = new Point[4];
        ps[0] = p1;
        ps[1] = p2;
        ps[2] = p3;
        ps[3] = p4;
        
        for(int i = 1; i < ps.length; i++) {
            for(int j = i - 1; j >= 0; j--) {
                if(ps[j].compareTo(ps[j+1]) > 0 ) {
                    exch(ps, j, j+1);
                } else break;
            }
        }
        
        sg = new LineSegment(ps[0], ps[3]);
        
        return sg;            
    }
    private void exch(Point[] ps, int le, int ri){
        Point temp = ps[le];
        ps[le] = ps[ri];
        ps[ri] = temp;
    }
    
    public           int numberOfSegments () {    
        return top;
    }       
    
    public LineSegment[] segments () {
        
        int len = segments.length;
        LineSegment[] sg = new LineSegment[len];
        
        for(int i = 0; i < len; i++){
            sg[i] = segments[i];
        }
        return sg;
    } 
    
    private boolean isSameLine( Point origin, Point p1, Point p2 ) {
        Comparator<Point> c = origin.slopeOrder();
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
        Point[] points = new Point[4];

        points[0] = new Point(9000, 6000);
        points[1] = new Point(13000, 0);
        points[2] = new Point(11000, 3000);
        points[3] = new Point(13000, 10);
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        LineSegment[] ls = bcp.segments();
        for(int i = 0; i < ls.length; i++){
            StdOut.println(ls[i]);
        }
    }        
}
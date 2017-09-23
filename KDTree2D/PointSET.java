import edu.princeton.cs.algs4.*;

import java.util.Iterator;

public class PointSET {

    private SET<Point2D> points;

    public PointSET() {
        this.points = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return this.points.isEmpty();
    }

    public int size() {
        return this.points.size();
    }

    public void insert(Point2D p) {
        validate(p);
        points.add(p);
    }

    public boolean contains(Point2D p) {
        validate(p);
        return points.contains(p);
    }

    public void draw() {
        Iterator<Point2D> it = points.iterator();
        Point2D p;
        while(it.hasNext()){
            p = it.next();
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {

        Iterator<Point2D> it;
        Stack<Point2D> stack = new Stack<Point2D>();
        Point2D p;
        double x,y;

        validate(rect);
        it = points.iterator();

        while(it.hasNext()){

            p = it.next();
            x = p.x();
            y = p.y();

            if(x <= rect.xmax() && x >= rect.xmin()
                    && y <= rect.ymax() && y >= rect.ymin() ){
                stack.push(p);
            }
        }

        return stack;
    }

    public Point2D nearest(Point2D p) {
        Iterator<Point2D> it;
        Point2D nearest, temp;
        double x,y, dist, tempDist;

        validate(p);

        if(this.points.size() == 0 ) return null;

        it = points.iterator();
        nearest = it.next();

        dist = nearest.distanceTo(p);
        while(it.hasNext()){
            temp = it.next();
            tempDist = temp.distanceTo(p);

            if(tempDist < dist){
                nearest = temp;
                dist = tempDist;
            }
        }

        return nearest;
    }

    private void validate(Point2D p){
        if(p == null) throw new java.lang.IllegalArgumentException();
    }

    private void validate(RectHV rect){
        if(rect == null) throw new java.lang.IllegalArgumentException();
    }


    public static void main(String[] args) {

    }
}

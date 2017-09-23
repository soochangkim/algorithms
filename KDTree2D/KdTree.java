import edu.princeton.cs.algs4.*;

import java.util.Iterator;

public class KdTree {

    private class Node {
        private Point2D p;
        private Node lt;
        private Node rb;
    }

    private Node root;
    private int size;

    public KdTree() {
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void insert(Point2D p) {

        validate(p);
        boolean isX = true;
        int re;
        Node cur, n;

        n = new Node();
        n.p = new Point2D(p.x(), p.y());
        if (this.size == 0) {

            this.root = n;

        } else {

            cur = this.root;

            while (cur != null) {

                re = compare(cur, n, isX);

                if (re == 0) {
                    return;

                } else if (re > 0) {

                    if (cur.lt == null) {
                        cur.lt = n;
                        break;
                    } else cur = cur.lt;

                } else {

                    if (cur.rb == null) {
                        cur.rb = n;
                        break;
                    } else cur = cur.rb;
                }

                isX ^= true;
            }
        }
        this.size++;
    }

    private boolean equals(Node n1, Node n2) {
        return n1.p.x() == n2.p.x() && n1.p.y() == n2.p.y();
    }

    private int compare(Node n1, Node n2, boolean isX) {

        if (equals(n1, n2)) return 0;
        else if (isX) {
            return n1.p.x() >= n2.p.x() ? 1 : -1;
        } else {
            return n1.p.y() >= n2.p.y() ? 1 : -1;
        }

    }

    public boolean contains(Point2D p) {

        validate(p);
        Node n = new Node();
        n.p = new Point2D(p.x(), p.y());
        Node cur = this.root;
        int re;
        boolean isX = true;
        if (this.size == 0) return false;

        while (cur != null) {

            re = compare(cur, n, isX);
            if (re == 0) {
                break;
            } else if (re > 0) {
                cur = cur.lt;
            } else {
                cur = cur.rb;
            }
            isX ^= true;
        }

        return cur == null ? false : true;
    }

    public void draw() {

    }

    public Iterable<Point2D> range(RectHV rect) {
        validate(rect);
        Stack<Point2D> s = new Stack<Point2D>();
        range(this.root, rect, s, true);
        return s;
    }

    private void range(Node n, RectHV rect, Stack<Point2D> stack, boolean isX) {

        if (n == null) return;
        if (rect.contains(n.p)) stack.push(n.p);
        double x, y;
        x = n.p.x();
        y = n.p.y();

        if (isX) {
            if (x >= rect.xmin()) range(n.lt, rect, stack, !isX);
            if (x <= rect.xmax()) range(n.rb, rect, stack, !isX);
        } else {
            if (y >= rect.ymin()) range(n.lt, rect, stack, !isX);
            if (y <= rect.ymax()) range(n.rb, rect, stack, !isX);
        }
    }

    public Point2D nearest(Point2D p) {
        validate(p);
        Node n = new Node();
        n.p = new Point2D(p.x(), p.y());
        RectHV bound = new RectHV(0, 0, 1, 1);
        Node result = nearest(this.root, n, Double.MAX_VALUE, true, bound);
        return result == null ? null : result.p;
    }

    private RectHV getLTBound(RectHV bound, Node n, boolean isX) {
        return isX ?
                new RectHV(
                        bound.xmin(),
                        bound.ymin(),
                        n.p.x(),
                        bound.ymax()
                ) :
                new RectHV(
                        bound.xmin(),
                        bound.ymin(),
                        bound.xmax(),
                        n.p.y()
                );
    }

    private RectHV getRBBound(RectHV bound, Node n, boolean isX) {
        return isX ?
                new RectHV(
                        n.p.x(),
                        bound.ymin(),
                        bound.xmax(),
                        bound.ymax()
                ) :
                new RectHV(
                        bound.xmin(),
                        n.p.y(),
                        bound.xmax(),
                        bound.ymax()
                );
    }

    private Node nearest(Node n1, Node n2, double d, boolean isX, RectHV bound) {

        if (n1 == null || n1.p == null) return null;
        int re = compare(n1, n2, isX);
        double distance = n1.p.distanceSquaredTo(n2.p), tempDist;

        Node pos, cur = n1;
        RectHV lt = getLTBound(bound, n1, isX);
        RectHV rb = getRBBound(bound, n1, isX);

        if (distance < d){
            d = distance;
        }

        if (re > 0) {
            pos = nearest(n1.lt, n2, d, !isX, lt);
            if (pos != null) {
                tempDist = pos.p.distanceSquaredTo(n2.p);
                if (tempDist < d) {
                    d = tempDist;
                    cur = pos;
                }
            }

            if (rb.distanceSquaredTo(n2.p) < d) {
                pos = nearest(n1.rb, n2, d, !isX, rb);
                if (pos != null) {
                    tempDist = pos.p.distanceSquaredTo(n2.p);
                    if (tempDist < d) {
                        d = tempDist;
                        cur = pos;
                    }
                }
            }


        } else if (re < 0) {
            pos = nearest(n1.rb, n2, d, !isX, rb);
            if (pos != null) {
                tempDist = pos.p.distanceSquaredTo(n2.p);
                if (tempDist < d) {
                    d = tempDist;
                    cur = pos;
                }
            }

            if (lt.distanceSquaredTo(n2.p) < d) {
                pos = nearest(n1.lt, n2, d, !isX, lt);
                if (pos != null) {
                    tempDist = pos.p.distanceSquaredTo(n2.p);
                    if (tempDist < d) {
                        d = tempDist;
                        cur = pos;
                    }
                }
            }
        }

        return cur;
    }
    private void validate(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
    }

    private void validate(RectHV rect) {
        if (rect == null) throw new java.lang.IllegalArgumentException();
    }

    public static void main(String[] args) {
        KdTree kt = new KdTree();

//        kt.insert(new Point2D(0.3125, 0.7734375));
//        kt.insert(new Point2D(0.796875, 0.7890625));
//        kt.insert(new Point2D(0.9296875, 0.2734375));
//        kt.insert(new Point2D(0.5625, 0.5859375));
//
//        StdOut.println(kt.nearest(new Point2D(0.296875, 0.109375)));

        Point2D p1, p2, p3;
        p1 = new Point2D(0.1375732421875, 0.08056640625);
        p2 = new Point2D(0.0850830078125, 0.461669921875);
        p3 = new Point2D(0.010009765625, 0.045654296875);
        kt.insert(new Point2D(0.0850830078125, 0.461669921875));
        kt.insert(new Point2D(0.1375732421875, 0.08056640625));
        kt.insert(new Point2D(0.5977783203125, 0.3182373046875));
        kt.insert(new Point2D(0.2193603515625, 0.8135986328125));
        StdOut.println(p1.distanceSquaredTo(p3));
        StdOut.println(p2.distanceSquaredTo(p3));
        StdOut.println(kt.nearest(new Point2D(0.010009765625, 0.045654296875)));
    }
}


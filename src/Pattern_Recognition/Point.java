//package Pattern_Recognition;

import java.util.Comparator;


/**
 * Created by Dim on 09.02.2015.
 */
public class Point implements Comparable<Point> {

    // compare points by slope
    //The SLOPE_ORDER comparator should compare points by the slopes they make with
    // the invoking point (x0, y0). Formally, the point (x1, y1) is less than the point (x2, y2)
    // if and only if the slope (y1 − y0) / (x1 − x0) is less than the slope (y2 − y0) / (x2 − x0).
    // Treat horizontal, vertical, and degenerate line segments as in the slopeTo() method.
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            if (o1 == null || o2 == null) throw new NullPointerException();

            double slope1 = Point.this.slopeTo(o1);
            double slope2 = Point.this.slopeTo(o2);

            return slope1 < slope2 ? -1 : slope1 == slope2 ? 0 : 1 ;
        }
    };       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (that == null) throw new NullPointerException();

        if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;
        if (this.x == that.x) return Double.POSITIVE_INFINITY;
        if (this.y == that.y) return +0;
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    // the invoking point (x0, y0) is less than the argument point (x1, y1)
    // if and only if either y0 < y1 or if y0 = y1 and x0 < x1
    public int compareTo(Point that) {
        if (that == null) throw new NullPointerException();

        if (this.y > that.y || (this.y == that.y && this.x > that.x)) return 1;
        if (this.y == that.y && this.x == that.x) return 0;
        return -1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point o1 = new Point(1, 1);
        o1.draw();
        Point o2 = new Point(100, 100);
        o2.draw();
        o1.drawTo(o2);
        double dob = o1.slopeTo(o2);
        System.out.println(dob);
    }
}
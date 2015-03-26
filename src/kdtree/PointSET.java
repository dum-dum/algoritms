import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Dim on 27.02.2015.
 */
public class PointSET {
    private TreeSet<Point2D> set;

    public PointSET()                               // construct an empty set of points
    {
        set = new TreeSet<Point2D>();
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return set.isEmpty();
    }

    public int size()                         // number of points in the set
    {
        return set.size();
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null) throw new NullPointerException();
        set.add(p);
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if (p == null) throw new NullPointerException();
        return set.contains(p);
    }

    public void draw()                         // draw all points to standard draw
    {
        for (Point2D point : set) {
            point.draw();
        }
        StdDraw.show(0);
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
    {
        if (rect == null) throw new NullPointerException();
        // обрезка по у сверху и снизу
        TreeSet<Point2D> setTemp = (TreeSet) set.tailSet(new Point2D(rect.xmin(), rect.ymin()), true);
        setTemp = (TreeSet) setTemp.headSet(new Point2D(rect.xmax(), rect.ymax()), true);

        List<Point2D> result = new ArrayList<>();
        for (Point2D point : setTemp) {
            if (point.x() >= rect.xmin() && point.x() <= rect.xmax())
            {
                result.add(point);
            }
        }
        return result;
    }

    public Point2D nearest(Point2D p)           // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null) throw new NullPointerException();
        if (set.isEmpty()) return null;
        Point2D nearestPoint = set.first();
        for (Point2D point : set){
            if(point.distanceTo(p) < nearestPoint.distanceTo(p)) nearestPoint = point;
        }
        return nearestPoint;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        PointSET pointSet = new PointSET();
        for (int i = 0; i < 100; i++) {
            for (int ii = 0; ii < 100; ii++) {
                Point2D p = new Point2D((double)i/100, (double)ii/100);
                pointSet.insert(p);
                System.out.println(p);
            }
        }
        System.out.println(pointSet.size());
        RectHV rectHV = new RectHV(0.1, 0.5, 0.4, 0.9);
        List<Point2D> list =(List<Point2D>) pointSet.range(rectHV);
        PointSET pointSet2 = new PointSET();
        for (Point2D p : list) {
            pointSet2.insert(p);
        }
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        pointSet2.draw();


    }
}
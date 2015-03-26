import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dim on 27.02.2015.
 */
public class KdTree {

    private Node rootNode;
    private int size = 0;

    public KdTree()                               // construct an empty set of points
    {

    }

    public boolean isEmpty()                      // is the set empty?
    {
        if (size == 0) return true;
        else return false;
    }

    public int size()                         // number of points in the set
    {
       return  size;
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null) throw new NullPointerException();

        if (isEmpty()) rootNode = new Node(p, true, new RectHV(0, 0, 1, 1)); //if it  the first element, greate rootNode
        else insert(rootNode, p);
    }

    private void insert(Node node, Point2D point)
    {
        if(node == null || point == null) throw new NullPointerException();

        int compare = node.compareToPoint(point);

        if (compare > 0) {
            if (node.rightChild == null) node.rightChild = new Node(point, !node.axis, node.rightRect);
            else insert(node.rightChild, point);
        }
        else if (compare < 0) {
            if (node.leftChild == null) node.leftChild = new Node(point, !node.axis, node.leftRect);
            else insert(node.leftChild, point);
        }
        else {
            if(node.point.equals(point)) return;
            if (node.leftChild == null) node.leftChild = new Node(point, !node.axis, node.leftRect);
            else insert(node.leftChild, point);
        }

    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if (p == null || isEmpty()) return false;
        return contains(rootNode, p);
    }

    private boolean contains(Node node, Point2D point)
    {
        if (node == null || point == null) throw new NullPointerException();

        if(node.point.x() == point.x() && node.point.y() == point.y()) return true;

        int compare = node.compareToPoint(point);

        if (compare > 0) {
            if (node.rightChild == null) return false;
            else return contains(node.rightChild, point);
        }
        else {
            if (node.leftChild == null) return false;
            else return contains(node.leftChild, point);
        }
    }

    public void draw()                         // draw all points to standard draw
    {
        if (isEmpty()) return;

        Queue<Node> queueNodes = new Queue<>();
        queueNodes.enqueue(rootNode);

        while (!queueNodes.isEmpty()){
            Node node = queueNodes.dequeue();
            node.draw();
            if (node.leftChild != null) queueNodes.enqueue(node.leftChild);
            if (node.rightChild != null) queueNodes.enqueue(node.rightChild);
        }
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
    {
        if (rect == null) throw new NullPointerException();

        List<Point2D> list = new ArrayList<>();
        Queue<Node> queueNodes = new Queue<>();
        queueNodes.enqueue(rootNode);

        while(!queueNodes.isEmpty()){
            Node node = queueNodes.dequeue();
            int compare = node.compareToRectangle(rect);
            if (compare < 0){
                if (node.leftChild != null)
                    queueNodes.enqueue(node.leftChild);
            } else if (compare > 0){
                if (node.rightChild != null)
                    queueNodes.enqueue(node.rightChild);
            } else {
                if (node.leftChild != null)
                    queueNodes.enqueue(node.leftChild);
                if (node.rightChild != null)
                    queueNodes.enqueue(node.rightChild);
                if (rect.contains(node.point)) {
                    list.add(node.point);
                }
            }
        }
        return list;
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null) throw new NullPointerException();
        if (isEmpty()) return null;

        Node nearestNode = rootNode;
        Stack<Node> stackNodes= new Stack<>();
        stackNodes.push(rootNode);

        while (!stackNodes.isEmpty()){
            Node node = stackNodes.pop();
            if (node.point.distanceTo(p) < nearestNode.point.distanceTo(p)) {
                nearestNode = node;
            }
            int compare = node.compareToPoint(p);
            if(compare >= 0) {
                if (node.leftChild != null &&
                        nearestNode.point.distanceTo(p) >= node.line.distanceTo(p)) stackNodes.push(node.leftChild);
                if (node.rightChild != null) stackNodes.push(node.rightChild);
            }  else {
                if (node.rightChild != null &&
                        nearestNode.point.distanceTo(p) >= node.line.distanceTo(p)) stackNodes.push(node.rightChild);
                if (node.leftChild != null) stackNodes.push(node.leftChild);
            }
        }
        return nearestNode.point;
    }

    public static void main(String[] args)
    {

    }

    private class Node {

        Node leftChild;
        Node rightChild;
        final RectHV leftRect;
        final RectHV rightRect;
        final RectHV parentRect;
        final boolean axis;  // true - x; false - y
        final Point2D  point;
        RectHV line;


        public Node(Point2D point, boolean axis, RectHV parentRect) {
            this.point = point;
            this.axis = axis;
            size++;
            this.parentRect = parentRect;
            if (axis){
                rightRect = new RectHV(parentRect.xmin(), parentRect.ymin(), point.x(), parentRect.ymax());
                leftRect = new RectHV(point.x(), parentRect.ymin(), parentRect.xmax(), parentRect.ymax());
                line = new RectHV(point.x(), parentRect.ymin(), point.x(), parentRect.ymax());
            }  else{
                rightRect = new RectHV(parentRect.xmin(), parentRect.ymin(), parentRect.xmax(), point.y());
                leftRect = new RectHV(parentRect.xmin(), point.y(), parentRect.xmax(), parentRect.ymax());
                line = new RectHV(parentRect.xmin(), point.y(), parentRect.xmax(), point.y());
            }
        }

        void draw()
        {
            this.point.draw();
            StdDraw.setPenRadius(.001);
            if (axis) StdDraw.setPenColor(StdDraw.RED);
            else StdDraw.setPenColor(StdDraw.BLUE);
            line.draw();
            StdDraw.setPenRadius(.01);
            StdDraw.setPenColor(StdDraw.BLACK);
        }

        // compare
        int compareToPoint(Point2D point) {
            if (point == null) throw new NullPointerException();

            if (this.axis){  // if axis x
                return this.point.x() > point.x() ? 1 : this.point.x() < point.x() ? -1 : 0;
            }
            else {           // if axis y
                return this.point.y() > point.y() ? 1 : this.point.y() < point.y() ? -1 : 0;
            }
        }
        // if node axis crosses rectangle return 0
        // if node axis up-left rectangle return 1 todo:fix   проверить условие
        // if node axis down-right rectangle return -1  todo:fix   проверить условие
        int compareToRectangle(RectHV rect){
            if (rect == null) throw new NullPointerException();

            if (this.axis){  //if x
                if (this.point.x() < rect.xmin()) return -1;
                else if (this.point.x() > rect.xmax()) return 1;
                else return 0;
            }
            else{            //if y
                if (this.point.y() < rect.ymin()) return -1;
                if (this.point.y() > rect.ymax()) return 1;
                else return 0;
            }
        }
    }
}

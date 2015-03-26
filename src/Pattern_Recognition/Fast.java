//package Pattern_Recognition;

import java.util.Arrays;

/**
 * Created by Dim on 09.02.2015.
 */
public class Fast {

    private static Point[] points;

    static {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);
    }

    public static void main(String[] args) {

        if (args.length != 1) return;

        readFile(args[0]);

        findCollinears(points);
    }

    private static void findCollinears(Point[] points) {

        for (int i = 0; i < points.length - 1; i++) {

            Arrays.sort(points, i, points.length, points[i].SLOPE_ORDER);

            for (int j = i + 1; j < points.length; j++) {

                int count2 = 0;
                while (j < points.length - 1 &&
                        points[i].slopeTo(points[j]) == points[i].slopeTo(points[j + 1])) {
                    j++;
                    count2++;
                }
                //if collinear  , print and draw
                if (count2 >= 2) {
                    Point[] collinear = new Point[count2 + 2];

                    collinear[0] = points[i];
                    for (int k = 1; k < collinear.length; k++) {
                        collinear[k] = points[j - k + 1];
                    }
                    Arrays.sort(collinear);
                    print(collinear);
                    collinear[0].drawTo(collinear[collinear.length - 1]);
                }
            }
        }
        StdDraw.show(0);
    }

    private static void readFile(String file){

        In in = new In(file);

        int count = 0;
        //read size
        if (!in.isEmpty()) count = in.readInt();

        points = new Point[count];
        //read file
        for (int i = 0; i < count; i++) {
            if (in.isEmpty()) break;
            int x = in.readInt();
            if (in.isEmpty()) break;
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }
        StdDraw.show(0);
    }

    private static void print (Point[] points) {
        for (int k = 0; k < points.length - 1; k++) {
            System.out.print(points[k] + " -> ");;
        }
        System.out.println(points[points.length - 1]);
    }

}

import java.util.Arrays;

//package Pattern_Recognition;
;

/**
 * Created by Dim on 09.02.2015.
 */
public class Brute {

    private static Point[] points;

    public static void main(String[] args) {

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);

        if (args.length != 1) return;

        In in = new In(args[0]);

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
        // find collinear
        for (int i = 0; i < points.length; i++) {
            for (int ii = i + 1; ii < points.length; ii++) {
                for (int iii = ii + 1; iii < points.length; iii++) {
                    for (int iiii = iii + 1; iiii < points.length; iiii++) {
                        //check isColinear
                        if (points[i].slopeTo(points[ii]) == points[i].slopeTo(points[iii]) &&
                                points[i].slopeTo(points[ii]) == points[i].slopeTo(points[iiii]))
                        {
                            Point[] linePoints = new Point[]{points[i], points[ii], points[iii], points[iiii]};
                            Arrays.sort(linePoints);
                            linePoints[0].drawTo(linePoints[3]);
                            StdDraw.show(0);

                            System.out.println(String.format("%s -> %s -> %s -> %s ",
                                    linePoints[0], linePoints[1], linePoints[2], linePoints[3]));

                        }
                    }
                }
            }
        }
    }

}

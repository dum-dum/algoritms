//package Pattern_Recognition;

public class PointTest {

    @org.junit.Test
    public void test(){
        Point o1 = new Point(1, 1);
        Point o2 = new Point(7, 0);
        double dob = o1.slopeTo(o2);
        System.out.println(dob);
    }


}
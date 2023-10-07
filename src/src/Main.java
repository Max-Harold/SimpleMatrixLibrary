import maxharold.matrix.*;
public class Main {
    public static void main(String[] args) {
        Vector a = new Vector(1,2,3);
        Vector b = new Vector(3,2,1);
        Vector c = a.crossProduct(b);
        System.out.println(Math.toDegrees(c.angleBetween(b)));
        System.out.println(Math.toDegrees(c.angleBetween(a)));
    }
}
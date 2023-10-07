package maxharold.matrix;

import static java.lang.Math.*;

/**
 * Class used for rotating matrices and vectors
 */
public class RotUtil {

    /**
     * Method that generates 2D Rotation Matrix
     * @param theta the angle of the rotation in radians
     * @return The 2D rotation matrix
     */
    public static Matrix rotationMatrix2D(double theta) {
        return new Matrix(new double[][]{
                new double[]{cos(theta), -sin(theta)},
                new double[]{sin(theta), cos(theta)}
        });
    }

    /**
     * Method that rotates a vector by some angle (returns null if invalid)
     * @param v the vector to rotate
     * @param theta the angle to rotate the vector by
     * @return the rotated vector
     */
    public static Vector rotate2DVector(Vector v, double theta) {
        if (v.dim() != 2) return null;
        return rotationMatrix2D(theta).transformVector(v);
    }


}

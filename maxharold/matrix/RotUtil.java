package maxharold.matrix;

import static java.lang.Math.*;

/**
 * Class used for rotating vectors
 */
public class RotUtil {

    /**
     * Method that generates 2D Rotation Matrix
     * @param theta the angle of the rotation (in radians)
     * @return The 2D rotation matrix
     */
    public static Matrix rotationMatrix2D(double theta) {
        return new Matrix(new double[][]{
                new double[]{Math.cos(theta), -Math.sin(theta)},
                new double[]{Math.sin(theta), Math.cos(theta)}
        });
    }

    /**
     * Method that rotates a vector by some angle (returns null if invalid)
     * @param v the vector to rotate
     * @param theta the angle to rotate the vector by (in radians)
     * @return the rotated vector
     */
    public static Vector rotate2DVector(Vector v, double theta) {
        if (v.dim() != 2) return null;
        return rotationMatrix2D(theta).transformVector(v);
    }

    /**
     * Method that performs yaw rotation to a 3D vector (returns null if invalid)
     * @param v the vector to rotate
     * @param yaw the angle to yaw vector by (in radians)
     * @return the rotated vector
     */
    public static Vector yaw(Vector v, double yaw) {
        if (v.dim() != 3) return null;
        return new Matrix(
                new Vector(Math.cos(yaw), 0, -Math.sin(yaw)),
                new Vector(0, 1, 0),
                new Vector(Math.sin(yaw), 0, Math.cos(yaw))
        ).transformVector(v);
    }

    /**
     * Method that performs pitch rotation to a 3D vector (returns null if invalid)
     * @param v the vector to rotate
     * @param pitch the angle to pitch vector by (in radians)
     * @return the rotated vector
     */
    public static Vector pitch(Vector v, double pitch) {
        if (v.dim() != 3) return null;
        return new Matrix(

                new Vector(1, 0, 0),
                new Vector(0, Math.cos(pitch), -Math.sin(pitch)),
                new Vector(0, Math.sin(pitch), Math.cos(pitch))
        ).transformVector(v);
    }

    /**
     * Method that performs roll rotation to a 3D vector (returns null if invalid)
     * @param v the vector to rotate
     * @param roll the angle to roll vector by (in radians)
     * @return the rotated vector
     */
    public static Vector roll(Vector v, double roll) {
        if (v.dim() != 3) return null;
        return new Matrix(
                new Vector(Math.cos(roll), -Math.sin(roll), 0),
                new Vector(Math.sin(roll), Math.cos(roll), 0),
                new Vector(0, 0, 1)
        ).transformVector(v);
    }


}

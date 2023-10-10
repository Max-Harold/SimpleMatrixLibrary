package maxharold.matrix;
import static java.lang.Math.*;

/**
 * Class for creating vectors and performing vector operations
 */
public class Vector {
    private final double[] vector;
    private final int dim;
    /**
     * Constructs a vector from an array
     * @param elements the vector as an array
     */
    public Vector(double... elements) {
        vector = elements;
        dim = vector.length;
    }

    /**
     * Constructs an empty vector from a specified dimension
     * @param dim the dimension of the vector
     */
    public Vector(int dim) {
        this.dim = dim;
        vector = new double[dim];
    }

    /**
     * Method that returns the dimension of the vector
     * @return the dimension of the vector
     */

    public int dim() {
        return dim;
    }

    /**
     * Method that turns the vector into an array
     * @return the vector as an array
     */

    public double[] vectorToArray() {
        return vector;
    }

    /**
     * Converts vector to column matrix
     * @return the column vector as a matrix
     */
    public Matrix toColumnMatrix() {
        return new Matrix(vector).T();
    }

    /**
     * Gets element at certain index
     * @param i the i-th index of the vector
     * @return the element of the vector (returns null if invalid)
     */
    public Double get(int i) {
        if (i >= dim || i < 0) return null;
        return vector[i];
    }

    /**
     * Sets value of vector at certain index (does nothing if index is invalid)
     * @param i the index to change
     * @param val the value to change it to
     */
    public void setValue(int i, double val) {
        if (i >= 0 && i < dim)  vector[i] = val;
    }

    /**
     * Calculates the magnitude of the vector
     * @return the magnitude
     */
    public double magnitude() {
        double sum = 0.0;
        for (double element : vector) {
            sum += element * element;
        }
        return Math.sqrt(sum);
    }

    /**
     * Scales all elements of vector by some scalar
     * @param scalar the scalar
     * @return the scaled vector
     */
    public Vector scale(double scalar) {
        Vector current = this;
        int i = 0;
        for (double val : current.vectorToArray()) {
            current.setValue(i++, val * scalar);
        }
        return current;
    }

    /**
     * Converts current vector to unit vector of magnitude 1
     * @return the unit vector
     */
    public Vector unitVector() {
        return new Vector(vectorToArray()).scale(1 / magnitude());
    }

    /**
     * Performs dot product with two vectors
     * @param v1 first vector
     * @param v2 second vector
     * @return the dot product of v1 and v2 (returns null if invalid)
     */
    public static Double dot(Vector v1, Vector v2) {
        if (v1.dim() != v2.dim()) return null;
        double[] v1Array = v1.vectorToArray();
        double[] v2Array = v2.vectorToArray();
        double s = 0;
        for (int i = 0; i < v1.dim(); i++) {
            s += v1Array[i] * v2Array[i];
        }
        return s;
    }

    /**
     * Performs dot product with current instance of vector and another one
     * @param v2 the second vector
     * @return the dot product (returns null if invalid)
     */
    public Double dot(Vector v2) {
        if (v2.dim() != dim) return null;
        return dot(this, v2);
    }

    /**
     * Performs cross product of two 3D vectors
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the cross product (returns null if invalid)
     */
    public static Vector crossProduct(Vector v1, Vector v2) {
        if (v1.dim() != 3 && v2.dim() != 3) return null;

        return new Vector(v1.get(1) * v2.get(2) - v1.get(2) * v2.get(1), v1.get(2) * v2.get(0) - v1.get(0) * v2.get(2), v1.get(0) * v2.get(1) - v1.get(1) * v2.get(0));
    }

    /**
     * Performs cross product with current vector instance and another instance
     * @param v2 the second vector
     * @return the cross product (returns null if invalid)
     */
    public Vector crossProduct(Vector v2) {
        return crossProduct(this, v2);
    }

    /**
     * Calculates the angle between two vectors
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the angle between the two vectors in radians (returns null if invalid)
     */
    public static Double angleBetween(Vector v1, Vector v2) {
        if (v1.dim() != v2.dim()) return null;
        return Math.acos((v1.dot(v2)) / (v1.magnitude() * v2.magnitude()));
    }

    /**
     * Calculates the angle between the current vector instance and another one
     * @param v2 the second vector
     * @return the angle between the two vectors in radians (returns null if invalid)
     */
    public double angleBetween(Vector v2) {
        return angleBetween(this, v2);
    }

    /**
     * Formats vector as a string
     * @return the formatted string
     */
    public String toString() {
        StringBuilder retStr = new StringBuilder("[");
        for (int i = 0; i < dim - 1; i++) {
            retStr.append(vector[i]).append(" ");
        }
        retStr.append(vector[dim - 1]).append("]");
        return retStr.toString();
    }
}

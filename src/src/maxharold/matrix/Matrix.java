package maxharold.matrix;

public class Matrix {

    private final double[][] mat;
    private final int rows;
    private final int cols;

    /**
     * Constructs a Matrix from a 2D array
     * @param raw The 2D array that contains the matrix
     */
    public Matrix(double[][] raw) {

        mat = raw;
        rows = raw.length;
        cols = raw[0].length;

    }

    /**
     * Constructs a Matrix from an array that acts as a row vector
     * @param vector The array that represents the row vector
     */
    public Matrix(double[] vector) {
        rows = 1;
        cols = vector.length;
        mat = new double[][]{vector};
    }

    /**
     * Constructs an empty matrix of a specified size
     * @param rows The number of rows of the matrix
     * @param cols The number of columns of the matrix
     */
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        mat = new double[rows][cols];
    }

    /**
     * @return The number of rows in the matrix
     */
    public int getRowLength() {
        return rows;
    }

    /**
     * @return the number of columns in the matrix
     */
    public int getColLength() {
        return cols;
    }

    /**
     * @param i The i-th row of the matrix
     * @return The i-th row of the matrix as an array (returns null if invalid)
     */
    public double[] getRow(int i) {
        if (i < 0 || i > rows) return null;
        return mat[i];
    }

    /**
     * @param i i-th column of the matrix
     * @return The i-th column of the matrix as an array (returns null if invalid)
     */
    public double[] getCol(int i) {
        if (i < 0 || i > cols) return null;
        double[] col = new double[rows];
        for (int row = 0; row < rows; row++) {
            col[row] = mat[row][i];
        }
        return col;
    }

    /**
     * @param row The row the desired element lies in
     * @param col The column the desired element lies in
     * @return The desired element of the matrix
     */
    public double getElement(int row, int col) {
        return mat[row][col];
    }

    /**
     * Sets the value of one of the elements of the matrix
     * @param val The replacing value
     * @param i The i-th row the value lies in
     * @param j The i-th column the value lies in
     */
    public void setElement(double val, int i, int j) {
        mat[i][j] = val;
    }

    /**
     * Puts current matrix through matrix multiplication with a column vector (returns null if invalid)
     * @param v the vector
     * @return the transformed vector
     */
    public Vector transformVector(Vector v) {
        if (v.dim() != rows) return null;
        return new Vector(mul(v.toColumnMatrix()).getCol(0));
    }

    /**
     * @param a The first vector
     * @param b The second vector
     * @return The dot product of the two vectors (returns null if invalid)
     */
    public Double dotVectors(double[] a, double[] b) {
        if (a.length != b.length) return null;
        double dot = 0.0;
        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
        }
        return dot;
    }

    /**
     * Performs matrix multiplication on two matrices (returns null if invalid)
     * @param m1 The first matrix
     * @param m2 The second matrix
     * @return m1 * m2
     */
    public static Matrix mul(Matrix m1, Matrix m2) {
        if (m1.getColLength() != m2.getRowLength()) return null;

        Matrix newMatrix = new Matrix(m1.getRowLength(), m2.getColLength());

        for (int row = 0; row < m1.getRowLength(); row++) {
            double[] rowVector = m1.getRow(row);
            for (int col = 0; col < m2.getColLength(); col++) {
                double[] colVector = m2.getCol(col);
                double val = m1.dotVectors(rowVector, colVector);
                newMatrix.setElement(val, row, col);
            }
        }
        return newMatrix;
    }

    /**
     * Performs matrix multiplication with the current matrix instance as the first matrix
     * @param m2 The second matrix
     * @return m1 * m2
     */
    public Matrix mul(Matrix m2) {
        return mul(this, m2);
    }

    /**
     * Transposes the current matrix (without changing the original matrix instance)
     * @return The transposed matrix
     */

    /**
     * Adds one matrix to another (returns null if invalid)
     * @param m1 the first matrix
     * @param m2 the second matrix
     * @return m1 + m2
     */
    public static Matrix add(Matrix m1, Matrix m2) {
        if (m1.getColLength() != m2.getColLength() || m1.getRowLength() == m2.getRowLength()) return null;
        int r = m1.getRowLength();
        int c = m1.getColLength();
        Matrix newMat = new Matrix(r,c);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                newMat.setElement(m1.getElement(i,j) + m2.getElement(i,j), i, j);
            }
        }
        return newMat;
    }

    /**
     * Adds one matrix to another with the current matrix instance as the first matrix
     * @param m2 the second matrix
     * @return current_matrix + m2
     */
    public Matrix add(Matrix m2) {
        return add(this, m2);
    }

    /**
     * Subtracts one matrix from another (returns null if invalid)
     * @param m1 the first matrix
     * @param m2 the second matrix
     * @return m1 - m2
     */
    public static Matrix subtract(Matrix m1, Matrix m2) {
        if (m1.getColLength() != m2.getColLength() && m1.getRowLength() != m2.getRowLength()) return null;
        int r = m1.getRowLength();
        int c = m1.getColLength();
        Matrix newMat = new Matrix(r,c);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                newMat.setElement(m1.getElement(i,j) - m2.getElement(i,j), i, j);
            }
        }
        return newMat;
    }

    /**
     * Subtracts one matrix from another with the current matrix instance as the first matrix
     * @param m2 the second matrix
     * @return current_matrix - m2
     */
    public Matrix subtract(Matrix m2) {
        return add(this, m2);
    }

    /**
     * Transposes the current matrix
     * @return the transposed matrix
     */
    public Matrix T() {
        Matrix newMatrix = new Matrix(cols, rows);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                newMatrix.setElement(getElement(r,c), c, r);
            }
        }
        return newMatrix;
    }

    /**
     * @return The matrix formatted as a string
     */
    public String toString() {
        StringBuilder retStr = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            retStr.append("[");
            for (int c = 0; c < cols; c++) {
                retStr.append(mat[r][c]);
                if (c != cols-1) retStr.append(" ");
            }
            retStr.append("]\n");
        }
        return retStr.toString();
    }
}
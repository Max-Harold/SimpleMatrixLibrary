package maxharold.matrix;

import java.util.Objects;

/**
 * Class used for creating matrices and performing matrix operations
 */
public class Matrix {

    private final double[][] mat;
    private final Integer rows;
    private final Integer cols;

    /**
     * Constructs a Matrix from a 2D array (makes the matrix null if invalid)
     * @param raw The 2D array that contains the matrix
     */
    public Matrix(double[][] raw) {

        if (isValidMatrix(raw)) {
            mat = raw;
            rows = raw.length;
            cols = raw[0].length;
        } else {
            mat = null;
            rows = null;
            cols = null;
        }
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
     * Constructs a matrix from specified rows as vectors (makes the matrix null if invalid)
     * @param rows the vectors that make up the rows
     */
    public Matrix(Vector... rows) {
        Integer theRows = rows.length;
        Integer theCols = rows[0].dim();

        double[][] theMat = new double[theRows][theCols];

        int index = 0;
        for (Vector row : rows) {
            if (row.dim() != theCols) {
                theMat = null;
                theRows = null;
                theCols = null;
                break;
            }
            theMat[index++] = row.vectorToArray();
        }
        this.rows = theRows;
        this.cols = theCols;
        this.mat = theMat;
    }

    private boolean isValidMatrix(double[][] m) {
        int r = m.length;
        int c = m[0].length;
        for (double[] row : m) {
            if (row.length != c) return false;
        }
        return true;
    }

    /**
     * Method that returns the number of rows in the matrix
     * @return the number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Method that returns the number of columns in the matrix
     * @return the number of columns in the matrix
     */
    public int getCols() {
        return cols;
    }

    /**
     * Returns a row of the matrix as an array
     * @param i The i-th row of the matrix
     * @return The i-th row of the matrix as an array (returns null if invalid)
     */
    public double[] getRow(int i) {
        if (i < 0 || i > rows) return null;
        return mat[i];
    }

    /**
     * Returns a column of the matrix as an array
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
     * Returns the element in a certain row and column of the matrix
     * @param row The row the desired element lies in
     * @param col The column the desired element lies in
     * @return The desired element of the matrix (returns null if invalid)
     */
    public Double getElement(int row, int col) {
        try {
            return mat[row][col];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Sets the value of one of the elements of the matrix (does nothing if coordinates are invalid)
     * @param val The replacing value
     * @param i The i-th row the value lies in
     * @param j The i-th column the value lies in
     */
    public void setElement(double val, int i, int j) {
        if (i >= 0 && i < rows && j >= 0 && j < cols) mat[i][j] = val;
    }

    /**
     * Puts current matrix through matrix multiplication with a column vector
     * @param v the vector
     * @return the transformed vector (returns null if invalid)
     */
    public Vector transformVector(Vector v) {
        if (v.dim() != cols) return null;
        return new Vector(mul(v.toColumnMatrix()).getCol(0));
    }

    private Double dotVectors(double[] a, double[] b) {
        if (a.length != b.length) return null;
        double dot = 0.0;
        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
        }
        return dot;
    }

    /**
     * Performs matrix multiplication on two matrices
     * @param m1 The first matrix
     * @param m2 The second matrix
     * @return m1 * m2 (returns null if invalid)
     */
    public static Matrix mul(Matrix m1, Matrix m2) {
        if (m1.getCols() != m2.getRows()) return null;

        Matrix newMatrix = new Matrix(m1.getRows(), m2.getCols());

        for (int row = 0; row < m1.getRows(); row++) {
            double[] rowVector = m1.getRow(row);
            for (int col = 0; col < m2.getCols(); col++) {
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
     * @return m1 * m2 (returns null if invalid)
     */
    public Matrix mul(Matrix m2) {
        return mul(this, m2);
    }

    /**
     * Adds one matrix to another
     * @param m1 the first matrix
     * @param m2 the second matrix
     * @return m1 + m2 (returns null if invalid)
     */
    public static Matrix add(Matrix m1, Matrix m2) {
        if (m1.getCols() != m2.getCols() || m1.getRows() != m2.getRows()) return null;
        int r = m1.getRows();
        int c = m1.getCols();
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
     * @return current_matrix + m2 (returns null if invalid)
     */
    public Matrix plus(Matrix m2) {
        return add(this, m2);
    }

    /**
     * Subtracts one matrix from another
     * @param m1 the first matrix
     * @param m2 the second matrix
     * @return m1 - m2 (returns null if invalid)
     */
    public static Matrix subtract(Matrix m1, Matrix m2) {
        if (m1.getCols() != m2.getCols() || m1.getRows() != m2.getRows()) return null;
        int r = m1.getRows();
        int c = m1.getCols();
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
     * @return current_matrix - m2 (returns null if invalid)
     */
    public Matrix minus(Matrix m2) {
        return subtract(this, m2);
    }

    /**
     * Performs hadamard product of two matrices
     * @param m1 the first matrix
     * @param m2 the second matrix
     * @return m1 ⊙ m2 (returns null if invalid)
     */
    public static Matrix hadamardProduct(Matrix m1, Matrix m2) {
        if (m1.getCols() != m2.getCols() || m1.getRows() != m2.getRows()) return null;
        int r = m1.getRows();
        int c = m1.getCols();
        Matrix newMat = new Matrix(r,c);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                newMat.setElement(m1.getElement(i,j) * m2.getElement(i,j), i, j);
            }
        }
        return newMat;
    }

    /**
     * Performs hadamard product of two matrices with the current instance of the matrix as the first one
     * @param m2 the second matrix
     * @return current_matrix ⊙ m2 (returns null if invalid)
     */
    public Matrix hadamardProduct(Matrix m2) {
        return hadamardProduct(this, m2);
    }

    private Matrix subMatrix(Matrix m, int column) {
        int r = m.getRows();
        int c = m.getCols();
        Matrix newMatrix = new Matrix(r - 1, c - 1);
        int row = 0;

        for (int i = 1; i < r; i++) {
            double[] rowArr = m.getRow(i);
            int col = 0;
            for (int j = 0; j < c; j++) {
                double element = rowArr[j];
                if (j != column) {
                    newMatrix.setElement(rowArr[j], row, col++);
                }
            }
            row++;
        }
        return newMatrix;
    }

    private double det(Matrix m) {
        if (m.getRows() == 2) {
            return m.getElement(0,0) * m.getElement(1,1) - m.getElement(0,1) * m.getElement(1, 0);
        } else {
            double sum = 0;
            for (int col = 0; col < m.getCols(); col++) {
                double element = m.getElement(0, col);
                if (col % 2 != 0) {
                    sum += element * det(subMatrix(m, col));
                } else {
                    sum -= element * det(subMatrix(m, col));
                }
            }
            return sum;
        }
    }

    /**
     * Method that calculate the determinant of a square matrix
     * @return the determinant (returns null if matrix is not square)
     */
    public Double det() {
        if (!Objects.equals(rows, cols)) return null;
        return det(this);
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
     * Converts the matrix to a string format
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
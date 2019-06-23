package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    /**
     * 2D array represents the status of each cell.
     * True means the cell is open and false means the cell is blocked;
     */
    private boolean[][] gridStatus;

    /**
     * Give a N x N grid, boundary equals to N.
     */
    private int boundary;

    /**
     * Represents the total numbers of cells opened.
     */
    private int numberOfOpenGrid;

    /**
     * Helper weighted quick union to connect opened cells.
     */
    private WeightedQuickUnionUF gridConnection;

    /**
     * Duplicated weighted quick union with no dummy bottom.
     */
    private WeightedQuickUnionUF gridNoDummyBottom;

    /**
     * Represents a single dummy cell above the first row.
     * This dummy top is used for return percolation status.
     */
    private int dummyTop;

    /**
     * Represents a single dummy cell below the last row.
     * This dummy bottom is used for return percolation status.
     */
    private int dummyBottom;

    public Percolation(int N) {
        handleException(N);
        gridStatus = new boolean[N][N];
        boundary = N;
        numberOfOpenGrid = 0;

        /**
         * The weighted quick union includes the dummy top and bottom.
         * Therefore, the total items equal to the total cell number plus 2.
         */
        gridConnection = new WeightedQuickUnionUF(N * N + 2);
        gridNoDummyBottom = new WeightedQuickUnionUF(N * N + 1);
        dummyTop = N * N;
        dummyBottom = N * N + 1;
    }

    /**
     * Validate input number.
     */
    private void handleException(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Validate input number.
     */
    private void handleException(int row, int col) {
        if (row >= boundary || col >= boundary || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Set the cell to open by change the status to true.
     *
     * @param row Row of the cell
     * @param col Column of the cell.
     */
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        handleException(row, col);
        gridStatus[row][col] = true;
        numberOfOpenGrid += 1;
        if (row == 0) {
            connectToDummyTop(col);
        }
        if (row == boundary - 1) {
            connectToDummyBottom(col);
        }
        if (numberOfOpenGrid > 1) {
            connectOpenNeighbor(row, col);
        }
    }

    /**
     * Convert row and column to ID that can be used for weighted quick union.
     */
    private int convertToID(int row, int col) {
        return row * boundary + col;
    }

    /**
     * Connect open cell in first row to the dummy top.
     */
    private void connectToDummyTop(int col) {
        gridConnection.union(col, dummyTop);
        gridNoDummyBottom.union(col, dummyTop);
    }

    /**
     * Connect open cell in last row to the dummy top.
     */
    private void connectToDummyBottom(int col) {
        int id = convertToID(boundary - 1, col);
        gridConnection.union(id, dummyBottom);
    }

    /**
     * Helper method to evaluate if a cell has open neighbor.
     * If open neighbor exists, connect it to all open neighbors.
     *
     * @param row Row of the cell
     * @param col Column of the cell.
     */
    private void connectOpenNeighbor(int row, int col) {
        int idCurrent = convertToID(row, col);

        if (row -1 >= 0 && isOpen(row - 1, col)) {
            gridConnection.union(idCurrent, idCurrent - boundary);
            gridNoDummyBottom.union(idCurrent, idCurrent - boundary);
        }
        if (row + 1 < boundary && isOpen(row + 1, col)) {
            gridConnection.union(idCurrent, idCurrent + boundary);
            gridNoDummyBottom.union(idCurrent, idCurrent + boundary);
        }
        if (col - 1 >= 0 && isOpen(row, col - 1)) {
            gridConnection.union(idCurrent, idCurrent - 1);
            gridNoDummyBottom.union(idCurrent, idCurrent - 1);
        }
        if (col + 1 < boundary && isOpen(row, col + 1)) {
            gridConnection.union(idCurrent, idCurrent + 1);
            gridNoDummyBottom.union(idCurrent, idCurrent + 1);
        }
    }

    /**
     * Return status of give a cell.
     *
     * @return True if the cell is open; otherwise false.
     */
    public boolean isOpen(int row, int col) {
        handleException(row, col);
        return gridStatus[row][col];
    }

    /**
     * Evaluate if the cell is filled with water by comparing if it is connected to row 0.
     *
     * @return True if the cell is connected to row 0; otherwise false.
     */
    public boolean isFull(int row, int col) {
        int idCurrent = convertToID(row, col);
        return gridNoDummyBottom.connected(idCurrent, dummyTop);
    }

    /**
     * Return total numbers of open cells.
     */
    public int numberOfOpenSites() {
        return numberOfOpenGrid;
    }

    /**
     * Evaluate if the system can percolate by comparing
     * if the dummy top and bottom are connected.
     */
    public boolean percolates() {
        return gridConnection.connected(dummyTop, dummyBottom);
    }

    public static void main(String[] args) {
        //Keep for Autograding
    }
}

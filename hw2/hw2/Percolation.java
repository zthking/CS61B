package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.stream.IntStream;

public class Percolation {

    /**
     * 2D array represents the ID of each cell.
     */
    private int[][] gridID;

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
        gridID = new int[N][N];
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

        //int temp = 0;
        for (int i = 0; i < N; i++) {

            gridID[i] = IntStream.rangeClosed(i * boundary, ((i + 1) * boundary - 1) ).toArray();
            /*
            for (int j = 0; j < N; j++) {
                gridStatus[i][j] = false;
                gridID[i][j] = temp;
                temp += 1;
            }

             */
        }
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
     * Connect open cell in first row to the dummy top.
     */
    private void connectToDummyTop(int col) {
        gridConnection.union(gridID[0][col], dummyTop);
        gridNoDummyBottom.union(gridID[0][col], dummyTop);
    }

    /**
     * Connect open cell in last row to the dummy top.
     */
    private void connectToDummyBottom(int col) {
        gridConnection.union(gridID[boundary - 1][col], dummyBottom);
    }

    /**
     * Helper method to evaluate if a cell has open neighbor.
     * If open neighbor exists, connect it to all open neighbors.
     *
     * @param row Row of the cell
     * @param col Column of the cell.
     */
    private void connectOpenNeighbor(int row, int col) {
        int up = row - 1;
        int down = row + 1;
        int left = col - 1;
        int right = col + 1;
        if (up >= 0 && isOpen(up, col)) {
            gridConnection.union(gridID[row][col], gridID[up][col]);
            gridNoDummyBottom.union(gridID[row][col], gridID[up][col]);
        }
        if (down < boundary && isOpen(down, col)) {
            gridConnection.union(gridID[row][col], gridID[down][col]);
            gridNoDummyBottom.union(gridID[row][col], gridID[down][col]);
        }
        if (left >= 0 && isOpen(row, left)) {
            gridConnection.union(gridID[row][col], gridID[row][left]);
            gridNoDummyBottom.union(gridID[row][col], gridID[row][left]);
        }
        if (right < boundary && isOpen(row, right)) {
            gridConnection.union(gridID[row][col], gridID[row][right]);
            gridNoDummyBottom.union(gridID[row][col], gridID[row][right]);
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
        return gridNoDummyBottom.connected(gridID[row][col], dummyTop);
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

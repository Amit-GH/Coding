package Concepts;

/**
 * Binary Indexed Tree over a 2D matrix.
 * For m rows, n columns the time complexity for both getSum() and update() is O(log(m)*log(n))
 * Initialization time complexity is O(m*n*log(m)*log(n))
 * Space complexity is O(m*n)
 * Used in https://leetcode.com/problems/range-sum-query-2d-mutable/
 */
public class BIT2D {
    long[][] bit;
    int max_x, max_y;
    int[][] arr; // actual matrix

    public BIT2D(int[][] a) {
        if (a == null || a.length == 0) return;
        if (a[0].length == 0) return;

        max_x = a.length;
        max_y = a[0].length;
        arr = new int[max_x][max_y];

        bit = new long[max_x + 1][max_y + 1];
        for (int i = 0; i < max_x; ++i) {
            for (int j = 0; j < max_y; ++j) {
                update(i, j, a[i][j]);
            }
        }
    }

    /**
     * Returns the sum of all elements in the rectangular area whose top left element is at index (0, 0)
     * and bottom right element is at index (x, y)
     */
    public long getSum(int x, int y) {
        validate(x, y);

        // bit matrix uses 1-indexing
        int xbit = x + 1;
        int ybit = y + 1;
        long sum = 0;
        // move up the tree by removing the last set bit.
        for (int i = xbit; i > 0; i -= (i & (-i))) {
            for (int j = ybit; j > 0; j -= (j & (-j))) {
                sum += bit[i][j];
            }
        }
        return sum;
    }

    /**
     * Updates the element at index (x, y) in the actual matrix with val
     */
    public void update(int x, int y, int val) {
        validate(x, y);

        int xbit = x + 1;
        int ybit = y + 1;
        int diff = val - arr[x][y];
        arr[x][y] = val;
        // move up the tree by adding the last set bit
        for (int i = xbit; i <= max_x; i += (i & (-i))) {
            for (int j = ybit; j <= max_y; j += (j & (-j))) {
                bit[i][j] += diff;
            }
        }
    }

    private void validate(int x, int y) {
        if (x < 0 || x >= max_x || y < 0 || y >= max_y) {
            throw new IllegalArgumentException("x and/or y are out of range.");
        }
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{1, 2, 3}, {1, 6, 3}, {-1, 0, 5}};
        BIT2D object = new BIT2D(matrix);
        System.out.println(object.getSum(1, 1));
        System.out.println(object.getSum(2, 2));
        object.update(0, 0, 4);
        object.update(1, 2, 4);
        System.out.println(object.getSum(2, 2));
    }
}


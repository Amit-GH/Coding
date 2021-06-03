package Concepts;

/**
 * Binary Indexed Tree
 * For n rows the time complexity for both getSum() and update() is O(log(n))
 * Initialization time complexity is O(n*log(n))
 * Space complexity is O(n)
 * Source: https://www.geeksforgeeks.org/binary-indexed-tree-or-fenwick-tree-2/
 * Used in https://leetcode.com/problems/range-sum-query-mutable/
 * Used in https://leetcode.com/problems/count-of-smaller-numbers-after-self/
 */
public class BIT {
    int[] bit;
    int[] arr;
    int size;

    public BIT(int[] a) {
        size = a.length;
        bit = new int[size + 1]; // bit array uses 1-based indexing
        arr = new int[size];
        for (int i = 0; i < size; ++i) {
            update(i, a[i]);
        }
    }

    /**
     * Updates the value present at the given index. This uses a view of the tree where index=size+1 is the root node.
     * This view is different from the view used during getSum() method.
     */
    public void update(int index, int val) {
        if (index < 0 || index >= size) {
            throw new IllegalStateException("index should be from 0 to " + (size - 1));
        }
        // update the actual array.
        int from = arr[index];
        arr[index] = val;

        // update the bit array.
        int i = index + 1;
        while (i <= size) {
            bit[i] = bit[i] - from + val;
            // move up the tree by adding last set bit.
            i += (i & (-i));
        }
    }

     /**
      * Adds the value present at the given index. Uses tree view similar to update() method.
      */
    public void add(int index, int val) {
        if (index < 0 || index >= size) {
            throw new IllegalStateException("index should be from 0 to " + (size - 1));
        }
        // update the actual array
        arr[index] += val;

        // update the bit array
        int i = index + 1;
        while (i <= size) {
            bit[i] = bit[i] + val;
            // move up the tree by adding last set bit.
            i += (i & (-i));
        }
    }
    
    /**
     * Returns sum of numbers from index 0 to the given index. This uses a view of tree where 0 (dummy) node
     * is at the root.
     */
    public int getSum(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalStateException("index should be from 0 to " + (size - 1));
        }
        int sum = 0;
        int i = index + 1;
        while (i > 0) {
            sum += bit[i];
            // move up the tree by removing the last set bit.
            i -= (i & (-i));
        }
        return sum;
    }

    public static void main(String args[]) {
        int[] a = new int[]{2, 1, 1, 3, 2, 3, 4, 5, 6, 7, 8, 9};
        BIT bit = new BIT(a);
        for (int i = 0; i < a.length; ++i) {
            System.out.print(bit.getSum(i) + " ");
        }
        System.out.println();
        bit.update(5, 6);
        for (int i = 0; i < a.length; ++i) {
            System.out.print(bit.getSum(i) + " ");
        }
        System.out.println();
    }
}

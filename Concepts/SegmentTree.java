import java.util.Arrays;

/**
 * Learnings from segment tree construction using array:
 *
 * We do not create a completely balanced tree and do not care about n being of the power of 2. Instead
 * we start creating tree recursively from the root till the bottom (but fill values in bottom-up fashion).
 * At every point, we have three main things - start index from original array, end index from original
 * array, and index of the tree that is storing the segment tree values. We need three methods to run
 * segment tree - buildTree, update, and query. In some cases, buildTree can just be repeated invocation
 * of update method.
 *
 * There is no need to think about placeholder value in tree array because only valid indices will be
 * accessed if we start from the root. 4n is the max length required for tree array. Unused positions are
 * never accessed so they can contain any value (0 default is fine).
 * 
 * Used in: https://leetcode.com/problems/minimum-number-of-increments-on-subarrays-to-form-a-target-array/
 */
public class SegmentTree {
    int[] arr;
    int[] tree;
    int arrlen, treelen;

    public SegmentTree(int[] input) {
        arrlen = input.length;
        arr = Arrays.copyOf(input, arrlen);

        treelen = 4 * arrlen; // more than the maximum possible elements.
        tree = new int[treelen];

        buildTree(0, arrlen - 1, 0);
    }

    /**
     *
     * @param start starting index of the original array
     * @param end ending index of the original array
     * @param treeidx index of the tree that represents the range [start, end]
     */
    public void buildTree(int start, int end, int treeidx) {
        // leaf node.
        if (start == end) {
            tree[treeidx] = arr[start];
        } else {
            int mid = start + (end - start)/2;
            buildTree(start, mid, 2 * treeidx + 1);
            buildTree(mid + 1, end, 2 * treeidx + 2);
            int left = tree[2 * treeidx + 1];
            int right = tree[2 * treeidx + 2];
            tree[treeidx] = Math.min(left, right);
        }
    }

    /**
     *
     * @param treeidx treeidx index of the tree that represents the range [start, end]
     * @param idx index in the original array where update needs to be done.
     * @param val value to be replaced with at index idx in original array.
     * @param start start index of the original array.
     * @param end end index of the original array.
     */
    public void update(int treeidx, int idx, int val, int start, int end) {
        // leaf node.
        if (start == end) {
            arr[start] = val;
            tree[treeidx] = val;
        } else {
            int mid = start + (end - start)/2;
            if (idx <= mid) {
                update(2 * treeidx + 1, idx, val, start, mid);
            } else {
                update(2 * treeidx + 2, idx, val, mid + 1, end);
            }
            int left = tree[2 * treeidx + 1];
            int right = tree[2 * treeidx + 2];
            tree[treeidx] = Math.min(left, right);
        }
    }

    /**
     *
     * @param qs starting index of the query range.
     * @param qe ending index of the query range.
     * @param treeidx index of the segment tree representing range [start, end].
     * @param start start index of the range represented by treeidx.
     * @param end end index of the range represented by treeidx.
     * @return
     */
    public int findMin(int qs, int qe, int treeidx, int start, int end) {
        // out of the range values.
        if (qe < start || qs > end) {
            return Integer.MAX_VALUE;
        }

        // complete overlap. We can skip the subtree calculation.
        if (start == qs && end == qe) {
            return tree[treeidx];
        }

        int mid = start + (end - start)/2;
        if (qe <= mid) {
            return findMin(qs, qe, 2 * treeidx + 1, start, mid);
        } else if (qs > mid) {
            return findMin(qs, qe, 2 * treeidx + 2, mid + 1, end);
        } else {
            return Math.min(
                    findMin(qs, mid, 2 * treeidx + 1, start, mid),
                    findMin(mid + 1, qe, 2 * treeidx + 2, mid + 1, end)
            );
        }
    }

    public void print() {
        for (int i=0 ; i<arrlen ; ++i) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        for (int i=0 ; i<treelen ; ++i) {
            System.out.print(tree[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,0,5,2,7};
        SegmentTree tree = new SegmentTree(arr);
        tree.print();
        System.out.println(tree.findMin(2, 4, 0, 0, arr.length-1));
    }
}

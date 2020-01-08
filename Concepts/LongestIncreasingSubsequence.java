package Concepts;

import java.util.Scanner;

/**
 * https://en.wikipedia.org/wiki/Longest_increasing_subsequence
 */
public class LongestIncreasingSubsequence {
    int[] A; // to store the input array
    int N; // size of array A
    int L; // length of LIS
    int[] P; // P[i] stores the index of previous element for the sequence ending at A[i]
    int[] M; // A[M[1]], A[M[2]], ... , A[M[L]] are sorted

    public LongestIncreasingSubsequence() {
        N = 1000; // max length for any use-case
        A = new int[N + 1];
        P = new int[N + 1];
        M = new int[N + 1];
    }

    public static void main(String[] args) {
        LongestIncreasingSubsequence object = new LongestIncreasingSubsequence();
        object.takeInput();
        object.findLis();
        object.takeInput();
        object.findLis();
    }

    private void takeInput() {
        Scanner s = new Scanner(System.in);
        N = s.nextInt();
        // 16
        // 0 8 4 12 2 10 6 14 1 9 5 13 3 11 7 15
        // 15
        // 988 857 744 492 228 366 860 937 433 552 438 229 276 408 475
        for (int i = 1; i <= N; i++) {
            A[i] = s.nextInt();
        }
    }

    private void findLis() {
        L = 1;
        P[1] = -1; // no previous element
        M[1] = 1;
        M[0] = -1; // no previous element. This is needed when we do `P[i] = M[newL - 1];` and newL = 1.
        int l, h, mid, newL;
        for (int i = 2; i <= N; i++) {
            /**
                When index i is processed, L indicates the length of the LIS in the array A[1..i].
                M[j] is the index of the last element of the LIS of length j. Only M[L] should be used
                as the last element of the final LIS, whose length is L.
             */
            l = 1;
            h = L;
            while (l <= h) {
                mid = (int) Math.ceil((l + h) / 2.0);
                if (A[i] <= A[M[mid]]) {
                    h = mid - 1;
                } else {
                    l = mid + 1;
                }
            }

            /**
                if A[i] is present in A[M[1]], A[M[2]], ..., A[M[L]] then l is the index where A[i] == A[M[l]].
                In this case, we will use A[i] instead of A[M[l]] as the last element of the sub-sequence(ss).
             */
            /** if A[i] is not present in A[M[1]], A[M[2]], ..., A[M[L]] then l is the smallest index such that
                A[i] < A[M[l]]. We will replace index M[l] with 'i' or in other words use A[i] instead of A[M[l]]
                as the last element of the sub-sequence because that will keep the length of the ss same but reduce
                the value of the last element, which is better for expanding the ss.

                If there is no l such that A[i] < A[M[l]], then l is L+1, this is where we update L.
             */
            newL = l;
            if (newL > L) {
                M[newL] = i;
                P[i] = M[L];
                L = newL;
            } else {
                // we'll use this new number instead of older one
                M[newL] = i;
                P[i] = M[newL - 1];
            }
        }

        System.out.println("Length of LIS = " + L);
        int k = M[L];
        while (k != -1) {
            System.out.print(A[k] + " ");
            k = P[k];
        }
        System.out.println();

        System.out.println("Printing elements according to array M[]");
        for (int i = 1; i <= L; i++) {
            System.out.print(A[M[i]] + " ");
        }
        System.out.println();
    }
}

package Codechef;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Accepted Solution
 * https://www.codechef.com/JAN20B/status/ISBIAS,amit_gh
 */
public class ISBIAS {
    private static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;
        private int stringLineLength = 210;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[stringLineLength]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
            writer.flush();
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
            writer.flush();
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }

    public static void main(String args[]) throws IOException {
        // The program used indexing starting from 1

        long A[] = new long[100005];
        // pInc[i] denotes the number of valid increasing groups from A[1] to A[i]
        int pInc[] = new int[100005];
        // pDec[i] denotes the number of valid decreasing groups from A[1] to A[i]
        int pDec[] = new int[100005];
        int N, Q, L, R;

        Reader reader = new Reader();
        OutputWriter writer = new OutputWriter(System.out);

        N = reader.nextInt();
        Q = reader.nextInt();

        for (int i = 1; i <= N; i++) {
            A[i] = reader.nextLong();
        }

        fillIncreasingPrefix(pInc, A, N);
        fillDecreasingPrefix(pDec, A, N);

        for (int i = 1; i <= Q; i++) {
            L = reader.nextInt();
            R = reader.nextInt();

            if (increasingGroups(A, L, R, pInc) == decreasingGroups(A, L, R, pDec)) {
                writer.printLine("YES");
            } else {
                writer.printLine("NO");
            }

//            if (solveLinearComplexity(A, L, R)) {
//                writer.printLine("YES");
//            } else {
//                writer.printLine("NO");
//            }
        }

        reader.close();
        writer.close();
    }

    /**
     * @return the number of valid decreasing groups from A[L] to A[R], inclusive
     */
    private static int decreasingGroups(long[] A, int L, int R, int[] pDec) {
        if (L == 1) {
            return pDec[R];
        }
        // group ends at pos 'L-1'
        if (A[L - 1] < A[L]) {
            return pDec[R] - pDec[L - 1];
        }
        // group ends at pos 'L'
        if (A[L] < A[L + 1]) {
            return pDec[R] - pDec[L];
        }
        // A[L - 1] > A[L] > A[L + 1] at this point
        if (L - 2 >= 1) {
            if (A[L - 2] > A[L - 1]) {
                return pDec[R] - pDec[L - 1] + 1;
            }
            return pDec[R] - pDec[L - 1];
        } else {
            return pDec[R];
        }
    }

    /**
     * @return the number of valid increasing groups from A[L] to A[R], inclusive
     */
    private static int increasingGroups(long[] A, int L, int R, int[] pInc) {
        if (L == 1) {
            return pInc[R];
        }
        // group ends at pos 'L-1'
        if (A[L - 1] > A[L]) {
            return pInc[R] - pInc[L - 1];
        }
        // group ends at pos 'L'
        if (A[L] > A[L + 1]) {
            return pInc[R] - pInc[L];
        }
        // A[L - 1] < A[L] < A[L + 1] at this point
        if (L - 2 >= 1) {
            if (A[L - 2] < A[L - 1]) {
                return pInc[R] - pInc[L - 1] + 1;
            }
            return pInc[R] - pInc[L - 1];
        } else {
            return pInc[R];
        }
    }

    /**
     * Fills out the array pDec
     * @param pDec Array which is filled. pDec[i] denotes the number of valid decreasing groups from A[1] to A[i]
     * @param A the input array of size N
     * @param N size of array A
     */
    private static void fillDecreasingPrefix(int[] pDec, long[] A, int N) {
        pDec[1] = 0;
        int groups = 0;
        int i = 1;
        while (i < N) {
            int j = i + 1;
            while (j <= N && (A[j] < A[j - 1])) {
                j++;
            }
            if (j > i + 1) {
                groups++;
                for (int k = i + 1; k <= j; k++) {
                    pDec[k] = groups;
                }
            } else {
                pDec[j] = pDec[j - 1];
            }
            i = j;
        }
    }

    /**
     * Fills out the array pInc
     * @param pInc Array which is filled. pInc[i] denotes the number of valid increasing groups from A[1] to A[i]
     * @param A the input array of size N
     * @param N size of array A
     */
    private static void fillIncreasingPrefix(int[] pInc, long[] A, int N) {
        pInc[1] = 0;
        int groups = 0;
        int i = 1;
        while (i < N) {
            int j = i + 1;
            while (j <= N && (A[j] > A[j - 1])) {
                j++;
            }
            if (j > i + 1) {
                groups++;
                for (int k = i + 1; k <= j; k++) {
                    pInc[k] = groups;
                }
            } else {
                pInc[j] = pInc[j - 1];
            }
            i = j;
        }
    }

    private static boolean solveLinearComplexity(long[] A, int L, int R) {
        int i = L;
        int countInc = 0;
        while (i < R) {
            int j = i + 1;
            while ((A[j] > A[j - 1]) && j <= R) {
                j++;
            }
            if (j > i + 1) {
                countInc++;
            }
            i = j;
        }

        i = L;
        int countDec = 0;
        while (i < R) {
            int j = i + 1;
            while ((A[j] < A[j - 1]) && j <= R) {
                j++;
            }
            if (j > i + 1) {
                countDec++;
            }
            i = j;
        }
        return countDec == countInc;
    }
}

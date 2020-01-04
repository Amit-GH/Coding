package Codechef;

import java.io.*;

/**
 * Accepted solution
 * https://www.codechef.com/JULY19B/status/CIRMERGE,amit_gh
 */
public class CIRMERGE {
    private static final int MAX_N = 400;
    static int N;
    /*
        1<=A[i]<=10^9
     */
    static long A[] = new long[MAX_N+1]; // max 400 elements
    /*
        cost[i][j] tells the cost of merging array A from index i to j. i<=j or i>j.
     */
    static long cost[][] = new long[MAX_N+1][MAX_N+1]; // to save the cost of merging array
    /*
        ans[i][j] saves the cost of merging A[i] to A[j], and A[j+1] to A[i-1].
     */
    static long ans[][] = new long[MAX_N+1][MAX_N+1]; // to save the final answer
    /*
        merge[i][j] saves the single number that is obtained after merging A[i] to A[j]
     */
    static long merge[][] = new long[MAX_N+1][MAX_N+1];

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
        Reader reader = new Reader();
        OutputWriter writer = new OutputWriter(System.out);

        int T = reader.nextInt();
        long min, m;
        for(int t=1 ; t<=T ; ++t) {
            N = reader.nextInt();
            for(int n=1 ; n<=N ; ++n) {
                A[n] = reader.nextLong();
            }
            initialize();

            min = Long.MAX_VALUE;

            // now try to fillAns ans[][] array
            for(int i=1 ; i<=N-1 ; ++i) {
                for(int j=i ; j<=N-1 ; ++j) {
                    m = fillAns(i, j);
                    if(m < min) {
                        min = m;
                    }
                }
            }
            m = fillAns(1, N);
            if(m < min) {
                min = m;
            }
            writer.printLine(min);
        }
    }

    /*
        i<=j
     */
    private static long fillAns(int i, int j) {
        if(ans[i][j] != -1) {
            return ans[i][j];
        }
        long ret1, ret2;
        ret1 = fillCost(i, j);
        ans[i][j] = cost[i][j] + ret1;

        if(i == 1) {
            if(j == N) {
                // do nothing
            }
            else {
                ret2 = fillCost(j+1, N);
                ans[i][j] += (cost[j+1][N] + ret2);
            }
        }
        else if(j == N) {
            ret2 = fillCost(1, i-1);
            ans[i][j] += (cost[1][i-1] + ret2);
        }
        else {
            ret2 = fillCost(j+1, i-1);
            ans[i][j] += (cost[j+1][i-1] + ret2);
        }
        return ans[i][j];
    }

    /**
     * Fills up the cell cost[i][j] which indicates cost of merging from i to j into one number.
     * @param i 1<=i<=N
     * @param j 1<=j<=N
     * @return returns the single number that is obtained after merging from i to j
     */
    private static long fillCost(int i, int j) {
        if(cost[i][j] != -1) {
            return merge[i][j];
        }
        if(i == j) {
            cost[i][j] = 0;
            merge[i][j] = A[i];
            return merge[i][j];
        }
        if(i < j) {
            if(j-i == 1) {
                cost[i][j] = A[i] + A[j];
                merge[i][j] = cost[i][j];
                return merge[i][j];
            }
            long ret1, ret2, min_cost, m, min_ret = 0;
            min_cost = Long.MAX_VALUE;
            for(int k=i ; k<j ; ++k) {
                ret1 = fillCost(i, k);
                ret2 = fillCost(k+1, j);
                m = cost[i][k] + cost[k+1][j] + ret1 + ret2;
                if(m < min_cost) {
                    min_cost = m;
                    min_ret = ret1 + ret2;
                }
            }
            cost[i][j] = min_cost;
            merge[i][j] = min_ret;
            return merge[i][j];
        }
        else {
            if(i==N && j==1) {
                cost[i][j] = A[i] + A[j];
                merge[i][j] = cost[i][j];
                return merge[i][j];
            }
            long ret1, ret2, min_cost, m, min_ret = 0;
            min_cost = Long.MAX_VALUE;
            for(int k=i ; k<N ; ++k) {
                ret1 = fillCost(i, k);
                ret2 = fillCost(k+1, j);
                m = cost[i][k] + cost[k+1][j] + ret1 + ret2;
                if(m < min_cost) {
                    min_cost = m;
                    min_ret = ret1 + ret2;
                }
            }
            ret1 = fillCost(i, N);
            ret2 = fillCost(1, j);
            m = cost[i][N] + cost[1][j] + ret1 + ret2;
            if(m < min_cost) {
                min_cost = m;
                min_ret = ret1 + ret2;
            }
            for(int k=1 ; k<j ; ++k) {
                ret1 = fillCost(i, k);
                ret2 = fillCost(k+1, j);
                m = cost[i][k] + cost[k+1][j] + ret1 + ret2;
                if(m < min_cost) {
                    min_cost = m;
                    min_ret = ret1 + ret2;
                }
            }
            cost[i][j] = min_cost;
            merge[i][j] = min_ret;
            return merge[i][j];
        }
    }

    private static void initialize() {
        for(int i=1 ; i<=N ; ++i) {
            for(int j=1 ; j<=N ; ++j) {
                cost[i][j] = -1;
                merge[i][j] = -1;
            }
        }
        for(int i=1 ; i<=N-1 ; ++i) {
            for(int j=i ; j<=N-1 ; ++j) {
                ans[i][j] = -1;
            }
        }
        ans[1][N] = -1;
    }
}

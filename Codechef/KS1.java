package Codechef;

import java.io.*;
import java.util.HashMap;

/**
 * Accepted Solution
 * https://www.codechef.com/AUG19B/status/KS1,amit_gh
 */
public class KS1 {
    private static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;
        private int stringLineLength = 100010;

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

    int A[], N, prefix[];
    long answer;
    Reader reader;
    OutputWriter writer;
    /*
        xorCount(i) saves the number of times we have got an array with element-wise-XOR equal to i

        If xorCount(i) is j, then there are j distinct arrays whose element-wise-XOR is i. xorSum(i)
        saves the sum of lengths of those arrays minus j.
     */
    HashMap<Integer, Long> xorCount, xorSum;
    boolean verificationMode;

    public KS1() {
        A = new int[100010];
        prefix = new int[100010];
        reader = new Reader();
        writer = new OutputWriter(System.out);
        verificationMode = false;
    }

    public static void main(String[] args) throws IOException {
        KS1 object = new KS1();
        object.takeInputAndSolve();
    }

    private void takeInputAndSolve() throws IOException {
        int T = reader.nextInt();
        for (int i = 0; i < T; i++) {
            N = reader.nextInt();
            for (int j = 1; j <= N; j++) {
                A[j] = reader.nextInt();
            }

            if (verificationMode) {
                initialize();
                solveBruteForce();
            }

            initialize();
            fillPrefixArray();
            long c, s;
            xorCount.put(prefix[1], 1l);
            xorSum.put(prefix[1], 0l);
            for (int j = 2; j <= N; j++) {
                // for length less than j
                c = xorCount.containsKey(prefix[j]) ? xorCount.get(prefix[j]) : 0;
                s = xorSum.containsKey(prefix[j]) ? xorSum.get(prefix[j]) : 0;
                answer += (c * (j - 2) - s);

                // for length equal to j
                if (prefix[j] == 0) {
                    answer += (j - 1);
                }

                // update xorCount
                if (xorCount.containsKey(prefix[j])) {
                    xorCount.put(prefix[j], xorCount.get(prefix[j]) + 1);
                } else {
                    xorCount.put(prefix[j], 1l);
                }

                // update xorSum
                if (xorSum.containsKey(prefix[j])) {
                    xorSum.put(prefix[j], xorSum.get(prefix[j]) + j - 1);
                } else {
                    xorSum.put(prefix[j], (long)(j - 1));
                }
            }
            writer.printLine(answer);
        }
    }

    private void solveBruteForce() {
        for (int i = 1; i <= N - 1; i++) {
            int prevLeft = 0;
            for (int j = i; j >= 1; --j) {
                prevLeft ^= A[j];
                int prevRight = 0;
                for (int k = i + 1; k <= N; ++k) {
                    prevRight ^= A[k];
                    if (prevLeft == prevRight) {
                        answer++;
                    }
                }
            }
        }
        writer.printLine(answer);
    }

    private void initialize() {
        xorCount = new HashMap<>();
        xorSum = new HashMap<>();
        answer = 0;
    }

    private void fillPrefixArray() {
        prefix[1] = A[1];
        for (int i = 2; i <= N; i++) {
            prefix[i] = prefix[i - 1] ^ A[i];
        }
    }
}

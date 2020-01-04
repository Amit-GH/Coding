package Codechef;

import java.io.*;
import java.util.Arrays;

/**
 * Accepted solution
 * https://www.codechef.com/AUG19B/status/ZOMCAV,amit_gh
 */
public class ZOMCAV {
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

    // code uses 1 indexing
    int C[], H[], N;
    int start[], end[];
    Reader reader;
    OutputWriter writer;

    public ZOMCAV() {
        C = new int[100010];
        H = new int[100010];
        start = new int[100010];
        end = new int[100010];
        reader = new Reader();
        writer = new OutputWriter(System.out);
    }

    public static void main(String[] args) throws IOException {
        ZOMCAV object = new ZOMCAV();
        object.takeInputAndSolve();
    }

    private void takeInputAndSolve() throws IOException {
        int T = reader.nextInt();
        int radiationLevel;
        boolean result;
        for (int i = 1; i <= T; ++i) {
            result = true;
            N = reader.nextInt();
            for (int j = 1; j <= N; ++j) {
                C[j] = reader.nextInt();
            }
            for (int j = 1; j <= N; ++j) {
                H[j] = reader.nextInt();
                if (H[j] > N) {
                    result = false;
                }
            }
            if (!result) {
                writer.printLine("NO");
                continue;
            }
            for (int j = 1; j <= N; ++j) {
                start[j] = 0;
                end[j] = 0;
            }
            fillStartAndEndArrays();

            radiationLevel = start[1];
            C[1] = radiationLevel;
            for (int j = 2; j <= N; ++j) {
                radiationLevel += (start[j] - end[j - 1]);
                C[j] = radiationLevel;
            }

            Arrays.sort(C, 1, N + 1);
            Arrays.sort(H, 1, N + 1);

            for (int j = 1; j <= N; ++j) {
                if (C[j] != H[j]) {
                    writer.printLine("NO");
                    result = false;
                    break;
                }
            }
            if (!result) {
                continue;
            }
            writer.printLine("YES");
        }
    }

    private void fillStartAndEndArrays() {
        int left, right;
        for (int i = 1; i <= N; ++i) {
            left = Math.max(1, i - C[i]);
            right = Math.min(N, i + C[i]);
            start[left]++;
            end[right]++;
        }
    }
}

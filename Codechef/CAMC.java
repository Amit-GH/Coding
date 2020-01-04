package Codechef;

import java.io.*;
import java.util.*;

/**
 * Accepted solution
 * https://www.codechef.com/NOV19B/status/CAMC,amit_gh
 */
public class CAMC {
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

    class MyNode {
        int number;
        int color;

        public MyNode(int number, int color) {
            this.number = number;
            this.color = color;
        }
    }

    public class MyNodeComparator implements Comparator<MyNode> {
        @Override
        public int compare(MyNode o1, MyNode o2) {
            if (o1.number < o2.number) return -1;
            if (o1.number > o2.number) return +1;
            if (o1.color < o2.color) return -1;
            if (o1.color > o2.color) return +1;
            return 0;
        }
    }

    int T, M, N, n, color;
    List<MyNode> A = new ArrayList<>();
    Reader reader = new Reader();
    OutputWriter writer = new OutputWriter(System.out);
    Map<Integer, MyNode> colorMap = new HashMap<>();
    TreeSet<MyNode> answerSet = new TreeSet<>(new MyNodeComparator());

    public void solve() throws IOException {
        T = reader.nextInt();
        for (int i = 0; i < T; i++) {
            N = reader.nextInt();
            M = reader.nextInt();
            color = 1;
            A.clear();
            for (int j = 1; j <= N; j++) {
                n = reader.nextInt();
                A.add(new MyNode(n, color));
                color++;
                if (color > M) color = 1;
            }
            A.sort(new MyNodeComparator());

            int min, max;
            int bestAnswer = Integer.MAX_VALUE;
            colorMap.clear();
            answerSet.clear();

            for (MyNode node : A) {
                // if color is new
                if (!colorMap.containsKey(node.color)) {
                    colorMap.put(node.color, node);
                    answerSet.add(node);
                    if (colorMap.size() == M) {
                        min = answerSet.first().number;
                        max = answerSet.last().number;
                        if (max - min < bestAnswer) {
                            bestAnswer = max - min;
                        }
                    }
                } else {
                    // if color is repeated
                    // update color
                    MyNode existent = colorMap.get(node.color);
                    colorMap.put(node.color, node);
                    // update min-max
                    answerSet.remove(existent);
                    answerSet.add(node);
                    if (answerSet.size() == M) {
                        min = answerSet.first().number;
                        max = answerSet.last().number;
                        if (max - min < bestAnswer) {
                            bestAnswer = max - min;
                        }
                    }
                }
            }
            writer.printLine(bestAnswer);
        }
    }

    public static void main(String[] args) throws IOException {
        CAMC object = new CAMC();
        object.solve();
    }
}

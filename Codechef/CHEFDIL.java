package Codechef;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Accepted Solution
 * https://www.codechef.com/AUG19B/status/CHEFDIL,amit_gh
 */
public class CHEFDIL {
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

    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        OutputWriter writer = new OutputWriter(System.out);
        int T = reader.nextInt();
        String s;
        Pattern p = Pattern.compile("(0*10*)(10*10*)*");
        Matcher m;
        boolean solveByPattern = false;

        for (int i = 0; i < T; ++i) {
            s = reader.readLine();

            if (solveByPattern) {
                m = p.matcher(s);
                if (m.matches()) {
                    writer.printLine("WIN");
                } else {
                    writer.printLine("LOSE");
                }
                continue;
            }

            int j = 0;

            // check 0*
            while (j < s.length() && s.charAt(j) == '0') {
                j++;
            }

            // check 1
            if (j < s.length() && s.charAt(j) == '1') {
                j++;
            } else {
                writer.printLine("LOSE");
                continue;
            }

            // check 0*
            while (j < s.length() && s.charAt(j) == '0') {
                j++;
            }

            if (checkPattern(s, j)) {
                writer.printLine("WIN");
            } else {
                writer.printLine("LOSE");
            }
        }
    }

    // (10*10*)*
    private static boolean checkPattern(String s, int j) {
        if (j == s.length()) {
            return true;
        }
        // check 1
        if (j < s.length() && s.charAt(j) == '1') {
            j++;

            // check 0*
            while (j < s.length() && s.charAt(j) == '0') {
                j++;
            }

            // check 1
            if (j < s.length() && s.charAt(j) == '1') {
                j++;

                // check 0*
                while (j < s.length() && s.charAt(j) == '0') {
                    j++;
                }

                return checkPattern(s, j);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}

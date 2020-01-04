package Codechef;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Accepted solution
 * https://www.codechef.com/FEB19B/status/ARTBALAN,amit_gh
 */
public class ARTBALAN {
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

	//FAST IO
	private static class InputReader {
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;
		private SpaceCharFilter filter;

		public InputReader(InputStream stream) {
			this.stream = stream;
		}

		public int read() {
			if (numChars == -1) {
				throw new InputMismatchException();
			}
			if (curChar >= numChars) {
				curChar = 0;
				try {
					numChars = stream.read(buf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (numChars <= 0) {
					return -1;
				}
			}
			return buf[curChar++];
		}

		public int readInt() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			int res = 0;
			do {
				if (c < '0' || c > '9') {
					throw new InputMismatchException();
				}
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			return res * sgn;
		}

		public String readString() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			StringBuilder res = new StringBuilder();
			do {
				res.appendCodePoint(c);
				c = read();
			} while (!isSpaceChar(c));
			return res.toString();
		}

		public double readDouble() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			double res = 0;
			while (!isSpaceChar(c) && c != '.') {
				if (c == 'e' || c == 'E') {
					return res * Math.pow(10, readInt());
				}
				if (c < '0' || c > '9') {
					throw new InputMismatchException();
				}
				res *= 10;
				res += c - '0';
				c = read();
			}
			if (c == '.') {
				c = read();
				double m = 1;
				while (!isSpaceChar(c)) {
					if (c == 'e' || c == 'E') {
						return res * Math.pow(10, readInt());
					}
					if (c < '0' || c > '9') {
						throw new InputMismatchException();
					}
					m /= 10;
					res += (c - '0') * m;
					c = read();
				}
			}
			return res * sgn;
		}

		public long readLong() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			long res = 0;
			do {
				if (c < '0' || c > '9') {
					throw new InputMismatchException();
				}
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			return res * sgn;
		}

		public boolean isSpaceChar(int c) {
			if (filter != null) {
				return filter.isSpaceChar(c);
			}
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public String next() {
			return readString();
		}

		public interface SpaceCharFilter {
			public boolean isSpaceChar(int ch);
		}
	}

	public static void main(String[] args) throws IOException {
		InputReader reader = new InputReader(System.in);
		OutputWriter writer = new OutputWriter(System.out);

		int T = reader.readInt();
		int C[] = new int[26];
		int CC[] = new int[26];
		int sum, nonZero1, level, answer1;
		int nonZero2, answer2;

		for (int t=0 ; t<T ; ++t) {
			for (int i=0 ; i<26 ; ++i) {
				C[i] = 0;
			}
			String S = reader.readString();
			for (int i=0 ; i<S.length() ; ++i) {
				C[S.charAt(i)-'A']++;
			}
			sum = S.length();
			nonZero1 = 0;
			for (int i=0 ; i<26 ; ++i) {
				if (C[i] != 0) {
					nonZero1++;
				}
			}
			Arrays.sort(C);

			for (int i=0 ; i<26 ; ++i) {
				CC[i] = C[i];
			}
			nonZero2 = nonZero1+1;

			// try reducing the characters
			answer1 = 0;
			while (nonZero1>=1) {
				if (sum%nonZero1 == 0) {
					level = sum/nonZero1;
					for (int i=26-nonZero1 ; i<26 ; ++i) {
						if (C[i]>level) {
							answer1 += (C[i]-level);
						}
					}
					break;
				}
				else {
					answer1 += C[26-nonZero1];
					C[26-nonZero1] = 0;
					nonZero1--;
				}
			}

			// try increasing the characters
			// e.g. test case ABBCCDDDEEEFFF which requires increasing the characters for optimum solution
			answer2 = -1;
			while (nonZero2<=26) {
				if (sum%nonZero2 == 0) {
					level = sum/nonZero2;
					for (int i=26-nonZero2 ; i<26 ; ++i) {
						if (CC[i]>level) {
							if (answer2 == -1) {
								answer2 = (CC[i]-level);
							}
							else {
								answer2 += (CC[i] - level);
							}
						}
					}
					break;
				}
				else {
					nonZero2++;
				}
			}

			if (answer2 == -1) {
				writer.printLine(answer1);
			}
			else {
				writer.printLine(Math.min(answer1, answer2));
			}
		}
	}
}
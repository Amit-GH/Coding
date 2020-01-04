package Codechef;

import java.io.*;

/**
 * Accepted solution
 * https://www.codechef.com/FEB19B/status/DEPCHEF,amit_gh
 */
public class DEPCHEF {
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

	public static void main(String[] args) throws IOException {
		Reader reader = new Reader();
		OutputWriter writer = new OutputWriter(System.out);

		int T = reader.nextInt();
		int A[] = new int[102];
		int D[] = new int[102];
		int N, maxShield;

		for (int t=0 ; t<T ; ++t) {
			maxShield = -1;
			N = reader.nextInt();
			for (int n=1 ; n<=N ; ++n) {
				A[n] = reader.nextInt();
			}
			for (int n=1 ; n<=N ; ++n) {
				D[n] = reader.nextInt();
			}
			A[0] = A[N];
			A[N+1] = A[1];
			D[0] = D[N];
			D[N+1] = D[1];

			for (int n=1 ; n<=N ; ++n) {
				if (A[n-1]+A[n+1] < D[n]) {
					if (maxShield == -1) {
						maxShield = D[n];
					}
					else if (D[n] > maxShield) {
						maxShield = D[n];
					}
				}
			}

			writer.printLine(maxShield);
		}
	}
}

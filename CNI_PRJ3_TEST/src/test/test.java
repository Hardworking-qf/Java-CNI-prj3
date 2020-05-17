package test;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class test {
	public static void main(String[] args) {
		try (Socket so = new Socket("localhost", 11111)) {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
			bw.write("VER 9129291794 0C-DD-24-02-1B-71\n");
			bw.flush();
		}
		catch (Exception e) {
		}
	}
}

package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientMain {
	public static Client client;

	private static String ServerIP = "127.0.0.1";
	private static int Port = 11111;

	public static String[] args;

	public static void main(String[] args) {
		ClientMain.args = args;
		client = new Client();
		new LoginWin(args);
	}

	public static boolean getVerify() {
		String readStr = "";
		try (Socket socket = new Socket(ServerIP, Port)) {
			socket.setSoTimeout(5000);
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			writer.write(client.sendVer() + "\n");
			writer.flush();

			readStr = reader.readLine();

		} catch (Exception e) {
//			e.printStackTrace();
			return false;
		}
		return readStr.equals("SUC");
	}
	
	public static void endVerify() {
		try (Socket socket = new Socket(ServerIP, Port)) {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			writer.write(client.sendEndVer() + "\n");
			writer.flush();
		} catch (Exception e) {
		}
	}
}

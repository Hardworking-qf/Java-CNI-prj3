package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerSocket extends ServerSocket {
	public MyServerSocket(int arg1) throws IOException {
		super(arg1);
	}

	public void runServer() {
		Socket socket;
		while (true) {
			try {
				socket = this.accept();
				new RECVTask(socket).start();
			} catch (IOException e) {
			}
		}
	}

}

class RECVTask extends Thread {
	private Socket socket;

	public RECVTask(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			String sendStr, recvStr;
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			recvStr = reader.readLine();
			sendStr = ServerMain.s.readCmd(recvStr + "\n");
			System.out.println("校验信息：" + recvStr + " " + sendStr);
			if (sendStr != null) {
				writer.write(sendStr);
				writer.flush();
			}
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
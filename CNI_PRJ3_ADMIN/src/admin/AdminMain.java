package admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class AdminMain {
	public static String ServerIP = "localhost";
	public static int ServerPort = 23333;

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("调用方式应为:\n"//
					+ "args[0]:用户名\n"//
					+ "args[1]:密码\n"//
					+ "args[2]:许可证人数");
			return;
		}
		try (Socket socket = new Socket(ServerIP, ServerPort)) {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw.write("REQ " + args[0] + " " + args[1] + " " + args[2] + "\n");
			String recv = br.readLine();
			System.out.println(recv.equals("FAI") ? "用户名或密码输入错误" : ("申请成功，校验码：" + recv));
		} catch (UnknownHostException e) {
			System.out.println("连接失败");
		} catch (IOException e) {
			System.out.println("未知错误");
		}
	}
}

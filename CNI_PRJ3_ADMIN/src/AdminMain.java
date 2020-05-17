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
	public static int ServerPort = 11111;

	public static void main(String[] args) {
		args=new String[3];
		args[0] = "User";
		args[1] = "Pass";
		args[2] = "10";
		if (args.length != 3) {
			System.out.println("���÷�ʽӦΪ:\n"//
					+ "args[0]:�û���\n"//
					+ "args[1]:����\n"//
					+ "args[2]:���֤����");
			return;
		}
		try {
			Integer.valueOf(args[2]);
		} catch (Exception e) {
			System.out.println("���һ����Ϊ����");
			return;
		}
		try (Socket socket = new Socket(ServerIP, ServerPort)) {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println(1);
			bw.write("REQ " + args[0] + " " + args[1] + " " + args[2] + "\n");
			bw.flush();
			System.out.println(1);
			String recv = br.readLine();
			System.out.println(1);
			System.out.println(recv.equals("FAI") ? "�û����������������" : ("����ɹ���У���룺" + recv));
		} catch (UnknownHostException e) {
			System.out.println("����ʧ��");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("δ֪����");
		}
	}
}

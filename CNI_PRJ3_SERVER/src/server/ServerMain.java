package server;

import java.io.IOException;
import java.util.Scanner;

public class ServerMain {
	public static Server s;

	public static int ServerPort = 11111;

	public static void main(String[] args) {
		s = new Server();
		new Thread(() -> UI()).start();
		try (MyServerSocket ss = new MyServerSocket(ServerPort)) {
			ss.runServer();
		} catch (IOException e) {
		}
	}

	private static void UI() {
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				System.out.println();
				System.out.println("1���г�����ʶ����");
				System.out.println("2��������ʶ����");
				try {
					switch (scanner.nextInt()) {
					case 1:
						s.ShowAllvCodes();
						break;
					case 2:
						System.out.println("������������");
						int usernum;
						while (true) {
							try {
								usernum = scanner.nextInt();
								s.getNextVCode(usernum);
								System.out.println("��ʶ���룺" + s.getLatestVCode());
								break;
							} catch (Exception e) {
							}
						}
					}
				} catch (Exception e) {
				}
			}
		}
	}
}

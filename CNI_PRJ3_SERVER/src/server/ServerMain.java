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
					switch (Integer.valueOf(scanner.nextLine())) {
					case 1:
						s.ShowAllvCodes();
						break;
					case 2:
						System.out.println("������������");
						int usernum;
						while (true) {
							try {
								usernum = Integer.valueOf(scanner.nextLine());
								System.out.println("��ʶ���룺" + s.getNextVCode(usernum));
								break;
							} catch (Exception e) {
								System.out.println("��������ȷ������");
							}
						}
					}
				} catch (Exception e) {
				}
			}
		}
	}
}

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
				System.out.println("1、列出所有识别码");
				System.out.println("2、创建新识别码");
				try {
					switch (scanner.nextInt()) {
					case 1:
						s.ShowAllvCodes();
						break;
					case 2:
						System.out.println("请输入人数：");
						int usernum;
						while (true) {
							try {
								usernum = scanner.nextInt();
								s.getNextVCode(usernum);
								System.out.println("新识别码：" + s.getLatestVCode());
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

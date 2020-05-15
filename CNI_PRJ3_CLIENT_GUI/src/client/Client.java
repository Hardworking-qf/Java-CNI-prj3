package client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Client {
	private String profilePATH = "clientprofile.dat";
	private String SerialNumber;

	public boolean getSNFromFile() {
		File file = new File(profilePATH);
		if (file.exists()) {
			try (FileInputStream fis = new FileInputStream(file)) {
				byte buff[] = new byte[1024];
				int len = fis.read(buff);
				SerialNumber = new String(buff, 0, len);
			} catch (Exception e) {
				return false;
			}
			return true;
		} else
			return false;
	}

	public void setSerialNumber(String SN) {
		SerialNumber = SN;
		try (FileOutputStream fos = new FileOutputStream(new File(profilePATH))) {
			fos.write(String.valueOf(SerialNumber).getBytes());
		} catch (Exception e) {
		}
		return;
	}

	public String sendVer() throws Exception {
		return "VER " + String.valueOf(SerialNumber) + " " + getMac();
	}

	public String sendEndVer() throws Exception {
		return "END " + String.valueOf(SerialNumber) + " " + getMac();
	}
	public String getMac() throws Exception {
		List<String> macList = MacTools.getMacList();
		return String.valueOf(macList.get(0));
	}

	public String getSerialNumber() {
		return SerialNumber;
	}
}

class MacTools {
	/*** 因为一台机器不一定只有一个网卡呀，所以返回的是数组是很合理的 ***/
	public static List<String> getMacList() throws Exception {
		java.util.Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
		StringBuilder sb = new StringBuilder();
		ArrayList<String> tmpMacList = new ArrayList<>();
		while (en.hasMoreElements()) {
			NetworkInterface iface = en.nextElement();
			List<InterfaceAddress> addrs = iface.getInterfaceAddresses();
			for (InterfaceAddress addr : addrs) {
				InetAddress ip = addr.getAddress();
				NetworkInterface network = NetworkInterface.getByInetAddress(ip);
				if (network == null) {
					continue;
				}
				byte[] mac = network.getHardwareAddress();
				if (mac == null) {
					continue;
				}
				sb.delete(0, sb.length());
				for (int i = 0; i < mac.length; i++) {
					sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
				}
				tmpMacList.add(sb.toString());
			}
		}
		if (tmpMacList.size() <= 0) {
			return tmpMacList;
		}
		List<String> unique = tmpMacList.stream().distinct().collect(Collectors.toList());
		return unique;
	}
}
package server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

//REQ 50 VER code mac
//10分钟 发一次更新时间 30分钟则认定超时
//超时可以继续使用 但是退出后需要重新认证
//VCO verificationCode
public class Server {
	private ArrayList<VCode> vCodes;
	private FileUtil fileUtil;
	private VCodeGenerator gen;
	private String profilePATH = "serverprofile.dat";

	public Server() {
		vCodes = new ArrayList<VCode>();
		File saveFile = new File(profilePATH);
		fileUtil = new SequentialFileUtil(saveFile);
		fileUtil.LoadFile(vCodes);
		gen = new VCodeGenerator(1);
	}

	private void save() throws IOException {
		fileUtil.SaveFile(vCodes);
	}

	public String readCmd(String cmd) throws IOException {
		String s[] = cmd.split(" ");
		if (s[0].equals("REQ")) {
			return getNextVCode(Integer.valueOf(s[1]));
		}
		if (s[0].equals("VER")) {
			if (verified(s[1], s[2]))
				return "SUC";
			else
				return "FAI";
		}
		if (s[0].equals("END")) {
			endVer(s[1], s[2]);
		}
		return null;
	}

	public String getNextVCode(int userNum) throws IOException {
		long ret;
		VCode tmp;
		while (true) {
			boolean notRepeat = true;
			ret = gen.getNextVCode();
			tmp = new VCode(ret, userNum);
			for (VCode v : vCodes) {
				if (v.equals(tmp)) {
					notRepeat = false;
					break;
				}
			}
			if (notRepeat)
				break;
		}
		vCodes.add(tmp);
		save();
		return String.valueOf(ret);
	}

	private boolean verified(String code, String mac) throws IOException {
//        System.out.println(code+" "+mac);
		long c = Long.valueOf(code);
		for (VCode vCode : vCodes) {
			if (c == vCode.getvCode()) {
				for (User u : vCode.getUsers()) {
					if (u.macEquals(mac)) {
						u.updateTime();
						save();
						return true;
					}
				}
//                System.out.println(Arrays.toString(macChar));
				boolean ret = vCode.addNewUser(mac);
				save();
				return ret;
			}
		}
		return false;
	}

	private void endVer(String code, String mac) throws IOException {
		long c = Long.valueOf(code);
		for (VCode vCode : vCodes) {
			if (c == vCode.getvCode()) {
				User delUser = null;
				for (User u : vCode.getUsers()) {
					if (u.macEquals(mac)) {
						delUser = u;
						break;
					}
				}
				if (delUser != null)
					vCode.getUsers().remove(delUser);
				save();
				return;
			}
		}
	}

	public void ShowAllvCodes() {
		for (VCode vCode : vCodes)
			System.out.println(vCode);
	}

	public String getLatestVCode() {
		return String.valueOf(vCodes.get(vCodes.size() - 1).getvCode());
	}
}

class VCodeGenerator {
	Random r;
	final long mod = (long) 1e10;

	public VCodeGenerator(int seed) {
		r = new Random(seed);
	}

	public long getNextVCode() {
		long ret = r.nextLong();
		ret %= mod;
		if (ret < 0)
			ret += mod;
		return ret;
	}

}
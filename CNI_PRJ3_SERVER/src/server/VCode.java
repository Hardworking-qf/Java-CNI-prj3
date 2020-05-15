package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String mac;
	long t;
	static final long gapTime = 30 * 60 * 1000l;

	public User(String mac) {
		this.mac = mac;
		t = new Date().getTime();
	}

	public long getT() {
		return t;
	}

	public boolean macEquals(String mac) {
		return this.mac.equals(mac);
	}

	public void updateTime() {
		t = new Date().getTime();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User) o;
		return mac.equals(user.mac);
	}

	@Override
	public int hashCode() {
		return mac.hashCode();
	}
}

public class VCode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long vCode;
	private int userNum;
	private ArrayList<User> users;

	public VCode(long vCode, int userNum) {
		this.vCode = vCode;
		this.userNum = userNum;
		users = new ArrayList<User>();
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public long getvCode() {
		return vCode;
	}

	public boolean addNewUser(String mac) {
		clearOverTime();
		if (users.size() < userNum) {
			// System.out.println(Arrays.toString(mac));
			User u = new User(mac);
			users.add(u);
			return true;
		}
		return false;
	}

	public void clearOverTime() {
		long t = new Date().getTime();
		ArrayList<User> newUsers = new ArrayList<User>();
		for (User u : users) {
			if (t - u.getT() < User.gapTime) {
				newUsers.add(u);
			}
		}
		users = newUsers;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		VCode vCode1 = (VCode) o;
		return vCode == vCode1.vCode;
	}

	@Override
	public int hashCode() {
		return Objects.hash(vCode);
	}
	
	public String toString() {
		return vCode+"\t"+users.size()+" / "+userNum;
	}
}
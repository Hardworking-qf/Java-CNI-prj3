package server;
import java.io.*;
import java.util.List;

interface FileUtil {
	void LoadFile(List<VCode> vCodes);

	void SaveFile(List<VCode> vCodes) throws IOException;
}

class SequentialFileUtil implements FileUtil {
	File file;

	SequentialFileUtil(File file) {
		if (file != null) {
			this.file = file;
		} else
			throw new NullPointerException("无法找到文件");
	}

	@Override
	public void LoadFile(List<VCode> vCodes) {
		ObjectInputStream input;
		try {
			if (!file.exists())
				return;
			input = new ObjectInputStream(new FileInputStream(file));
			Object o;
			while ((o = input.readObject()) != null) {
				VCode tmp = (VCode) o;
				vCodes.add(tmp);
			}
			input.close();
		} catch (IOException | ClassNotFoundException ex) {
			ex.printStackTrace();
			System.exit(1);
		}

	}

	@Override
	public void SaveFile(List<VCode> vCodes) throws IOException {
		ObjectOutputStream output;
		try {
			output = new ObjectOutputStream(new FileOutputStream(file));
			for (VCode v : vCodes) {
				output.writeObject(v);
			}
			output.writeObject(null);// 防止EOFException
			output.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new IOException("保存文件错误，请尝试手动保存");
		}
	}
}
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String fromFile(File file) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		// Change MD5 to SHA1 to get SHA checksum
		// MessageDigest md = MessageDigest.getInstance("SHA1");

		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
			byte[] dataBytes = new byte[1024];
			int nread = 0;
			while ((nread = bis.read(dataBytes)) != -1) {
				md.update(dataBytes, 0, nread);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println(file + " could not be found");
			return null;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}

		byte[] mdbytes = md.digest();

		// convert the byte to hex format
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < mdbytes.length; i++) {
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}
}

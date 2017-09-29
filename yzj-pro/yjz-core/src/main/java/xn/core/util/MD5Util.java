package xn.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.codec.Hex;

public class MD5Util {
	
	public static String generateMD5code(String password) {
		
		char[] encode=null;
		
		try {
            // 创建加密对象
			MessageDigest messageDigest=MessageDigest.getInstance("md5");
			
            // 加密
			byte[] digest = messageDigest.digest(password.getBytes());

			encode = Hex.encode(digest);
			
			return  new String(encode);	
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return null;
			
	}
}

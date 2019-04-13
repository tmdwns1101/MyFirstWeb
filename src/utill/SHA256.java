package utill;

import java.security.MessageDigest;

public class SHA256 {
	
	//특정한 입력 값을 SHA256 해싱 하는것
	public static String getSHA256(String input) {
		StringBuffer result = new StringBuffer();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] salt = "My SJ's Salt".getBytes(); //보안 강화 목적 //개개인 마다 다르게 해주는것이 좋음
			digest.reset();
			digest.update(salt);
			byte[] chars = digest.digest(input.getBytes("UTF-8"));
			for(int i = 0;i < chars.length;i++) {
				String hex = Integer.toHexString(0xff & chars[i]);
				if(hex.length() == 1) result.append("0");
				result.append(hex);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}

}

package utilities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PasswordHashing {
	
	private final static String pepper = "acme";
	
	public String makeSalt() {
		int leftLimit = 97;
		int rightLimit = 122;
		int targetStringLength = 4;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomInt);
		}
		String saltString = buffer.toString();
		return saltString;
	}
	
	public String encryptPassword(String salt, String pass) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			
			//Seasons our hash!
			StringBuilder buffer = new StringBuilder(pass);
			buffer.append(salt);
			buffer.append(pepper);
			
			byte[] mDig = md.digest(buffer.toString().getBytes());
			BigInteger nu = new BigInteger(1, mDig);
			String hashed = nu.toString(16);
			while (hashed.length() < 32) {
				hashed = "0" + hashed;
			}
			
			return hashed;
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}

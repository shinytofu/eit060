package server;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SecurityTools {
	
	public static byte[] getPasswordHash(String password, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException{
		if (salt == null){
			final Random r = new SecureRandom();
			salt = new byte[16];
			r.nextBytes(salt);
		}
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000, 256);
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		byte[] encoded = f.generateSecret(spec).getEncoded();
		byte[] toReturn = new byte[encoded.length+16];
		for(int i = 0; i<16; i++){
			toReturn[i] = salt[i];
		}
		for (int i=16; i<encoded.length+16;i++){
			toReturn[i] = encoded[i-16];
		}
		return toReturn;
	}
}

package server;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SecurityTools {
	
	public static byte[] getPasswordHash(String password) throws InvalidKeySpecException, NoSuchAlgorithmException{
		final Random r = new SecureRandom();
		byte[] salt = new byte[16];
		r.nextBytes(salt);
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000, 256);
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		return f.generateSecret(spec).getEncoded();
	}
}

package utilesGUIxSeguridad;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
algoritmo PBKDF2 (Password-Based Key Derivation Function 2)
*/
public class PBKDF2WithHmacSHA1 {

    public static String generarPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int NumIteraciones = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt().getBytes();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, NumIteraciones, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return NumIteraciones + ":" + toHex(salt) + ":" + toHex(hash);
    }
    
    public static boolean ValidarPassword(String passwordInicial, String passwordGuardada) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] partes = passwordGuardada.split(":");
        if(partes.length > 1) {
            int iteraciones = Integer.parseInt(partes[0]);
            byte[] salt = fromHex(partes[1]);
            byte[] hash = fromHex(partes[2]);

            PBEKeySpec spec = new PBEKeySpec(passwordInicial.toCharArray(), salt, iteraciones, hash.length * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] testHash = skf.generateSecret(spec).getEncoded();

            int diff = hash.length ^ testHash.length;
            for (int i = 0; i < hash.length && i < testHash.length; i++) {
                diff |= hash[i] ^ testHash[i];
            }
            return diff == 0;
        } else {
            return false;
        }
    }
    
    private static String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}

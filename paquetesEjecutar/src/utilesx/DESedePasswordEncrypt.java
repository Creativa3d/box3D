/*
 * NewClass.java
 *
 * Created on 22 de agosto de 2008, 18:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesx;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import sun.misc.*;
 
public class DESedePasswordEncrypt {

    private String characterEncoding;
    private Cipher encryptCipher;
    private Cipher decryptCipher;
    private BASE64Encoder base64Encoder = new BASE64Encoder();
    private BASE64Decoder base64Decoder = new BASE64Decoder();

    public DESedePasswordEncrypt(byte[] keyBytes, byte[] ivBytes, String characterEncoding) throws Exception {
        SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        this.characterEncoding = characterEncoding;
        this.encryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding", "SunJCE");
        this.encryptCipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key, iv);
        this.decryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding", "SunJCE");
        this.decryptCipher.init(javax.crypto.Cipher.DECRYPT_MODE, key, iv);
    }
    
    public synchronized String encrypt(String password) throws Exception {
        byte[] passwordBytes = password.getBytes(characterEncoding);
        byte[] encryptedPasswordBytes = this.encryptCipher.doFinal(passwordBytes);
        String encodedEncryptedPassword = this.base64Encoder.encode(encryptedPasswordBytes);
        return encodedEncryptedPassword;
    }
    
    public synchronized String decrypt(String encodedEncryptedPassword) throws Exception  {
        byte[] encryptedPasswordBytes = this.base64Decoder.decodeBuffer(encodedEncryptedPassword);
        byte[] passwordBytes = this.decryptCipher.doFinal(encryptedPasswordBytes);
        String recoveredPassword = new String(passwordBytes, characterEncoding);
        return recoveredPassword;
    }
    
    // Simple test 
    public static void main(String[] args)
    {
        try
        {
            // Make sure SUN JCE are a provider
            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            
            // The DES EDE Key - any 24 bytes will do though beware of weak keys.
            // This could be read from a file.
            final byte[] DESedeKeyBytes =
            {
                0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
                0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10,
                0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
            };
            
            // IV For CBC mode
            // Again, could be read from a file.
            final byte[] ivBytes =
            {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};
            
            // Password encrypt agent that assumes the password is only ASCII characters
            // Change character encoding as required.
            DESedePasswordEncrypt passwordEncryptAgent = new DESedePasswordEncrypt(DESedeKeyBytes, ivBytes, "ASCII");
            
            // Get the password to encrypt from the command line
            String password = (args.length == 0)? "The quick brown fox jumps over the lazy dog." : args[0];
            
            // Since the example password encrypter is setup to convert the password to ASCII bytes
            // before encrypting, make sure that the password is in fact ASCII (in this case printable ASCII).
            if (!password.matches("[ -~]{6,}"))
                throw new IllegalArgumentException("Password must be printable ASCII");
            System.out.println("Password ....................[" + password + "]");
            
            String encodedEncryptedPassword = passwordEncryptAgent.encrypt(password);
            System.out.println("Encoded encrypted password ..[" + encodedEncryptedPassword + "]");
            
            String recoveredPassword = passwordEncryptAgent.decrypt(encodedEncryptedPassword);
            System.out.println("Recovered password ..........[" + recoveredPassword + "]");
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
    }

}

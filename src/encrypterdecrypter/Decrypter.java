package encrypterdecrypter;
import javax.crypto.Cipher;
import java.security.spec.KeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKey;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKeyFactory;


import javax.crypto.spec.IvParameterSpec;

class Decrypter {
	
	private Cipher dcipher;
    private SecretKey key;

    Decrypter(String passPhrase, String salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(GblConsts.ENCRYPTALGO);
        KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), salt.getBytes(), GblConsts.ITERATIONS, GblConsts.KEYSTRENGTH);
        SecretKey tmp = factory.generateSecret(spec);
        key = new SecretKeySpec(tmp.getEncoded(), "AES");
        dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    }


    String decrypt(String base64EncryptedData, String IV) throws Exception {
        dcipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(Base64.getDecoder().decode(IV)));
        byte[] decryptedData = Base64.getDecoder().decode(base64EncryptedData);
        byte[] utf8 = dcipher.doFinal(decryptedData);
        return new String(utf8, "UTF8");
    }
}
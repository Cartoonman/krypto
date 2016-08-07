package encrypterdecrypter;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

class Encrypter {
	
    private Cipher dcipher;
    
    private SecureRandom random = new SecureRandom();
    private String lesalt = null; 
    private SecretKey key;
	
	
    Encrypter(String passPhrase) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(GblConsts.ENCRYPTALGO);
        KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), generateRandomSalt().getBytes(), GblConsts.ITERATIONS, GblConsts.KEYSTRENGTH);
        SecretKey tmp = factory.generateSecret(spec);
        key = new SecretKeySpec(tmp.getEncoded(), "AES");
        dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    }
    
    private String generateRandomSalt(){
    	String saltysalt = new BigInteger(random.nextInt(130)+36, random).toString(Character.MAX_RADIX);
    	String saltiersalt = new BigInteger(36, random).toString(Character.MAX_RADIX);
    	for(int i = 0; i < saltysalt.length(); i++){
    		char oboi = saltysalt.charAt(i);
    		String k = String.valueOf(oboi);
    		if(random.nextBoolean())
    			k = k.toUpperCase();
    		saltiersalt = saltiersalt + k;
    	}
    	this.lesalt = saltiersalt;
    	return saltiersalt;
    }
    
    String encrypt(String data) throws Exception {
        dcipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters params = dcipher.getParameters();
        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] utf8EncryptedData = dcipher.doFinal(data.getBytes());
        Base64.getEncoder().encodeToString(utf8EncryptedData);
        String base64EncryptedData = Base64.getEncoder().encodeToString(utf8EncryptedData);

        base64EncryptedData = lesalt + ":" + Base64.getEncoder().encodeToString(iv) + ":" + base64EncryptedData;
        base64EncryptedData = base64EncryptedData.replaceAll("=", "");
        
        return base64EncryptedData;
    }
    
}

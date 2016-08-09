package encrypterdecrypter;

import java.io.Console;

// Standalone PBKDF2 Encrypt/Decrypt.
//TODO variable iteration possibility.
public class mainEncDec {
    public static void main(String args[]) throws Exception {
    	 Console cons;
    	 char[] passwd;
    	 String mp = null;
    	 if ((cons = System.console()) != null &&
    	     (passwd = cons.readPassword("[%s]", "Master Password:")) != null) {
    		 mp = String.valueOf(passwd);
    	     java.util.Arrays.fill(passwd, ' ');
    	 }
    	if (args[0].toLowerCase().equals("encrypt")){
    		Encrypter encrypter = new Encrypter(mp);
    		char[] pwd;
       	 	if ((cons = System.console()) != null &&
        	     (pwd = cons.readPassword("[%s]", "Password to be Encrypted:")) != null) {
    		System.out.println(encrypter.encrypt(String.valueOf(pwd)));
    		java.util.Arrays.fill(pwd, ' ');
       	 	}
    	}
    	else if (args[0].toLowerCase().equals("decrypt")){
    		String[] input = args[1].split(":");
    		Decrypter decrypter = new Decrypter(mp,input[0]);
    		System.out.println(decrypter.decrypt(input[2],input[1]));
    	}
    }
}

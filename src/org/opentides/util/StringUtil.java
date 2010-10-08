package org.opentides.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

import org.opentides.InvalidImplementationException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StringUtil {
    
	private static String zeros = "0000000000";
	private static Random random = new Random((new Date()).getTime());
        
    public static boolean isEmpty(String obj) {
    	if ((obj==null) || (obj.trim().length()==0))
    		return true;
    	else
    		return false;
    }
    
    public static String toFixedString(int value, int length) {
    	String val = Integer.toString(value);
    	int diff = length-val.length();
    	if (diff>0)
    		return (zeros.substring(10-diff)+val);
    	else
    		return val;
    }
    
    public static String removeHTMLTags(String html) {
    	return  html.replaceAll("<(.*?)>"," ")
    				.replaceAll("\\s+"," ");
    }
    
    public static int convertToInt(String str, int defValue) {
		int value = defValue;
		try {
			value = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			// do nothing...
		}
		return value;
    }
    
    public static long convertToLong(String str, long defValue) {
		long value = defValue;
		try {
			value = Long.parseLong(str);
		} catch (NumberFormatException nfe) {
			// do nothing...
		}
		return value;
    }
    
    public static Double convertToDouble(String str, double defValue){
    	Double doub = defValue;
		try {
			doub = Double.parseDouble(str);			
		} catch (NumberFormatException nfe) {
			// do nothing...
		}
    	return doub;
    }

    /**
     * generates an alphanumeric string based on specified length.
     * @param length # of characters to generate
     * @return random string
     */
    public static String generateRandomString(int length) {
    	char[] values = {'a','b','c','d','e','f','g','h','i','j',
    					 'k','l','m','n','o','p','q','r','s','t',
    					 'u','v','w','x','y','z','0','1','2','3',
    					 '4','5','6','7','8','9'};
    	String out = "";
    	for (int i=0;i<length;i++) {
        	int idx=random.nextInt(values.length);
    		out += values[idx];
    	}
    	return out;
    }
    
 
	/**
	 * Encrypt password by using SHA-256 algorithm, encryptedPassword length is 32 bits
	 * @param clearTextPassword
	 * @return
	 * @throws NoSuchAlgorithmException
	 * reference http://java.sun.com/j2se/1.4.2/docs/api/java/security/MessageDigest.html
	 */
	public static String getEncryptedPassword(String clearTextPassword)	 {		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(clearTextPassword.getBytes());
			return new sun.misc.BASE64Encoder().encode(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
    /**
     * Encrypts the string along with salt 
     * @param userId
     * @return
     * @throws Exception
     */
	public static String encrypt(String userId) {	
		BASE64Encoder encoder = new BASE64Encoder();

		// let's create some dummy salt
		byte[] salt = new byte[8];
		random.nextBytes(salt);
		return encoder.encode(salt)+
			encoder.encode(userId.getBytes());
	}

    	
	/**
	 * Decrypts the string and removes the salt
	 * @param encryptKey
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String encryptKey) {
		// let's ignore the salt
		if (!StringUtil.isEmpty(encryptKey) &&
				encryptKey.length() > 12) {
			String cipher = encryptKey.substring(12);
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				return new String(decoder.decodeBuffer(cipher));
			} catch (IOException e) {
				throw new InvalidImplementationException(
						"Failed to perform decryption for key ["+encryptKey+"]",e);
			}
		} else
			return null;				
	}
}

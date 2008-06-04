package com.ideyatech.core.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.ideyatech.core.InvalidImplementationException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StringUtil {
    
	private static String zeros = "0000000000";
	private static Random random = new Random((new Date()).getTime());
	
	public static String dateToString(Date obj, String format) {
    	if (obj == null)
    		return "";    	
        SimpleDateFormat dtFormatter = new SimpleDateFormat(format);
        return dtFormatter.format(obj);	
	}
    
    public static Date stringToDate(String strDate, String format) throws ParseException {
        SimpleDateFormat dtFormatter = new SimpleDateFormat(format);
        if (StringUtil.isEmpty(strDate))
        	throw new ParseException("Cannot convert empty string to Date.", 0);
       	return dtFormatter.parse(strDate.trim());
    }
    
    public static Date convertFlexibleDate(String strDate, String[] formats) throws ParseException {
    	if (StringUtil.isEmpty(strDate))
    		return null;    	
    	for (int i=0; i<formats.length; i++) {
            try {
            	SimpleDateFormat dtFormatter = new SimpleDateFormat(formats[i]);
            	dtFormatter.setLenient(false);
            	return dtFormatter.parse(strDate.trim());
            } catch (ParseException e) {
            	// do nothing... try other format
            }    	
    	}
    	// we are unable to convert
    	throw new ParseException("No matching date format for "+strDate, 0);    	
    }

    public static String convertShortDate(Date obj) {
    	return dateToString(obj, "yyyyMMdd");
    }
    
    public static Date convertShortDate(String str) throws ParseException {
    	return stringToDate(str, "yyyyMMdd");
    }

    public static Date convertShortDate(String str, Date defaultDate) {
    	try {
    		return stringToDate(str, "yyyyMMdd");
    	} catch (ParseException pex) {
    		return defaultDate;
    	}
    }
 
    public static Date convertFlexibleDate(String strDate) throws ParseException {
    	if (StringUtil.isEmpty(strDate))
    		throw new ParseException("Cannot convert empty string to Date.", 0);

    	String[] formats = { "MM/dd/yyyy",
    					     "MM-dd-yyyy", 
    					     "yyyyMMdd",
    					     "yyyy-MM-dd",
						     "MMM dd yyyy",
						     "MMM dd, yyyy",
    					     "MMM yyyy",
						     "MM/yyyy",
						     "MM-yyyy",
						     "yyyy"};

   		return convertFlexibleDate(strDate, formats);
    }   
        
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
    
    public static boolean compareNullableDates(Date date1, Date date2) {
    	if ((date1==null) && (date2==null))
    		return true;
    	if (date1!=null) {
    		if (date1.equals(date2))
    			return true;
    		else
    			return false;
    	}
    	return false;
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
						"StringUtil","decrypt",encryptKey,
						"Failed to perform decryption",e);
			}
		} else
			return null;				
	}
}

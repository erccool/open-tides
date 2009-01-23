package org.opentides.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
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
    
}

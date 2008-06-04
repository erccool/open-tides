/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * FileUtil.java
 * Created on Jan 30, 2008, 1:48:31 PM
 */
package com.ideyatech.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.ideyatech.core.InvalidImplementationException;

/**
 * @author allanctan
 *
 */
public class FileUtil {
	
	private static Logger _log = Logger.getLogger(FileUtil.class);

	/**
	 * Helper class to read certain file and return contents as string.
	 * The file must be located relative to classpath.
	 * @param filename
	 * @return
	 */
	public static String readFile(String filename) {
		try {
			InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(filename);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    		String line = null;
    		StringBuffer ret = new StringBuffer();
    		while ((line = reader.readLine())!=null) {    			
    			ret.append(line);
    		}
    		reader.close();
    		return ret.toString();
    	} catch (FileNotFoundException fe) {
    		String msg = "Failed to find file ["+filename+"].";
    		_log.error(msg,fe);
    		throw new InvalidImplementationException("FileUtil", "readFile", 
    				filename, msg, fe);    	
    	} catch (IOException ioe) {
    		String msg = "Cannot access file ["+filename+"].";
    		_log.error(ioe,ioe);
    		throw new InvalidImplementationException("FileUtil", "readFile", 
    				filename, msg, ioe);   
    	} 
	}
	
	/**
	 * Helper class to write specified content to certain file.
	 * @param filename
	 * @param content
	 * @return true if writing is successful
	 */
	public static void writeFile(String filename, String content) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(filename));
			writer.write(content);
			writer.close();
		} catch (IOException ioe) {
			String msg = "Cannot access file ["+filename+"].";
    		_log.error(ioe,ioe);
    		throw new InvalidImplementationException("FileUtil", "readFile", 
    				filename, msg, ioe);  
		}
	}
	
}

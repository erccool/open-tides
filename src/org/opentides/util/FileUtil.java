/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * FileUtil.java
 * Created on Jan 30, 2008, 1:48:31 PM
 */
package org.opentides.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.opentides.InvalidImplementationException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author allanctan
 * 
 */
public class FileUtil {

	private static Logger _log = Logger.getLogger(FileUtil.class);

	/**
	 * Helper class to read certain file and return contents as string. The file
	 * must be located relative to classpath.
	 * 
	 * @param filename
	 * @return
	 */
	public static String readFile(String filename) {
		try {
			InputStream is = FileUtil.class.getClassLoader()
					.getResourceAsStream(filename);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String line = null;
			StringBuffer ret = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				ret.append(line);
			}
			reader.close();
			return ret.toString();
		} catch (NullPointerException npe) {
			String msg = "Failed to find file [" + filename + "].";
			_log.error(msg, npe);
			throw new InvalidImplementationException(msg, npe);
		} catch (FileNotFoundException fe) {
			String msg = "Failed to find file [" + filename + "].";
			_log.error(msg, fe);
			throw new InvalidImplementationException(msg, fe);
		} catch (IOException ioe) {
			String msg = "Cannot access file [" + filename + "].";
			_log.error(ioe, ioe);
			throw new InvalidImplementationException(msg, ioe);
		}
	}

	/**
	 * Helper class to write specified content to certain file.
	 * 
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
			String msg = "Cannot access file [" + filename + "].";
			_log.error(ioe, ioe);
			throw new InvalidImplementationException(msg, ioe);
		}
	}

	/**
	 * Helper class to append specified content to certain file.
	 * 
	 * @param filename
	 * @param content
	 * @return true if writing is successful
	 */
	public static void appendFile(String filename, String content) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(filename, true));
			writer.write(content);
			writer.close();
		} catch (IOException ioe) {
			String msg = "Cannot access file [" + filename + "].";
			_log.error(ioe, ioe);
			throw new InvalidImplementationException(msg, ioe);
		}
	}

	/**
	 * Creates a directory if not yet existing
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static File createDirectory(String path) throws IOException {
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();
		}

		return file;
	}

	/**
	 * Saves multipart file into disk
	 * 
	 * @param multipartFile
	 * @param dest
	 * @return
	 * @throws IOException
	 */
	public static boolean copyMultipartFile(final MultipartFile multipartFile,
			File dest) throws IOException {
		InputStream inStream = multipartFile.getInputStream();
		OutputStream outStream = new FileOutputStream(dest);

		try {
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, len);
			}
			outStream.flush();
			return true;
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (outStream != null) {
				outStream.close();
			}

			if (inStream != null) {
				inStream.close();
			}
		}
	}
}

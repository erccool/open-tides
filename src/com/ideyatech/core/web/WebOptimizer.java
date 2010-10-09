package com.ideyatech.core.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

public class WebOptimizer {
		
	/* options from YUI Compressor */
	private boolean verbose;
	private boolean munge;
	private int linebreakpos; 
	private boolean preserveAllSemiColons;
	private boolean disableOptimizations;
	private String charset;
	
	/**
	 * Helper class to read certain file and return contents as string.
	 * The file must be located relative to classpath.
	 * @param filename
	 * @return
	 */
	private String readFile(String filename) throws Exception {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
    		String line = null;
    		StringBuffer ret = new StringBuffer();
    		while ((line = reader.readLine())!=null) {    			
    			ret.append(line+"\n");
    		}
    		reader.close();
    		return ret.toString();
    	} catch (FileNotFoundException fe) {
    		String msg = "Failed to find file ["+filename+"].";
    		fe.printStackTrace();
    		throw fe;
    	} catch (IOException ioe) {
    		String msg = "Cannot access file ["+filename+"].";
    		ioe.printStackTrace();
    		throw ioe;
    	}
	}
	
	/**
	 * Helper class to append specified content to certain file.
	 * @param filename
	 * @param content
	 * @return true if writing is successful
	 */
	private void writeFile(String filename, String content, boolean append) throws Exception {
		BufferedWriter writer;
		System.out.println("Writing to "+filename);
		try {
			File f = new File(filename);
			f.createNewFile();
			writer = new BufferedWriter(new FileWriter(filename,append));
			writer.write(content);
			writer.close();
		} catch (IOException ioe) {
			String msg = "Cannot access file ["+filename+"].";
			ioe.printStackTrace();
			throw ioe;
		}
	}
	
	
	private void compressJS (Reader in, Writer out) throws IOException {
        
		try {
            JavaScriptCompressor compressor = new JavaScriptCompressor(in, new ErrorReporter() {

                public void warning(String message, String sourceName,
                        int line, String lineSource, int lineOffset) {
                    if (line < 0) {
                        System.out.println("\n[WARNING] " + message);
                    } else {
                        System.out.println("\n[WARNING] " + line + ':' + lineOffset + ':' + message);
                    }
                }

                public void error(String message, String sourceName,
                        int line, String lineSource, int lineOffset) {
                    if (line < 0) {
                        System.out.println("\n[ERROR] " + message);
                    } else {
                        System.out.println("\n[ERROR] " + line + ':' + lineOffset + ':' + message);
                    }
                }

                public EvaluatorException runtimeError(String message, String sourceName,
                        int line, String lineSource, int lineOffset) {
                    error(message, sourceName, line, lineSource, lineOffset);
                    return new EvaluatorException(message);
                }
            });

            // Close the input stream first, and then open the output stream,
            // in case the output file should override the input file.
            in.close(); in = null;

            compressor.compress(out, linebreakpos, munge, verbose,
                    preserveAllSemiColons, disableOptimizations);
        } catch (EvaluatorException e) {

            e.printStackTrace();
            // Return a special error code used specifically by the web front-end.
            System.exit(2);

        } finally {

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	private void compressCSS(Reader in, Writer out) throws IOException {
        
        try {
			CssCompressor compressor = new CssCompressor(in);
	
	        // Close the input stream first, and then open the output stream,
	        // in case the output file should override the input file.
	        in.close(); in = null;
	
	        compressor.compress(out, linebreakpos);
        } finally {

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }	        
	}
	
    public static boolean isEmpty(String obj) {
    	if ((obj==null) || (obj.trim().length()==0))
    		return true;
    	else
    		return false;
    }
    
    /**
     * This helper method retrieves all the matching pattern in sequence
     * for the given regex matcher. Used on both js and css pattern matching.
     * @param groupMatcher
     * @param matcher
     * @return
     */
    private List<List<String>> getWordSequence(Matcher groupMatcher, Pattern innerPattern) {
    	List<List<String>> master = new ArrayList<List<String>>();
		while (groupMatcher.find()) {
			String group = groupMatcher.group();
			Matcher matcher = innerPattern.matcher(group);
			List<String> sequence = new ArrayList<String>();
			while (matcher.find()) {
				String jsName = matcher.group(1);
				sequence.add(jsName);					
			}
			if (!sequence.isEmpty()) master.add(sequence);
		} 
		return master;
    }
    
    private String generateHash(List<String> sequence) {
    	StringBuffer buf = new StringBuffer();
    	for (String item:sequence) {
    		buf.append(item);
    	}
    	try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(buf.toString().getBytes());
			return (new BigInteger(md5.digest()).toString(16)).toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return buf.toString();
		}    	
    }
	
	/**
	 * This method will process the given HTML file by reading its contents and parse js/css tags.
	 * Tags that are in sequence will be merged into 1 file and minimized accordingly.
	 * 
	 * @param inputFilename
	 * @param outputFilename
	 * @param verbose
	 * @param munge
	 * @param linebreakpos
	 * @param preserveAllSemiColons
	 * @param disableOptimizations
	 * @param charset
	 */
	public void processHTML(String inputFilename, String outputFilename) throws Exception {
		String html = readFile(inputFilename);
		String outHtml = html;
		Matcher sGroupMatcher = WebOptimizer.SCRIPT_GROUP_PATTERN.matcher(html);
		File f = new File(inputFilename);
		String abs = f.getAbsolutePath();
		String path =  abs.substring(0,abs.lastIndexOf("/"))+"/";
		int ctr =0;
		while (sGroupMatcher.find()) {
			String group = sGroupMatcher.group();
			Matcher sMatcher = WebOptimizer.SCRIPT_PATTERN.matcher(group);
			StringBuffer mergedJS = new StringBuffer("");
			String mergedJSName = "";
			List<String> sequence = new ArrayList<String>();
			int filesCnt = 0;
			while (sMatcher.find()) {
				try {
					String jsName =sMatcher.group(1);
					String js = readFile(path+jsName);
					if (isEmpty(mergedJSName)) {
						mergedJSName = jsName.substring(0,jsName.lastIndexOf("."))+"-min"+ ctr++ +".js";
						outHtml = outHtml.replace(sMatcher.group(1), mergedJSName);
						File t = new File(path+mergedJSName);
						t.delete();
					} else 
						outHtml = outHtml.replace(sMatcher.group(), "");
					System.out.println("Merging "+jsName);
					sequence.add(jsName);
					mergedJS.append(js);
					filesCnt++;
				} catch (Exception e) {
					System.out.println("ERROR: Failed to include "+sMatcher.group(1));
				}
			}			
			// now let's minify the merged output
			if (mergedJS.length()>0) {
				String hashedJSName = mergedJSName.substring(0, mergedJSName.lastIndexOf("/")+1)+generateHash(sequence)+".js";
				outHtml = outHtml.replace(mergedJSName, hashedJSName);
				System.out.println(filesCnt + " files merged to " + hashedJSName);
				Reader in = new StringReader(mergedJS.toString());
				Writer out = new BufferedWriter(new FileWriter(path+hashedJSName,false));
				System.out.println("Minimizing "+hashedJSName);
				compressJS(in, out);
			}
		}
		// JS complete... let's process CSS naman
		Matcher lGroupMatcher = WebOptimizer.LINK_GROUP_PATTERN.matcher(html);
		while (lGroupMatcher.find()) {
			String group = lGroupMatcher.group();
			Matcher lMatcher = WebOptimizer.LINK_PATTERN.matcher(group);
			String mergedCSSName = "";
			StringBuffer mergedCSS = new StringBuffer("");
			List<String> sequence = new ArrayList<String>();
			int filesCnt = 0;
			while (lMatcher.find()) {
				try {
					String cssName =lMatcher.group(1);
					String css = readFile(path+cssName);
					if (isEmpty(mergedCSSName)) {
						mergedCSSName = cssName.substring(0,cssName.lastIndexOf("."))+"-min"+ ctr++ +".css";
						outHtml = outHtml.replace(lMatcher.group(1), mergedCSSName);
						File t = new File(path+mergedCSSName);
						t.delete();
					} else 
						outHtml = outHtml.replace(lMatcher.group(), "");
					System.out.println("Processing "+cssName);
					mergedCSS.append(css);
					sequence.add(cssName);
					filesCnt++;
				} catch (Exception e) {
					System.err.println("Failed to include "+lMatcher.group(1));
				}
			}
			// now let's minify the merged output
			if (mergedCSS.length()>0) {
				String hashedCSSName = mergedCSSName.substring(0, mergedCSSName.lastIndexOf("/")+1)+generateHash(sequence)+".css";
				outHtml = outHtml.replace(mergedCSSName, hashedCSSName);
				System.out.println(filesCnt + " files merged to " + hashedCSSName);
				Reader in = new StringReader(mergedCSS.toString());
				Writer out = new BufferedWriter(new FileWriter(path+hashedCSSName,false));
				System.out.println("Optimizing for "+hashedCSSName);
				compressCSS(in, out);
			}
		}
		// tapos let's write the html output
		if (isEmpty(outputFilename)) {
			// let's force delete the min
			int idx = inputFilename.lastIndexOf(".");
			outputFilename = inputFilename.substring(0,idx)+"-min"+inputFilename.substring(idx);
		}
		File t = new File(outputFilename);
		t.delete();
		writeFile(outputFilename, outHtml, true);
	}
    
	public static final String SCRIPT_GROUP_EXPR = 
		"(<script[^>]+src=[\"\'].*?[\"'][^>]*>\\s*</script>\\s*)+";
	
	public static final Pattern SCRIPT_GROUP_PATTERN = Pattern.compile(
			SCRIPT_GROUP_EXPR, Pattern.CASE_INSENSITIVE
			| Pattern.MULTILINE | Pattern.DOTALL);
	
	public static final String SCRIPT_EXPR = 
		"<script[^>]+src=[\"\'](.*?)[\"'][^>]*>\\s*</script>";
	
	public static final Pattern SCRIPT_PATTERN = Pattern.compile(
			SCRIPT_EXPR, Pattern.CASE_INSENSITIVE
			| Pattern.MULTILINE | Pattern.DOTALL);
	
	public static final String LINK_GROUP_EXPR = 
		"(<link[^>]+type=[\"\']+text/css[\"\'][^>]*>\\s*)+";
	
	public static final Pattern LINK_GROUP_PATTERN = Pattern.compile(
			LINK_GROUP_EXPR, Pattern.CASE_INSENSITIVE
			| Pattern.MULTILINE | Pattern.DOTALL);

	public static final String LINK_EXPR = 
		"<link[^>]+href=[\"\'](.*?)[\"\'][^>]*>";

	public static final Pattern LINK_PATTERN = Pattern.compile(
			LINK_EXPR, Pattern.CASE_INSENSITIVE
			| Pattern.MULTILINE | Pattern.DOTALL);
	/**
	 * @return the verbose
	 */
	public boolean isVerbose() {
		return verbose;
	}

	/**
	 * @param verbose the verbose to set
	 */
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	/**
	 * @return the munge
	 */
	public boolean isMunge() {
		return munge;
	}

	/**
	 * @param munge the munge to set
	 */
	public void setMunge(boolean munge) {
		this.munge = munge;
	}

	/**
	 * @return the linebreakpos
	 */
	public int getLinebreakpos() {
		return linebreakpos;
	}

	/**
	 * @param linebreakpos the linebreakpos to set
	 */
	public void setLinebreakpos(int linebreakpos) {
		this.linebreakpos = linebreakpos;
	}

	/**
	 * @return the preserveAllSemiColons
	 */
	public boolean isPreserveAllSemiColons() {
		return preserveAllSemiColons;
	}

	/**
	 * @param preserveAllSemiColons the preserveAllSemiColons to set
	 */
	public void setPreserveAllSemiColons(boolean preserveAllSemiColons) {
		this.preserveAllSemiColons = preserveAllSemiColons;
	}

	/**
	 * @return the disableOptimizations
	 */
	public boolean isDisableOptimizations() {
		return disableOptimizations;
	}

	/**
	 * @param disableOptimizations the disableOptimizations to set
	 */
	public void setDisableOptimizations(boolean disableOptimizations) {
		this.disableOptimizations = disableOptimizations;
	}

	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @param charset the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}
}

/**
 * 
 */
package org.hightides.annotations.processor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.hightides.annotations.bean.SyncMode;
import org.hightides.annotations.filter.ProcessorFilter;
import org.hightides.annotations.util.PackageHelper;
import org.opentides.util.FileUtil;

/**
 * This is a generic template processor that retrieves velocity templates and
 * clones the corresponding source codes.
 * 
 * @author allantan
 */
public class CloningProcessor implements Processor {

	private static Logger _log = Logger.getLogger(CloningProcessor.class);

	public static final String ENCODING = "utf-8";

	// helper class for retrieving package, path or file locations
	private PackageHelper packager;
	
	// filter for inserting values in param
	private ProcessorFilter filter;
	
	// synchronization mode
	private SyncMode syncMode;
	
	// original data backed-up prior to rewriting
	private String backupData;

	/**
	 * Constructor with outputFolder defaulted to same directory of model
	 * @param templatePath
	 */
	public CloningProcessor(String templateFolder) {
		this(templateFolder,".");
	}

	/**
	 * Constructor
	 * @param templatePath
	 */
	public CloningProcessor(String templateFolder, String outputFolder) {
		packager = new PackageHelper(templateFolder, outputFolder);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opentides.annotations.processor.Processor#execute(java.lang.Class,
	 * java.util.Map)
	 */
	public void execute(Map<String, Object> params) {
		// recurse all the subdirectories in the templatePath
		File filePath = packager.getTemplateFolder();
		recurseTemplates(filePath, params);
	}

	/**
	 * Recurse all the directories under templatePath and clone all vm files.
	 */
	private void recurseTemplates(File path, Map<String, Object> params) {
		if (path.isDirectory()) {
			// let's recurse subdirectories
			String[] children = path.list();
			for (int i = 0; i < children.length; i++) {
				recurseTemplates(new File(path, children[i]), params);
			}
		} else {
			// this is a file, let's clone.
			clone(path, params);
		}
	}

	/**
	 * Clones the velocity file and injects params accordingly
	 */
	private void clone(File path, Map<String, Object> params) {
		// Clone only velocity files
		if (path.isFile() && path.getName().endsWith(".vm")) {
			// Let's render the page
			FileWriter output = createOutputFile(path, params);
			if (output==null) {
				_log.error("Failed to create output file.");
				return;
			}
			try {
				// execute the filter
				if (filter!=null)
					filter.doFilter(path, params);
				// Put all params to context
				VelocityContext context = new VelocityContext();
				for (String key : params.keySet()) {
					context.put(key, params.get(key));
				}
				// Get the relative path for velocity template merging
				String relativePath = PackageHelper.getTemplateForMerge(path);
				// Now, let's the do merging
				if (org.opentides.util.StringUtil.isEmpty(backupData)) 
					Velocity.mergeTemplate(relativePath,ENCODING, context, output);
				else {
					// there is data to retain
					// String retainData;
				}
			} catch (ResourceNotFoundException e) {
				// handle this templating error
				_log.error(e, e);
			} catch (ParseErrorException e) {
				// handle this templating error
				_log.error(e, e);
			} catch (MethodInvocationException e) {
				// handle this templating error
				_log.error(e, e);
			} catch (Exception e) {
				// report this unknown error
				throw new RuntimeException(e);
			} finally {
				// just make sure file is closed
				try { output.close(); } catch (IOException e) { };
			}
		}
	}
	
	/**
	 * Creates the output file for writing
	 * @param params
	 * @return
	 */
	private FileWriter createOutputFile(File template, Map<String, Object> params) {
		String outputDir = packager.getPackageFolder(""+params.get("modelPackage"),template);
		try {
			// create the output directory if not existing
			FileUtil.createDirectory(outputDir);
		} catch (IOException e) {
			_log.error("Failed to create output directory ["+outputDir+"]",e);
			throw new RuntimeException(e);
		}
		// build the filename
		String outputName = template.getName()
								.replace("modelName", params.get("modelName").toString())
								.replaceAll(".vm$", "");
		File outputFile = new File(outputDir+"/"+outputName);
		// extract the output package
		params.put("package", packager.getPackage(outputFile));
		
		try {
			// get the syncmode
			if (syncMode==SyncMode.CREATE) {
				if (outputFile.exists()) {
					// Need to provide ability to process existing source.
					_log.info(outputFile.getAbsoluteFile()+" already exist. Synchronization mode = ["+syncMode+"]");
					return null;
				}
			} else if (syncMode==SyncMode.RETAIN) {
				if (outputFile.exists()) {
					// need to provide ability to process existing source.
					_log.info(outputFile.getAbsoluteFile()+" already exist. Synchronization mode = ["+syncMode+"]");
					// save the contents to memory for future use
					backupData = FileUtil.readFile(outputFile.getAbsolutePath());
				}				
			} // else if SyncMode.REPLACE is ignored since it will always generate a new file.
			// create the file
			_log.info("Generating " + outputFile.getAbsoluteFile() + ".");
			outputFile.createNewFile();
			return new FileWriter(outputFile);
		} catch (IOException e1) {
			_log.error(e1,e1);
		}
		return null;
	}

	/**
	 * @param filter the filter to set
	 */
	public final void setFilter(ProcessorFilter filter) {
		this.filter = filter;
	}

	/**
	 * @param syncMode the syncMode to set
	 */
	public final void setSyncMode(SyncMode syncMode) {
		this.syncMode = syncMode;
	}
}
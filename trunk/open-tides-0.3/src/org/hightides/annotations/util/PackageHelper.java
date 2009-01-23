/**
 * 
 */
package org.hightides.annotations.util;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.hightides.annotations.processor.CloningProcessor;

/**
 * Helper class for high-tides in generating package and folder paths.
 * This class needs to be instantiated to set the appropriate package 
 * variables.
 * @author allantan
 *
 */
public class PackageHelper {
	
	// For logging 
	private static Logger _log = Logger.getLogger(CloningProcessor.class);
	
	// Base path where application is executed
	private static String basePath;
	// Relative path where template is located
	private static String baseTemplatePath;
	// Relative path of root source location 
	private static String baseOutputPath;
	// Folder of template to process
	private String templateFolder;
	// Folder where output will be written
	private String outputFolder; 
	
	public PackageHelper(String templateFolder, String outputFolder) {
		super();
		this.outputFolder = outputFolder;
		this.templateFolder = templateFolder;
	}

	/**
	 * Returns the file location of the template folder
	 * @param templateFolder
	 * @return
	 */
	public final File getTemplateFolder() {
		return new File(baseTemplatePath + templateFolder);
	}
	
	/**
	 * Returns the package location of the output code.
	 * @return
	 */
	public final String getPackageFolder(String modelPackage, File template) {
		// output directory is generated as outputPath + modelPackage + outputFolder + template subFolder
		String out = baseOutputPath + 
					 modelPackage.replaceAll("\\.", "/") + 
					 "/"+outputFolder;
		// retrieve the subfolder in template if exist
		String sub = template.getParentFile().getAbsolutePath()
						.replace(this.getTemplateFolder().getAbsolutePath(), "");
		return out + sub;
	}
	
	/**
	 * Returns the java package value for the given file. 
	 * @return 
	 */
	public final String getPackage(File outputFile) {
		try {
			String outputPackage;
			outputPackage = outputFile.getParentFile().getCanonicalPath();
			int index = outputPackage.indexOf(baseOutputPath) + baseOutputPath.length();
			String packagePath = outputPackage.substring(index);
			return packagePath.replaceAll("/", "\\.");		
		} catch (IOException e) {
			_log.error("Cannot retrieve package value.",e);
			return "";
		}
	}
	

	/**
	 * Returns the location of the template relative to base path specified 
	 * in velocity.
	 * @param absTemplate - Absolute path 
	 * @return
	 */
	public static final String getTemplateForMerge(File template) {
		// File pointer to base path
		File base = new File(baseTemplatePath);
		// Pass only filename relative to templatePath since
		// baseTemplatePath is already defined in Velocity.properties
		return template.getAbsolutePath().replace(base.getAbsolutePath(), "");
	}
	
	/**
	 * @param basePath the basePath to set
	 */
	public static final void setBasePath(String basePath) {
		PackageHelper.basePath = basePath;
	}
	/**
	 * @param baseTemplatePath the baseTemplatePath to set
	 */
	public static final void setBaseTemplatePath(String baseTemplatePath) {
		PackageHelper.baseTemplatePath = baseTemplatePath;
	}
	/**
	 * @param baseOutputPath the baseOutputPath to set
	 */
	public static final void setBaseOutputPath(String baseOutputPath) {
		PackageHelper.baseOutputPath = baseOutputPath;
	}
}

/**
 * 
 */
package org.opentides.bean;

import java.util.List;

/**
 * This is the marker to indicate if an entity supports 
 * file upload.
 * 
 * @author allantan
 *
 */
public interface Uploadable {
	public List<FileInfo> getFiles();
	public void setFiles(List<FileInfo> files);
}

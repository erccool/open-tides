package org.opentides.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This bean is holder of uploaded file information. 
 * Used by BaseUploadEntity to store uploaded files.
 * 
 * @author allantan
 *
 */
@Entity
@Table(name="FILE_INFO")
public class FileInfo extends BaseEntity {

	private static final long serialVersionUID = 3526479205130157604L;

	@Column(name="FILENAME", nullable=false)
	private String fileName;
	
	@Column(name="FULL_PATH")
	private String fullPath;
	
	@Column(name="FILE_SIZE", nullable=false)
	private Long fileSize;
	
	/**
	 * @return the fileName
	 */
	public final String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public final void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fullPath
	 */
	public final String getFullPath() {
		return fullPath;
	}

	/**
	 * @param fullPath the fullPath to set
	 */
	public final void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	/**
	 * @return the fileSize
	 */
	public final Long getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public final void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	/**
	 * @return the fileSize in kilobytes
	 */
	public Long getFileSizeInKB() {
		return fileSize / 1024;
	}
}

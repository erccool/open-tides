package org.opentides.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This entity supports file upload functionality. 
 * Use this entity to enable support for file upload.
 * 
 * @author allantan
 */
@Entity
@Table(name = "UPLOAD_ENTITY")
@Inheritance(strategy = InheritanceType.JOINED)
public class BaseUploadEntity extends BaseEntity implements Uploadable {

	private static final long serialVersionUID = 3526479205130157604L;
	
	// this is the property that will hold the file information
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="UPLOAD_ID") 
	private List<FileInfo> files;

	/**
	 * @return the files
	 */
	public final List<FileInfo> getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public final void setFiles(List<FileInfo> files) {
		this.files = files;
	}
	/**
	 * Returns the last uploaded file.
	 * @return
	 */
	public final FileInfo getLastUploadedFileInfo() {
		int size = files.size();
		if (size > 0) 
			return files.get(size-1);
		else
			return null;
	}
}

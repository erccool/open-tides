/**
 * 
 */
package org.opentides.controller;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentides.controller.BaseCrudController;

import org.apache.log4j.Logger;
import org.opentides.service.FileInfoService;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import org.opentides.bean.FileInfo;

/**
 * @author allantan
 *
 */
public class DownloadFileInfoController extends AbstractController {
	
	private static Logger _log = Logger.getLogger(BaseCrudController.class);

	private FileInfoService fileInfoService;
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FileInfo fileInfo = fileInfoService.load(request.getParameter("FileInfoId"));
		if (fileInfo != null) {
			File file = new File(fileInfo.getFullPath());
			if(!file.exists()) {
				_log.error("File [" + fileInfo.getFullPath() + "] not found.");
			} else {
				response.setHeader(
						"Content-disposition",
						"attachment; filename=" + fileInfo.getFileName());
				
				byte[] bytes = FileCopyUtils.copyToByteArray(file);
				OutputStream outputStream = response.getOutputStream();
				outputStream.write(bytes);
				outputStream.flush();		
				return null;
			}
		}
		// failed to download
		Map<String,String> model = new HashMap<String,String>();
		model.put("message", "msg.file-not-found");
		model.put("title", "label.download-file");		
		return new ModelAndView("user-message", model);
	}
	
	/**
	 * @param fileInfoService the fileInfoService to set
	 */
	public final void setFileInfoService(FileInfoService fileInfoService) {
		this.fileInfoService = fileInfoService;
	}
}

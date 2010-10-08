/**
 * 
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 * 
 * BaseCRudController.java
 * Created on Jan 31, 2008, 10:17:04 PM
 */
package com.ideyatech.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractFormController;

import com.ideyatech.core.bean.BaseEntity;
import com.ideyatech.core.bean.SearchResults;
import com.ideyatech.core.bean.SystemCategory;
import com.ideyatech.core.bean.SystemCodes;
import com.ideyatech.core.service.BaseCrudService;
import com.ideyatech.core.util.StringUtil;

/**
 * This controller is used to handle basic CRUD operations.
 * Operation is identified using URL param "action".
 * If action is null, page defaults to search all.
 * 
 * @author allanctan
 */
public class BaseCrudController<T extends BaseEntity> extends AbstractFormController {
	
	private static Logger _log = Logger.getLogger(BaseCrudController.class);

	public static final String ACTION = "action";
	public static final String CREATE = "create";
	public static final String READ   = "read";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String SEARCH = "search";
	public static final String ID 	  = "codeId";
	
	private BaseCrudService<T> service;
	private BaseCrudService<SystemCodes> systemCodesService;
	private boolean supportsPaging = true;
	private int pageSize;
	private int numLinks;	
	
	/**
	 * Inject view for displaying search page
	 */
	private String searchView;
	/**
	 * Inject view for refresh entry on search page
	 */
	private String refreshView;
	/**
	 * Inject view for form page.
	 */
	private String formView;
	/**
	 * Internal use. Don't mess with me.
	 */	
	private String showView;
	
	/**
	 * When user requests for READ or UPDATE action, the corresponding object needs
	 * to be loaded for display.
	 * This implementation invokes {@link BaseCrudService.load(HttpServletRequest)}
	 * to retrieve the object for loading.
	 * @see BaseCrudService.load
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		String action = getAction(request);
		if (READ.equals(action) || UPDATE.equals(action)) {
			return service.load(request.getParameter(ID));
		} 
		return super.formBackingObject(request);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#suppressValidation(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected boolean suppressValidation(HttpServletRequest request) {
		String action = getAction(request);
		if (SEARCH.equals(action) || DELETE.equals(action)) 
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#isFormSubmission(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected boolean isFormSubmission(HttpServletRequest request) {
		if ("POST".equals(request.getMethod()))
			return true;
		String action = getAction(request);
		if (SEARCH.equals(action) || DELETE.equals(action)) 
			return true;
		// make sure showView is set in case it is not form Submission
		if (CREATE.equals(action) || UPDATE.equals(action))
			showView = getFormView();
		else if (READ.equals(action)) {
			showView = getRefreshView();
		} 
		return false;
	}

	/**
	 * This implementation shows the configured form view, delegating to the analogous
	 * {@link #showForm(HttpServletRequest, HttpServletResponse, BindException, Map)}
	 * variant with a "controlModel" argument.
	 * <p>Can be called within
	 * {@link #onSubmit(HttpServletRequest, HttpServletResponse, Object, BindException)}
	 * implementations, to redirect back to the form in case of custom validation errors
	 * (errors not determined by the validator).
	 * <p>Can be overridden in subclasses to show a custom view, writing directly
	 * to the response or preparing the response before rendering a view.
	 * <p>If calling showForm with a custom control model in subclasses, it's preferable
	 * to override the analogous showForm version with a controlModel argument
	 * (which will handle both standard form showing and custom form showing then).
	 * @see #setFormView
	 * @see #showForm(HttpServletRequest, HttpServletResponse, BindException, Map)
	 */
	@Override
	protected ModelAndView showForm(
			HttpServletRequest request, HttpServletResponse response, BindException errors)
			throws Exception {
		return showForm(request, response, errors, null);
	}

	/**
	 * This implementation shows the configured form view.
	 * <p>Can be called within
	 * {@link #onSubmit(HttpServletRequest, HttpServletResponse, Object, BindException)}
	 * implementations, to redirect back to the form in case of custom validation errors
	 * (errors not determined by the validator).
	 * <p>Can be overridden in subclasses to show a custom view, writing directly
	 * to the response or preparing the response before rendering a view.
	 * @param request current HTTP request
	 * @param errors validation errors holder
	 * @param controlModel model map containing controller-specific control data
	 * (e.g. current page in wizard-style controllers or special error message)
	 * @return the prepared form view
	 * @throws Exception in case of invalid state or arguments
	 * @see #setFormView
	 */
	@SuppressWarnings("unchecked")
	protected ModelAndView showForm(
			HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel)
			throws Exception {
		return showForm(request, errors, getShowView(), controlModel);
	}
	
	/**
	 * Create a reference data map for the given request and command,
	 * consisting of bean name/bean instance pairs as expected by ModelAndView.
	 * <p>The default implementation delegates to {@link #referenceData(HttpServletRequest)}.
	 * Subclasses can override this to set reference data used in the view.
	 * @param request current HTTP request
	 * @param command form object with request parameters bound onto it
	 * @param errors validation errors holder
	 * @return a Map with reference data entries, or <code>null</code> if none
	 * @throws Exception in case of invalid state or arguments
	 * @see ModelAndView
	 */
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
		return referenceData(request);
	}

	/**
	 * Create a reference data map for the given request.
	 * Called by the {@link #referenceData(HttpServletRequest, Object, Errors)}
	 * variant with all parameters.
	 * <p>The default implementation returns <code>null</code>.
	 * Subclasses can override this to set reference data used in the view.
	 * @param request current HTTP request
	 * @return a Map with reference data entries, or <code>null</code> if none
	 * @throws Exception in case of invalid state or arguments
	 * @see #referenceData(HttpServletRequest, Object, Errors)
	 * @see ModelAndView
	 */
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws Exception {
		return null;
	}
	
	/**
	 * This implementation performs the corresponding action for CRUD operation.
	 * Operations include CREATE, UPDATE, DELETE and SEARCH.
	 * To override the operations, you may override the corresponding action 
	 * methods.
	 * @see #processFormSubmission(HttpServletRequest, HttpServletResponse, Object, BindException)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView processFormSubmission(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		// check for bind errors - extracted from SimpleFormController
		if (errors.hasErrors()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Data binding errors: " + errors.getErrorCount());
			}
			return showForm(request, response, errors);
		}
		
		String action = getAction(request);
		T obj = (T) command;
		SearchResults results = new SearchResults<T>(pageSize, numLinks);

		// perform the action
		if (action.equals(UPDATE)) {
			this.updateAction(obj);
		} else if (action.equals(CREATE)) {
			this.createAction(obj);
		} else if (action.equals(DELETE)) {
			this.deleteAction(request.getParameter(ID));
		} else if (action.equals(SEARCH)) {
			long startTime = System.currentTimeMillis();
			String strPage = request.getParameter("page");
			int currPage = 1; 
			try {
				currPage = Integer.parseInt(strPage);
			} catch (NumberFormatException nfe) {
				// do nothing
			} 
			results.setCurrPage(currPage);			
			results.setTotalResults(this.countAction(obj));
			results.addResults(this.searchAction(obj,
								results.getStartIndex(),
								results.getPageSize()));
			results.setSearchTime(System.currentTimeMillis() - startTime);
		}
		
		// return the response and view
		Map<String, Object> model = referenceData(request, command, errors);
		if (model==null)
			model = new HashMap<String,Object>();
		model.put(getCommandName(), obj);
		model.put("results",results);
		if (action.equals(CREATE))
			model.put("newRow",true);
		return new ModelAndView(getShowView(),model);
	}
	
	/**
	 * This is an internal helper that retrieves the proper ACTION 
	 * based on request parameters. When no action is found, defaults
	 * to SEARCH.
	 * @param request
	 * @return action to perform
	 */
	private String getAction(HttpServletRequest request) {
		String action = request.getParameter("action");
		// default to SEARCH
		if (StringUtil.isEmpty(action)) {
			action = SEARCH;	
		} 		
		return action;
	}
	
	/**
	 * Override this method if you want to control how data will be updated.
	 * Make sure to call setShowView() to specify the view to display for 
	 * this action.
	 * @param command
	 * @return
	 */
	protected void createAction(T command) {
		showView = getRefreshView();
		service.save(command);
	}
	
	/**
	 * Override this method if you want to control how data will be updated.
	 * Make sure to call setShowView() to specify the view to display for 
	 * this action.
	 * @param command
	 * @return
	 */
	protected void updateAction(T command) {
		showView = getRefreshView();
		service.save(command);
	}
	
	/**
	 * Override this method if you want to control how data will be deleted.
	 * Make sure to call setShowView() to specify the view to display for 
	 * this action.
	 * @param command
	 * @return
	 */
	protected void deleteAction(String id) {
		showView = getRefreshView();
		service.delete(id);
	}
	
	/**
	 * Override this method if you want to control how data will be searched.
	 * Make sure to call setShowView() to specify the view to display for 
	 * this action.
	 * @param command
	 * @return
	 */
	protected List<T> searchAction(T command, int start, int total) {
		showView = getSearchView();
		if (supportsPaging) {
			if (command==null) {
				// no command, let's search everything
				return service.findAll(start,total);			
			} else {
				// let's do a query by example
				return service.findByExample(command,start,total);
			}
		} else {
			if (command==null) {
				// no command, let's search everything
				return service.findAll();			
			} else {
				// let's do a query by example
				return service.findByExample(command);
			}
			
		}
	}

	/**
	 * Override this method if you want to control how data will be searched
	 * along with searchAction(). This method returns the total result count.
	 * @param command
	 * @return
	 */
	protected long countAction(T command) {
		if (command==null) {
			// no command, let's search everything
			return service.countAll();
		} else {
			// let's do a query by example
			return service.countByExample(command);
		}
	}
	
	/**
	 * Retrieves all registered system codes for a category.
	 * @param category
	 * @return
	 */
	protected final List<SystemCodes> getSystemCodesByCategory(SystemCategory category) {
		SystemCodes example = new SystemCodes();
		example.setCategory(category.getName());
		return systemCodesService.findByExample(example);
	}
	
	/**
	 * @param service the service to set
	 */
	public void setService(BaseCrudService<T> service) {
		this.service = service;
	}

	/**
	 * @return the searchView
	 */
	public String getSearchView() {
		return searchView;
	}

	/**
	 * @param searchView the searchView to set
	 */
	public void setSearchView(String searchView) {
		this.searchView = searchView;
	}

	/**
	 * @return the refreshView
	 */
	public String getRefreshView() {
		return refreshView;
	}

	/**
	 * @param refreshView the refreshView to set
	 */
	public void setRefreshView(String refreshView) {
		this.refreshView = refreshView;
	}

	/**
	 * @return the formView
	 */
	public String getFormView() {
		return formView;
	}

	/**
	 * @param formView the formView to set
	 */
	public void setFormView(String formView) {
		this.formView = formView;
	}

	/**
	 * @return the showView
	 */
	public String getShowView() {
		return showView;
	}

	/**
	 * @param showView the showView to set
	 */
	protected void setShowView(String showView) {
		this.showView = showView;
	}

	/**
	 * @return the service
	 */
	protected BaseCrudService<T> getService() {
		return service;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the numLinks
	 */
	public int getNumLinks() {
		return numLinks;
	}

	/**
	 * @param numLinks the numLinks to set
	 */
	public void setNumLinks(int numLinks) {
		this.numLinks = numLinks;
	}

	/**
	 * @param systemCodesService the systemCodesService to set
	 */
	public void setSystemCodesService(
			BaseCrudService<SystemCodes> systemCodesService) {
		this.systemCodesService = systemCodesService;
	}

	/**
	 * @return the systemCodesService
	 */
	public BaseCrudService<SystemCodes> getSystemCodesService() {
		return systemCodesService;
	}

	/**
	 * @return the supportsPaging
	 */
	public boolean isSupportsPaging() {
		return supportsPaging;
	}

	/**
	 * @param supportsPaging the supportsPaging to set
	 */
	public void setSupportsPaging(boolean supportsPaging) {
		this.supportsPaging = supportsPaging;
	}
}

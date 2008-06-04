package com.ideyatech.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.ideyatech.core.service.EventReminderTemplateService;

public class EventReminderTemplateController extends AbstractController {
	private EventReminderTemplateService eventReminderTemplateService;

	private String listView;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		List templates = eventReminderTemplateService.findAll();
		return new ModelAndView(listView, "eventTemplates",
				templates);
	}

	public void setEventReminderTemplateService(
			EventReminderTemplateService eventReminderTemplateService) {
		this.eventReminderTemplateService = eventReminderTemplateService;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}
}

package org.opentides.service;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.opentides.bean.ReportDefinition;
import org.opentides.service.impl.ReportServiceImpl;

public class ReportServiceTest {

	@Test
	public void testGetParameterValues() {
		String reportFile = "/resources/jasper/test.jrxml";	
		InputStream jrXml = ReportServiceTest.class.getResourceAsStream(reportFile);
		ReportServiceImpl service = new ReportServiceImpl();
		Map<String, String[]> inputParam = new HashMap<String, String[]>();
		inputParam.put("raffleId", new String[] {"24"});
		inputParam.put("branch", new String[] {"Cebu"});
		inputParam.put("reportDate", new String[] {"12 Aug 1995"});
		Map<String, Object> reportParam = service.getParameterValues(inputParam, jrXml);
		Assert.assertEquals(new Long(24), reportParam.get("raffleId"));
		Assert.assertEquals("Cebu", reportParam.get("branch"));
		Assert.assertEquals(new Date("12 Aug 1995"), reportParam.get("reportDate"));
	}

	@Test
	public void testGetMissingParameters() {
		String reportFile = "/resources/jasper/test.jrxml";	
		InputStream jrXml = ReportServiceTest.class.getResourceAsStream(reportFile);
		ReportServiceImpl service = new ReportServiceImpl();
		Map<String, String[]> inputParam = new HashMap<String, String[]>();
		List<ReportDefinition> reportParam = 
			service.getMissingParameters(inputParam, jrXml);
		Assert.assertEquals(5, reportParam.size());
//		Assert.assertTrue(reportParam.containsKey("raffleId"));
//		Assert.assertTrue(reportParam.containsKey("branch"));
//		Assert.assertTrue(reportParam.containsKey("imageStream"));
//		Assert.assertTrue(reportParam.containsKey("reportDate"));
		// check raffle_id
		ReportDefinition raffleId= reportParam.get(0);
		Assert.assertEquals("raffleId",raffleId.getName());
		Assert.assertEquals("java.lang.Long",raffleId.getClazz());
		// check branch
		ReportDefinition branch= reportParam.get(1);
		Assert.assertEquals("branch",branch.getName());
		Assert.assertEquals("java.lang.String",branch.getClazz());
		Assert.assertEquals("dropdown",branch.getType());
		Assert.assertTrue(branch.getProperties().containsKey("value.one"));
		Assert.assertTrue(branch.getProperties().containsKey("value.two"));
		// check report date
		ReportDefinition reportDate= reportParam.get(3);
		Assert.assertEquals("reportDate",reportDate.getName());
		Assert.assertEquals("java.util.Date",reportDate.getClazz());
		Assert.assertEquals("Report Date",reportDate.getLabel());
	}

}

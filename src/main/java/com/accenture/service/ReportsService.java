package com.accenture.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.accenture.request.SearchRequest;
import com.accenture.response.SearchResponse;

public interface ReportsService {
	
	public List<String> getUniquePlanNames();
	
	public List<String> getUniquePlanStatues();
	
	public List<SearchResponse> search(SearchRequest request);
	
	public void generateExcel(HttpServletResponse response) throws Exception;
	
	public void generatePdf(HttpServletResponse response) throws Exception;

}

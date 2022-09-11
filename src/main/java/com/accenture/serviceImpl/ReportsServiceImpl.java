package com.accenture.serviceImpl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.accenture.entity.EligibilityDetails;
import com.accenture.repo.EligibilityDtlsRepo;
import com.accenture.request.SearchRequest;
import com.accenture.response.SearchResponse;
import com.accenture.service.ReportsService;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class ReportsServiceImpl implements ReportsService {

	@Autowired
	EligibilityDtlsRepo eligRepo;
	
	@Override
	public List<String> getUniquePlanNames() {
		// TODO Auto-generated method stub
		return eligRepo.findPlanNames();
	}

	@Override
	public List<String> getUniquePlanStatues() {
		// TODO Auto-generated method stub
		return eligRepo.findPlanStatuses();
	}

	@Override
	public List<SearchResponse> search(SearchRequest request) {
		// TODO Auto-generated method stub

		EligibilityDetails queryBuilder = getQueryBuilder(request);
	
		
		Example<EligibilityDetails> example = Example.of(queryBuilder);
		
		
		List<EligibilityDetails> entities = eligRepo.findAll(example);
		
		List<SearchResponse> searchResponseList = new ArrayList<>();
		
		SearchResponse searchResponse = new SearchResponse();
		
		
		entities.forEach(entity -> {
			BeanUtils.copyProperties(entity, searchResponse);
			searchResponseList.add(searchResponse);
		});
		
		
		
		return searchResponseList;
	}
	
	public EligibilityDetails getQueryBuilder(SearchRequest request)
	{
	
		EligibilityDetails queryBuilder = new EligibilityDetails();
	
		if(request.getPlanName()!=null && !request.getPlanName().equals(""))
			{
				queryBuilder.setPlanName(request.getPlanName());
			}
	
		if(request.getPlanStatus()!=null && !request.getPlanStatus().equals(""))
			{
				queryBuilder.setPlanStatus(request.getPlanStatus());
			}
	
		if(request.getPlanStartDate()!=null)
			{
				queryBuilder.setPlanStartDate(request.getPlanStartDate());
			}
	
		if(request.getPlanEndDate()!=null)
			{
				queryBuilder.setPlanEndDate(request.getPlanEndDate());
			}
	
	return queryBuilder;
}
	

	@SuppressWarnings("resource")
	@Override
	public void generateExcel(HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		List<EligibilityDetails> entities = eligRepo.findAll();
		
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet();
		HSSFRow headerRow = sheet.createRow(0);
		
		headerRow.createCell(0).setCellValue("Name");
		headerRow.createCell(1).setCellValue("Mobile");
		headerRow.createCell(2).setCellValue("Gender");
		headerRow.createCell(3).setCellValue("SSn");
		
		int i=1;
		
		for(EligibilityDetails entity:entities)
		{	
			HSSFRow dataRow = sheet.createRow(i);
			dataRow.createCell(0).setCellValue(entity.getName());
			dataRow.createCell(1).setCellValue(String.valueOf(entity.getMobile()));
			dataRow.createCell(2).setCellValue(String.valueOf(entity.getGender()));
			dataRow.createCell(3).setCellValue(entity.getSsn());
			i=i+1;
		}
		
		ServletOutputStream outputstream = response.getOutputStream();
		workBook.write(outputstream);
		workBook.close();
		outputstream.close();
		
		
	}

	@Override
	public void generatePdf(HttpServletResponse response) throws Exception{
		// TODO Auto-generated method stub
		
		List<EligibilityDetails> entities = eligRepo.findAll();
		
		Document document = new Document(PageSize.A4);
		
		PdfWriter.getInstance(document, response.getOutputStream());
        
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
        
        Paragraph p = new Paragraph("Search Report", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
		
        document.add(p);
        
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);
        
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
         
        font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("Name", font));
         
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("phno", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Gender", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("SSN", font));
        table.addCell(cell);  
        
        for(EligibilityDetails entity:entities)
		{	
			table.addCell(entity.getName());
			table.addCell(entity.getEmail());
			table.addCell(String.valueOf(entity.getMobile()));
			table.addCell(String.valueOf(entity.getGender()));
			table.addCell(String.valueOf(entity.getSsn()));
		}
        
        document.add(table);
        
        document.close();
    }
}

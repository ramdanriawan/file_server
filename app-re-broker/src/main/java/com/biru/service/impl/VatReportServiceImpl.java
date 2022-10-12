package com.biru.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.component.DateComponent;
import com.biru.component.ReportUtils;
import com.biru.entity.ViewLaporanVatEntity;
import com.biru.repository.ViewLaporanVatRepo;
import com.biru.service.VatReportService;
import com.biru.specifications.ViewLaporanVatSpecifications;

import net.sf.jasperreports.engine.JRException;

@Service
public class VatReportServiceImpl implements VatReportService{
	
	@Autowired
	private ViewLaporanVatRepo viewLaporanVatRepo;
	
	@Autowired
	private ViewLaporanVatSpecifications viewLaporanVatSpecifications;
	
	@Autowired
	private DateComponent dateComponent;
	
	@Autowired
	private ReportUtils reportUtils;
	
	@Value("${report.path.jasper}")
	private String reportPathJasper;

	@Override
	public Object inq(Map<String, Object> param) {
		// TODO Auto-generated method stub
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		String transaction = Param.getStr(param, "transactionDate");
		Date transactionDate = null;
		if(!transaction.equals("")) {
			String [] date = transaction.split("/");
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
			c.set(Calendar.MONTH, (Integer.parseInt(date[1])-1));
			c.set(Calendar.YEAR, Integer.parseInt(date[2]));
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			transactionDate = c.getTime();
		}

		String to = Param.getStr(param, "to");
		Date toDate = null;
		if(!to.equals("")) {
			String [] date = to.split("/");
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
			c.set(Calendar.MONTH, (Integer.parseInt(date[1])-1));
			c.set(Calendar.YEAR, Integer.parseInt(date[2]));
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			toDate = c.getTime();
		}

		if(sort.equals("trxDateStr")) {
			sort = "trxDate";
		}

		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		Page<ViewLaporanVatEntity>pageViewLaporanVatEntity = null;
		
		
		if(!transaction.equals("") || !to.equals("")) {
			Specification<ViewLaporanVatEntity> specificationViewLaporanVatEntity = null;
			
			if(!transaction.equals("")) {
				specificationViewLaporanVatEntity = viewLaporanVatSpecifications.trxDateGreaterThanOrEqualTo(transactionDate);
			}
			
			if(!to.equals("")) {
				if(specificationViewLaporanVatEntity == null) {
					specificationViewLaporanVatEntity = viewLaporanVatSpecifications.trxDatelessThanOrEqualTo(toDate);
				}else {
					specificationViewLaporanVatEntity = specificationViewLaporanVatEntity
							.and(viewLaporanVatSpecifications.trxDatelessThanOrEqualTo(toDate));
				}
			}
			pageViewLaporanVatEntity = viewLaporanVatRepo.findAll(specificationViewLaporanVatEntity, pageable);
		}else {
			pageViewLaporanVatEntity = viewLaporanVatRepo.findAll(pageable);
		}
		
		return new DatatableSet(pageViewLaporanVatEntity.getTotalElements(), pageViewLaporanVatEntity.getTotalElements(), pageViewLaporanVatEntity.getContent());
	}

	@Override
	public Object print(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		String transactionDate = Param.getStr(param, "transactionDate");
		String to = Param.getStr(param, "to");
		String transactionDatePreview = "";
		String toPreview = "";
		
		if(transactionDate.equals("")) {
			transactionDate = "0000-00-00";
			
		}else {
			String [] date =  transactionDate.split("/");
			System.out.println(date[0]);
			System.out.println(date[1]);
			System.out.println(date[2]);
			transactionDate = date[2].concat("-").concat(date[1]).concat("-").concat(date[0]);
			transactionDatePreview = date[0].concat(" ").concat(dateComponent.getMonthName((Integer.parseInt(date[1])-1))).concat(" ").concat(date[2]);
		}
		
		if(to.equals("")) {
			to = "9999-12-31";
			
		}else {
			String [] date =  to.split("/");
			System.out.println(date[0]);
			System.out.println(date[1]);
			System.out.println(date[2]);
			to = date[2].concat("-").concat(date[1]).concat("-").concat(date[0]);
			toPreview = date[0].concat(" ").concat(dateComponent.getMonthName((Integer.parseInt(date[1])-1))).concat(" ").concat(date[2]);
		}
		
		
		param.put("transactionDate", transactionDate);
		param.put("to", to);
		param.put("transactionDatePreview", transactionDatePreview);
		param.put("toPreview", toPreview);
		param.put("SUBREPORT_PATH", reportPathJasper);
		
		System.out.println(param);
		reportUtils.compileReport("VatReportSub.jrxml");
		return reportUtils.exportPdf("VatReport.jrxml", param);
	}

	@Override
	public Object export(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				String transactionDate = Param.getStr(param, "transactionDate");
				String to = Param.getStr(param, "to");
				String transactionDatePreview = "";
				String toPreview = "";
				
				if(transactionDate.equals("")) {
					transactionDate = "0000-00-00";
					
				}else {
					String [] date =  transactionDate.split("/");
					System.out.println(date[0]);
					System.out.println(date[1]);
					System.out.println(date[2]);
					transactionDate = date[2].concat("-").concat(date[1]).concat("-").concat(date[0]);
					transactionDatePreview = date[0].concat(" ").concat(dateComponent.getMonthName((Integer.parseInt(date[1])-1))).concat(" ").concat(date[2]);
				}
				
				if(to.equals("")) {
					to = "9999-12-31";
					
				}else {
					String [] date =  to.split("/");
					System.out.println(date[0]);
					System.out.println(date[1]);
					System.out.println(date[2]);
					to = date[2].concat("-").concat(date[1]).concat("-").concat(date[0]);
					toPreview = date[0].concat(" ").concat(dateComponent.getMonthName((Integer.parseInt(date[1])-1))).concat(" ").concat(date[2]);
				}
				
				
				param.put("transactionDate", transactionDate);
				param.put("to", to);
				param.put("transactionDatePreview", transactionDatePreview);
				param.put("toPreview", toPreview);
				param.put("SUBREPORT_PATH", reportPathJasper);
				
				System.out.println(param);
				reportUtils.compileReport("VatReportSub.jrxml");
				return reportUtils.exportExcel("VatReport.jrxml", param);
	}

}

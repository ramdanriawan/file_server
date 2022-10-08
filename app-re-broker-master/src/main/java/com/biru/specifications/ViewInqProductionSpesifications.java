package com.biru.specifications;

import java.util.Date;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.biru.common.param.Param;
import com.biru.view.ViewInqProduction;

public class ViewInqProductionSpesifications {
	
	public static final String CLIENT 			= "client";
	public static final String TRE_INS_START	= "treInsStart";
	public static final String TRE_INS_END 		= "treInsEnd";
	public static final String TRX_CLASS		= "trxClass";
	public static final String TRX_DATA_STATUS	= "trxDataStatus";
	public static final String TRX_NON_PRO		= "trxNonPro";

	public static Specification<ViewInqProduction> inquiryAdjustment(
			final Map<String, Object> param) {
		
		return (root, query, cb) -> {
			Predicate predicate = cb.conjunction();
			
			String client = Param.getStr(param, CLIENT);
			Date treInsStart = Param.getDate(param, TRE_INS_START);
			Date treInsEnd = Param.getDate(param, TRE_INS_END);
			String trxClass = Param.getStr(param, TRX_CLASS);
			String trxDataStatus = Param.getStr(param, TRX_DATA_STATUS);
			String trxNonPro = Param.getStr(param, TRX_NON_PRO);
			
			if(StringUtils.isNotBlank(client))
				predicate.getExpressions().add(
						cb.equal(root.get(CLIENT).as(String.class), client)
				);
			
			if(treInsStart != null)
				predicate.getExpressions().add(
						cb.greaterThanOrEqualTo(root.get(TRE_INS_START).as(Date.class), treInsStart)
				);
			
			if(treInsEnd != null)
				predicate.getExpressions().add(
						cb.lessThanOrEqualTo(root.get(TRE_INS_END).as(Date.class), treInsEnd)
				);
			
			if(StringUtils.isNotBlank(trxClass))
				predicate.getExpressions().add(
						cb.equal(root.get(TRX_CLASS).as(String.class), trxClass)
				);
			
			if(StringUtils.isNotBlank(trxDataStatus))
				predicate.getExpressions().add(
						cb.equal(root.get(TRX_DATA_STATUS).as(String.class), trxDataStatus)
				);
			
			if(StringUtils.isNotBlank(trxNonPro))
				predicate.getExpressions().add(
						cb.equal(root.get(TRX_NON_PRO).as(String.class), trxNonPro)
				);
			
			return predicate;
		};
	}
	
}

package com.biru.specifications;

import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.biru.common.param.Param;
import com.biru.view.ViewInqDirectModify;

public class ViewInqDirectModifySpesifications {
	
	public static final String TRX_CLIENT 		= "trxClient";
	public static final String TRX_COVER_CODE	= "trxCoverCode";

	public static Specification<ViewInqDirectModify> inquiry(
			final Map<String, Object> params) {
		
		return (root, query, cb) -> {
			Predicate predicate = cb.conjunction();
			
			String trxClient = Param.getStr(params, TRX_CLIENT);
			String trxCoverCode = Param.getStr(params, TRX_COVER_CODE);
			
			if(StringUtils.isNotBlank(trxClient))
				predicate.getExpressions().add(
						cb.equal(root.get(TRX_CLIENT).as(String.class), trxClient)
				);
			
			if(StringUtils.isNotBlank(trxCoverCode))
				predicate.getExpressions().add(
						cb.equal(root.get(TRX_COVER_CODE).as(String.class), trxCoverCode)
				);
			
			return predicate;
		};
	}
	
}

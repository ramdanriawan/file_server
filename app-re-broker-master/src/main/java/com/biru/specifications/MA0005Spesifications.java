package com.biru.specifications;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.biru.entity.MA0005Entity;

public class MA0005Spesifications {
	
	public static final String CLI_NAME 		= "cliName";
	public static final String CLI_CODE 		= "cliCode";
	public static final String CLI_TYPE 		= "cliType";
	public static final String CLI_DATA_STATUS	= "cliDataStatus";

	public static Specification<MA0005Entity> lookupClient(
			final String cliName,
			final String cliCode, 
			final String cliType, 
			final String cliDataStatus) {
		
		return (root, query, cb) -> {
			Predicate predicate = cb.conjunction();
			
			if(StringUtils.isNotBlank(cliName))
				predicate.getExpressions().add(
						cb.like(root.get(CLI_NAME).as(String.class), "%" + cliName + "%"));
			
			if(StringUtils.isNotBlank(cliCode))
				predicate.getExpressions().add(
						cb.like(root.get(CLI_CODE).as(String.class), "%" + cliCode + "%"));
			
			if(StringUtils.isNotBlank(cliType)) {
				if(cliType.contains("-"))
					predicate.getExpressions().add(
						cb.notEqual(root.get(CLI_TYPE).as(String.class), cliType.replace("-", "")));
				else
					predicate.getExpressions().add(
						cb.equal(root.get(CLI_TYPE).as(String.class), cliType));
			}
			
			if(StringUtils.isNotBlank(cliDataStatus))
				predicate.getExpressions().add(
						cb.equal(root.get(CLI_DATA_STATUS).as(String.class), cliDataStatus));
			
			return predicate;
		};
	}
	
}

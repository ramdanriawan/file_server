package com.biru.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The persistent class for the TR0012 database table.
 * 
 */
@StaticMetamodel(ViewInqTr0006Entity.class)
public class ViewInqTr0006Entity_ implements Serializable {
	private static final long serialVersionUID = 1L;

	public static volatile SingularAttribute<ViewInqTr0006Entity, Long> idKey;

	public static volatile SingularAttribute<ViewInqTr0006Entity, String> cliName;

	public static volatile SingularAttribute<ViewInqTr0006Entity,String> trxCoverCode;

	public static volatile SingularAttribute<ViewInqTr0006Entity,String> trxCurrId;

	public static volatile SingularAttribute<ViewInqTr0006Entity,Date> trxDate;

	public static volatile SingularAttribute<ViewInqTr0006Entity,String> trxInsuredName;

	public static volatile SingularAttribute<ViewInqTr0006Entity,BigDecimal> trxTsiAmount;

	public static volatile SingularAttribute<ViewInqTr0006Entity,String> trxVoucherId;
	
	public static volatile SingularAttribute<ViewInqTr0006Entity,String> trxRemarks;
	
	public static volatile SingularAttribute<ViewInqTr0006Entity,Date> trxReinsStart;
	
	public static volatile SingularAttribute<ViewInqTr0006Entity,Date> trxReinsEnd;
}
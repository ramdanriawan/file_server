package com.biru.entity;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(TR0001Entity.class)
public class TR0012Entity_ {
	public static volatile SingularAttribute<TR0012Entity, Long> idKey;

	public static volatile SingularAttribute<TR0012Entity, String> trxTrxClass;

	public static volatile SingularAttribute<TR0012Entity, String> trxType;

	public static volatile SingularAttribute<TR0012Entity, String> trxVoucherId;
	
	public static volatile SingularAttribute<TR0012Entity, Date> trxDate;
	
	public static volatile SingularAttribute<TR0012Entity, Date> trxDueDate;

	public static volatile SingularAttribute<TR0012Entity, String> trxMethPay;

	public static volatile SingularAttribute<TR0012Entity, String> trxCoverCode;

	public static volatile SingularAttribute<TR0012Entity, Integer> trxCountInv;

	public static volatile SingularAttribute<TR0012Entity, String> trxDataStatus;

	public static volatile SingularAttribute<TR0012Entity, String> trxOldType;

	public static volatile SingularAttribute<TR0012Entity, String> trxOldVoucherId;

	public static volatile SingularAttribute<TR0012Entity, String> trxClient;

	public static volatile SingularAttribute<TR0012Entity, String> trxDescription;

	public static volatile SingularAttribute<TR0012Entity, String> trxCurrId;

	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxCurrRate;

	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxOrgAmount;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxIntAmount ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxInvcAmount ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxSetAmount ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxComAmount ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxPremium ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxBrkrFee ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxDiscAmount ;

	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxDeducAmount ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxTaxinBf ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxTaxinCl ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxGrossAmount ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxGrossBf ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxComoAmount ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxOthers1Amount ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxOthers2Amount ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxOthers3Amount ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxOthers4Amount ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxNetTou ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxNetTtl ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxPolAmount ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxSdutyAmount ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxOthersAmount ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxAdminAmount ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxBankAmount ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxIncOthers ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxTaxinOth ;
	
	public static volatile SingularAttribute<TR0012Entity, BigDecimal> trxWithAmount ;
	
	public static volatile SingularAttribute<TR0012Entity, String> trxRemarks;

	public static volatile SingularAttribute<TR0012Entity, String> trxInsOfficer;
	
	public static volatile SingularAttribute<TR0012Entity, String> trxNxType;
	
	public static volatile SingularAttribute<TR0012Entity, String> trxNxVoucherId;
	
	public static volatile SingularAttribute<TR0012Entity, Date> trxSetDate;
	
	public static volatile SingularAttribute<TR0012Entity, String> createBy;

	public static volatile SingularAttribute<TR0012Entity, Date> createOn;

	public static volatile SingularAttribute<TR0012Entity, String> modifyBy;

	public static volatile SingularAttribute<TR0012Entity, Date> modifyOn;
	
	public static volatile SingularAttribute<TR0012Entity, String> trxSource;
}

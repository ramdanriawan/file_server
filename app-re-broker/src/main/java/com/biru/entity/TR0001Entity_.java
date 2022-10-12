package com.biru.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TR0001Entity.class)
public class TR0001Entity_ {

	public static volatile SingularAttribute<TR0001Entity, String>  idKey;
	public static volatile SingularAttribute<TR0001Entity, String> createBy;
	public static volatile SingularAttribute<TR0001Entity, Date> createOn;
	public static volatile SingularAttribute<TR0001Entity, String> glDataStatus;
	public static volatile SingularAttribute<TR0001Entity, String> glTrxClass;
	public static volatile SingularAttribute<TR0001Entity, String> glTrxClient;
	public static volatile SingularAttribute<TR0001Entity, Date> glTrxDate;
	public static volatile SingularAttribute<TR0001Entity, String> glTrxDesc;

	public static volatile SingularAttribute<TR0001Entity, Date> glTrxDue;

	public static volatile SingularAttribute<TR0001Entity, String> glTrxInvoice;

	public static volatile SingularAttribute<TR0001Entity, Byte> glTrxMonth;

	public static volatile SingularAttribute<TR0001Entity, String> glTrxOfficeId;

	public static volatile SingularAttribute<TR0001Entity, String> glTrxProject;

	public static volatile SingularAttribute<TR0001Entity, String> glTrxStatus;

	public static volatile SingularAttribute<TR0001Entity, BigDecimal> glTrxValueIdr;

	public static volatile SingularAttribute<TR0001Entity, BigDecimal> glTrxValueOrg;

	public static volatile SingularAttribute<TR0001Entity, Short>glTrxYear;

	public static volatile SingularAttribute<TR0001Entity, String> glType;

	public static volatile SingularAttribute<TR0001Entity, String> glVoucherId;

	public static volatile SingularAttribute<TR0001Entity, String> modifyBy;

	public static volatile SingularAttribute<TR0001Entity, Date> modifyOn;

}
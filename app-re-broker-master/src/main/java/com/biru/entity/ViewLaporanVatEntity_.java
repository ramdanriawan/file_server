package com.biru.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.metamodel.SingularAttribute;

import com.biru.view.ViewInqTr0006Entity;

import java.math.BigDecimal;
import java.util.Date;


public class ViewLaporanVatEntity_ implements Serializable {
	private static final long serialVersionUID = 1L;

	public static volatile SingularAttribute<ViewLaporanVatEntity, String> idKey;
	
	public static volatile SingularAttribute<ViewLaporanVatEntity, String> curr;

	public static volatile SingularAttribute<ViewLaporanVatEntity, String> idrAmountCgos;

	public static volatile SingularAttribute<ViewLaporanVatEntity, String> idrAmountVat;

	public static volatile SingularAttribute<ViewLaporanVatEntity, String> invoice;

	public static volatile SingularAttribute<ViewLaporanVatEntity, BigDecimal> kmkRate;

	public static volatile SingularAttribute<ViewLaporanVatEntity, Integer> month;

	public static volatile SingularAttribute<ViewLaporanVatEntity, String> originalAmountCgos;

	public static volatile SingularAttribute<ViewLaporanVatEntity, String> originalAmountVat;

	public static volatile SingularAttribute<ViewLaporanVatEntity, Date> trxDate;

	public static volatile SingularAttribute<ViewLaporanVatEntity, String> type;
}
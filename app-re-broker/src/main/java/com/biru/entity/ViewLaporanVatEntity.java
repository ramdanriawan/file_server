package com.biru.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * The persistent class for the view_laporan_vat database table.
 * 
 */
@Entity
@Table(name="VIEW_LAPORAN_VAT")
@NamedQuery(name="ViewLaporanVatEntity.findAll", query="SELECT v FROM ViewLaporanVatEntity v")
public class ViewLaporanVatEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_KEY")
	private String idKey;
	
	private String curr;
	
	private String account;

	@Column(name="IDR_AMOUNT_CGOS")
	private String idrAmountCgos;
	
	transient String idrAmountCgosStr;

	@Column(name="IDR_AMOUNT_VAT") 
	private String idrAmountVat;
	
	transient String idrAmountVatStr;

	private String invoice;

	@Column(name="KMK_RATE")
	private BigDecimal kmkRate;
	
	transient String kmkRateStr;

	private String month;

	@Column(name="ORIGINAL_AMOUNT_CGOS")
	private String originalAmountCgos;
	
	transient String originalAmountCgosStr;

	@Column(name="ORIGINAL_AMOUNT_VAT")
	private String originalAmountVat;

	transient String originalAmountVatStr;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_DATE")
	private Date trxDate;
	
	transient String trxDateStr;

	private String type;

	public String getIdKey() {
		return idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public ViewLaporanVatEntity() {
	}

	public String getCurr() {
		return this.curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	public String getIdrAmountCgos() {
		return this.idrAmountCgos;
	}

	public void setIdrAmountCgos(String idrAmountCgos) {
		this.idrAmountCgos = idrAmountCgos;
	}

	public String getIdrAmountVat() {
		return this.idrAmountVat;
	}

	public void setIdrAmountVat(String idrAmountVat) {
		this.idrAmountVat = idrAmountVat;
	}

	public String getInvoice() {
		return this.invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public BigDecimal getKmkRate() {
		return this.kmkRate;
	}

	public void setKmkRate(BigDecimal kmkRate) {
		this.kmkRate = kmkRate;
	}

	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getOriginalAmountCgos() {
		return this.originalAmountCgos;
	}

	public void setOriginalAmountCgos(String originalAmountCgos) {
		this.originalAmountCgos = originalAmountCgos;
	}

	public String getOriginalAmountVat() {
		return this.originalAmountVat;
	}

	public void setOriginalAmountVat(String originalAmountVat) {
		this.originalAmountVat = originalAmountVat;
	}

	public Date getTrxDate() {
		return this.trxDate;
	}

	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getIdrAmountCgosStr() {
		Boolean kurung = false;
		if(getIdrAmountCgos().contains("(") && getIdrAmountCgos().contains(")")) {
			kurung = true;
		}
		String result = getIdrAmountCgos().replace("(", "");
		result = result.replace(")", "");
		result = String.format("%,.2f", (new BigDecimal(result)).setScale(2, RoundingMode.HALF_EVEN)); 
		
		
		if(kurung) {
			result = ("(").concat(result).concat(")");
		}
		
		return result;
	}

	public void setIdrAmountCgosStr(String idrAmountCgosStr) {
		this.idrAmountCgosStr = idrAmountCgosStr;
	}

	public String getIdrAmountVatStr() {
		Boolean kurung = false;
		
		if(getIdrAmountVat().contains("(") && getIdrAmountVat().contains(")")) {
			kurung = true;
		}
		String result = getIdrAmountVat().replace("(", "");
		result = result.replace(")", "");
		result = String.format("%,.2f", (new BigDecimal(result)).setScale(2, RoundingMode.HALF_EVEN)); 
		if(kurung) {
			result = ("(").concat(result).concat(")");
		}
		return result;
	}

	public void setIdrAmountVatStr(String idrAmountVatStr) {
		this.idrAmountVatStr = idrAmountVatStr;
	}

	public String getKmkRateStr() {
		return NumberFormat.getCurrencyInstance().format(getKmkRate()).replace("$", "");
	}

	public void setKmkRateStr(String kmkRateStr) {
		this.kmkRateStr = kmkRateStr;
	}

	public String getOriginalAmountCgosStr() {
		Boolean kurung = false;
		
		if(getOriginalAmountCgos().contains("(") && getOriginalAmountCgos().contains(")")) {
			kurung = true;
		}
		String result = getOriginalAmountCgos().replace("(", "");
		result = result.replace(")", "");
		result = String.format("%,.2f", (new BigDecimal(result)).setScale(2, RoundingMode.HALF_EVEN)); 
		if(kurung) {
			result = ("(").concat(result).concat(")");
		}
		return result;
	}

	public void setOriginalAmountCgosStr(String originalAmountCgosStr) {
		this.originalAmountCgosStr = originalAmountCgosStr;
	}

	public String getOriginalAmountVatStr() {
		Boolean kurung = false;
		
		if(getOriginalAmountVat().contains("(") && getOriginalAmountVat().contains(")")) {
			kurung = true;
		}
		String result = getOriginalAmountVat().replace("(", "");
		result = result.replace(")", "");
		result = String.format("%,.2f", (new BigDecimal(result)).setScale(2, RoundingMode.HALF_EVEN)); 
		if(kurung) {
			result = ("(").concat(result).concat(")");
		}
		return result;
	}

	public void setOriginalAmountVatStr(String originalAmountVatStr) {
		this.originalAmountVatStr = originalAmountVatStr;
	}

	public String getTrxDateStr() {
		Calendar c = Calendar.getInstance();
		c.setTime(getTrxDate());
		String date = String.valueOf(c.get(Calendar.DATE));
		String month = getMonthName(c.get(Calendar.MONTH));
		String year = String.valueOf(c.get(Calendar.YEAR));
		return date.concat(" ").concat(month).concat(" ").concat(year);
	}

	public void setTrxDateStr(String trxDateStr) {
		this.trxDateStr = trxDateStr;
	}

	private String getMonthName (int month) {
		if(month == 0) {
			return "Januari";
		}else if(month == 1) {
			return "Februari";
		}else if(month == 2) {
			return "Maret";
		}else if(month == 3) {
			return "April";
		}else if(month == 4) {
			return "Mei";
		}else if(month == 5) {
			return "Juni";
		}else if(month == 6) {
			return "Juli";
		}else if(month == 7) {
			return "Agustus";
		}else if(month == 8) {
			return "September";
		}else if(month == 9) {
			return "Oktober";
		}else if(month == 10) {
			return "November";
		}else if(month == 11) {
			return "Desember";
		}else {
			return "";
		}
	}
}
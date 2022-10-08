package com.biru.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


/**
 * The persistent class for the outstd_rpt database table.
 * 
 */
@Entity
@Table(name="OUTSTD_RPT")
@NamedQuery(name="OutstdRptEntity.findAll", query="SELECT o FROM OutstdRptEntity o")
public class OutstdRptEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="BEG_BAL_IDR")
	private BigDecimal begBalIdr = BigDecimal.ZERO;

	@Column(name="BEG_BALANCE")
	private BigDecimal begBalance = BigDecimal.ZERO;

	private BigDecimal brkvat = BigDecimal.ZERO;

	private BigDecimal brokergare = BigDecimal.ZERO;

	@Column(name="CEDANT_NAME")
	private String cedantName;

	private String description;

	@Temporal(TemporalType.DATE)
	@Column(name="DUE_DATE")
	private Date dueDate;

	private BigDecimal endorsement = BigDecimal.ZERO;

	@Column(name="ENDORSEMENT_IDR")
	private BigDecimal endorsementIdr = BigDecimal.ZERO;

	@Id
	@Column(name="ID_KEY")
	private String idKey = UUID.randomUUID().toString();

	@Column(name="IDR_OUTS")
	private BigDecimal idrOuts = BigDecimal.ZERO;

	@Column(name="KMK_RATE")
	private BigDecimal kmkRate = BigDecimal.ZERO;

	private String name;

	@Column(name="`ORG CCY`")
	private String org_ccy;

	@Column(name="ORG_OUTS")
	private BigDecimal orgOuts = BigDecimal.ZERO;

	@Column(name="OUT_IDR")
	private BigDecimal outIdr = BigDecimal.ZERO; 

	private BigDecimal outstanding = BigDecimal.ZERO;

	private BigDecimal payment = BigDecimal.ZERO;

	@Column(name="PAYMENT_IDR")
	private BigDecimal paymentIdr = BigDecimal.ZERO;

	@Column(name="PROD_NO")
	private String prodNo;

	private String risk;

	private String status;

	@Column(name="T_F")
	private String tF;

	@Temporal(TemporalType.DATE)
	@Column(name="TRX_DATE")
	private Date trxDate;

	@Column(name="TRX_SOURCE")
	private String trxSource;

	private String type;

	private String userid;

	private BigDecimal vat = BigDecimal.ZERO;

	private String voucher;
	
	private Integer invno;
	
	@Temporal(TemporalType.DATE)
	private Date lastdate;
	
	private String dcno;
	
	@Column(name="EXCHANGE_RATE")
	private BigDecimal exchangeRate = BigDecimal.ZERO;
	
	@Column(name="OUTSTANDING_2")
	private BigDecimal outstanding2 = BigDecimal.ZERO;
	
	@Column(name="BROKERAGE")
	private BigDecimal brokerage = BigDecimal.ZERO;
	
	@Column(name="VAT_2")
	private BigDecimal vat2 = BigDecimal.ZERO;
	
	public OutstdRptEntity() {
	}

	public BigDecimal getBegBalIdr() {
		return this.begBalIdr;
	}

	public void setBegBalIdr(BigDecimal begBalIdr) {
		this.begBalIdr = begBalIdr;
	}

	public BigDecimal getBegBalance() {
		return this.begBalance;
	}

	public void setBegBalance(BigDecimal begBalance) {
		this.begBalance = begBalance;
	}

	public BigDecimal getBrkvat() {
		return this.brkvat;
	}

	public void setBrkvat(BigDecimal brkvat) {
		this.brkvat = brkvat;
	}

	public BigDecimal getBrokergare() {
		return this.brokergare;
	}

	public void setBrokergare(BigDecimal brokergare) {
		this.brokergare = brokergare;
	}

	public String getCedantName() {
		return this.cedantName;
	}

	public void setCedantName(String cedantName) {
		this.cedantName = cedantName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getEndorsement() {
		return this.endorsement;
	}

	public void setEndorsement(BigDecimal endorsement) {
		this.endorsement = endorsement;
	}

	public BigDecimal getEndorsementIdr() {
		return this.endorsementIdr;
	}

	public void setEndorsementIdr(BigDecimal endorsementIdr) {
		this.endorsementIdr = endorsementIdr;
	}

	public String getIdKey() {
		return this.idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public BigDecimal getIdrOuts() {
		return this.idrOuts;
	}

	public void setIdrOuts(BigDecimal idrOuts) {
		this.idrOuts = idrOuts;
	}

	public BigDecimal getKmkRate() {
		return this.kmkRate;
	}

	public void setKmkRate(BigDecimal kmkRate) {
		this.kmkRate = kmkRate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrg_ccy() {
		return this.org_ccy;
	}

	public void setOrg_ccy(String org_ccy) {
		this.org_ccy = org_ccy;
	}

	public BigDecimal getOrgOuts() {
		return this.orgOuts;
	}

	public void setOrgOuts(BigDecimal orgOuts) {
		this.orgOuts = orgOuts;
	}

	public BigDecimal getOutIdr() {
		return this.outIdr;
	}

	public void setOutIdr(BigDecimal outIdr) {
		this.outIdr = outIdr;
	}

	public BigDecimal getOutstanding() {
		return this.outstanding;
	}

	public void setOutstanding(BigDecimal outstanding) {
		this.outstanding = outstanding;
	}

	public BigDecimal getPayment() {
		return this.payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	public BigDecimal getPaymentIdr() {
		return this.paymentIdr;
	}

	public void setPaymentIdr(BigDecimal paymentIdr) {
		this.paymentIdr = paymentIdr;
	}

	public String getProdNo() {
		return this.prodNo;
	}

	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}

	public String getRisk() {
		return this.risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTF() {
		return this.tF;
	}

	public void setTF(String tF) {
		this.tF = tF;
	}

	public Date getTrxDate() {
		return this.trxDate;
	}

	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}

	public String getTrxSource() {
		return this.trxSource;
	}

	public void setTrxSource(String trxSource) {
		this.trxSource = trxSource;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public BigDecimal getVat() {
		return this.vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public String getVoucher() {
		return this.voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	public Integer getInvno() {
		return invno;
	}

	public void setInvno(Integer invno) {
		this.invno = invno;
	}

	public Date getLastdate() {
		return lastdate;
	}

	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}

	public String getDcno() {
		return dcno;
	}

	public void setDcno(String dcno) {
		this.dcno = dcno;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public BigDecimal getOutstanding2() {
		return outstanding2;
	}

	public void setOutstanding2(BigDecimal outstanding2) {
		this.outstanding2 = outstanding2;
	}

	public BigDecimal getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	public BigDecimal getVat2() {
		return vat2;
	}

	public void setVat2(BigDecimal vat2) {
		this.vat2 = vat2;
	}

	@Override
	public String toString() {
		return "{\n\tbegBalIdr : " + begBalIdr + ",\n\tbegBalance : " + begBalance + ",\n\tbrkvat : " + brkvat
				+ ",\n\tbrokergare : " + brokergare + ",\n\tcedantName : " + cedantName + ",\n\tdescription : "
				+ description + ",\n\tdueDate : " + dueDate + ",\n\tendorsement : " + endorsement
				+ ",\n\tendorsementIdr : " + endorsementIdr + ",\n\tidKey : " + idKey + ",\n\tidrOuts : " + idrOuts
				+ ",\n\tkmkRate : " + kmkRate + ",\n\tname : " + name + ",\n\torg_ccy : " + org_ccy + ",\n\torgOuts : "
				+ orgOuts + ",\n\toutIdr : " + outIdr + ",\n\toutstanding : " + outstanding + ",\n\tpayment : "
				+ payment + ",\n\tpaymentIdr : " + paymentIdr + ",\n\tprodNo : " + prodNo + ",\n\trisk : " + risk
				+ ",\n\tstatus : " + status + ",\n\ttF : " + tF + ",\n\ttrxDate : " + trxDate + ",\n\ttrxSource : "
				+ trxSource + ",\n\ttype : " + type + ",\n\tuserid : " + userid + ",\n\tvat : " + vat
				+ ",\n\tvoucher : " + voucher + ",\n\tinvno : " + invno + ",\n\tlastdate : " + lastdate + ",\n\tdcno : "
				+ dcno + ",\n\texchangeRate : " + exchangeRate + ",\n\toutstanding2 : " + outstanding2
				+ ",\n\tbrokerage : " + brokerage + ",\n\tvat2 : " + vat2 + "\n}";
	}

}
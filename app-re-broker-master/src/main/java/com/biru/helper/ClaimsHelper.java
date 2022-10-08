package com.biru.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ClaimsHelper implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long idKey; 
	private String txDateEst;
	private String txType;
	private String txVoucherId;
	private String txClientId;
	private String txCoverId;
	private String txInterest;
	private String txPolicyNo;
	private String txInsId;
	private String trxPartyName;
	private String txCurrId;
	private BigDecimal txSumIns;
	private String txDataStatus;
	private String action;
	
	public ClaimsHelper(Long idKey, String txDateEst, String txType, String txVoucherId, String txClientId, String txCoverId, String txInterest,
			String txPolicyNo, String txInsId, String trxPartyName, String txCurrId, BigDecimal txSumIns, String txDataStatus) {
		super();
		this.idKey = idKey;
		this.txDateEst = txDateEst;
		this.txType = txType;
		this.txVoucherId = txVoucherId;
		this.txClientId = txClientId;
		this.txCoverId = txCoverId;
		this.txInterest = txInterest;
		this.txPolicyNo = txPolicyNo;
		this.txInsId = txInsId;
		this.trxPartyName = trxPartyName;
		this.txCurrId = txCurrId;
		this.txSumIns = txSumIns;
		this.txDataStatus = txDataStatus;
	}
	

	public Long getIdKey() {
		return idKey;
	}


	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public void setTxPolicyNo(String txPolicyNo) {
		this.txPolicyNo = txPolicyNo;
	}
	
	public String getTxPolicyNo() {
		return txPolicyNo;
	}

	public String getTxDateEst() {
		return txDateEst;
	}

	public void setTxDateEst(String txDateEst) {
		this.txDateEst = txDateEst;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}
	
	public String getTxCoverId() {
		return txCoverId;
	}

	public void setTxCoverId(String txCoverId) {
		this.txCoverId = txCoverId;
	}

	public String getTxVoucherId() {
		return txVoucherId;
	}

	public void setTxVoucherId(String txVoucherId) {
		this.txVoucherId = txVoucherId;
	}

	public String getTxClientId() {
		return txClientId;
	}

	public void setTxClientId(String txClientId) {
		this.txClientId = txClientId;
	}

	public String getTxInterest() {
		return txInterest;
	}

	public void setTxInterest(String txInterest) {
		this.txInterest = txInterest;
	}

	public String getTxInsId() {
		return txInsId;
	}

	public void setTxInsId(String txInsId) {
		this.txInsId = txInsId;
	}

	public String getTrxPartyName() {
		return trxPartyName;
	}

	public void setTrxPartyName(String trxPartyName) {
		this.trxPartyName = trxPartyName;
	}

	public String getTxCurrId() {
		return txCurrId;
	}

	public void setTxCurrId(String txCurrId) {
		this.txCurrId = txCurrId;
	}

	public BigDecimal getTxSumIns() {
		return txSumIns;
	}

	public void setTxSumIns(BigDecimal txSumIns) {
		this.txSumIns = txSumIns;
	}

	public String getTxDataStatus() {
		return txDataStatus;
	}

	public void setTxDataStatus(String txDataStatus) {
		this.txDataStatus = txDataStatus;
	}
	
	
	
}

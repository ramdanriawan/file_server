package com.biru.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the tr0006c database table.
 * 
 */
@Entity
@Table(name="TR0006C")
@NamedQuery(name="TR0006CEntity.findAll", query="SELECT t FROM TR0006CEntity t")
public class TR0006CEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String idKey;

	@Column(name="TRX_BFEE_BUY")
	private BigDecimal trxBfeeBuy = BigDecimal.ZERO;

	@Column(name="TRX_BFEE_SELL")
	private BigDecimal trxBfeeSell = BigDecimal.ZERO;

	@Column(name="TRX_CURR_ID")
	private String trxCurrId;

	@Column(name="TRX_CURR_RATE")
	private BigDecimal trxCurrRate;

	@Column(name="TRX_DISC_SELL")
	private BigDecimal trxDiscSell = BigDecimal.ZERO;

	@Column(name="TRX_INS_SUB")
	private Integer trxInsSub;

	@Column(name="TRX_PREMIUM_BUY")
	private BigDecimal trxPremiumBuy = BigDecimal.ZERO;

	@Column(name="TRX_PREMIUM_SELL")
	private BigDecimal trxPremiumSell = BigDecimal.ZERO;

	@Column(name="TRX_SUM_INSURED")
	private BigDecimal trxSumInsured;
	
	@Column(name="TRX_CLASS")
	private String trxClass;

	@Column(name="TRX_TRX_ID")
	private String trxTrxId;

	@Column(name="TRX_VAT_BUY")
	private BigDecimal trxVatBuy = BigDecimal.ZERO;

	@Column(name="TRX_VAT_SELL")
	private BigDecimal trxVatSell = BigDecimal.ZERO;

	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_COM_AMOUNT")
	private BigDecimal trxComAmount;
	
	@Column(name="TRX_BRKR_FEE")
	private BigDecimal trxBrkrFee;
	
	@Column(name="TRX_TAXIN_BF")
	private BigDecimal trxTaxinBf;
	
	@Column(name="TRX_TAXIN_CL")
	private BigDecimal trxTaxinCl;
	
	@Column(name="TRX_TAXIN_OTH")
	private BigDecimal trxTaxinOth;
	
	@Column(name="TRX_OTHERS_AMOUNT")
	private BigDecimal trxOthersAmount;
	
	@Column(name="TRX_OTHERS1_AMOUNT")
	private BigDecimal trxOthers1Amount;
	
	@Column(name="TRX_OTHERS2_AMOUNT")
	private BigDecimal trxOthers2Amount;
	
	@Column(name="TRX_OTHERS3_AMOUNT")
	private BigDecimal trxOthers3Amount;
	
	@Column(name="TRX_OTHERS4_AMOUNT")
	private BigDecimal trxOthers4Amount;
	
	@Column(name="TRX_NET_TOU")
	private BigDecimal trxNetTou = BigDecimal.ZERO;
	
	@Column(name="TRX_NET_TTL")
	private BigDecimal trxNetTtl = BigDecimal.ZERO;
	
	@Column(name="TRX_POL_AMOUNT")
	private BigDecimal trxPolAmount;
	
	@Column(name="TRX_GROSS_AMOUNT")
	private BigDecimal trxGrossAmount;
	
	@Column(name="TRX_COMO_AMOUNT")
	private BigDecimal trxComoAmount;
	
	@Column(name="TRX_SDUTY_AMOUNT")
	private BigDecimal trxSdutyAmount;
	
	@Column(name="TRX_ADMIN_AMOUNT")
	private BigDecimal trxAdminAmount;
	
	@Column(name="TRX_BANK_AMOUNT")
	private BigDecimal trxBankAmount;
	
	@Column(name="TRX_INC_AMOUNT")
	private BigDecimal trxIncAmount;
	
	@Column(name="TRX_WITH_AMOUNT")
	private BigDecimal trxWithAmount;
	
	@Column(name="IS_MODIFY_TRX_BRKR_FEE")
	private Boolean isModifyTrxBrkrFee;
	
	@Column(name="IS_MODIFY_TRX_TAXIN_BF")
	private Boolean isModifyTrxTaxinBf;
	
	@Column(name="IS_MODIFY_TRX_TAXIN_OTH")
	private Boolean isModifyTrxTaxinOth;
	
	@Transient
	private String interestName;

	public TR0006CEntity() {
	}

	public TR0006CEntity(String idKey, BigDecimal trxBfeeBuy, BigDecimal trxBfeeSell, String trxCurrId,
			BigDecimal trxCurrRate, BigDecimal trxDiscSell, Integer trxInsSub, BigDecimal trxPremiumBuy,
			BigDecimal trxPremiumSell, BigDecimal trxSumInsured, String trxTrxId, BigDecimal trxVatBuy,
			BigDecimal trxVatSell, String trxVoucherId, String interestName, 
			BigDecimal trxNetTou, BigDecimal trxNetTtl) {
		super();
		this.idKey = idKey;
		this.trxBfeeBuy = trxBfeeBuy;
		this.trxBfeeSell = trxBfeeSell;
		this.trxCurrId = trxCurrId;
		this.trxCurrRate = trxCurrRate;
		this.trxDiscSell = trxDiscSell;
		this.trxInsSub = trxInsSub;
		this.trxPremiumBuy = trxPremiumBuy;
		this.trxPremiumSell = trxPremiumSell;
		this.trxSumInsured = trxSumInsured;
		this.trxTrxId = trxTrxId;
		this.trxVatBuy = trxVatBuy;
		this.trxVatSell = trxVatSell;
		this.trxVoucherId = trxVoucherId;
		this.interestName = interestName;
		this.trxNetTou = trxNetTou;
		this.trxNetTtl = trxNetTtl;
	}

	public String getIdKey() {
		return this.idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}
	
	public String getTrxClass() {
		return trxClass;
	}

	public void setTrxClass(String trxClass) {
		this.trxClass = trxClass;
	}

	public BigDecimal getTrxBfeeBuy() {
		return this.trxBfeeBuy;
	}

	public void setTrxBfeeBuy(BigDecimal trxBfeeBuy) {
		this.trxBfeeBuy = trxBfeeBuy;
	}

	public BigDecimal getTrxBfeeSell() {
		return this.trxBfeeSell;
	}

	public void setTrxBfeeSell(BigDecimal trxBfeeSell) {
		this.trxBfeeSell = trxBfeeSell;
	}

	public String getTrxCurrId() {
		return this.trxCurrId;
	}

	public void setTrxCurrId(String trxCurrId) {
		this.trxCurrId = trxCurrId;
	}

	public BigDecimal getTrxCurrRate() {
		return this.trxCurrRate;
	}

	public void setTrxCurrRate(BigDecimal trxCurrRate) {
		this.trxCurrRate = trxCurrRate;
	}

	public BigDecimal getTrxDiscSell() {
		return this.trxDiscSell;
	}

	public void setTrxDiscSell(BigDecimal trxDiscSell) {
		this.trxDiscSell = trxDiscSell;
	}

	public int getTrxInsSub() {
		return this.trxInsSub;
	}

	public void setTrxInsSub(Integer trxInsSub) {
		this.trxInsSub = trxInsSub;
	}

	public BigDecimal getTrxPremiumBuy() {
		return this.trxPremiumBuy;
	}

	public void setTrxPremiumBuy(BigDecimal trxPremiumBuy) {
		this.trxPremiumBuy = trxPremiumBuy;
	}

	public BigDecimal getTrxPremiumSell() {
		return this.trxPremiumSell;
	}

	public void setTrxPremiumSell(BigDecimal trxPremiumSell) {
		this.trxPremiumSell = trxPremiumSell;
	}

	public BigDecimal getTrxSumInsured() {
		return this.trxSumInsured;
	}

	public void setTrxSumInsured(BigDecimal trxSumInsured) {
		this.trxSumInsured = trxSumInsured;
	}

	public String getTrxTrxId() {
		return this.trxTrxId;
	}

	public void setTrxTrxId(String trxTrxId) {
		this.trxTrxId = trxTrxId;
	}

	public BigDecimal getTrxVatBuy() {
		return this.trxVatBuy;
	}

	public void setTrxVatBuy(BigDecimal trxVatBuy) {
		this.trxVatBuy = trxVatBuy;
	}

	public BigDecimal getTrxVatSell() {
		return this.trxVatSell;
	}

	public void setTrxVatSell(BigDecimal trxVatSell) {
		this.trxVatSell = trxVatSell;
	}
	
	public String getTrxVoucherId() {
		return this.trxVoucherId;
	}

	public void setTrxVoucherId(String trxVoucherId) {
		this.trxVoucherId = trxVoucherId;
	}
	
	public BigDecimal getTrxComAmount() {
		return trxComAmount;
	}

	public void setTrxComAmount(BigDecimal trxComAmount) {
		this.trxComAmount = trxComAmount;
	}
	
	public BigDecimal getTrxBrkrFee() {
		return trxBrkrFee;
	}

	public void setTrxBrkrFee(BigDecimal trxBrkrFee) {
		this.trxBrkrFee = trxBrkrFee;
	}
	
	public BigDecimal getTrxTaxinBf() {
		return trxTaxinBf;
	}

	public void setTrxTaxinBf(BigDecimal trxTaxinBf) {
		this.trxTaxinBf = trxTaxinBf;
	}

	public BigDecimal getTrxTaxinCl() {
		return trxTaxinCl;
	}

	public void setTrxTaxinCl(BigDecimal trxTaxinCl) {
		this.trxTaxinCl = trxTaxinCl;
	}

	public BigDecimal getTrxTaxinOth() {
		return trxTaxinOth;
	}

	public void setTrxTaxinOth(BigDecimal trxTaxinOth) {
		this.trxTaxinOth = trxTaxinOth;
	}

	public BigDecimal getTrxOthersAmount() {
		return trxOthersAmount;
	}

	public void setTrxOthersAmount(BigDecimal trxOthersAmount) {
		this.trxOthersAmount = trxOthersAmount;
	}

	public BigDecimal getTrxOthers1Amount() {
		return trxOthers1Amount;
	}

	public void setTrxOthers1Amount(BigDecimal trxOthers1Amount) {
		this.trxOthers1Amount = trxOthers1Amount;
	}

	public BigDecimal getTrxOthers2Amount() {
		return trxOthers2Amount;
	}

	public void setTrxOthers2Amount(BigDecimal trxOthers2Amount) {
		this.trxOthers2Amount = trxOthers2Amount;
	}

	public BigDecimal getTrxOthers3Amount() {
		return trxOthers3Amount;
	}

	public void setTrxOthers3Amount(BigDecimal trxOthers3Amount) {
		this.trxOthers3Amount = trxOthers3Amount;
	}

	public BigDecimal getTrxOthers4Amount() {
		return trxOthers4Amount;
	}

	public void setTrxOthers4Amount(BigDecimal trxOthers4Amount) {
		this.trxOthers4Amount = trxOthers4Amount;
	}
	
	public BigDecimal getTrxNetTou() {
		return trxNetTou;
	}

	public void setTrxNetTou(BigDecimal trxNetTou) {
		this.trxNetTou = trxNetTou;
	}

	public BigDecimal getTrxNetTtl() {
		return trxNetTtl;
	}

	public void setTrxNetTtl(BigDecimal trxNetTtl) {
		this.trxNetTtl = trxNetTtl;
	}

	public BigDecimal getTrxAdminAmount() {
		return trxAdminAmount;
	}

	public void setTrxAdminAmount(BigDecimal trxAdminAmount) {
		this.trxAdminAmount = trxAdminAmount;
	}

	public BigDecimal getTrxGrossAmount() {
		return trxGrossAmount;
	}
	
	public BigDecimal getTrxComoAmount() {
		return trxComoAmount;
	}

	public void setTrxComoAmount(BigDecimal trxComoAmount) {
		this.trxComoAmount = trxComoAmount;
	}

	public void setTrxGrossAmount(BigDecimal trxGrossAmount) {
		this.trxGrossAmount = trxGrossAmount;
	}

	public BigDecimal getTrxPolAmount() {
		return trxPolAmount;
	}

	public void setTrxPolAmount(BigDecimal trxPolAmount) {
		this.trxPolAmount = trxPolAmount;
	}

	public BigDecimal getTrxSdutyAmount() {
		return trxSdutyAmount;
	}

	public void setTrxSdutyAmount(BigDecimal trxSdutyAmount) {
		this.trxSdutyAmount = trxSdutyAmount;
	}
	
	public BigDecimal getTrxBankAmount() {
		return trxBankAmount;
	}

	public void setTrxBankAmount(BigDecimal trxBankAmount) {
		this.trxBankAmount = trxBankAmount;
	}
	
	public BigDecimal getTrxIncAmount() {
		return trxIncAmount;
	}

	public void setTrxIncAmount(BigDecimal trxIncAmount) {
		this.trxIncAmount = trxIncAmount;
	}
	
	public BigDecimal getTrxWithAmount() {
		return trxWithAmount;
	}

	public void setTrxWithAmount(BigDecimal trxWithAmount) {
		this.trxWithAmount = trxWithAmount;
	}
	
	public Boolean getIsModifyTrxBrkrFee() {
		return isModifyTrxBrkrFee;
	}

	public void setIsModifyTrxBrkrFee(Boolean isModifyTrxBrkrFee) {
		this.isModifyTrxBrkrFee = isModifyTrxBrkrFee;
	}

	public Boolean getIsModifyTrxTaxinBf() {
		return isModifyTrxTaxinBf;
	}

	public void setIsModifyTrxTaxinBf(Boolean isModifyTrxTaxinBf) {
		this.isModifyTrxTaxinBf = isModifyTrxTaxinBf;
	}

	public Boolean getIsModifyTrxTaxinOth() {
		return isModifyTrxTaxinOth;
	}

	public void setIsModifyTrxTaxinOth(Boolean isModifyTrxTaxinOth) {
		this.isModifyTrxTaxinOth = isModifyTrxTaxinOth;
	}

	public String getInterestName() {
		return interestName;
	}

	public void setInterestName(String interestName) {
		this.interestName = interestName;
	}
	
}
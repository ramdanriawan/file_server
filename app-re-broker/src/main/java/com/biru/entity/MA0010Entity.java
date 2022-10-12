package com.biru.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;

/**
 * The persistent class for the ma0010 database table.
 * 
 */
@Entity
@Table(name="MA0010")
@NamedQuery(name="MA0010Entity.findAll", query="SELECT m FROM MA0010Entity m")
public class MA0010Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CO_ADDRESS")
	private String coAddress;

	@Lob
	@Column(name="CO_BGPIC")
	private byte[] coBgpic;

	@Column(name="CO_BOD_DIRECTOR")
	private String coBodDirector;

	@Column(name="CO_BOD_PRESIDENT")
	private String coBodPresident;

	@Temporal(TemporalType.DATE)
	@Column(name="CO_EX_DATE")
	private Date coExDate;

	@Temporal(TemporalType.DATE)
	@Column(name="CO_EXPIRED")
	private Date coExpired;

	@Temporal(TemporalType.DATE)
	@Column(name="CO_JOINT")
	private Date coJoint;

	@Lob
	@Column(name="CO_LICENSE")
	private String coLicense;

	@Lob
	@Column(name="CO_LOGO")
	private byte[] coLogo;

	@Lob
	@Column(name="CO_MESSAGE")
	private String coMessage;

	@Column(name="CO_NAME")
	private String coName;

	@Column(name="CO_PIC_EMAIL")
	private String coPicEmail;

	@Column(name="CO_PIC_EMAIL_PASS")
	private String coPicEmailPass;
	
	@Column(name="CO_PIC_EMAIL_ALIAS")
	private String coPicEmailAlias;

	@Column(name="CO_PIC_NAME")
	private String coPicName;

	@Column(name="CO_PIC_TELP")
	private String coPicTelp;

	@Column(name="CO_TAX_ID")
	private String coTaxId;

	@Column(name="CO_TYPE")
	private String coType;

	@Column(name="CREATE_BY")
	private String createBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_ON")
	private Date createOn;

	@Id
	@Column(name = "ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKey;

	@Column(name="MODIFY_BY")
	private String modifyBy;

	@Temporal(TemporalType.DATE)
	@Column(name="MODIFY_ON")
	private Date modifyOn;

	public MA0010Entity() {
	}
	
	public MA0010Entity(String coPicEmail, String coPicEmailPass, String coPicEmailAlias) {
		this.coPicEmail = coPicEmail;
		this.coPicEmailPass = coPicEmailPass;
		this.coPicEmailAlias = coPicEmailAlias;
	}

	public String getCoAddress() {
		return this.coAddress;
	}

	public void setCoAddress(String coAddress) {
		this.coAddress = coAddress;
	}

	public byte[] getCoBgpic() {
		return this.coBgpic;
	}

	public void setCoBgpic(byte[] coBgpic) {
		this.coBgpic = coBgpic;
	}

	public String getCoBodDirector() {
		return this.coBodDirector;
	}

	public void setCoBodDirector(String coBodDirector) {
		this.coBodDirector = coBodDirector;
	}

	public String getCoBodPresident() {
		return this.coBodPresident;
	}

	public void setCoBodPresident(String coBodPresident) {
		this.coBodPresident = coBodPresident;
	}

	public Date getCoExDate() {
		return this.coExDate;
	}

	public void setCoExDate(Date coExDate) {
		this.coExDate = coExDate;
	}

	public Date getCoExpired() {
		return this.coExpired;
	}

	public void setCoExpired(Date coExpired) {
		this.coExpired = coExpired;
	}

	public Date getCoJoint() {
		return this.coJoint;
	}

	public void setCoJoint(Date coJoint) {
		this.coJoint = coJoint;
	}

	public String getCoLicense() {
		return this.coLicense;
	}

	public void setCoLicense(String coLicense) {
		this.coLicense = coLicense;
	}

	public byte[] getCoLogo() {
		return this.coLogo;
	}

	public void setCoLogo(byte[] coLogo) {
		this.coLogo = coLogo;
	}

	public String getCoMessage() {
		return this.coMessage;
	}

	public void setCoMessage(String coMessage) {
		this.coMessage = coMessage;
	}

	public String getCoName() {
		return this.coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

	public String getCoPicEmail() {
		return this.coPicEmail;
	}

	public void setCoPicEmail(String coPicEmail) {
		this.coPicEmail = coPicEmail;
	}

	public String getCoPicEmailPass() {
		return this.coPicEmailPass;
	}

	public void setCoPicEmailPass(String coPicEmailPass) {
		this.coPicEmailPass = coPicEmailPass;
	}
	
	public String getCoPicEmailAlias() {
		return coPicEmailAlias;
	}

	public void setCoPicEmailAlias(String coPicEmailAlias) {
		this.coPicEmailAlias = coPicEmailAlias;
	}

	public String getCoPicName() {
		return this.coPicName;
	}

	public void setCoPicName(String coPicName) {
		this.coPicName = coPicName;
	}

	public String getCoPicTelp() {
		return this.coPicTelp;
	}

	public void setCoPicTelp(String coPicTelp) {
		this.coPicTelp = coPicTelp;
	}

	public String getCoTaxId() {
		return this.coTaxId;
	}

	public void setCoTaxId(String coTaxId) {
		this.coTaxId = coTaxId;
	}

	public String getCoType() {
		return this.coType;
	}

	public void setCoType(String coType) {
		this.coType = coType;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateOn() {
		return this.createOn;
	}

	public void setCreateOn(Timestamp createOn) {
		this.createOn = createOn;
	}

	public Long getIdKey() {
		return this.idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public String getModifyBy() {
		return this.modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyOn() {
		return this.modifyOn;
	}

	public void setModifyOn(Date modifyOn) {
		this.modifyOn = modifyOn;
	}

}
package com.biru.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the VOUCHER_COUNTER database table.
 * 
 */
@Entity
@Table(name="VOUCHER_COUNTER")
@NamedQuery(name="VoucherCounterEntity.findAll", query="SELECT v FROM VoucherCounterEntity v")
public class VoucherCounterEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private int id = 0;

	@Column(name="COUNTER")
	private int counter;

	@Column(name="DATE")
	private String date;

	public VoucherCounterEntity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCounter() {
		return this.counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
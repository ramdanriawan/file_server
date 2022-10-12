package com.biru.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the SEQUENCE database table.
 * 
 */
@Entity
@Table(name="SEQUENCE")
@NamedQuery(name="SequenceEntity.findAll", query="SELECT s FROM SequenceEntity s")
public class SequenceEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private Integer id;
	
	@Column(name="SEQUENCE_NAME")
	private String sequenceName;

	@Column(name="COUNTER")
	private Integer counter;

	public SequenceEntity() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}

}
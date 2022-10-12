package com.biru.common.entity;

public class DropdownDash {

	private String id;
	private String text;

	public DropdownDash(String id, String text) {
		this.id = id.trim();
		this.text = id.trim() + "-" + text.trim();
	}

	public DropdownDash() {
	}

	public String getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}	
	
}

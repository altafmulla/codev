package com.hvk.societmaintain.model;

import java.io.Serializable;

public class RefDataResponse implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String abbreviation;
private String name;
private String filter;
public String getFilter() {
	return filter;
}
public void setFilter(String filter) {
	this.filter = filter;
}
public String getAbbreviation() {
	return abbreviation;
}
public void setAbbreviation(String abbreviation) {
	this.abbreviation = abbreviation;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}


}

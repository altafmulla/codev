package com.hvk.societmaintain.model;

import java.io.Serializable;

public class SocietyResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Society society;

	public Society getSociety() {
		return society;
	}

	public void setSociety(Society society) {
		this.society = society;
	}
	
}

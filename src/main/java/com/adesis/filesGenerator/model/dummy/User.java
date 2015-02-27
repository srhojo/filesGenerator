package com.adesis.filesGenerator.model.dummy;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4543597804765175757L;
	private String name;
	private String company;

	public User() {
	}

	public User(final String name, final String company) {
		super();
		this.name = name;
		this.company = company;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(final String company) {
		this.company = company;
	}

}

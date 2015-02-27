package com.adesis.filesGenerator.model.dummy;

import java.io.Serializable;

public class Planet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2444760626653097669L;
	private String name;

	public Planet() {
	}

	public Planet(final String name) {
		super();
		this.name = name;
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

}

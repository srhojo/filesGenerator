package com.adesis.filesGenerator.model.dummy;

public class Direccion {
	private String calle;
	private Integer numero;

	public Direccion() {
	}

	public Direccion(final String calle, final Integer numero) {
		super();
		this.calle = calle;
		this.numero = numero;
	}

	/**
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}

	/**
	 * @param calle
	 *            the calle to set
	 */
	public void setCalle(final String calle) {
		this.calle = calle;
	}

	/**
	 * @return the numero
	 */
	public Integer getNumero() {
		return numero;
	}

	/**
	 * @param numero
	 *            the numero to set
	 */
	public void setNumero(final Integer numero) {
		this.numero = numero;
	}

}

package com.adesis.filesGenerator.model.dummy;

public class Maquetador {
	private String nombre;
	private String empresa;

	public Maquetador() {
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the empresa
	 */
	public String getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa
	 *            the empresa to set
	 */
	public void setEmpresa(final String empresa) {
		this.empresa = empresa;
	}

	public Maquetador(final String nombre, final String empresa) {
		super();
		this.nombre = nombre;
		this.empresa = empresa;
	}

}

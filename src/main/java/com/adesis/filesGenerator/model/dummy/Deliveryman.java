package com.adesis.filesGenerator.model.dummy;

import org.joda.time.LocalTime;

public class Deliveryman {

	private Integer id;
	private String name;
	private String surname;
	private Integer years;
	private LocalTime time;
	private Planet planet;

	public Deliveryman() {
	}

	public Deliveryman(final Integer id, final String name, final String surname, final Integer years, final LocalTime time,
			final Planet planet) {
		super();
		this.setId(id);
		this.name = name;
		this.surname = surname;
		this.years = years;
		this.time = time;
		this.planet = planet;
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
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname
	 *            the surname to set
	 */
	public void setSurname(final String surname) {
		this.surname = surname;
	}

	/**
	 * @return the years
	 */
	public Integer getYears() {
		return years;
	}

	/**
	 * @param years
	 *            the years to set
	 */
	public void setYears(final Integer years) {
		this.years = years;
	}

	/**
	 * @return the time
	 */
	public LocalTime getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(final LocalTime time) {
		this.time = time;
	}

	/**
	 * @return the planet
	 */
	public Planet getPlanet() {
		return planet;
	}

	/**
	 * @param planet
	 *            the planet to set
	 */
	public void setPlanet(final Planet planet) {
		this.planet = planet;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

}

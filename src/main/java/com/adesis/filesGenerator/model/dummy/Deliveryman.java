package com.adesis.filesGenerator.model.dummy;

import java.io.Serializable;

import org.joda.time.LocalTime;

public class Deliveryman implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5964890068623666838L;
	private Integer id;
	private String name;
	private String surname;
	private Integer years;
	private LocalTime time;
	private Planet planet;
	private Money salary;

	public Deliveryman() {
	}

	public Deliveryman(final Integer id, final String name, final String surname, final Integer years, final LocalTime time,
			final Planet planet, final Money salary)  {
		super();
		this.setId(id);
		this.name = name;
		this.surname = surname;
		this.years = years;
		this.time = time;
		this.planet = planet;
		this.salary = salary;
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
	 * @param id
	 *            the id to set
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * @return the salary
	 */
	public Money getSalary() {
		return salary;
	}

	/**
	 * @param salary
	 *            the salary to set
	 */
	public void setSalary(final Money salary) {
		this.salary = salary;
	}

}

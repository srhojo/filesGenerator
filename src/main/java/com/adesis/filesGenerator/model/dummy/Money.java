/**
 *
 */
package com.adesis.filesGenerator.model.dummy;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Javier Lacalle
 *
 */
public class Money implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6511705291785702096L;
	private BigDecimal amount;
	private String currency;

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	/**
	 * @param amount
	 * @return
	 */
	public static Money createInEuros(final BigDecimal amount) {
		final Money money = new Money();
		money.setAmount(amount);
		money.setCurrency("EUR");
		return money;
	}
}

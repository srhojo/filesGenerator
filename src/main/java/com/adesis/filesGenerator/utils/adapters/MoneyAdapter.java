package com.adesis.filesGenerator.utils.adapters;

import com.adesis.filesGenerator.model.dummy.Money;

/**
 * @author Javier Lacalle
 *
 */
public class MoneyAdapter {

	/**
	 * @param money
	 * @return
	 */
	public String parseMoneyToString(final Money money) {
		return money.getAmount() + " " + money.getCurrency();
	}
}

package com.adesis.filesGenerator.utils;

import com.adesis.filesGenerator.model.dummy.Money;

/**
 * @author Javier Lacalle
 *
 */
public class UtilsMoney {

	/**
	 * @param money
	 * @return
	 */
	public String parseMoneyToString(final Money money) {
		return money.getAmount() + " " + money.getCurrency();
	}
}

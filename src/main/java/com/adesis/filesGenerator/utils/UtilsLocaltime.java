/**
 *
 */
package com.adesis.filesGenerator.utils;

import org.joda.time.LocalTime;

/**
 * @author Javier Lacalle
 *
 */
public class UtilsLocaltime {

	public String parseLocaltimeToString(final LocalTime time, final String timeFormat) {
		if (time == null) {
			return null;
		}
		return time.toString(timeFormat);
	}
}

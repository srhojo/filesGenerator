package com.adesis.filesGenerator.utils.adapters;

import org.joda.time.LocalTime;

/**
 * @author Javier Lacalle
 *
 */
public class LocaltimeAdapter {

	public String parseLocaltimeToString(final LocalTime time, final String timeFormat) {
		if (time == null) {
			return null;
		}
		return time.toString(timeFormat);
	}
}

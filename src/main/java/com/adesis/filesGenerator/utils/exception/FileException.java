/**
 *
 */
package com.adesis.filesGenerator.utils.exception;

/**
 * @author Javier Lacalle
 *
 */
public class FileException extends Exception {

	private static final long serialVersionUID = 1242875364800654858L;

	private final EnumFileException enumFileException;

	public FileException(final EnumFileException enumFileException, final String message) {
		super(message);
		this.enumFileException = enumFileException;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append("(ERROR)-");
		sb.append(this.enumFileException.getCode());
		sb.append(": ");
		sb.append(super.getMessage());
		return sb.toString();
	}

}

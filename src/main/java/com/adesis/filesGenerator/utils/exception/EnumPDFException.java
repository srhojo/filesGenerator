package com.adesis.filesGenerator.utils.exception;


/**
 * @author Javier Lacalle
 *
 */
public enum EnumPDFException {
	ERROR_GENERATE_TEMPLATE("Error generando la plantilla Jade"), ERROR_GENERATE_PDF("Error al redederizar el PDF"), ERROR("");

	private String code;

	EnumPDFException(final String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static EnumPDFException fromCode(final String code) {
		for (final EnumPDFException status : values()) {
			if (status.code.equals(code)) {
				return status;
			}
		}
		return EnumPDFException.ERROR;
	}
}

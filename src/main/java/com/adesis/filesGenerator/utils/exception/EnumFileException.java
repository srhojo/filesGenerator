package com.adesis.filesGenerator.utils.exception;

/**
 * @author Javier Lacalle
 *
 */
public enum EnumFileException {
	ERROR_PDF_GENERATE_TEMPLATE("Error generando la plantilla Jade."), ERROR_PDF_GENERATE("Error al redederizar el PDF."), ERROR_EXCEL_GENERATE(
			"Error generando el Excel."), ERROR_TXT_GENERATE("Error generando el fichero txt."), ERROR("");

	private String code;

	EnumFileException(final String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static EnumFileException fromCode(final String code) {
		for (final EnumFileException status : values()) {
			if (status.code.equals(code)) {
				return status;
			}
		}
		return EnumFileException.ERROR;
	}
}

package com.adesis.filesGenerator.utils;

import java.util.Map;

/**
 * @author Javier Lacalle
 *
 */
public interface IUtilsFileGenerator {

	/**
	 * Método para generar un fichero PDF a partir de una plantilla Jade con un CSS especifico y un Modelo de datos asociado a la plantilla
	 *
	 * El modelo debe llevar todas las "key" que se usan en la plantilla aunque estás estén vacias.
	 *
	 *
	 * @param cssFile
	 *            location form css file
	 * @param templateFile
	 *            location from Jade template file
	 * @param Map
	 *            with data model
	 * @return PDF encoding to byte
	 */
	byte[] createPDFInBytes(final String templateFile, final String cssFile, final Map<String, Object> data);

}

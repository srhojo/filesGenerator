package com.adesis.filesGenerator.utils;

import com.adesis.filesGenerator.model.ExcelGenerationInfo;
import com.adesis.filesGenerator.model.FileGenerationInfo;
import com.adesis.filesGenerator.utils.exception.FileException;

/**
 * @author Javier Lacalle
 *
 */
public interface IUtilsFileGenerator {

	/**
	 * M�todo para generar un fichero PDF a partir de una plantilla Jade con un CSS especifico y un Modelo de datos asociado a la
	 * plantilla
	 *
	 * El modelo debe llevar todas las "key" que se usan en la plantilla aunque est�s est�n vacias.
	 *
	 * @param fileGenerationInfo
	 * @return
	 * @throws FileException
	 */
	byte[] createPDFInBytes(final FileGenerationInfo fileGenerationInfo) throws FileException;

	/**
	 * @param fileGenerationInfo
	 * @return
	 */
	byte[] createTXTInBytes(final FileGenerationInfo fileGenerationInfo) throws FileException;;

	/**
	 * @param fileGenerationInfo
	 * @return
	 * @throws FileException
	 */
	byte[] createExcelInBytes(final ExcelGenerationInfo fileGenerationInfo) throws FileException;
}

/**
 *
 */
package com.adesis.filesGenerator.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.adesis.filesGenerator.model.FileGenerationInfo;
import com.adesis.filesGenerator.utils.exception.PDFException;
import com.lowagie.text.pdf.PdfWriter;

import de.neuland.jade4j.Jade4J;
import de.neuland.jade4j.exceptions.JadeCompilerException;

/**
 * @author Javier Lacalle
 *
 */
@Component
public class UtilsFilesGeneratorImpl implements IUtilsFileGenerator {

	private static final String ENCODE = "UTF-8";

	/**
	 *
	 */
	@Override
	public byte[] createPDFInBytes(final FileGenerationInfo fileGenerationInfo) {

		byte[] pdfBytes = null;

		try {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final ITextRenderer renderer = new ITextRenderer();

			renderer.setPDFVersion(new Character(PdfWriter.VERSION_1_7));
			renderer.setDocumentFromString(renderJadeToString(fileGenerationInfo));
			renderer.layout();
			renderer.createPDF(baos);

			baos.flush();
			baos.close();

			pdfBytes = baos.toByteArray();

		} catch (final Exception e) {
			e.printStackTrace();
		}
		return pdfBytes;
	}

	@Override
	public byte[] createTXTInBytes(final Map<String, Object> data) {

		byte[] txtBytes = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;

		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(data);
			oos.flush();
			bos.flush();
			bos.close();
			oos.close();
			txtBytes = bos.toByteArray();

		} catch (final Exception e) {
			e.printStackTrace();
		}

		return txtBytes;
	}

	/**
	 * Método para generer el array de bytes con el que se generará el PDF.
	 *
	 * @param jadeFile
	 *            Dirección completa donde se encuentra el fichero Jade para rederizar.
	 * @param data
	 *            HashMap con los datos con los que rellenar la plantilla.
	 * @return array de bytes con el HTML rederizado.
	 * @throws JadeCompilerException
	 * @throws IOException
	 */
	private byte[] renderJadeToBytes(final String jadeFile, final Map<String, Object> data) throws JadeCompilerException, IOException {
		final String html = Jade4J.render(jadeFile, data);
		return html.getBytes(ENCODE);
	}

	/**
	 * Método para generar un Sting a partir de la plantilla Jade y los datos del modelo.
	 *
	 * @param pdfGenerationInfo
	 *            Objeto que contiene toda la información relacionada con la generación del PDF.
	 * @return
	 * @throws PDFException
	 */
	@SuppressWarnings("unchecked")
	private String renderJadeToString(final FileGenerationInfo pdfGenerationInfo) throws PDFException {
		// Add CSS
		// pdfGenerationInfo.getDataModel().put("css", pdfGenerationInfo.getTemplateCss());
		// Add utils lib.
		((Map<String, Object>) pdfGenerationInfo.getDataModel()).put("utilsTime", new UtilsLocaltime());
		((Map<String, Object>) pdfGenerationInfo.getDataModel()).put("utilsMoney", new UtilsMoney());
		// Render Jade with data model
		try {
			return Jade4J.render(pdfGenerationInfo.getTemplate(), (Map<String, Object>) pdfGenerationInfo.getDataModel());
		} catch (JadeCompilerException | IOException e) {
			e.printStackTrace();
			throw new PDFException();
		}
	}

}

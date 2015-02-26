/**
 *
 */
package com.adesis.filesGenerator.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

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
	public byte[] createPDFInBytes(final String templateFile, final String cssFile, final Map<String, Object> data) {

		byte[] pdfBytes = null;
		// Añadimos el CSS.
		data.put("css", cssFile);

		// Add utils classes
		data.put("utilsTime", new UtilsLocaltime());
		data.put("utilsMoney", new UtilsMoney());

		try {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final ITextRenderer renderer = new ITextRenderer();

			final byte[] byteArray = renderJadeToBytes(templateFile, data);
			final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			final ByteArrayInputStream baosAux = new ByteArrayInputStream(byteArray);
			final Document doc = builder.parse(baosAux);

			renderer.setDocument(doc, null);
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

	/**
	 * Método para generer el array de bytes con el que se generará el PDF.
	 *
	 * @param jadeFile
	 *            Dirección completa donde se encuentra el fichero Jade para rederizar.
	 * @param data
	 *            HasMap con los datos con los que rellenar la plantilla.
	 * @return array de bytes con el HTML rederizado.
	 * @throws JadeCompilerException
	 * @throws IOException
	 */
	private byte[] renderJadeToBytes(final String jadeFile, final Map<String, Object> data) throws JadeCompilerException, IOException {
		final String html = Jade4J.render(jadeFile, data);
		return html.getBytes(ENCODE);
	}

}

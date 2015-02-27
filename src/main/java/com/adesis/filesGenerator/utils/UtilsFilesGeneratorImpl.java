package com.adesis.filesGenerator.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.adesis.filesGenerator.model.FileGenerationInfo;
import com.adesis.filesGenerator.utils.exception.EnumFileException;
import com.adesis.filesGenerator.utils.exception.FileException;
import com.lowagie.text.pdf.PdfWriter;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.exceptions.JadeCompilerException;
import de.neuland.jade4j.template.JadeTemplate;

/**
 * @author Javier Lacalle
 *
 */
@Component
public class UtilsFilesGeneratorImpl implements IUtilsFileGenerator {

	private final static String ENCODE = "UTF-8";
	// TODO Estaría mejor obteniendo el context path y en el template pasarle simplemente el nombre de la plantilla "futurama-demo"
	private final static String CONTEXT_PATH = "";

	@Autowired
	private ConfigurationJadeTemplate configurationJadeTemplate;

	/**
	 * @throws FileException
	 *
	 */
	@Override
	public byte[] createPDFInBytes(final FileGenerationInfo fileGenerationInfo) throws FileException {

		byte[] pdfBytes = null;

		try {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final ITextRenderer renderer = new ITextRenderer();

			// Replace images
			renderer.getSharedContext().setReplacedElementFactory(
					new ImagesReplacesElementFactory(renderer.getSharedContext().getReplacedElementFactory()));
			renderer.setPDFVersion(new Character(PdfWriter.VERSION_1_7));
			renderer.setDocumentFromString(renderJadeToString(fileGenerationInfo));
			renderer.layout();
			renderer.createPDF(baos);

			baos.flush();
			baos.close();

			pdfBytes = baos.toByteArray();

		} catch (final FileException fe) {
			throw fe;
		} catch (final Exception e) {
			// e.printStackTrace();
			throw new FileException(EnumFileException.ERROR_PDF_GENERATE, e.getMessage());
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
	 * Método para generar un Sting a partir de la plantilla Jade y los datos del modelo.
	 *
	 * @param pdfGenerationInfo
	 *            Objeto que contiene toda la información relacionada con la generación del PDF.
	 * @return
	 * @throws FileException
	 */
	@SuppressWarnings("unchecked")
	private String renderJadeToString(final FileGenerationInfo pdfGenerationInfo) throws FileException {
		// Add utils lib.
		final Map<String, Object> dataModel = (Map<String, Object>) pdfGenerationInfo.getDataModel();
		dataModel.put("utilsTime", new UtilsLocaltime());
		dataModel.put("utilsMoney", new UtilsMoney());
		try {
			// Set configuration
			final JadeConfiguration jadeConfiguration = new JadeConfiguration();
			configurationJadeTemplate.setConfiguration(CONTEXT_PATH, ENCODE, pdfGenerationInfo.getTemplate());

			// Generate Template
			final JadeTemplate template = configurationJadeTemplate.configureJadeTemplate();

			// Render Jade with data model
			return jadeConfiguration.renderTemplate(template, dataModel);
		} catch (JadeCompilerException | IOException e) {
			throw new FileException(EnumFileException.ERROR_PDF_GENERATE_TEMPLATE, e.getMessage());
		}
	}

}

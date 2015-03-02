package com.adesis.filesGenerator.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;

import net.sf.jett.transform.ExcelTransformer;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.adesis.filesGenerator.model.ExcelGenerationInfo;
import com.adesis.filesGenerator.model.FileGenerationInfo;
import com.adesis.filesGenerator.utils.adapters.LocaltimeAdapter;
import com.adesis.filesGenerator.utils.adapters.MoneyAdapter;
import com.adesis.filesGenerator.utils.excel.CustomTagLibrary;
import com.adesis.filesGenerator.utils.exception.EnumFileException;
import com.adesis.filesGenerator.utils.exception.FileException;
import com.adesis.filesGenerator.utils.pdf.ConfigurationJadeTemplate;
import com.adesis.filesGenerator.utils.pdf.ImagesReplacesElementFactory;
import com.lowagie.text.pdf.PdfWriter;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.exceptions.JadeCompilerException;
import de.neuland.jade4j.template.JadeTemplate;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author Javier Lacalle
 *
 */
@Component
public class UtilsFilesGeneratorImpl implements IUtilsFileGenerator {

	private final static String ENCODE = "UTF-8";
	// TODO Estar�a mejor obteniendo el context path y en el template pasarle simplemente el nombre de la plantilla "futurama-demo"
	private final static String CONTEXT_PATH = "";

	@Autowired
	private ConfigurationJadeTemplate configurationJadeTemplate;

	/**
	 * {@inheritDoc}
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
			throw new FileException(EnumFileException.ERROR_PDF_GENERATE, e.getMessage());
		}
		return pdfBytes;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws FileException
	 */
	@Override
	@SuppressWarnings("unchecked")
	public byte[] createExcelInBytes(final ExcelGenerationInfo fileGenerationInfo) throws FileException {
		byte[] excelBytes = null;

		try {
			InputStream fileIn = null;
			fileIn = new BufferedInputStream(new FileInputStream(fileGenerationInfo.getTemplate()));

			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final ExcelTransformer transformer = new ExcelTransformer();
			final CustomTagLibrary libraryTags = CustomTagLibrary.getCustomTagLibrary();
			transformer.registerTagLibrary("futurama", libraryTags);
			transformer.addCssFile(fileGenerationInfo.getCssPath());
			final Workbook workbook = transformer.transform(fileIn, (Map<String, Object>) fileGenerationInfo.getDataModel());
			workbook.write(baos);
			workbook.close();
			baos.flush();
			baos.close();
			excelBytes = baos.toByteArray();
		} catch (final Exception e) {
			throw new FileException(EnumFileException.ERROR_EXCEL_GENERATE, e.getMessage());
		}
		return excelBytes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] createTXTInBytes(final FileGenerationInfo fileGenerationInfo) throws FileException {

		byte[] txtBytes = null;
		try {
			final Configuration cfg = new Configuration();
			cfg.setDefaultEncoding(ENCODE);
			cfg.setDirectoryForTemplateLoading(new File(FilenameUtils.getFullPath(fileGenerationInfo.getTemplate())));
			final Template template = cfg.getTemplate(FilenameUtils.getName(fileGenerationInfo.getTemplate()));
			final StringWriter sw = new StringWriter();
			template.process(fileGenerationInfo.getDataModel(), sw);
			txtBytes = sw.toString().getBytes(ENCODE);

		} catch (final Exception e) {
			throw new FileException(EnumFileException.ERROR_TXT_GENERATE, e.getMessage());
		}

		return txtBytes;
	}

	/*
	 * M�todo para generar un Sting a partir de la plantilla Jade y los datos del modelo.
	 *
	 * @param pdfGenerationInfo Objeto que contiene toda la informaci�n relacionada con la generaci�n del PDF.
	 *
	 * @return
	 *
	 * @throws FileException
	 */
	@SuppressWarnings("unchecked")
	private String renderJadeToString(final FileGenerationInfo pdfGenerationInfo) throws FileException {
		// Add adapter lib.
		final Map<String, Object> dataModel = (Map<String, Object>) pdfGenerationInfo.getDataModel();
		dataModel.put("utilsTime", new LocaltimeAdapter());
		dataModel.put("utilsMoney", new MoneyAdapter());
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

package com.adesis.filesGenerator.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.adesis.filesGenerator.model.ExcelGenerationInfo;
import com.adesis.filesGenerator.model.FileGenerationInfo;
import com.adesis.filesGenerator.model.dummy.Deliveryman;
import com.adesis.filesGenerator.model.dummy.Money;
import com.adesis.filesGenerator.model.dummy.Planet;
import com.adesis.filesGenerator.model.dummy.User;
import com.adesis.filesGenerator.utils.IUtilsFileGenerator;

/**
 * @author Javier Lacalle
 * 
 */
@Controller
public class FilesController {

	@Autowired
	private IUtilsFileGenerator utilsFileGenerator;

	/**
	 * Método que devuelve un PDF a partir de una plantilla creada en JADE
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pdf", method = RequestMethod.GET)
	public ResponseEntity<byte[]> generatePDF(final HttpServletRequest request) {
		// Final response.
		ResponseEntity<byte[]> response;

		// Get template
		final ClassLoader classLoader = getClass().getClassLoader();
		final URL templateUrl = classLoader.getResource("templates/pdf/futurama-demo.jade");

		final FileGenerationInfo pdfGenerationInfo = generateInfoPDF(templateUrl);

		// PDF header of response
		final String filename = pdfGenerationInfo.getNameFile() + ".pdf";
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		try {
			// Generate PDF
			final byte[] contents = utilsFileGenerator.createPDFInBytes(pdfGenerationInfo);

			// Include PDF on Response
			response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
		} catch (final Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<byte[]>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;

	}

	@RequestMapping(value = "/excel", method = RequestMethod.GET)
	public ResponseEntity<byte[]> generateExcel(final HttpServletRequest request) {

		ResponseEntity<byte[]> response;

		final ClassLoader classLoader = getClass().getClassLoader();
		final URL templateUrl = classLoader.getResource("templates/excel/plantilla.xlsx");
		final URL cssUrl = classLoader.getResource("templates/excel/css/print.css");
		final ExcelGenerationInfo excelGenerationInfo = generateInfoExcel(templateUrl, cssUrl);

		try {

			final byte[] contents = utilsFileGenerator.createExcelInBytes(excelGenerationInfo);

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));

			final String filename = "output.xlsx";
			headers.setContentDispositionFormData(filename, filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

			response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
		} catch (final Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<byte[]>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;

	}

	private ExcelGenerationInfo generateInfoExcel(final URL templateUrl, final URL cssUrl) {
		final ExcelGenerationInfo excelGenerationInfo = new ExcelGenerationInfo();
		excelGenerationInfo.setTemplate(templateUrl.getPath());
		excelGenerationInfo.setCssPath(cssUrl.getPath());
		excelGenerationInfo.setDataModel(this.generateModelDummy());
		return excelGenerationInfo;
	}

	@RequestMapping(value = "/txt", method = RequestMethod.GET)
	public ResponseEntity<byte[]> generateTXT(final HttpServletRequest request) {

		ResponseEntity<byte[]> response;

		try {
			final byte[] contents = utilsFileGenerator.createTXTInBytes(this.generateModelDummy());

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("text/plain;charset=UTF-8"));
			
			final String filename = "output.txt";
			headers.setContentDispositionFormData(filename, filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

			response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
		} catch (final Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<byte[]>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;

	}

	/************************** Dummy Methods **************************/

	private FileGenerationInfo generateInfoPDF(final URL templateUrl) {
		final FileGenerationInfo pdfGenerationInfo = new FileGenerationInfo();
		pdfGenerationInfo.setTemplate(templateUrl.getPath());
		// pdfGenerationInfo.setTemplate("futurama-demo");
		pdfGenerationInfo.setDataModel(this.generateModelDummy());
		pdfGenerationInfo.setNameFile("futurama");
		return pdfGenerationInfo;
	}

	private Map<String, Object> generateModelDummy() {
		final Map<String, Object> model = new HashMap<String, Object>();
		model.put("deliverymen", this.generateListDeliverymen());
		model.put("user", this.generateUser());
		model.put("subject", "Delivery notes");
		model.put("message", "Impresión de datos en texto plano.");
		return model;
	}

	private User generateUser() {
		return new User("Javier Lacalle", "Adesis");
	}

	private List<Deliveryman> generateListDeliverymen() {
		final List<Deliveryman> list = new ArrayList<Deliveryman>();
		final Deliveryman u1 = new Deliveryman(1, "Javier", "Lacalle", 26, new LocalTime(12, 56, 0), new Planet("Tierra"),
				Money.createInEuros(BigDecimal.valueOf(1000)));
		final Deliveryman u2 = new Deliveryman(2, "Fry", "", 24, new LocalTime(16, 56, 0), new Planet("Tierra"),
				Money.createInEuros(BigDecimal.valueOf(200)));
		final Deliveryman u3 = new Deliveryman(3, "Leela", "", 27, new LocalTime(17, 56, 0), new Planet("Tierra(mutante)"),
				Money.createInEuros(BigDecimal.valueOf(1200)));
		final Deliveryman u4 = new Deliveryman(4, "Amy Wong", "", 26, new LocalTime(19, 56, 0), new Planet("Marte"),
				Money.createInEuros(BigDecimal.valueOf(5200)));
		final Deliveryman u5 = new Deliveryman(5, "Zoiberg", "", 32, new LocalTime(20, 56, 0), new Planet("Decapod 10"),
				Money.createInEuros(BigDecimal.ZERO));

		list.add(u1);
		list.add(u2);
		list.add(u3);
		list.add(u4);
		list.add(u5);
		return list;
	}
}

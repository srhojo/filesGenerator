package com.adesis.filesGenerator.controller;

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

import com.adesis.filesGenerator.model.dummy.Deliveryman;
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

	@RequestMapping(value = "/pdf", method = RequestMethod.GET)
	public ResponseEntity<byte[]> generatePDF(final HttpServletRequest request) {

		ResponseEntity<byte[]> response;

		final ClassLoader classLoader = getClass().getClassLoader();
		final URL templateUrl = classLoader.getResource("templates/pdf/template.jade");
		final URL cssUrl = classLoader.getResource("templates/pdf/css/print.css");

		try {

			final byte[] contents = utilsFileGenerator.createPDFInBytes(templateUrl.getPath(), cssUrl.getPath(), this.generateModelDummy());

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));

			final String filename = "output.pdf";
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

	private Map<String, Object> generateModelDummy() {
		final Map<String, Object> model = new HashMap<String, Object>();
		model.put("deliverymen", this.generateListDeliverymen());
		model.put("user", this.generateUser());
		model.put("subject", "Delivery notes");
		return model;
	}

	private User generateUser() {
		return new User("Javier Lacalle", "Adesis");
	}

	private List<Deliveryman> generateListDeliverymen() {
		final List<Deliveryman> list = new ArrayList<Deliveryman>();
		final Deliveryman u1 = new Deliveryman(1, "Javier", "Lacalle", 26, new LocalTime(12, 56, 0), new Planet("Tierra"));
		final Deliveryman u2 = new Deliveryman(2, "Fry", "", 24, new LocalTime(16, 56, 0), new Planet("Tierra"));
		final Deliveryman u3 = new Deliveryman(3, "Leela", "", 27, new LocalTime(17, 56, 0), new Planet("Tierra(mutante)"));
		final Deliveryman u4 = new Deliveryman(4, "Amy Wong", "", 26, new LocalTime(19, 56, 0), new Planet("Marte"));
		final Deliveryman u5 = new Deliveryman(5, "Zoiberg", "", 32, new LocalTime(20, 56, 0), new Planet("Decapod 10"));

		list.add(u1);
		list.add(u2);
		list.add(u3);
		list.add(u4);
		list.add(u5);
		return list;
	}
}

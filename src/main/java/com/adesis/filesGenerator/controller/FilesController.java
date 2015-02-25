package com.adesis.filesGenerator.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.adesis.filesGenerator.model.dummy.Maquetador;
import com.adesis.filesGenerator.model.dummy.Usuarios;
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

			final byte[] contents = utilsFileGenerator.createPDFInBytes(cssUrl.getPath(), templateUrl.getPath(), this.generateModelDummy());

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
		model.put("usuarios", this.generateList());
		model.put("maquetador", this.generateDummyDesigner());
		return model;
	}

	private Maquetador generateDummyDesigner() {
		return new Maquetador("Javier Lacalle", "Adesis");
	}

	private List<Usuarios> generateList() {
		final List<Usuarios> lista = new ArrayList<Usuarios>();
		final Usuarios u1 = new Usuarios("Javier", "Lacalle", 26);
		final Usuarios u2 = new Usuarios("Diego", "Asensio", 25);
		final Usuarios u3 = new Usuarios("Manuel", "Bayona", null);

		lista.add(u1);
		lista.add(u2);
		lista.add(u3);
		return lista;
	}

}

package com.adesis.filesGenerator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Javier Lacalle
 *
 */
@RestController
@RequestMapping("/files")
public class FilesController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String HelloWorld() {
		return "hello!";
	}

}

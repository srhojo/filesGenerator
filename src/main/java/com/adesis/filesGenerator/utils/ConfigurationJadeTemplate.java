package com.adesis.filesGenerator.utils;

import java.io.IOException;

import org.springframework.stereotype.Component;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.exceptions.JadeException;
import de.neuland.jade4j.template.FileTemplateLoader;
import de.neuland.jade4j.template.JadeTemplate;

/**
 * @author Javier Lacalle
 *
 */
@Component
public class ConfigurationJadeTemplate {

	private String contextPath;
	private String encode;
	private String template;

	public void setConfiguration(final String contextPath, final String encode, final String jadeTemplate) {
		this.contextPath = contextPath;
		this.encode = encode;
		this.template = jadeTemplate;
	}

	public JadeTemplate configureJadeTemplate() throws JadeException, IOException {
		final FileTemplateLoader templateLoader = new FileTemplateLoader(this.contextPath, this.encode);
		final JadeConfiguration jadeConfiguration = new JadeConfiguration();
		jadeConfiguration.setTemplateLoader(templateLoader);
		return jadeConfiguration.getTemplate(this.template);
	}

}

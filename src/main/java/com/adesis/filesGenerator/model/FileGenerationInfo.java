package com.adesis.filesGenerator.model;


/**
 * @author Javier Lacalle
 *
 */
public class FileGenerationInfo {
	private String nameFile;
	private String template;
	private Object dataModel;

	/**
	 * @return the nameFile
	 */
	public String getNameFile() {
		return nameFile;
	}

	/**
	 * @param nameFile
	 *            the nameFile to set
	 */
	public void setNameFile(final String nameFile) {
		this.nameFile = nameFile;
	}

	/**
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * @param template
	 *            the template to set
	 */
	public void setTemplate(final String template) {
		this.template = template;
	}

	/**
	 * @return the dataModel
	 */
	public Object getDataModel() {
		return dataModel;
	}

	/**
	 * @param dataModel
	 *            the dataModel to set
	 */
	public void setDataModel(final Object dataModel) {
		this.dataModel = dataModel;
	}

}

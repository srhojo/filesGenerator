package com.adesis.filesGenerator.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.sf.jett.model.Block;
import net.sf.jett.tag.BaseTag;
import net.sf.jett.tag.TagContext;
import net.sf.jett.util.AttributeUtil;
import net.sf.jett.util.SheetUtil;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Sheet;
import org.joda.time.LocalTime;

/**
 * @author Adesis
 * 
 */
public class DateTimeTag extends BaseTag {
	public static final String ATTR_VALUE = "value";
	private static final List<String> REQ_ATTRS = new ArrayList<String>(Arrays.asList(ATTR_VALUE));
	private static final String LOCAL_TIME_FORMAT = "HH:mm";

	private Object value;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return "localTime";
	}

	@Override
	public void validateAttributes() {
		super.validateAttributes();
		final TagContext context = getContext();
		final Map<String, Object> beans = context.getBeans();
		final Map<String, RichTextString> attributes = getAttributes();
		value = AttributeUtil.evaluateObject(context.getCurrentTag(), attributes.get(ATTR_VALUE), beans, ATTR_VALUE, Object.class, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean process() {
		final TagContext context = getContext();
		final Sheet sheet = context.getSheet();
		final Block block = context.getBlock();

		final Cell cell = sheet.getRow(block.getTopRowNum()).getCell(block.getLeftColNum());

		final String finalValue;
		finalValue = ((LocalTime) value).toString(LOCAL_TIME_FORMAT);
		SheetUtil.setCellValue(cell, finalValue);

		return true;
	}

	@Override
	protected List<String> getRequiredAttributes() {
		final List<String> reqAttrs = super.getRequiredAttributes();
		reqAttrs.addAll(REQ_ATTRS);
		return reqAttrs;
	}
}

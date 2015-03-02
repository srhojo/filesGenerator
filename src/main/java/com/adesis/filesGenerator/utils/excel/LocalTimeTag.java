package com.adesis.filesGenerator.utils.excel;

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

/**
 * @author Adesis
 *
 */
public class LocalTimeTag extends BaseTag {
	public static final String ATTR_MINUTES = "minutes";
	public static final String ATTR_HOURS = "hours";

	private static final List<String> REQ_ATTRS = new ArrayList<String>(Arrays.asList(ATTR_HOURS, ATTR_MINUTES));

	private String hours;
	private String minutes;

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
		hours = AttributeUtil.evaluateString(context.getCurrentTag(), attributes.get(ATTR_HOURS), beans, ATTR_HOURS);
		minutes = AttributeUtil.evaluateString(context.getCurrentTag(), attributes.get(ATTR_MINUTES), beans, ATTR_MINUTES);
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
		final String finalValue = hours + ":" + minutes;
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

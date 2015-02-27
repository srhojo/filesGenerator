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

/**
 * @author Adesis
 * 
 */
public class MoneyTag extends BaseTag {
	public static final String ATTR_CURRENCY = "currency";
	public static final String ATTR_AMOUNT = "amount";

	private static final List<String> REQ_ATTRS = new ArrayList<String>(Arrays.asList(ATTR_AMOUNT, ATTR_CURRENCY));

	private double amount;
	private String currency;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return "money";
	}

	@Override
	public void validateAttributes() {
		super.validateAttributes();
		final TagContext context = getContext();
		final Map<String, Object> beans = context.getBeans();
		final Map<String, RichTextString> attributes = getAttributes();

		amount = AttributeUtil.evaluateDouble(context.getCurrentTag(), attributes.get(ATTR_AMOUNT), beans, ATTR_AMOUNT, 0);
		currency = AttributeUtil.evaluateString(context.getCurrentTag(), attributes.get(ATTR_CURRENCY), beans, ATTR_CURRENCY);
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
		final String finalValue = amount + " " + currency;
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

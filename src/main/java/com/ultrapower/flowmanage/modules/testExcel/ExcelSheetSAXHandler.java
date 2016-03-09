package com.ultrapower.flowmanage.modules.testExcel;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 *  创建SAX解析处理器必须继承自
 *  org.xml.sax.helpers.DefaultHandler
 * 实现响应的方法。
 * @Title: 
 * @Description: 实现TODO
 * @Copyright:Copyright (c) 2011
 * @Company:易程科技股份有限公司
 * @Date:2012-6-17
 * @author  longgangbai
 * @version 1.0
 */
public class ExcelSheetSAXHandler extends DefaultHandler {
	private SharedStringsTable sst;
	private String lastContents;
	private boolean nextIsString;
	
	public ExcelSheetSAXHandler(SharedStringsTable sst) {
		this.sst = sst;
	}
	
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		//System.out.println(localName+"uuuuuuuuuuuu");
		attributes.getValue("w");
		// c => cell
		
		System.out.println(localName);
		
		if(name.equals("c")) {
			// Print the cell reference
			System.out.print(attributes.getValue("r") + " - ");
			// Figure out if the value is an index in the SST
			String cellType = attributes.getValue("t");
			if(cellType != null && cellType.equals("s")) {
				nextIsString = true;
			} else {
				nextIsString = false;
			}
		}
		if(localName.equals("sheetData")) {
			// Print the cell reference
			//System.out.println(localName+"uuuuuuuuuuuu");
			System.out.println(attributes.getValue(localName)+"ssssstttttttttttt");
		}
		// Clear contents cache
		lastContents = "";
	}
	
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		// Process the last contents as required.
		// Do now, as characters() may be called more than once
		if(nextIsString) {
			int idx = Integer.parseInt(lastContents);
			lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
        nextIsString = false;
		}

		// v => contents of a cell
		// Output after we've seen the string contents
		if(name.equals("v")) {
			System.out.println(lastContents+"aaa");
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		lastContents += new String(ch, start, length);
	}
}

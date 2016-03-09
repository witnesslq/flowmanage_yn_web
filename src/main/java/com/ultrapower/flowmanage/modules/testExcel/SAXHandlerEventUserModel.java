package com.ultrapower.flowmanage.modules.testExcel;

import java.io.InputStream;
import java.util.Iterator;
import org.apache.poi.openxml4j.opc.Package;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
/**
 * 采用XSSF and SAX (Event API) 方式解析Excel2007
 *  
 *    excel2007是一个标准xml所以可以采用sax解析的模式。
 *    优点：1.效率比较高。
 *    缺点： 2.只能支持excel2007，针对97~2003格式的excel非标准的xml不支持
 * 
 * @Title: 
 * @Description: 实现TODO
 * @Copyright:Copyright (c) 2011
 * @Company:易程科技股份有限公司
 * @Date:2012-6-17
 * @author  longgangbai
 * @version 1.0
 */
public class SAXHandlerEventUserModel {
	/**
	 * 处理excel中只有一个sheet的方法
	 * @param filename
	 * @throws Exception
	 */
	public void processOneSheet(String filename) throws Exception {
		@SuppressWarnings("deprecation")
		Package pkg = Package.open(filename);
		//创建excel2007的阅读器对象
		XSSFReader r = new XSSFReader( pkg );
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);

		// rId2 found by processing the Workbook
		// Seems to either be rId# or rSheet#
		InputStream sheet2 = r.getSheet("rId2");
		InputSource sheetSource = new InputSource(sheet2);
		parser.parse(sheetSource);
		sheet2.close();
	}

	/**
	 * 处理excel中有多个sheet的格式的 
	 * @param filename
	 * @throws Exception
	 */
	public void processAllSheets(String filename) throws Exception {
		Package pkg = Package.open(filename);
		XSSFReader r = new XSSFReader( pkg );
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);
		//获取多个sheet的输入流对象
		Iterator<InputStream> sheets = r.getSheetsData();
		while(sheets.hasNext()) {
			System.out.println("Processing new sheet:\n");
			InputStream sheet = sheets.next();
			InputSource sheetSource = new InputSource(sheet);
			parser.parse(sheetSource);
			sheet.close();
			System.out.println("");
		}
	}

	/**
	 * 设置xml阅读器的解析器对象
	 * @param sst
	 * @return
	 * @throws SAXException
	 */
	public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
		//通过放射方式获取xml阅读器对象
		XMLReader parser =
			XMLReaderFactory.createXMLReader(
					"org.apache.xerces.parsers.SAXParser"
			);
	     //创建相关的xml解析器对象
		ContentHandler handler = new ExcelSheetSAXHandler(sst);
		//设置解析器对象
		parser.setContentHandler(handler);
		return parser;
	}
}

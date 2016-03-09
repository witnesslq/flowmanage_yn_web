package com.ultrapower.flowmanage.common.utils.excelRead;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class Excel2007Reader extends DefaultHandler{
	// 共享字符串表
	private SharedStringsTable sst;
	// 上一次的内容
	private String lastContents;
	private boolean nextIsString;

	private int sheetIndex = -1;
	private List<String> rowlist = new ArrayList<String>();
	// 当前行
	private int curRow = 0;
	// 当前列
	private int curCol = -1;
	// 日期标志
	private boolean dateFlag;
	// 数字标志
	private boolean numberFlag;

	private boolean isTElement;

	private IRowReader rowReader;
	
	/**
	 * 单元格数据类型，默认为字符串类型
	 */
	private CellDataType nextDataType = CellDataType.SSTINDEX;

	private final DataFormatter formatter = new DataFormatter();

	private short formatIndex;

	private String formatString;

	/**
	 * 单元格
	 */
	private StylesTable stylesTable;

	
	/**
	 * 单元格中的数据可能的数据类型
	 */
	enum CellDataType
	{
		BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER, DATE, NULL
	}

	
	public void setRowReader(IRowReader rowReader) {
		this.rowReader = rowReader;
	}

	/**
	 * 只遍历一个电子表格，其中sheetId为要遍历的sheet索引，从1开始，1-3
	 * 
	 * @param filename
	 * @param sheetId
	 * @throws Exception
	 */
	public void processOneSheet(byte[] bt, int sheetId) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(bt);
		OPCPackage pkg = OPCPackage.open(bis);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);
		// 根据 rId# 或 rSheet# 查找sheet
		InputStream sheet2 = r.getSheet("rId" + sheetId);
		sheetIndex++;
		InputSource sheetSource = new InputSource(sheet2);
		parser.parse(sheetSource);
		sheet2.close();
	}

	/**
	 * 遍历工作簿中所有的电子表格
	 * 
	 * @param filename
	 * @throws Exception
	 */
	public void process(byte[] bt) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(bt);
		OPCPackage pkg = OPCPackage.open(bis);
		XSSFReader r = new XSSFReader(pkg);
		stylesTable = r.getStylesTable();
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);
		Iterator<InputStream> sheets = r.getSheetsData();
		while (sheets.hasNext()) {
			curRow = 0;
			sheetIndex++;
			InputStream sheet = sheets.next();
			InputSource sheetSource = new InputSource(sheet);
			
			parser.parse(sheetSource);
			sheet.close();
		}
	}

	public XMLReader fetchSheetParser(SharedStringsTable sst)
			throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		this.sst = sst;
		parser.setContentHandler(this);
		return parser;
	} 

	/**
	 * 处理数据类型
	 * 
	 * @param attributes
	 */
	public void setNextDataType(Attributes attributes) {
		nextDataType = CellDataType.NUMBER;
		formatIndex = -1;
		formatString = null;
		String cellType = attributes.getValue("t");
		String cellStyleStr = attributes.getValue("s");

		if ("b".equals(cellType)) {
			nextDataType = CellDataType.BOOL;
		} else if ("e".equals(cellType)) {
			nextDataType = CellDataType.ERROR;
		} else if ("inlineStr".equals(cellType)) {
			nextDataType = CellDataType.INLINESTR;
		} else if ("s".equals(cellType)) {
			nextDataType = CellDataType.SSTINDEX;
		} else if ("str".equals(cellType)) {
			nextDataType = CellDataType.FORMULA;
		}else if(cellType == null){//如果单元格内容为空
			if(curCol != 0){
				rowlist.add(curCol, null);
				curCol++;
//				nextDataType = CellDataType.NULL;
			}
		}

		
		
		if (cellStyleStr != null) {
			int styleIndex = Integer.parseInt(cellStyleStr);
			XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
			formatIndex = style.getDataFormat();
			formatString = style.getDataFormatString();
			if ("m/d/yy" == formatString) {
				nextDataType = CellDataType.DATE;
				formatString = "yyyy-MM-dd hh:mm:ss.SSS";
			}

			if (formatString == null) {
				nextDataType = CellDataType.NULL;
				formatString = BuiltinFormats.getBuiltinFormat(formatIndex);
			}
			
			
		}
	}

	/**
	 * 对解析出来的数据进行类型处理
	 * 
	 * @param value
	 *            单元格的值（这时候是一串数字）
	 * @param thisStr
	 *            一个空字符串
	 * @return
	 */
	public String getDataValue(String value, String thisStr) {
		switch (nextDataType) {
		// 这几个的顺序不能随便交换，交换了很可能会导致数据错误
		case BOOL:
			char first = value.charAt(0);
			thisStr = first == '0' ? "FALSE" : "TRUE";
			break;
		case ERROR:
			thisStr = "\"ERROR:" + value.toString() + '"';
			break;
		case FORMULA:
			thisStr = '"' + value.toString() + '"';
			break;
		case INLINESTR:
			XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());

			thisStr = rtsi.toString();
			rtsi = null;
			break;
		case SSTINDEX:
			String sstIndex = value.toString();
			try {
				int idx = Integer.parseInt(sstIndex);
				XSSFRichTextString rtss = new XSSFRichTextString(
						sst.getEntryAt(idx));
				thisStr = rtss.toString();
				rtss = null;
			} catch (NumberFormatException ex) {
				thisStr = value.toString();
			}
			break;
		case NUMBER:
			if (formatString != null) {
				thisStr = formatter.formatRawCellContents(
						Double.parseDouble(value), formatIndex, formatString)
						.trim();
			} else {
				thisStr = value;
			}

			thisStr = thisStr.replace("_", "").trim();
			break;
		case DATE:
			thisStr = formatter.formatRawCellContents(
					Double.parseDouble(value), formatIndex, formatString);

			// 对日期字符串作特殊处理
			thisStr = thisStr.replace(" ", "T");
			break;
		default:
			thisStr = "";
			break;
		}

		return thisStr;
	}
	
	public void startElement(String uri, String localName, String name,Attributes attributes) throws SAXException {

		// c => 单元格
 		if ("c".equals(name)) {
			// 设定单元格类型
//			this.setNextDataType(attributes);

			// 如果下一个元素是 SST 的索引，则将nextIsString标记为true
			String cellType = attributes.getValue("t");
			curCol++;
			rowlist.add(curCol, null);
			if ("s".equals(cellType)) {
				nextIsString = true;
			} else {
				nextIsString = false;
			}
			
			/*// 日期格式
			String cellDateType = attributes.getValue("s");
			if ("1".equals(cellDateType)) {
				dateFlag = true;
			} else {
				dateFlag = false;
			}
			String cellNumberType = attributes.getValue("s");
			if ("2".equals(cellNumberType)) {
				numberFlag = true;
			} else {
				numberFlag = false;
			}*/

		}
		// 当元素为t时
		if ("t".equals(name)) {
			isTElement = true;
		} else {
			isTElement = false;
		}
		
		// 置空
		lastContents = "";
	}

	public void endElement(String uri, String localName, String name)throws SAXException {

		// 根据SST的索引值的到单元格的真正要存储的字符串
		// 这时characters()方法可能会被调用多次
		if (nextIsString) {
			try {
					
				int idx = Integer.parseInt(lastContents);
				lastContents = new XSSFRichTextString(sst.getEntryAt(idx))
				.toString();
				
			} catch (Exception e) {

			}
		}
		// t元素也包含字符串
		if (isTElement) {
			String value = lastContents.trim();
			rowlist.add(curCol, value);
			//curCol++;
			isTElement = false;
			// v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
			// 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
		} else if ("v".equals(name)) {
			// v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
//			String value = this.getDataValue(lastContents.trim(), "");
			
			String value = lastContents.trim();
			value = value.equals("") ? " " : value;

			/*// 日期格式处理
			if (dateFlag) {
				Date date = HSSFDateUtil.getJavaDate(Double.valueOf(value));
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				value = dateFormat.format(date);
			}
			// 数字类型处理
			if (numberFlag) {
				BigDecimal bd = new BigDecimal(value);
				value = bd.setScale(3, BigDecimal.ROUND_UP).toString();
			}*/
			rowlist.set(curCol, value);
			//curCol++;
		} else {
			// 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
			if (name.equals("row")) {
				rowReader.getRows(sheetIndex, curRow, rowlist);
				rowlist.clear();
				curRow++;
				curCol = -1;
			}
		}

	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// 得到单元格内容的值
		lastContents += new String(ch, start, length);
	}

}

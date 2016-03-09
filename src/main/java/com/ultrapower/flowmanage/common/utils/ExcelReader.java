/**
 * 
 */
package com.ultrapower.flowmanage.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author acer
 *
 */
public class ExcelReader {
	private Workbook wb = null;
	
//	private Workbook wb = null;

	private Sheet sheet = null;

	private Row row = null;

	private int sheetNum = 0; // 第sheetnum个工作表

	private int rowNum = 0;
	
	private InputStream is = null;

	private FileInputStream fis = null;

	private File file = null;
	
	private String type;
	
	public ExcelReader() {
	}

	public ExcelReader(File file) {
		this.file = file;
		this.type = "2";
	}
	
	public ExcelReader(InputStream is, String type)
	{
		this.is = is;
		this.type = type;
	}

	/**
	 * 读取excel文件获得HSSFWorkbook对象
	 */
	public void open() throws IOException {
		if(fis != null)
		{
			fis = new FileInputStream(file);
			if("1".equals(type))
			{
				wb = new XSSFWorkbook(fis);
			}else if("2".equals(type))
			{
				wb = new HSSFWorkbook(new POIFSFileSystem(fis));
			}
//			wb = WorkbookFactory.create(new POIFSFileSystem(fis));
			fis.close();
		}else if(is != null)
		{
			if("1".equals(type))
			{
				wb = new XSSFWorkbook(is);
			}else if("2".equals(type))
			{
				wb = new HSSFWorkbook(new POIFSFileSystem(is));
			}
			is.close();
		}
	}
	
	/**
	 * 返回指定sheet名称
	 * @param sheetNum sheet序号
	 * @return
	 */
	public String getSheetName(int sheetNum)
	{
		return wb.getSheetName(sheetNum);
	}
	
	/**
	 * 返回sheet表数目
	 * 
	 * @return int
	 */
	public int getSheetCount() {
		int sheetCount = -1;
		sheetCount = wb.getNumberOfSheets();
		return sheetCount;
	}

	/**
	 * 得到当前sheetNum下有效的总行数
	 * 
	 * @return int
	 */
	public int getRowCount() {
		if (wb == null)
			System.out.println("=============>WorkBook为空");
		Sheet sheet = wb.getSheetAt(this.sheetNum);
		int rowCount = -1;
		rowCount = sheet.getLastRowNum();
		int result = 0;
		for (int i = 1; i <= rowCount; i++) {  
			if (!readStringExcelCell(i, 1).equals(""))
				result++;
		}
		return result;
	}

	public int getExcelRowCount() {
		if (wb == null)
			System.out.println("=============>WorkBook为空");
		Sheet sheet = wb.getSheetAt(this.sheetNum);
		int rowCount = -1;
		rowCount = sheet.getLastRowNum();
				int result = 0;
		int colCount=getColCount();
		for (int i = 1; i <= rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				if (!readStringExcelCell(i, j).equals("")){
					result++;
					break;
				}
			}
		}
		return ++result;
	}
	
	/**
	 * 获得实际行数（getRowCount()算法有问题）
	 * @return
	 */
	public int getTrueRowCount() {
		if (wb == null)
			System.out.println("=============>WorkBook为空");
		Sheet sheet = wb.getSheetAt(this.sheetNum);
		int rowCount = -1;
		rowCount = sheet.getLastRowNum();
		int result = 0;
		for (int i = 0; i <= rowCount; i++) { 
			row = sheet.getRow(i);
			if(null!=row){
				int flag = 0;
				int colCount = row.getLastCellNum();
				for(int j=0;j<=colCount;j++){
					if (!readStringExcelCell(i, j).equals(""))
						flag++;
				}
				if(flag>0)
					result++;
			}
		}
		return result;
	}

	/**
	 * 得到当前sheetNum下第一行有效的总列数
	 */
	public int getColCount() {
		if (wb == null)
			System.out.println("=============>WorkBook为空");
		Sheet sheet = wb.getSheetAt(this.sheetNum);
		int result = 0;
		int colCount = -1;
		row = sheet.getRow(0);
		colCount = row.getLastCellNum();
		
		for (int i = 0; i <= colCount; i++) {
			if (!readStringExcelCell(0, i).equals(""))
				result++;
		}
		return result;
	}

	/**
	 * 得到当前sheetNum下第一行有效的总列数
	 */
	public int getExcelColCount() {
		if (wb == null)
			System.out.println("=============>WorkBook为空");
		Sheet sheet = wb.getSheetAt(this.sheetNum);
		int colCount = -1;
		row = sheet.getRow(0);
		colCount = row.getLastCellNum();
		
		return colCount;
	}

	/**
	 * 指定行和列编号的内容
	 * 
	 * @param rowNum
	 * @param cellNum
	 * @return String
	 */
	public String readStringExcelCell(int rowNum, int cellNum) {
		return readStringExcelCell(this.sheetNum, rowNum, cellNum);
	}
	

	/**
	 * 指定工作表、行、列下的内容
	 * 
	 * @param sheetNum
	 * @param rowNum
	 * @param cellNum
	 * @return String
	 */
	public String readStringExcelCell(int sheetNum, int rowNum, int cellNum) {
		if (sheetNum < 0 || rowNum < 0)
			return "";
		String strExcelCell = "";
		try {
			sheet = wb.getSheetAt(sheetNum);
			row = sheet.getRow(rowNum);
			if (row.getCell((short) cellNum) != null) { // add this condition
				// judge
				switch (row.getCell((short) cellNum).getCellType()) {
				case Cell.CELL_TYPE_FORMULA:
					strExcelCell = "FORMULA ";
					break;
				case Cell.CELL_TYPE_NUMERIC: {
					double dTheValue = row.getCell((short) cellNum).getNumericCellValue();
					if ( isInteger( dTheValue ) ) {
						strExcelCell = ""+ (int) dTheValue;
					} else {
						strExcelCell = ""+ dTheValue;
					}
					break;
				}
				case Cell.CELL_TYPE_STRING:
					strExcelCell = row.getCell((short) cellNum).getStringCellValue().trim();
					break;
				case Cell.CELL_TYPE_BLANK:
					strExcelCell = "";
					break;
				default:
					strExcelCell = "";
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strExcelCell;
	}

	private static boolean isInteger(double value) {
		int iValue = (int) value;
		return value == iValue;
	}

	public Workbook getWb() {
		return wb;
	}

	public void setWb(Workbook wb) {
		this.wb = wb;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public Row getRow() {
		return row;
	}

	public void setRow(Row row) {
		this.row = row;
	}

	public int getSheetNum() {
		return sheetNum;
	}

	public void setSheetNum(int sheetNum) {
		this.sheetNum = sheetNum;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public FileInputStream getFis() {
		return fis;
	}

	public void setFis(FileInputStream fis) {
		this.fis = fis;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}

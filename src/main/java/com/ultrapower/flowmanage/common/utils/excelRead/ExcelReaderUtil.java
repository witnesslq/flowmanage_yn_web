package com.ultrapower.flowmanage.common.utils.excelRead;

public class ExcelReaderUtil {
	// excel2003扩展名
	public static final String EXCEL03_EXTENSION = ".xls";
	// excel2007扩展名
	public static final String EXCEL07_EXTENSION = ".xlsx";

	/**
	 * 读取Excel文件，可能是03也可能是07版本
	 * 
	 * @param excel03
	 * @param excel07
	 * @param fileName
	 * @throws Exception
	 */
	public static void readExcel(IRowReader reader, byte[] bt, String mode)
			throws Exception {
		// 处理excel2003文件
		if ("2".equals(mode)) {
			Excel2003Reader excel03 = new Excel2003Reader();
			excel03.setRowReader(reader);
			excel03.process(bt);
			// 处理excel2007文件
		} else if ("1".equals(mode)) {
			Excel2007Reader excel07 = new Excel2007Reader();
			excel07.setRowReader(reader);
			excel07.process(bt);
		} else {
			throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
		}
	}
}

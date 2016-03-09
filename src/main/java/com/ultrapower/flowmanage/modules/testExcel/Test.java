package com.ultrapower.flowmanage.modules.testExcel;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class Test {
	
	public static void main(String[] args) throws Exception{
		FileInputStream file = new FileInputStream("G:/testexcel/ttt.xlsx");
		POIFSFileSystem poifs = new POIFSFileSystem(file);
		InputStream din = poifs.createDocumentInputStream("Workbook");
		HSSFRequest request = new HSSFRequest();
		request.addListenerForAllRecords(new HxlsAbstract());
		HSSFEventFactory factory = new HSSFEventFactory();
		factory.processEvents(request, din);
		file.close();
		din.close();
		
	}

}

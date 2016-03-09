package com.ultrapower.flowmanage.common.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ultrapower.flowmanage.modules.internetResources.service.impl.InternetResourcesServiceImpl;

public class ThreadPoolTask implements Runnable{
	/*
	private XSSFSheet sheet;
	
	private XSSFRow row;
	
	private XSSFCell cell;
	
	private String[] mapKeyName;
	
	private XSSFCellStyle cellStyle;
	
	private int k;
	*/
	//private List<Map<String, String>> list;
	
	private int taskSize;
	
	public ThreadPoolTask(){};
	

	public ThreadPoolTask(int taskSize) {
		super();
		this.taskSize = taskSize;
	}
	

	@Override
	public void run(){
		LinkedBlockingQueue<Map<String, Object>> linkedBlockingQueue = InternetResourcesServiceImpl.getLinkedBlockingQueue();
		Map<String, Object> maps = linkedBlockingQueue.poll();
		List<Map<String, String>> list = (List<Map<String, String>>)maps.get("list");
		XSSFSheet sheet = (XSSFSheet)maps.get("sheet");
	    XSSFRow row = (XSSFRow)maps.get("row");
	    XSSFCell cell = (XSSFCell)maps.get("cell");
	    String[] mapKeyName = (String[])maps.get("mapKeyName");
	    XSSFWorkbook wb = (XSSFWorkbook)maps.get("wb");
	    XSSFCellStyle cellStyle = (XSSFCellStyle)maps.get("cellStyle");
	    FileOutputStream writeFile = (FileOutputStream)maps.get("writeFile");
		System.out.println(""+list.size());
			int listSize = (taskSize + 1) * 5000 > list.size() ? list.size() : (taskSize + 1) * 5000;
			for (int i = taskSize * 5000; i < listSize; i++) {
				Map<String, String> map = (Map<String, String>)list.get(i);
				row = sheet.createRow(i + 1);
				for (int j = 0; j < mapKeyName.length; j++) {
					cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
					cell.setCellValue(new XSSFRichTextString(value));
				}
			}
			try {
				wb.write(writeFile);
				writeFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}

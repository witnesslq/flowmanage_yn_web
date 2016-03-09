package com.ultrapower.flowmanage.modules.wlanLogAnalyse.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.common.utils.DateUtil;
import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.modules.wlanLogAnalyse.dao.WlanLogAnalyseDao;
import com.ultrapower.flowmanage.modules.wlanLogAnalyse.service.WlanLogAnalyseService;
import com.ultrapower.flowmanage.modules.wlanLogAnalyse.vo.WlanLogParameter;

@Service("wlanLogAnalyseService")
public class WlanLogAnalyseServiceImpl implements WlanLogAnalyseService {
	private static Logger logger = Logger.getLogger(WlanLogAnalyseServiceImpl.class);
	@Resource(name="wlanLogAnalyseDao")
	private WlanLogAnalyseDao wlanLogAnalyseDao;

	//根据时间、用户名称、上线认证、下线认证、认证成功、认证失败分页查询portal日志列表
	public Page findPortalLogTable(WlanLogParameter parameter) throws Exception {
		Page page = new Page();
		String[] action = parameter.getAction().length == 0 ? null : parameter.getAction();
		parameter.setAction(action);
		String[] result = parameter.getResult().length == 0 ? null : parameter.getResult();
		parameter.setResult(result);
		
		int rowCount = wlanLogAnalyseDao.findPortalLogRowCount(parameter);
		List<Map<String, String>> list = wlanLogAnalyseDao.findPortalLogTable(parameter);
		page.setPageNo(parameter.getPageNo());// 当前页
		page.setPageSize(parameter.getPageSize());// 每页的记录数
		page.setRowCount(rowCount);// 总行数
		page.setResultList(list);// 每页的记录
		return page;
	}

	//根据时间、用户名称、执行成功、执行失败分页查询MML日志列表
	public Page findMmlLogTable(WlanLogParameter parameter) throws Exception{
		Page page = new Page();
		String[] result = parameter.getResult().length == 0 ? null : parameter.getResult();
		parameter.setResult(result);
		int rowCount = wlanLogAnalyseDao.findMmlLogRowCount(parameter);
		List<Map<String, String>> list = wlanLogAnalyseDao.findMmlLogTable(parameter);
		page.setPageNo(parameter.getPageNo());// 当前页
		page.setPageSize(parameter.getPageSize());// 每页的记录数
		page.setRowCount(rowCount);// 总行数
		page.setResultList(list);// 每页的记录
		return page;
	}

	//根据时间、用户名称、上线认证、下线认证、认证成功、认证失败查询portal日志导出Excel表格
	public byte[] exportPortalLogExcelTable(WlanLogParameter parameter) throws Exception {
		String[] action = parameter.getAction().length == 0 ? null : parameter.getAction();
		parameter.setAction(action);
		String[] result = parameter.getResult().length == 0 ? null : parameter.getResult();
		parameter.setResult(result);
		
		ByteArrayOutputStream fos=new ByteArrayOutputStream();
//		FileOutputStream writeFile = new FileOutputStream("D:/桌面/湖北移动WLAN日志分析_PORTAL日志分析列表.xls");
		
		WritableWorkbook book = Workbook.createWorkbook(fos);
		WritableSheet sheet = book.createSheet("湖北移动WLAN日志分析_PORTAL日志分析(1)", 0);//创建工作薄
		String[] titles = new String[]{"日志时间","来源服务器","用户名","AC IP","AC厂家","动作","结果","失败原因","域名","PORTAL编号"};//表头名称
		String[] mapKeyName = {"collecttime","souceServers","userName","userIp","acIp","acOperator","action","result","failureReason","domainName","portalNumber"};//Map的Key值
		WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.DARK_BLUE);
		WritableCellFormat titleCellFormat = new WritableCellFormat(titleFont);
		titleCellFormat.setAlignment(Alignment.CENTRE);
		Label label = null;
		for (int i = 0; i < titles.length; i++) {
		 sheet.setColumnView(i, 30);
	     label = new Label(i, 0, titles[i], titleCellFormat);
	     sheet.addCell(label);
	    }
		
		//PORTAL日志列表总行数
		int portalLogRowCount = wlanLogAnalyseDao.findPortalLogRowCount(parameter);
		if(portalLogRowCount > 0){
			parameter.setPageNo(1);
			parameter.setPageSize(portalLogRowCount);
		}
		//PORTAL日志列表
		List<Map<String, String>> list = wlanLogAnalyseDao.findPortalLogTable(parameter);
		
		//内容
		if(portalLogRowCount > 0){
			 int k=0, n=1;
			for (int i = 0; i < list.size(); ++i) {
				Map<String, String> mp = list.get(i);
				if(50000 * n == i){
					k = 0;
    		    	n++;
    		    	sheet = book.createSheet("湖北移动WLAN日志分析_PORTAL日志分析("+n+")", n);
	    			for (int t = 0; t < titles.length; t++) {
						sheet.setColumnView(t, 30);
						label = new Label(t, 0, titles[t], titleCellFormat);
						sheet.addCell(label);
	    		    }
				}
				
				for (int j = 0; j < mapKeyName.length; j++) {
					String value = mp.get(mapKeyName[j]) == null ? "" : mp.get(mapKeyName[j]);
					label = new Label(j, k+1, value); 
	    			sheet.addCell(label);
				}
				k++;
			}
		}
	    
	    try {
	    	book.write();
	    	book.close();
	    	
			byte[] content =fos.toByteArray();
			return content;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	//根据时间、用户名称、执行成功、执行失败查询MML日志导出Excel表格
	public byte[] exportMmlLogExcelTable(WlanLogParameter parameter) throws Exception {
		String[] result = parameter.getResult().length == 0 ? null : parameter.getResult();
		parameter.setResult(result);
		
		ByteArrayOutputStream fos=new ByteArrayOutputStream();
//		FileOutputStream writeFile = new FileOutputStream("D:/桌面/湖北移动WLAN日志分析_MML日志分析列表.xls");
		
		WritableWorkbook book = Workbook.createWorkbook(fos);
		WritableSheet sheet = book.createSheet("湖北移动WLAN日志分析_MML日志分析(1)", 0);//创建工作薄
		String[] titles = new String[]{"日志时间","来源服务器","用户名","指令内容","执行结果","失败原因","域名"};//表头名称
		String[] mapKeyName = {"collecttime","souceServers","userName","commandContent","result","failureReason","domainName"};//Map的Key值
		WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.DARK_BLUE);
		WritableCellFormat titleCellFormat = new WritableCellFormat(titleFont);
		titleCellFormat.setAlignment(Alignment.CENTRE);
		Label label = null;
		for (int i = 0; i < titles.length; i++) {
		 sheet.setColumnView(i, 30);
	     label = new Label(i, 0, titles[i], titleCellFormat);
	     sheet.addCell(label);
	    }
		
		//MML日志列表总行数
		int mmlLogRowCount = wlanLogAnalyseDao.findMmlLogRowCount(parameter);
		if(mmlLogRowCount > 0){
			parameter.setPageNo(1);
			parameter.setPageSize(mmlLogRowCount);
		}
		//MML日志列表
		List<Map<String, String>> list = wlanLogAnalyseDao.findMmlLogTable(parameter);

		
		//内容
		if(mmlLogRowCount > 0){
			 int k=0, n=1;
			for (int i = 0; i < list.size(); ++i) {
				Map<String, String> mp = list.get(i);
				if(50000 * n == i){
					k = 0;
    		    	n++;
    		    	sheet = book.createSheet("湖北移动WLAN日志分析_MML日志分析("+n+")", n);
	    			for (int t = 0; t < titles.length; t++) {
						sheet.setColumnView(t, 30);
						label = new Label(t, 0, titles[t], titleCellFormat);
						sheet.addCell(label);
	    		    }
				}
				
				for (int j = 0; j < mapKeyName.length; j++) {
					String value = mp.get(mapKeyName[j]) == null ? "" : mp.get(mapKeyName[j]);
					label = new Label(j, k+1, value); 
	    			sheet.addCell(label);
				}
				k++;
			}
		}
	    
	    try {
	    	book.write();
	    	book.close();
	    	
			byte[] content =fos.toByteArray();
			return content;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}

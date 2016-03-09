package com.ultrapower.flowmanage.modules.internetResAnalyse.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.formula.functions.Today;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import com.ultrapower.flowmanage.common.utils.DateUtil;
import com.ultrapower.flowmanage.common.utils.Utils;
import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.modules.health.vo.CityContrastVo;
import com.ultrapower.flowmanage.modules.health.vo.ThreeNetHealth;
import com.ultrapower.flowmanage.modules.internetResAnalyse.dao.InternetResAnalyseDao;
import com.ultrapower.flowmanage.modules.internetResAnalyse.service.InternetResAnalyseService;

@Service("internetResAnalyseService")
public class InternetResAnalyseServiceImpl implements InternetResAnalyseService {
	private static Logger logger = Logger.getLogger(InternetResAnalyseServiceImpl.class);
	@Resource(name="internetResAnalyseDao")
	private InternetResAnalyseDao internetResAnalyseDao;
	
	public List<Map<String, Object>> findTop10WebName(String webType,
			String sourceType, String getTime) throws Exception {
		Map<String, Object> map1 = new HashMap<String, Object>();
		List<Map<String, Object>> list = internetResAnalyseDao.findTop10WebName(webType, sourceType, getTime);
		if (list.size()>0) {
			map1.put("webName", "全部");
			list.add(0,map1);
		}
		
		return list;
	}

	public List<Map<String, Object>> findSourceAnalyse(String webType, String webName, 
			String sourceType, String getTime)
			throws Exception {
		
		return internetResAnalyseDao.findSourceAnalyse(webType, webName, sourceType, getTime);
	}

	public List<Map<String, String>> findMobileSourceRank(String webType, String webName, 
			String sourceType, String getTime) throws Exception {
		List<Map<String, String>> list = internetResAnalyseDao.findMobileSourceRank(webType, webName, sourceType, getTime);
		List<Map<String, String>> lists = new ArrayList<Map<String,String>>();
		double sourceCount = 0.00;
		double persentCount = 0.00;
		double sourceSize = 0.00;
		double persentSize = 0.00;
		Map<String, String> newMap = new HashMap<String, String>();
		if (list.size()>0&&list.get(0).get("label").equals("陕西")) {
			for (Map<String, String> map : list) {
				Object object = map.get("rowNum");
				Object sourceCountObj = map.get("resourcesNumber");
				Object persentCountObj = map.get("persentCount");
				Object sourceSizeObj = map.get("contentSize");
				Object persentSizeObj = map.get("persentSize");
				if(Integer.valueOf(object.toString())<=5){
					lists.add(map);
				}else if(Integer.valueOf(object.toString())>5) {
					sourceCount = sourceCount+Double.valueOf(sourceCountObj.toString());
					persentCount = persentCount+Double.valueOf(persentCountObj.toString());
					sourceSize = sourceSize+Double.valueOf(sourceSizeObj.toString());
					persentSize = persentSize+Double.valueOf(persentSizeObj.toString());
				}
			}
		}else {
			for (Map<String, String> map : list) {
				Object object = map.get("rowNum");
				Object sourceCountObj = map.get("resourcesNumber");
				Object persentCountObj = map.get("persentCount");
				Object sourceSizeObj = map.get("contentSize");
				Object persentSizeObj = map.get("persentSize");
				if(Integer.valueOf(object.toString())<=6){
					lists.add(map);
				}else if(Integer.valueOf(object.toString())>6) {
					sourceCount = sourceCount+Double.valueOf(sourceCountObj.toString());
					persentCount = persentCount+Double.valueOf(persentCountObj.toString());
					sourceSize = sourceSize+Double.valueOf(sourceSizeObj.toString());
					persentSize = persentSize+Double.valueOf(persentSizeObj.toString());
				}
			}
		}
		
		Map<String, String> map1 = new HashMap<String, String>();
		if (list.size()>6) {
			map1.put("label", "其它");
			map1.put("resourcesNumber", String.valueOf(sourceCount));
			map1.put("persentCount", String.valueOf(persentCount));
			map1.put("contentSize", String.valueOf(sourceSize));
			map1.put("persentSize", String.valueOf(persentSize));
			lists.add(map1);
			
		}
		for (Map<String, String> rank : lists) {
			Object object1 = rank.get("persentCount");
			Object object2 = rank.get("persentSize");
			rank.put("persentCount", String.valueOf(Double.valueOf(object1.toString())*100));
			rank.put("persentSize", String.valueOf(Double.valueOf(object2.toString())*100));
		}
		
		return lists;
	}

	public Page findResDetailTable(int pageNo, int pageSize, String webType,
			String webName, String getTime) throws Exception {
		Page page = new Page();
	    Date nextDay;
	    Date today;
		int rowCount = 0;
		today = DateUtil.StringToDate(DateUtil.getDayOfDate(getTime, -1), "yyyy-MM-dd");
		//获取第二天时间
		nextDay = DateUtil.StringToDate(DateUtil.getDayOfDate(getTime, 0), "yyyy-MM-dd");
		rowCount = internetResAnalyseDao.findResDetailTableCount(webType, webName, today, nextDay);
		List<Map<String, String>> list = internetResAnalyseDao.findResDetailTable(webType, webName, today, nextDay, pageNo, pageSize);
		for (Map<String, String> map : list) {
			String sTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get("sumStartTime"));
			map.put("sumStartTime", sTime);
			if(map.get("firstByteOffSetTime")!=null){
				map.put("firstByteOffSetTime", Utils.valueOf(String.valueOf(map.get("firstByteOffSetTime")))+"ms");
			}
			if (map.get("dealSpeed")!=null) {
				map.put("dealSpeed", Utils.valueOf(String.valueOf(map.get("dealSpeed")))+"KB/s");
			}
			if (map.get("contentSize")!=null) {
				map.put("contentSize", Utils.valueOf(String.valueOf(map.get("contentSize")))+"MB");
			}
			if (map.get("downloadSize")!=null) {
				map.put("downloadSize", Utils.valueOf(String.valueOf(map.get("downloadSize")))+"MB");
			}
			
		}
		page.setPageNo(pageNo);//当前页
		page.setPageSize(pageSize);//每页的记录数
		page.setRowCount(rowCount);//总行数
		page.setResultList(list);//每页的记录
		return page;
	}

	
	//互联网资源分析表格数据Excel导出
		public byte[] qualityExcelTable(String webType,String getTime) throws Exception {
			List<Map<String, Object>> webList = findTop10WebName(webType, "sourceCount", getTime);//地市TOP200网站时延
			Date nextDay;
		    Date today;
			today = DateUtil.StringToDate(DateUtil.getDayOfDate(getTime, -1), "yyyy-MM-dd");
			//获取第二天时间
			nextDay = DateUtil.StringToDate(DateUtil.getDayOfDate(getTime, 0), "yyyy-MM-dd");
			List<Map<String, String>> list = null;

			/************************** Excel导出   *******************************/
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = null;
			//标题样式
			HSSFCellStyle cellStyleTit = wb.createCellStyle();
			cellStyleTit.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
			cellStyleTit.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
			//			cellStyleTit.setWrapText(true);// 指定单元格自动换行
			HSSFFont fontTit = wb.createFont();
			fontTit.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			fontTit.setFontHeightInPoints((short) 12);
			fontTit.setFontName("微软雅黑");
			fontTit.setFontHeight((short) 220);
			cellStyleTit.setFont(fontTit);
			
			//内容单元格样式
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
			//			cellStyle.setWrapText(true);// 指定单元格自动换行
			HSSFFont font = wb.createFont();
			font.setFontHeightInPoints((short) 12);
			font.setFontName("宋体");
			font.setFontHeight((short) 200);
			cellStyle.setFont(font);
			
			if (webType.equals("WEBPAGE")) {
				String[] str = new String[]{"时间","源节点","目的节点","主机IP","归属地","归属运营商","首祯时间",
						"吞吐率","资源大小","资源数量","综合质量"};//表头名称
				String[] mapKeyName = new String[]{"sumStartTime","webName","cityName","webIp","resultProvinceName","resultOperator",
						"firstByteOffSetTime","dealSpeed","contentSize","resourcesNumber","comprehensiveQuality"};//Map的Key值
				for(int i = 0; i < webList.size(); ++i){//遍历业务名称
					Map<String, Object> webName = webList.get(i);
					sheet =  wb.createSheet("网页资源明细_"+webName.get("webName"));
					sheet.setDefaultRowHeightInPoints(12);//设置默认的行高   
					sheet.setDefaultColumnWidth(30);//设置默认的列宽
					HSSFRow rowTit = sheet.createRow(0);//添加表头
					for (int j = 0; j < str.length; ++j) {//遍历表头单元格信息
						String tit = (String)str[j];
						HSSFCell cell = rowTit.createCell(j);
						cell.setCellStyle(cellStyleTit);
						cell.setCellValue(new HSSFRichTextString(tit));
					}
					int rowSize = 0;
					list = internetResAnalyseDao.findResDetailTableAll(webType, String.valueOf(webName.get("webName")), today, nextDay);
						if(list.size() > 0){
							for (int k = 0; k < list.size(); ++k) {
								Map<String, String> map = (Map<String, String>)list.get(k);
								HSSFRow row = sheet.createRow((k + 1 ) +  rowSize);
								HSSFCell cell = row.createCell(0);
								cell.setCellStyle(cellStyle);
								//cell.setCellValue(new HSSFRichTextString(city.getCityname()));
								for (int n = 0; n < mapKeyName.length; n++) {
									cell = row.createCell(n);
									cell.setCellStyle(cellStyle);
									String value = map.get(mapKeyName[n]) == null ? "" : String.valueOf(map.get(mapKeyName[n]));
									cell.setCellValue(new HSSFRichTextString(value));
								}
							}
						}
					
				}
			}else if(webType.equals("VIDEO")){
				String[] str = new String[]{"时间","源节点","目的节点","主机IP","归属地","归属运营商","首祯时间",
						"吞吐率","下载大小","资源大小","资源数量","综合质量"};//表头名称
				String[] mapKeyName = new String[]{"sumStartTime","webName","cityName","webIp","resultProvinceName","resultOperator",
						"firstByteOffSetTime","dealSpeed","downloadSize","contentSize","resourcesNumber","comprehensiveQuality"};//Map的Key值
				for(int i = 0; i < webList.size(); ++i){//遍历业务名称
					Map<String, Object> webName = webList.get(i);
					sheet =  wb.createSheet("视频资源明细_"+webName.get("webName"));
					sheet.setDefaultRowHeightInPoints(12);   
					sheet.setDefaultColumnWidth(30);
					HSSFRow rowTit = sheet.createRow(0);//添加表头
					for (int j = 0; j < str.length; ++j) {//遍历表头单元格信息
						String tit = (String)str[j];
						HSSFCell cell = rowTit.createCell(j);
						cell.setCellStyle(cellStyleTit);
						cell.setCellValue(new HSSFRichTextString(tit));
					}
					int rowSize = 0;
					list = internetResAnalyseDao.findResDetailTableAll(webType, String.valueOf(webName.get("webName")), today, nextDay);
						if(list.size() > 0){
							for (int k = 0; k < list.size(); ++k) {
								Map<String, String> map = (Map<String, String>)list.get(k);
								HSSFRow row = sheet.createRow((k + 1 ) +  rowSize);
								HSSFCell cell = row.createCell(0);
								cell.setCellStyle(cellStyle);
								//cell.setCellValue(new HSSFRichTextString(city.getCityname()));
								for (int n = 0; n < mapKeyName.length; n++) {
									cell = row.createCell(n);
									cell.setCellStyle(cellStyle);
									String value = map.get(mapKeyName[n]) == null ? "" : String.valueOf(map.get(mapKeyName[n]));
									cell.setCellValue(new HSSFRichTextString(value));
								}
							}
						}
					
				}
			}
			
			
			
			try {
				ByteArrayOutputStream fos=new ByteArrayOutputStream();
				wb.write(fos);
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

package com.ultrapower.flowmanage.modules.keyIndexSurvey.service.impl;


import java.util.ArrayList;
import java.util.Calendar;
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
import org.apache.poi.ss.util.SSCellRange;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import com.ultrapower.flowmanage.common.utils.DateUtil;
import com.ultrapower.flowmanage.common.utils.Utils;
import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.modules.health.vo.CityContrastVo;
import com.ultrapower.flowmanage.modules.keyIndexSurvey.dao.KeyIndexSurveyDao;
import com.ultrapower.flowmanage.modules.keyIndexSurvey.service.KeyIndexSurveyService;

@Service("keyIndexSurveyService")
public class KeyIndexSurveyServiceImpl implements KeyIndexSurveyService {
	private static Logger logger = Logger.getLogger(KeyIndexSurveyServiceImpl.class);
	@Resource(name="keyIndexSurveyDao")
	private KeyIndexSurveyDao keyIndexSurveyDao;
	
	

	public List<Map<String, Object>> findFlowNetRate(String getTime)
			throws Exception {
		Date startTime;
		Date endTime;
		startTime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd");
		endTime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd");
		List<Map<String, Object>> list = keyIndexSurveyDao.findFlowNetRate(startTime, endTime);
		if (list.size()>0) {
			for (Map<String, Object> map : list) {
				String sTime = new SimpleDateFormat("yyyy-MM-dd").format(map.get("label"));
				map.put("toolText",sTime+"{br}"+"流量本网率："+map.get("value")+"%"+"{br}"
						+"省际入流量："+map.get("sj")+"MB"+"{br}"+"联通入流量："+map.get("lt")+"MB"+"{br}"
						+"电信入流量："+map.get("dx")+"MB"+"{br}"+"国外&港澳台入流量："+map.get("gw_gat")+"MB"+"{br}"
						+"其它入流量："+map.get("qt")+"MB"+"{br}"+"下行总流量："+map.get("down_sum")+"MB");
				Date label = new SimpleDateFormat("yyyy-MM-dd").parse(map.get("label").toString());
				map.put("label",label.getMonth()+1+"月"+label.getDate()+"日");
			}
		}
		return list;
	}



	public Page findFlowNetRateTable(int pageNo, int pageSize, String getTime)
			throws Exception {
		Page page = new Page();
		Date startTime;
		Date endTime;
		int rowCount = 0;
		startTime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd");
		endTime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd");
		rowCount = keyIndexSurveyDao.findFlowNetRateTableCount(startTime, endTime);
		List<Map<String, String>> list = keyIndexSurveyDao.findFlowNetRateTable(startTime, endTime, pageNo, pageSize);
		if (list.size()>0) {
			for (Map<String, String> map : list) {
				String sTime = new SimpleDateFormat("yyyy-MM-dd").format(map.get("time_id"));
				map.put("time_id", sTime);
			}
		}
		page.setPageNo(pageNo);//当前页
		page.setPageSize(pageSize);//每页的记录数
		page.setRowCount(rowCount);//总行数
		page.setResultList(list);//每页的记录
		return page;
	}



	public List<Map<String, Object>> findPhoneHiteRate(String getTime)
			throws Exception {
		Date startTime;
		Date endTime;
		startTime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd");
		endTime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd");
		List<Map<String, Object>> list = keyIndexSurveyDao.findPhoneHiteRate(startTime, endTime);
		if (list.size()>0) {
			for (Map<String, Object> map : list) {
				String sTime = new SimpleDateFormat("yyyy-MM-dd").format(map.get("label"));
				map.put("toolText",sTime+"{br}"+"手机点击本网率："+map.get("value")+"%"+"{br}"
						+"省内手机点击数："+map.get("sn")+"{br}"+"网内手机点击数："+map.get("wn")+"{br}"
						+"铁通手机点击数："+map.get("tt")+"{br}"+"电信手机点击数："+map.get("dx")+"{br}"
						+"联通手机点击数："+map.get("lt")+"{br}"+"教育网手机点击数："+map.get("jy"));
				Date label = new SimpleDateFormat("yyyy-MM-dd").parse(map.get("label").toString());
				map.put("label",label.getMonth()+1+"月"+label.getDate()+"日");
			}
		}
		return list;
	}



	public Page findPhoneHiteRateTable(int pageNo, int pageSize, String getTime)
			throws Exception {
		Page page = new Page();
		Date startTime;
		Date endTime;
		int rowCount = 0;
		startTime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd");
		endTime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd");
		rowCount = keyIndexSurveyDao.findPhoneHiteRateTableCount(startTime, endTime);
		List<Map<String, String>> list = keyIndexSurveyDao.findPhoneHiteRateTable(startTime, endTime, pageNo, pageSize);
		if (list.size()>0) {
			for (Map<String, String> map : list) {
				String sTime = new SimpleDateFormat("yyyy-MM-dd").format(map.get("time_id"));
				
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//获取周四的日期
				
		        Date dt=sdf.parse(sTime);
		        Calendar monday = Calendar.getInstance();
		        Calendar sunday = Calendar.getInstance();
		        monday.setTime(dt);
		        sunday.setTime(dt);
		        monday.add(Calendar.DAY_OF_YEAR,-3);//日期减3天，即本周一的日期
		        sunday.add(Calendar.DAY_OF_YEAR,3);//日期加3天，即本周日的日期
		        Date dt1=monday.getTime();
		        Date dt2=sunday.getTime();
		        String mon = sdf.format(dt1);
		        String sun = sdf.format(dt2);
				map.put("time_id", mon+"~"+sun);
			}
		}
		page.setPageNo(pageNo);//当前页
		page.setPageSize(pageSize);//每页的记录数
		page.setRowCount(rowCount);//总行数
		page.setResultList(list);//每页的记录
		return page;
	}



	public List<Map<String, Object>> findContentmeetRate(String getTime)
			throws Exception {
		Date startTime;
		Date endTime;
		startTime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd");
		endTime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd");
		
		List<Map<String, Object>> list = keyIndexSurveyDao.findContentmeetRate(startTime, endTime);
		if (list.size()>0) {
			for (Map<String, Object> map : list) {
				String sTime = new SimpleDateFormat("yyyy-MM-dd").format(map.get("label"));
				map.put("toolText",sTime+"{br}"+"本省内容满足率1："+map.get("content_rate1")+"%"+"{br}"
						+"本省内容满足率2："+map.get("content_rate2")+"%"+"{br}"+"IDC服务本省流量："+map.get("service_province")+"MB"+"{br}"
						+"WEBCACHE流量："+map.get("web_cache")+"MB"+"{br}"+"P2PCACHE流量："+map.get("p2p_cache")+"MB"+"{br}"
						+"省内铁通流量："+map.get("tietong")+"MB"+"{br}"+"地市下行总流量："+map.get("trunk_link")+"MB");
				Date label = new SimpleDateFormat("yyyy-MM-dd").parse(map.get("label").toString());
				map.put("label",label.getMonth()+1+"月"+label.getDate()+"日");
			}
		}
		return list;
	}



	public Page findContentmeetRateTable(int pageNo, int pageSize,
			String getTime) throws Exception {
		Page page = new Page();
		Date startTime;
		Date endTime;
		int rowCount = 0;
		startTime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd");
		endTime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd");
		rowCount = keyIndexSurveyDao.findContentmeetRateTableCount(startTime, endTime);
		List<Map<String, String>> list = keyIndexSurveyDao.findContentmeetRateTable(startTime, endTime, pageNo, pageSize);
		if (list.size()>0) {
			for (Map<String, String> map : list) {
				String sTime = new SimpleDateFormat("yyyy-MM-dd").format(map.get("time_id"));
				map.put("time_id", sTime);
			}
		}
		page.setPageNo(pageNo);//当前页
		page.setPageSize(pageSize);//每页的记录数
		page.setRowCount(rowCount);//总行数
		page.setResultList(list);//每页的记录
		return page;
	}

	

	//互联网关键指标监测表格数据Excel导出
		public byte[] keyIndexSurveyExcelTable(int pageNo, int pageSize, String getTime) throws Exception {
//			List<Map<String, Object>> siteTimedelayNetList = findCityTop200WebsiteTimedelay(getTime);//地市TOP200网站时延
//			List<Map<String, Object>> top10NetList = findGroupHotspotTop10Net(getTime);//集团热点TOP10网站信息
//			List<Map<String, Object>> bizNameList = findBusinessName("QU_HOTSPOT");//业务名称
//			List<CityContrastVo> cityList = healthservice.findCityInfo();//地市名称
			Page flowRateList = findFlowNetRateTable(pageNo, pageSize, getTime);
			/************************** Excel导出   *******************************/
			/*** 省内热点网站情况***/
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
			
//			
			
			/*** 集团热点TOP10网站信息 ***/
			sheet =  wb.createSheet("流量本网率信息");
			sheet.setDefaultRowHeightInPoints(10);   
			sheet.setDefaultColumnWidth(30);
			String[] str = new String[]{"采集时间","省际入流量(MB)","铁通入流量(MB)","联通入流量(MB)",
					"电信入流量(MB)","国外&港澳台入流量(MB)","其它入流量(MB)","下行总流量(MB)","流量本网率(%)"};//表头名称
			String[] mapKeyName = new String[]{"time_id","sj","tt","lt","dx","gw_gat",
					"qt","down_sum","bw_percent"};//Map的Key值
			HSSFRow rowTit = sheet.createRow(0);
			for (int i = 0; i < str.length; ++i) {
				String tit = (String)str[i];
				HSSFCell cell = rowTit.createCell(i);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
			}
			//内容
			if(flowRateList.getPageCount() > 0){
//				for (int i = 0; i < flowRateList.getPageCount(); ++i) {
//					Map<String, Object> map = (Map<String, Object>)flowRateList.get(i);
//					HSSFRow row = sheet.createRow(i + 1);
//					for (int j = 0; j < mapKeyName.length; j++) {
//						HSSFCell cell = row.createCell(j);
//						cell.setCellStyle(cellStyle);
//						String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
//						cell.setCellValue(new HSSFRichTextString(value));
//					}
//				}
				sheet.addMergedRegion(new Region(1, (short) 0, flowRateList.getPageCount(),(short) 0));//湖北省跨行
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

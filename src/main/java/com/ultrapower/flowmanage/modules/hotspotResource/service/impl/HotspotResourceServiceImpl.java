package com.ultrapower.flowmanage.modules.hotspotResource.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.common.utils.DateUtil;
import com.ultrapower.flowmanage.common.utils.MapComparator;
import com.ultrapower.flowmanage.common.utils.Utils;
import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;
import com.ultrapower.flowmanage.modules.health.vo.CityContrastVo;
import com.ultrapower.flowmanage.modules.hotspotResource.dao.HotspotResourceDao;
import com.ultrapower.flowmanage.modules.hotspotResource.service.HotspotResourceService;

@Service("hotspotResourceService")
public class HotspotResourceServiceImpl implements HotspotResourceService{
	 private static Logger logger = Logger.getLogger(HotspotResourceServiceImpl.class);
	 @Resource(name="hotspotResourceDao")
	 private HotspotResourceDao hotspotResourceDao;
	
	 public List<FsChartVo> findExitNetVideoFlowRank(String videoType,String exitType, String flowType, String getTime,String timeType) throws Exception {
		 
		    Date starttime;
			Date endtime;
			List<FsChartVo> list;
			
			if("month".equals(timeType)){  //月粒度
				
				
				starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
				//获取下个月第一天时间
				endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
					
				 list = hotspotResourceDao.findExitNetVideoFlowRank(videoType, exitType, flowType, starttime, endtime);
					
			}else{   //天粒度
				//获取当天开始时间
				starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
				//获取当天结束时间
				endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
				list = hotspotResourceDao.findExitNetVideoFlowRankOfDay(videoType, exitType, flowType, starttime, endtime);
					
					}
			
			
			
		/* //获取当前月第一天时间
		  starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		 //获取下个月第一天时间
		 endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		 list = hotspotResourceDao.findExitNetVideoFlowRank(videoType, exitType, flowType, starttime, endtime);
		*/ 
			return list;
	 }
	 
	 public List<Map<String, Object>> findHotspotVideoExitByVideoExitType(String videoType, String exitType, String getTime,String timeType) throws Exception {
    	List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> flowType = new HashMap<String, Object>();
		flowType.put("总流量", "sumFlow");
		flowType.put("入流量", "inFlow");
		flowType.put("出流量", "outFlow");
		String webName = "";
		int index = 0;

        Date starttime;
	    Date endtime;
	    List<Map<String, Object>> lists=null;
	
	    if("month".equals(timeType)){ 
	    	 //获取当前月第一天时间
			 starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
		     endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			 lists = hotspotResourceDao.findHotspotVideoExitByVideoExitType(videoType, exitType, starttime, endtime);
			 }
	    else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			
			 lists = hotspotResourceDao.findHotspotVideoExitByVideoExitTypeOfDay(videoType, exitType, starttime, endtime);
				}
		
	   for (Map<String, Object> flow : lists) {
			if(!String.valueOf(flow.get("webName")).equals(webName) && !"".equals(webName)){
				list.add(map);
				map = new HashMap<String, Object>();
			}
			map.put("webName", flow.get("webName"));
			map.put(flowType.get(flow.get("flowType"))+"Val", flow.get("val"));
			map.put(flowType.get(flow.get("flowType"))+"Perc", flow.get("perc"));
			webName = String.valueOf(flow.get("webName"));
			index ++;
			if(lists.size() == index){
				list.add(map);
			}
		}
		MapComparator mc = new MapComparator();
		Collections.sort(list, mc);//排序
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> flow = list.get(i);
			Iterator iterator = flow.entrySet().iterator();
			while(iterator.hasNext()){
				Map.Entry<String, Object> entry = (Entry<String, Object>)iterator.next();
				if(!"webName".equals(entry.getKey())){
					if(entry.getKey().indexOf("Val") != -1){
						flow.put(entry.getKey(), Utils.valueOf(String.valueOf(entry.getValue()), "#,###.00"));
					}else{
						flow.put(entry.getKey(), Utils.valueOf(String.valueOf(entry.getValue())));
					}
				}
			}
			flow.put("id", i+1);
		}
		return list;
	 }

	public Page findIDCResTable(int pageNo, int pageSize, String resType,String getTime,String timeType) throws Exception {
		Page pg = new Page();
		List<Map<String, Object>> list=null;
		    Date starttime;
			Date endtime;
			int rowCount = 0;
			
		
		if("month".equals(timeType)){ 
			
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			rowCount = hotspotResourceDao.findIDCResRowCount(resType, starttime, endtime);//总行数
			list = hotspotResourceDao.findIDCResTable(resType, starttime, endtime, pageNo, pageSize);
			
		
		}else {
			 //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			list = hotspotResourceDao.findIDCResTableOfDay(resType, starttime, endtime, pageNo, pageSize);
			
		}
		
		//获取当前月第一天时间
		for (Map<String, Object> idc : list) {
			idc.put("accVal", Utils.valueOf(String.valueOf(idc.get("accVal")), "#,###.00"));
			idc.put("flowVal", Utils.valueOf(String.valueOf(idc.get("flowVal")), "#,###.00"));
		}
		pg.setPageNo(pageNo);//当前页
		pg.setPageSize(pageSize);//每页的记录数
		pg.setRowCount(rowCount);//总行数
		pg.setResultList(list);//每页的记录
		return pg;
	}

	public List<Map<String, Object>> findHotspotWebTable(String getTime,String timeType) throws Exception {
		
		Date starttime;
		Date endtime;
		List<Map<String, Object>> list=null;
	
	if("month".equals(timeType)){ 
		
		starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		 list = hotspotResourceDao.findHotspotWebTable(starttime, endtime);
			
	}else {
		 //天粒度
		//获取当天开始时间
		starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
		//获取当天结束时间
		endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
		list = hotspotResourceDao.findHotspotWebTableOfDay(starttime, endtime);
			
	}
		//获取当前月第一天时间
		for (Map<String, Object> web : list) {
			web.put("hits", Utils.valueOf(String.valueOf(web.get("HITS")), "#,###"));
			web.put("flowPercCM", Utils.valueOf(String.valueOf(web.get("flowPercCM")))+"%");
			web.put("flowPercCRC", Utils.valueOf(String.valueOf(web.get("flowPercCRC")))+"%");
			web.put("flowPercOUT", Utils.valueOf(String.valueOf(web.get("flowPercOUT")))+"%");
			web.put("bwRate", Utils.valueOf(String.valueOf(web.get("bwRate"))));
		}
		return list;
	}

	public List<Map<String, Object>> findHotspotP2PTable(String getTime,String timeType) throws Exception {
		Date starttime;
		Date endtime;
		List<Map<String, Object>> list =null;
	
		if("month".equals(timeType)){ 
		
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			 list = hotspotResourceDao.findHotspotP2PTable(starttime, endtime);
		
		}else {
			 //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			list = hotspotResourceDao.findHotspotP2PTableOfDay(starttime, endtime);
		}
		
		
		//获取当前月第一天时间
	
		for (Map<String, Object> web : list) {
			web.put("flowVal", Utils.valueOf(String.valueOf(web.get("flowVal")), "#,###.00"));
			web.put("flowValCM", Utils.valueOf(String.valueOf(web.get("flowValCM")), "#,###.00"));
			web.put("flowValCRC", Utils.valueOf(String.valueOf(web.get("flowValCRC")), "#,###.00"));
			web.put("flowPercCM", Utils.valueOf(String.valueOf(web.get("flowPercCM")))+"%");
			web.put("flowPercCRC", Utils.valueOf(String.valueOf(web.get("flowPercCRC")))+"%");
			web.put("flowPercOUT", Utils.valueOf(String.valueOf(web.get("flowPercOUT")))+"%");
			web.put("bwRate", Utils.valueOf(String.valueOf(web.get("bwRate"))));
		}
		return list;
	}

	public Page findHotspotFlow50Table(int pageNo, int pageSize, String getTime,String timeType)
			throws Exception {
		Page pg = new Page();
		Date starttime;
		Date endtime;
		int rowCount;
		List<Map<String, Object>> lists;
		
		
		/*starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		
		rowCount = hotspotResourceDao.findHotspotFlow50RowCount(starttime, endtime);//总行数
	
		 lists = hotspotResourceDao.findHotspotFlow50Table(starttime, endtime, pageNo, pageSize);//每页的记录数
		*/
		
		
		if("month".equals(timeType)){  //月粒度
			
			
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			
			rowCount = hotspotResourceDao.findHotspotFlow50RowCount(starttime, endtime);//总行数
		
			 lists = hotspotResourceDao.findHotspotFlow50Table(starttime, endtime, pageNo, pageSize);//每页的记录数
		}else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			
			 rowCount = hotspotResourceDao.findHotspotFlow50RowCountOfDay(starttime, endtime);//总行数
			 lists = hotspotResourceDao.findHotspotFlow50TableOfDay(starttime, endtime, pageNo, pageSize);//每页的记录数
		}
		
		
		//获取当前月第一天时间
		/*Date getMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		Date nextMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		int rowCount = hotspotResourceDao.findHotspotFlow50RowCount(getMonthFirstDay, nextMonthFirstDay);//总行数
		List<Map<String, Object>> list = hotspotResourceDao.findHotspotFlow50Table(getMonthFirstDay, nextMonthFirstDay, pageNo, pageSize);//每页的记录数
*/		for (Map<String, Object> idc : lists) {
			idc.put("flowVal", Utils.valueOf(String.valueOf(idc.get("flowVal")), "#,###.00"));
		}
		pg.setPageNo(pageNo);//当前页
		pg.setPageSize(pageSize);//每页的记录数
		pg.setRowCount(rowCount);//总行数
		pg.setResultList(lists);//每页的记录
		return pg;
	}
	
	public Page findHotspotAcc50Table(int pageNo, int pageSize, String getTime,String timeType)
			throws Exception {
		Page pg = new Page();
		
		Date starttime;
		Date endtime;
		int rowCount;
		List<Map<String, Object>> list;
	
	if("month".equals(timeType)){ 
		
		starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		rowCount = hotspotResourceDao.findHotspotAcc50RowCount(starttime, endtime);//总行数
		list = hotspotResourceDao.findHotspotAcc50Table(starttime, endtime, pageNo, pageSize);//每页的记录数
		
	
	}else {
		 //天粒度
		//获取当天开始时间
		starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
		//获取当天结束时间
		endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
		 rowCount = hotspotResourceDao.findHotspotAcc50RowCountOfDay(starttime, endtime);//总行数
		 list = hotspotResourceDao.findHotspotAcc50TableOfDay(starttime, endtime, pageNo, pageSize);//每页的记录数
		
	}
		/* rowCount = hotspotResourceDao.findHotspotAcc50RowCount(starttime, endtime);//总行数
		 list = hotspotResourceDao.findHotspotAcc50Table(starttime, endtime, pageNo, pageSize);//每页的记录数
		*/for (Map<String, Object> idc : list) {
			idc.put("accVal", Utils.valueOf(String.valueOf(idc.get("accVal")), "#,###"));
		}
		pg.setPageNo(pageNo);//当前页
		pg.setPageSize(pageSize);//每页的记录数
		pg.setRowCount(rowCount);//总行数
		pg.setResultList(list);//每页的记录
		return pg;
	}

	public Page findHotspotPer70Table(int pageNo, int pageSize, String getTime,String timeType) throws Exception {
		Page pg = new Page();
		Date starttime;
		Date endtime;
		int rowCount;
		List<Map<String, Object>> list;
	
	if("month".equals(timeType)){ 
		
		starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		rowCount = hotspotResourceDao.findHotspotPer70RowCount(starttime, endtime);//总行数
		list = hotspotResourceDao.findHotspotPer70Table(starttime, endtime, pageNo, pageSize);//每页的记录数
		
	
	}else {
		 //天粒度
		//获取当天开始时间
		starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
		//获取当天结束时间
		endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
		rowCount = hotspotResourceDao.findHotspotPer70RowCountOfDay(starttime, endtime);//总行数
		list = hotspotResourceDao.findHotspotPer70TableOfDay(starttime, endtime, pageNo, pageSize);//每页的记录数
		
	}
		
		for (Map<String, Object> idc : list) {
			idc.put("flowVal", Utils.valueOf(String.valueOf(idc.get("flowVal")), "#,###.00"));
			idc.put("flowPerc", Utils.valueOf(String.valueOf(idc.get("flowPerc")))+"%");
		}
		pg.setPageNo(pageNo);//当前页
		pg.setPageSize(pageSize);//每页的记录数
		pg.setRowCount(rowCount);//总行数
		pg.setResultList(list);//每页的记录
		return pg;
	}

	public Page findHotspotDNS1000Table(int pageNo, int pageSize, String getTime,String timeType)
			throws Exception {
		Page pg = new Page();
		
		Date starttime;
		Date endtime;
		int rowCount;
		List<Map<String, Object>> list;
	
	if("month".equals(timeType)){ 
		
		starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		rowCount = hotspotResourceDao.findHotspotDNS1000RowCount(starttime, endtime);//总行数
		list = hotspotResourceDao.findHotspotDNS1000Table(starttime, endtime, pageNo, pageSize);//每页的记录数
		
	
	}else {
		 //天粒度
		//获取当天开始时间
		starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
		//获取当天结束时间
		endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
		rowCount = hotspotResourceDao.findHotspotDNS1000RowCountOfDay(starttime, endtime);//总行数
		list = hotspotResourceDao.findHotspotDNS1000TableOfDay(starttime, endtime, pageNo, pageSize);//每页的记录数
		
	}
		for (Map<String, Object> idc : list) {
			idc.put("dnsTimes", Utils.valueOf(String.valueOf(idc.get("dnsTimes")), "#,###"));
			idc.put("dnsRate", Utils.valueOf(String.valueOf(idc.get("dnsRate")))+"%");
		}
		pg.setPageNo(pageNo);//当前页
		pg.setPageSize(pageSize);//每页的记录数
		pg.setRowCount(rowCount);//总行数
		pg.setResultList(list);//每页的记录
		return pg;
	}

	public Page findHotspotDNSNOCMTable(int pageNo, int pageSize, String getTime,String timeType) throws Exception {
		Page pg = new Page();
		
		Date starttime;
		Date endtime;
		int rowCount ;
		List<Map<String, Object>> list;
	
	if("month".equals(timeType)){ 
		
		starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		rowCount = hotspotResourceDao.findHotspotDNSNOCMRowCount(starttime, endtime);//总行数
	    list = hotspotResourceDao.findHotspotDNSNOCMTable(starttime, endtime, pageNo, pageSize);//每页的记录数
		
	}else {
		 //天粒度
		//获取当天开始时间
		starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
		//获取当天结束时间
		endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
		rowCount = hotspotResourceDao.findHotspotDNSNOCMRowCountOfDay(starttime, endtime);//总行数
	    list = hotspotResourceDao.findHotspotDNSNOCMTableOfDay(starttime, endtime, pageNo, pageSize);//每页的记录数
		
	}
		for (Map<String, Object> idc : list) {
			idc.put("dnsTimes", Utils.valueOf(String.valueOf(idc.get("dnsTimes")), "#,###"));
			idc.put("dnsRate", Utils.valueOf(String.valueOf(idc.get("dnsRate")))+"%");
		}
		pg.setPageNo(pageNo);//当前页
		pg.setPageSize(pageSize);//每页的记录数
		pg.setRowCount(rowCount);//总行数
		pg.setResultList(list);//每页的记录
		return pg;
	}

	public Page findHotspotResTable(int pageNo, int pageSize, String getTime,String timeType) throws Exception {
		Page pg = new Page();
		
		Date starttime;
		Date endtime;
		int rowCount ;
		List<Map<String, Object>> list;
	
	if("month".equals(timeType)){ 
		
		starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
	
		 rowCount = hotspotResourceDao.findHotspotResRowCount(starttime, endtime);//总行数
		 list = hotspotResourceDao.findHotspotResTable(starttime, endtime, pageNo, pageSize);//每页的记录数
		
	}else {
		 //天粒度
		//获取当天开始时间
		starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
		//获取当天结束时间
		endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
		 rowCount = hotspotResourceDao.findHotspotResRowCountOfDay(starttime, endtime);//总行数
		 list = hotspotResourceDao.findHotspotResTableOfDay(starttime, endtime, pageNo, pageSize);//每页的记录数
		
	}
		for (Map<String, Object> idc : list) {
			idc.put("flowPerc", Utils.valueOf(String.valueOf(idc.get("flowPerc")))+"%");
		}
		pg.setPageNo(pageNo);//当前页
		pg.setPageSize(pageSize);//每页的记录数
		pg.setRowCount(rowCount);//总行数
		pg.setResultList(list);//每页的记录
		return pg;
	}

	//热点资源分析表格数据Excel导出
	public byte[] hotspotResourceExcelTable(String getTime,String timeType) throws Exception {
		
		
		Date FirstDay;
		Date LastDay;
		
		//流量TOP50网站列表总行数
		int flowRowCount = 0 ;
		
		//访问TOP50网站列表总行数
		int accRowCount=0;
		//网内流量占比<70%网站列表总行数
		int per70RowCount=0 ;
		//TOP1000域名列表总行数
		int top1000RowCount=0 ;
		//非移动引入域名列表总行数
		int nocmRowCount=0 ;
		//热点资源引入分析列表总行数
		int hotspotResRowCount=0;
		//省内IDC对外提供资源列表_网内他省总行数
		int idcResNetInRowCount=0 ;
	    //省内IDC对外提供资源列表_网外总行数
		int idcResNetOutRowCount=0;
				
				
		
		
		if("month".equals(timeType)){ 
			
			FirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			LastDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			//流量TOP50网站列表总行数
			flowRowCount = hotspotResourceDao.findHotspotFlow50RowCount(FirstDay, LastDay);
			//访问TOP50网站列表总行数
			accRowCount = hotspotResourceDao.findHotspotAcc50RowCount(FirstDay, LastDay);
			//网内流量占比<70%网站列表总行数
			per70RowCount = hotspotResourceDao.findHotspotPer70RowCount(FirstDay, LastDay);
			//TOP1000域名列表总行数
			top1000RowCount = hotspotResourceDao.findHotspotDNS1000RowCount(FirstDay, LastDay);
			//非移动引入域名列表总行数
			nocmRowCount = hotspotResourceDao.findHotspotDNSNOCMRowCount(FirstDay, LastDay);
			//热点资源引入分析列表总行数
			hotspotResRowCount = hotspotResourceDao.findHotspotResRowCount(FirstDay, LastDay);	
			//省内IDC对外提供资源列表_网内他省总行数
			idcResNetInRowCount = hotspotResourceDao.findIDCResRowCount("网内他省", FirstDay, LastDay);
			//省内IDC对外提供资源列表_网外总行数
			idcResNetOutRowCount = hotspotResourceDao.findIDCResRowCount("网外", FirstDay, LastDay);
			
			
				
		}else {
			 //天粒度
			//获取当天开始时间
			FirstDay = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			LastDay = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//流量TOP50网站列表总行数
			flowRowCount = hotspotResourceDao.findHotspotFlow50RowCountOfDay(FirstDay, LastDay);
			//访问TOP50网站列表总行数
			accRowCount = hotspotResourceDao.findHotspotAcc50RowCountOfDay(FirstDay, LastDay);
			//网内流量占比<70%网站列表总行数
			per70RowCount = hotspotResourceDao.findHotspotPer70RowCountOfDay(FirstDay, LastDay);
			//TOP1000域名列表总行数
			top1000RowCount = hotspotResourceDao.findHotspotDNS1000RowCountOfDay(FirstDay, LastDay);
			//非移动引入域名列表总行数
			nocmRowCount = hotspotResourceDao.findHotspotDNSNOCMRowCountOfDay(FirstDay, LastDay);
			//热点资源引入分析列表总行数
			hotspotResRowCount = hotspotResourceDao.findHotspotResRowCountOfDay(FirstDay, LastDay);	
			//省内IDC对外提供资源列表_网内他省总行数
			idcResNetInRowCount = hotspotResourceDao.findIDCResRowCountOfDay("网内他省", FirstDay, LastDay);
			//省内IDC对外提供资源列表_网外总行数
			idcResNetOutRowCount = hotspotResourceDao.findIDCResRowCountOfDay("网外", FirstDay, LastDay);
			
		}
		
		
		
		Page flowPage = null,accPage = null,per70Page = null,top1000Page = null,nocmPage = null,hotspotResPage = null,idcResNetInPage = null,idcResNetOutPage = null;
		if(flowRowCount > 0){
			flowPage = findHotspotFlow50Table(1, flowRowCount, getTime,timeType);//流量TOP50网站列表
		}
		if(accRowCount > 0){
			accPage = findHotspotAcc50Table(1, accRowCount, getTime,timeType);//访问TOP50网站列表
		}
		if(per70RowCount > 0){
			per70Page = findHotspotPer70Table(1, per70RowCount, getTime,timeType);//网内流量占比<70%网站列表
		}
		if(top1000RowCount > 0){
			top1000Page = findHotspotDNS1000Table(1, top1000RowCount, getTime,timeType);//TOP1000域名列表
		}
		if(nocmRowCount > 0){
			nocmPage = findHotspotDNSNOCMTable(1, nocmRowCount, getTime,timeType);//非移动引入域名列表
		}
		if(hotspotResRowCount > 0){
			hotspotResPage = findHotspotResTable(1, hotspotResRowCount, getTime,timeType);//热点资源引入分析列表
		}
		
		if(idcResNetInRowCount > 0){
			idcResNetInPage = findIDCResTable(1, idcResNetInRowCount, "网内他省", getTime,timeType);//省内IDC对外提供资源列表_网内他省
		}
		if(idcResNetOutRowCount > 0){
			idcResNetOutPage = findIDCResTable(1, idcResNetOutRowCount, "网外", getTime,timeType);//省内IDC对外提供资源列表_网外
		}
		
		List<Map<String, Object>> webList = findHotspotWebTable(getTime,timeType);//省内热点网站情况 
		List<Map<String, Object>> p2pList = findHotspotP2PTable(getTime,timeType);//省内热点P2P下载/P2P视频情况
		List<Map<String, Object>> p2pThirdPartyList = findHotspotVideoExitByVideoExitType("P2P视频", "第三方出口", getTime,timeType);//出网P2P视频排名(第三方出口)
		List<Map<String, Object>> p2pProvinceNetList = findHotspotVideoExitByVideoExitType("P2P视频", "省网出口", getTime,timeType);//出网P2P视频排名(省网出口)
		List<Map<String, Object>> sitesThirdPartyList = findHotspotVideoExitByVideoExitType("网站视频", "第三方出口", getTime,timeType);//出网网站视频排名(第三方出口)
		List<Map<String, Object>> sitesProvinceNetList = findHotspotVideoExitByVideoExitType("网站视频", "省网出口", getTime,timeType);//出网网站视频排名(省网出口)
		
		/************************** Excel导出  *******************************/
		/*** 热点资源引入分析_流量TOP10000网站列表***/
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet =  wb.createSheet("热点资源引入分析_流量TOP10000网站列表");
		String[] str = new String[]{"排名","网站域名","流量(GB)"};//表头名称
		String[] mapKeyName = new String[]{"number","domainName","flowVal"};//Map的Key值
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		//标题样式
		HSSFCellStyle cellStyleTit = wb.createCellStyle();
		cellStyleTit.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyleTit.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		//			cellStyle.setWrapText(true);// 指定单元格自动换行
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
		
		HSSFRow rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
		}
		//内容
		if(flowRowCount > 0){
			for (int i = 0; i < flowPage.getResultList().size(); ++i) {
				Map<String, Object> map = (Map<String, Object>)flowPage.getResultList().get(i);
				HSSFRow row = sheet.createRow(i + 1);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}
		
		/*** 热点资源引入分析_访问TOP10000网站列表***/
		sheet =  wb.createSheet("热点资源引入分析_访问TOP10000网站列表");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"排名","网站域名","访问量"};
		mapKeyName = new String[]{"number","domainName","accVal"};//Map的Key值
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
		}
		//内容
		if(accRowCount > 0){
			for (int i = 0; i < accPage.getResultList().size(); ++i) {
				Map<String, Object> map = (Map<String, Object>)accPage.getResultList().get(i);
				HSSFRow row = sheet.createRow(i + 1);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}
		
		/*** 热点资源引入分析_网内流量占比<70%网站列表 ***/
		sheet =  wb.createSheet("热点资源引入分析_网内流量占比<70%网站列表");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"排名","网站域名","流量(GB)","网内流量占比"};
		mapKeyName = new String[]{"number","domainName","flowVal","flowPerc"};//Map的Key值
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
		}
		//内容
		if(per70RowCount > 0){
			for (int i = 0; i < per70Page.getResultList().size(); ++i) {
				Map<String, Object> map = (Map<String, Object>)per70Page.getResultList().get(i);
				HSSFRow row = sheet.createRow(i + 1);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}

		/*** 热点资源引入分析_TOP1000域名列表 ***/
		sheet =  wb.createSheet("热点资源引入分析_TOP1000域名列表");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"排名","网站域名","IP地址","IP地址归属","DNS请求次数","DNS解析成功率"};
		mapKeyName = new String[]{"number","domainName","ip","ipBelonging","dnsTimes","dnsRate"};//Map的Key值
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
		}
		//内容
		if(top1000RowCount > 0){
			for (int i = 0; i < top1000Page.getResultList().size(); ++i) {
				Map<String, Object> map = (Map<String, Object>)top1000Page.getResultList().get(i);
				HSSFRow row = sheet.createRow(i + 1);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}
		
		/*** 热点资源引入分析_非移动引入域名列表 ***/
		sheet =  wb.createSheet("热点资源引入分析_非移动引入域名列表");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"排名","网站域名","IP地址","IP地址归属","DNS请求次数","DNS解析成功率"};
		mapKeyName = new String[]{"number","domainName","ip","ipBelonging","dnsTimes","dnsRate"};//Map的Key值
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
		}
		//内容
		if(nocmRowCount > 0){
			for (int i = 0; i < nocmPage.getResultList().size(); ++i) {
				Map<String, Object> map = (Map<String, Object>)nocmPage.getResultList().get(i);
				HSSFRow row = sheet.createRow(i + 1);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}
		
		/*** 热点资源引入分析_需引入的热点资源列表 ***/
		sheet =  wb.createSheet("热点资源引入分析_需引入的热点资源列表");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"排名","网站域名","网站IP","流量排名","访问量排名","DNS请求次数","网内流量占比"};
		mapKeyName = new String[]{"number","domainName","ip","flowRank","accRank","dnsTimes","flowPerc"};//Map的Key值
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
		}
		//内容
		if(hotspotResRowCount > 0){
			for (int i = 0; i < hotspotResPage.getResultList().size(); ++i) {
				Map<String, Object> map = (Map<String, Object>)hotspotResPage.getResultList().get(i);
				HSSFRow row = sheet.createRow(i + 1);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}
		
		/*** 省内热点网站情况 ***/
		sheet =  wb.createSheet("省内热点网站情况");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"点击排名","网站名称","网站类型","点击率","移动资源提供流量占比","铁通资源提供流量占比","网外资源提供流量占比","本网率"};
		mapKeyName = new String[]{"number","webName","webType","hits","flowPercCM","flowPercCRC","flowPercOUT","bwRate"};//Map的Key值
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
		}
		//内容
		if(webList.size() > 0){
			for (int i = 0; i < webList.size(); ++i) {
				Map<String, Object> map = (Map<String, Object>)webList.get(i);
				HSSFRow row = sheet.createRow(i + 1);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
					if("bwRate".equals(mapKeyName[j])){
						value = !"".equals(value) ? value+"%" : "";
					}
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}
		
		/*** 省内热点P2P下载/P2P视频情况 ***/
		sheet =  wb.createSheet("省内热点P2P下载(P2P视频)情况");
		sheet.setDefaultRowHeightInPoints(10);   
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"P2P类型","名称","总流量(Mbps)","移动资源流量(Mbps)","铁通资源流量(Mbps)","移动资源流量占比","铁通资源流量占比","网外资源流量占比","本网率"};
		mapKeyName = new String[]{"p2pType","webName","flowVal","flowValCM","flowValCRC","flowPercCM","flowPercCRC","flowPercOUT","bwRate"};//Map的Key值
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
		}
		//内容
		if(p2pList.size() > 0){
			String p2pType = "";
			int index = 1;
			for (int i = 0; i < p2pList.size(); ++i) {
				Map<String, Object> map = (Map<String, Object>)p2pList.get(i);
				HSSFRow row = sheet.createRow(i + 1);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
					if("bwRate".equals(mapKeyName[j])){
						value = !"".equals(value) ? value+"%" : "";
					}
					cell.setCellValue(new HSSFRichTextString(value));
				}
				if(!"".equals(p2pType) && !String.valueOf(map.get("p2pType")).equals(p2pType)){
					sheet.addMergedRegion(new Region(index, (short) 0, i,(short) 0));//P2P类型跨行
					index = i+1;
				}
				p2pType = String.valueOf(map.get("p2pType"));
			}
			sheet.addMergedRegion(new Region(index, (short) 0, p2pList.size(),(short) 0));//P2P类型跨行
		}
		
		/*** 出网P2P视频排名(第三方出口) ***/
		sheet =  wb.createSheet("出网P2P视频排名(第三方出口)");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"排名","P2P视频","双向总流量","入流量","总流量"};
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			if(i<2){
				HSSFCell cell = rowTit.createCell(i);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
			}else{
				HSSFCell cell = rowTit.createCell(2*i-2);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
				sheet.addMergedRegion(new Region(0, (short) (2*i-2), 0,(short) (2*i-1)));
			}
		}
		str = new String[]{"流量值(MB)","占比(%)","流量值(MB)","占比(%)","流量值(MB)","占比(%)"};
		mapKeyName = new String[]{"id","webName","sumFlowVal","sumFlowPerc","inFlowVal","inFlowPerc","outFlowVal","outFlowPerc"};//Map的Key值
		rowTit = sheet.createRow(1);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i+2);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i<2){//合并第0列和第1列的第0行和第1行
				sheet.addMergedRegion(new Region(0, (short) i, 1,(short) i));
			}
		}
		//内容
		if(p2pThirdPartyList.size() > 0){
			for (int i = 0; i < p2pThirdPartyList.size(); ++i) {
				Map<String, Object> map = (Map<String, Object>)p2pThirdPartyList.get(i);
				HSSFRow row = sheet.createRow(i + 2);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}
		
		/*** 出网P2P视频排名(省网出口) ***/
		sheet =  wb.createSheet("出网P2P视频排名(省网出口)");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"排名","P2P视频","双向总流量","入流量","总流量"};
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			if(i<2){
				HSSFCell cell = rowTit.createCell(i);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
			}else{
				HSSFCell cell = rowTit.createCell(2*i-2);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
				sheet.addMergedRegion(new Region(0, (short) (2*i-2), 0,(short) (2*i-1)));
			}
		}
		str = new String[]{"流量值(MB)","占比(%)","流量值(MB)","占比(%)","流量值(MB)","占比(%)"};
		mapKeyName = new String[]{"id","webName","sumFlowVal","sumFlowPerc","inFlowVal","inFlowPerc","outFlowVal","outFlowPerc"};//Map的Key值
		rowTit = sheet.createRow(1);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i+2);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i<2){//合并第0列和第1列的第0行和第1行
				sheet.addMergedRegion(new Region(0, (short) i, 1,(short) i));
			}
		}
		//内容
		if(p2pProvinceNetList.size() > 0){
			for (int i = 0; i < p2pProvinceNetList.size(); ++i) {
				Map<String, Object> map = (Map<String, Object>)p2pProvinceNetList.get(i);
				HSSFRow row = sheet.createRow(i + 2);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}
		
		/*** 出网网站视频排名(第三方出口) ***/
		sheet =  wb.createSheet("出网网站视频排名(第三方出口)");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"排名","P2P视频","双向总流量","入流量","总流量"};
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			if(i<2){
				HSSFCell cell = rowTit.createCell(i);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
			}else{
				HSSFCell cell = rowTit.createCell(2*i-2);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
				sheet.addMergedRegion(new Region(0, (short) (2*i-2), 0,(short) (2*i-1)));
			}
		}
		str = new String[]{"流量值(MB)","占比(%)","流量值(MB)","占比(%)","流量值(MB)","占比(%)"};
		mapKeyName = new String[]{"id","webName","sumFlowVal","sumFlowPerc","inFlowVal","inFlowPerc","outFlowVal","outFlowPerc"};//Map的Key值
		rowTit = sheet.createRow(1);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i+2);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i<2){//合并第0列和第1列的第0行和第1行
				sheet.addMergedRegion(new Region(0, (short) i, 1,(short) i));
			}
		}
		//内容
		if(sitesThirdPartyList.size() > 0){
			for (int i = 0; i < sitesThirdPartyList.size(); ++i) {
				Map<String, Object> map = (Map<String, Object>)sitesThirdPartyList.get(i);
				HSSFRow row = sheet.createRow(i + 2);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}
		
		/*** 出网网站视频排名(省网出口) ***/
		sheet =  wb.createSheet("出网网站视频排名(省网出口)");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"排名","P2P视频","双向总流量","入流量","总流量"};
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			if(i<2){
				HSSFCell cell = rowTit.createCell(i);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
			}else{
				HSSFCell cell = rowTit.createCell(2*i-2);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
				sheet.addMergedRegion(new Region(0, (short) (2*i-2), 0,(short) (2*i-1)));
			}
		}
		str = new String[]{"流量值(MB)","占比(%)","流量值(MB)","占比(%)","流量值(MB)","占比(%)"};
		mapKeyName = new String[]{"id","webName","sumFlowVal","sumFlowPerc","inFlowVal","inFlowPerc","outFlowVal","outFlowPerc"};//Map的Key值
		rowTit = sheet.createRow(1);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i+2);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i<2){//合并第0列和第1列的第0行和第1行
				sheet.addMergedRegion(new Region(0, (short) i, 1,(short) i));
			}
		}
		//内容
		if(sitesProvinceNetList.size() > 0){
			for (int i = 0; i < sitesProvinceNetList.size(); ++i) {
				Map<String, Object> map = (Map<String, Object>)sitesProvinceNetList.get(i);
				HSSFRow row = sheet.createRow(i + 2);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}
		
		/*** 省内IDC对外提供资源列表_网内他省 ***/
		sheet =  wb.createSheet("省内IDC对外提供资源列表_网内他省");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"序号","网站域名","访问量","流量(GB)"};
		mapKeyName = new String[]{"number","webName","accVal","flowVal"};//Map的Key值
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
		}
		//内容
		if(idcResNetInRowCount > 0){
			for (int i = 0; i < idcResNetInPage.getResultList().size(); ++i) {
				Map<String, Object> map = (Map<String, Object>)idcResNetInPage.getResultList().get(i);
				HSSFRow row = sheet.createRow(i + 1);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}
		
		/*** 省内IDC对外提供资源列表_网外 ***/
		sheet =  wb.createSheet("省内IDC对外提供资源列表_网外");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"序号","网站域名","访问量","流量(GB)"};
		mapKeyName = new String[]{"number","webName","accVal","flowVal"};//Map的Key值
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
		}
		//内容
		if(idcResNetOutRowCount > 0){
			for (int i = 0; i < idcResNetOutPage.getResultList().size(); ++i) {
				Map<String, Object> map = (Map<String, Object>)idcResNetOutPage.getResultList().get(i);
				HSSFRow row = sheet.createRow(i + 1);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
					cell.setCellValue(new HSSFRichTextString(value));
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

	public Page findUserClickhotspotDomainsTable(int pageNo, int pageSize, String getTime,String timeType) throws Exception {
		Page pg = new Page();
		Date starttime;
		Date endtime;
		int rowCount;
		List<Map<String, Object>> list;
	
	if("month".equals(timeType)){ 
		
		starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		rowCount = hotspotResourceDao.findUserClickhotspotDomainsRowCount(starttime, endtime);//总行数
		list = hotspotResourceDao.findUserClickhotspotDomainsTable(starttime, endtime, pageNo, pageSize);//每页的记录数
		
	}else {
		 //天粒度
		//获取当天开始时间
		starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
		//获取当天结束时间
		endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
		rowCount = hotspotResourceDao.findUserClickhotspotDomainsRowCountOfDay(starttime, endtime);//总行数
		list = hotspotResourceDao.findUserClickhotspotDomainsTableOfDay(starttime, endtime, pageNo, pageSize);//每页的记录数
		
	}
		
		
		for (Map<String, Object> idc : list) {
			idc.put("dnsTimes", Utils.valueOf(String.valueOf(idc.get("dnsTimes")), "#,###"));
			idc.put("dnsRate", Utils.valueOf(String.valueOf(idc.get("dnsRate")))+"%");
		}
		pg.setPageNo(pageNo);//当前页
		pg.setPageSize(pageSize);//每页的记录数
		pg.setRowCount(rowCount);//总行数
		pg.setResultList(list);//每页的记录
		return pg;
	}

	
	
	
}

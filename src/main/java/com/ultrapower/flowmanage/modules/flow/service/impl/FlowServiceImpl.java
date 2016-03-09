package com.ultrapower.flowmanage.modules.flow.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.ExtendedFormatRecord;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.common.utils.Common;
import com.ultrapower.flowmanage.common.utils.DateUtil;
import com.ultrapower.flowmanage.common.utils.FlowTree;

import com.ultrapower.flowmanage.common.utils.Utils;

import com.ultrapower.flowmanage.common.utils.MapComparator;

import com.ultrapower.flowmanage.common.vo.FlowExitTree;
import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.common.vo.Tree;
import com.ultrapower.flowmanage.modules.flow.dao.FlowDao;
import com.ultrapower.flowmanage.modules.flow.service.FlowService;
import com.ultrapower.flowmanage.modules.flow.vo.CustomFlowDirection;
import com.ultrapower.flowmanage.modules.flow.vo.FlowExitLinkVo;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;
import com.ultrapower.flowmanage.modules.health.vo.ThreeNetHealth;

@Service("flowservice")
public class FlowServiceImpl implements FlowService {
	private static Logger logger = Logger.getLogger(FlowServiceImpl.class);
	 @Resource(name="flowdao")
	 private FlowDao flowDao;
	
	//各地市访问电信及联通流量分析
	public List<FsChartVo> findFlowOper(String operName, String getTime, String timeType)
			throws Exception {
		Date starttime;
		Date endtime;
		List<FsChartVo> list = null;
		if("month".equals(timeType)){  //月粒度
			//获取当前月第一天时间
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			list = flowDao.findFlowOper(operName, starttime, endtime);
		}else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			list = flowDao.findFlowOperOfDay(operName, starttime, endtime);
		}
		return list;
	}


	//业务类型名称查询
	@Override
	public List<Map<String, String>> findBusinessTypeName(String type)
			throws Exception {
		return flowDao.findBusinessTypeName(type);
	}



	//流量出口分析 全国对比
	public List<FsChartVo> findFlowExitCountry(String param, String extiname, String timeStr)
			throws Exception {
		//获取当前月第一天时间
		Date getMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(timeStr, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		Date nextMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(timeStr, 1), "yyyy-MM-dd hh:mm:ss");
		return flowDao.findFlowExitCountry(param, extiname, getMonthFirstDay, nextMonthFirstDay);
	}


	public List<Map<String, String>> findBusinessVisitKeyKpiDetails(String getTime, String timeType)
			throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>();
		Date starttime;
		Date endtime;
		List<Map<String, String>> lists = null;
		if("month".equals(timeType)){  //月粒度
			//获取当前月第一天时间
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			lists = flowDao.findBusinessVisitKeyKpiDetails(starttime, endtime);
		}else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			lists = flowDao.findBusinessVisitKeyKpiDetailsOfDay(starttime, endtime);
		}
		
		String id = "";
		int index = 0;
		for (Map<String, String> biz : lists) {
			if(!String.valueOf(biz.get("id")).equals(id) && !"".equals(id)){
				list.add(map);
				map = new HashMap<String, String>();
			}
			map.put("bizName", biz.get("name"));
			String val = biz.get("val") == null ? "\\" : Utils.valueOf(String.valueOf(biz.get("val")))+"%"/*+biz.get("unit")*/;
			String perc = biz.get("perc") == null ? "\\" : Utils.valueOf(String.valueOf(biz.get("perc")))+""+biz.get("unit");
			map.put(Common.flowNameMap.get(biz.get("type"))+"Value", val);
			map.put(Common.flowNameMap.get(biz.get("type"))+"Perc", perc);
			map.put("collecttime", String.valueOf(biz.get("collecttime")));
			id = String.valueOf(biz.get("id"));
			index ++;
			if(lists.size() == index){
				list.add(map);
			}
		}
		return list;
	}

	public List<Map<String, String>> findResourceProvideKeyKpiDetails(String getTime, String timeType)
			throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>();
		Date starttime;
		Date endtime;
		List<Map<String, String>> lists = null;
		if("month".equals(timeType)){  //月粒度
			//获取当前月第一天时间
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			lists = flowDao.findResourceProvideKeyKpiDetails(starttime, endtime);
		}else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			lists = flowDao.findResourceProvideKeyKpiDetailsOfDay(starttime, endtime);
		}
		
		for (Map<String, String> biz : lists) {
			map.put("bizName", biz.get("name"));
			map.put("idcValue", "\\");
			map.put("idcPerc", "\\");
			map.put("caCheValue", "\\");
			map.put("caChePerc", "\\");
			String val = biz.get("val") == null ? "\\" : Utils.valueOf(String.valueOf(biz.get("val")))+""+biz.get("unit");
			String perc = biz.get("perc") == null ? "\\" : Utils.valueOf(String.valueOf(biz.get("perc")))+""+biz.get("unit");
			map.put(Common.flowNameMap.get(biz.get("name"))+"Value", val);
			map.put(Common.flowNameMap.get(biz.get("name"))+"Perc", perc);
			map.put("collecttime", String.valueOf(biz.get("collecttime")));
			list.add(map);
			map = new HashMap<String, String>();
		}
		return list;
	}

	public List<Map<String, String>> findNetInOutResourceFlowDir(String getTime)
			throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> totalMap = new HashMap<String, String>();
		totalMap.put("bizName", "所有流向汇总");
		totalMap.put("sumFlag", "true");
		//获取当前月第一天时间
		Date getMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		Date nextMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		List<Map<String, String>> lists = flowDao.findNetInOutResourceFlowDir(getMonthFirstDay,nextMonthFirstDay);
		String id = "";
		int index = 0;
		for (Map<String, String> flow : lists) {
			if(!String.valueOf(flow.get("id")).equals(id) && !"".equals(id)){
				list.add(map);
				Iterator iterator = map.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(!"bizName".equals(entry.getKey())){
						String totalValue = totalMap.get(entry.getKey()) == null ? "0" : totalMap.get(entry.getKey());
						totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
						totalMap.put(entry.getKey(), totalValue);
						String mapValue = entry.getValue();
						if(entry.getKey().indexOf("perDayFlow") != -1){
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
						}else{
							mapValue = Utils.valueOf(entry.getValue())+"%";
						}
						map.put(entry.getKey(), mapValue);
					}
				}
				map = new HashMap<String, String>();
			}
			map.put("bizName", flow.get("name"));
			map.put(Common.flowNameMap.get(flow.get("type"))+"perDayFlow", String.valueOf(flow.get("val")));
			map.put(Common.flowNameMap.get(flow.get("type"))+"Perc", String.valueOf(flow.get("perc")));
//			map.put("collecttime", String.valueOf(flow.get("collecttime")));
			id = String.valueOf(flow.get("id"));
			index ++;
			if(lists.size() == index){
				list.add(map);
				Iterator iterator = map.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(!"bizName".equals(entry.getKey())){
						String totalValue = totalMap.get(entry.getKey()) == null ? "0" : totalMap.get(entry.getKey());
						totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
						String mapValue = entry.getValue();
						if(entry.getKey().indexOf("perDayFlow") != -1){
							totalMap.put(entry.getKey(), Utils.valueOf(totalValue,"#,###.00"));
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
						}else{
							totalMap.put(entry.getKey(), Utils.valueOf(totalValue)+"%");
							mapValue = Utils.valueOf(entry.getValue())+"%";
						}
						map.put(entry.getKey(), mapValue);
					}
				}
				list.add(0, totalMap);
			}
		}
		
		return list;
	}

	public List<Map<String, String>> findNetInOtherProvinceIDCFlowDir(String getTime)
			throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> totalMap = new HashMap<String, String>();
		totalMap.put("bizName", "所有流向汇总");
		totalMap.put("sumFlag", "true");
		//获取当前月第一天时间
		Date getMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		Date nextMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		List<Map<String, String>> lists = flowDao.findNetInOtherProvinceIDCFlowDir(getMonthFirstDay,nextMonthFirstDay);
		String id = "";
		int index = 0;
		for (Map<String, String> flow : lists) {
			if(!String.valueOf(flow.get("id")).equals(id) && !"".equals(id)){
				list.add(map);
				Iterator iterator = map.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(!"bizName".equals(entry.getKey())){
						String totalValue = totalMap.get(entry.getKey()) == null ? "0" : totalMap.get(entry.getKey());
						totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
						totalMap.put(entry.getKey(), totalValue);
						String mapValue = entry.getValue();
						if(entry.getKey().indexOf("perDayFlow") != -1){
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
						}else{
							mapValue = Utils.valueOf(entry.getValue())+"%";
						}
						map.put(entry.getKey(), mapValue);
					}
				}
				map = new HashMap<String, String>();
			}
			map.put("bizName", flow.get("name"));
			map.put(Common.flowNameMap.get(flow.get("type"))+"perDayFlow", String.valueOf(flow.get("val")));
			map.put(Common.flowNameMap.get(flow.get("type"))+"Perc", String.valueOf(flow.get("perc")));
//			map.put("collecttime", String.valueOf(flow.get("collecttime")));
			id = String.valueOf(flow.get("id"));
			index ++;
			if(lists.size() == index){
				list.add(map);
				Iterator iterator = map.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(!"bizName".equals(entry.getKey())){
						String totalValue = totalMap.get(entry.getKey()) == null ? "0" : totalMap.get(entry.getKey());
						totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
						String mapValue = entry.getValue();
						if(entry.getKey().indexOf("perDayFlow") != -1){
							totalMap.put(entry.getKey(), Utils.valueOf(totalValue,"#,###.00"));
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
						}else{
							totalMap.put(entry.getKey(), Utils.valueOf(totalValue)+"%");
							mapValue = Utils.valueOf(entry.getValue())+"%";
						}
						map.put(entry.getKey(), mapValue);
					}
				}
				list.add(0, totalMap);
			}
		}
		
		return list;
	}

	public List<Map<String, String>> findResourceNodeFlowDir(String getTime) throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> totalMap = new HashMap<String, String>();
		totalMap.put("parentName", "所有流向汇总");
		totalMap.put("childName", "");
		totalMap.put("sumFlag", "true");
		//获取当前月第一天时间
		Date getMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		Date nextMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		List<Map<String, String>> lists = flowDao.findResourceNodeFlowDir(getMonthFirstDay,nextMonthFirstDay);
		String id = "";
		int index = 0;
		for (Map<String, String> flow : lists) {
			if(!String.valueOf(flow.get("id")).equals(id) && !"".equals(id)){
				list.add(map);
				Iterator iterator = map.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(entry.getKey().indexOf("Name") == -1){
						String totalValue = totalMap.get(entry.getKey()) == null ? "0" : totalMap.get(entry.getKey());
						totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
						totalMap.put(entry.getKey(), totalValue);
						String mapValue = entry.getValue();
						if(entry.getKey().indexOf("perDayFlow") != -1){
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
						}else{
							mapValue = Utils.valueOf(entry.getValue())+"%";
						}
						map.put(entry.getKey(), mapValue);
					}
				}
				map = new HashMap<String, String>();
			}
			map.put("parentName", flow.get("parentname"));
			map.put("childName", flow.get("childname"));
			map.put(Common.flowNameMap.get(flow.get("type"))+"perDayFlow", String.valueOf(flow.get("val")));
			map.put(Common.flowNameMap.get(flow.get("type"))+"Perc", String.valueOf(flow.get("perc")));
//			map.put("collecttime", String.valueOf(flow.get("collecttime")));
			id = String.valueOf(flow.get("id"));
			index ++;
			if(lists.size() == index){
				list.add(map);
				Iterator iterator = map.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(entry.getKey().indexOf("Name") == -1){
						String totalValue = totalMap.get(entry.getKey()) == null ? "0" : totalMap.get(entry.getKey());
						totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
						String mapValue = entry.getValue();
						if(entry.getKey().indexOf("perDayFlow") != -1){
							totalMap.put(entry.getKey(), Utils.valueOf(totalValue,"#,###.00"));
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
						}else{
							totalMap.put(entry.getKey(), Utils.valueOf(totalValue)+"%");
							mapValue = Utils.valueOf(entry.getValue())+"%";
						}
						map.put(entry.getKey(), mapValue);
					}
				}
				list.add(0, totalMap);
			}
		}
		
		return list;
	}

	public List<Map<String, String>> findOtherProvinceIDCFlow(String getTime/*,String timeType*/)
			throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> totalMap = new HashMap<String, String>();
		totalMap.put("bizName", "所有流向汇总");
		totalMap.put("sumFlag", "true");
		Date start = null ;
		Date end = null;
		/*if(timeType.equals("month")) {*/
			//获取当前月第一天时间
			start = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			end = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		/*} else if(timeType.equals("day")) {
			start = DateUtil.StringToDate(getTime,"yyyy-MM-dd");
			end = new Date(start.getTime() + 24*60*60*1000);
		}*/
		
		
		List<Map<String, String>> lists = flowDao.findOtherProvinceIDCFlow(start,end/*,timeType*/);
		String id = "";
		int index = 0;
		for (Map<String, String> flow : lists) {
			if(!String.valueOf(flow.get("id")).equals(id) && !"".equals(id)){
				list.add(map);
				Iterator iterator = map.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(!"bizName".equals(entry.getKey())){
						String totalValue = totalMap.get(entry.getKey()) == null ? "0" : totalMap.get(entry.getKey());
						totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
						totalMap.put(entry.getKey(), totalValue);
						String mapValue = entry.getValue();
						if(entry.getKey().indexOf("perDayFlow") != -1){
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
						}else{
							mapValue = Utils.valueOf(entry.getValue())+"%";
						}
						map.put(entry.getKey(), mapValue);
					}
				}
				map = new HashMap<String, String>();
			}
			map.put("bizName", flow.get("name"));
			map.put(Common.flowNameMap.get(flow.get("type"))+"perDayFlow", String.valueOf(flow.get("val")));
			map.put(Common.flowNameMap.get(flow.get("type"))+"Perc", String.valueOf(flow.get("perc")));
//			map.put("collecttime", String.valueOf(flow.get("collecttime")));
			id = String.valueOf(flow.get("id"));
			index ++;
			if(lists.size() == index){
				list.add(map);
				Iterator iterator = map.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(!"bizName".equals(entry.getKey())){
						String totalValue = totalMap.get(entry.getKey()) == null ? "0" : totalMap.get(entry.getKey());
						totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
						String mapValue = entry.getValue();
						if(entry.getKey().indexOf("perDayFlow") != -1){
							totalMap.put(entry.getKey(), Utils.valueOf(totalValue,"#,###.00"));
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
						}else{
							totalMap.put(entry.getKey(), Utils.valueOf(totalValue)+"%");
							mapValue = Utils.valueOf(entry.getValue())+"%";
						}
						map.put(entry.getKey(), mapValue);
					}
				}
				list.add(0, totalMap);
			}
		}
		
		return list;
	}

	public List<Map<String, String>> findCityVisitCTCCAndCUCCFlow(String getTime, String timeType)
			throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> totalMap = new HashMap<String, String>();
		totalMap.put("bizName", "所有流向汇总");
		totalMap.put("sumFlag", "true");
		Date starttime;
		Date endtime;
		List<Map<String, String>> lists = null;
		if("month".equals(timeType)){  //月粒度
			//获取当前月第一天时间
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			lists = flowDao.findCityVisitCTCCAndCUCCFlow(starttime, endtime);
		}else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			lists = flowDao.findCityVisitCTCCAndCUCCFlowOfDay(starttime, endtime);
		}
		String id = "";
		int index = 0;
		for (Map<String, String> flow : lists) {
			if(!String.valueOf(flow.get("id")).equals(id) && !"".equals(id)){
				list.add(map);
				Iterator iterator = map.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(!"bizName".equals(entry.getKey())){
						String totalValue = totalMap.get(entry.getKey()) == null ? "0" : totalMap.get(entry.getKey());
						totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
						totalMap.put(entry.getKey(), totalValue);
						String mapValue = entry.getValue();
						/*
						if(entry.getKey().indexOf("sumFlow") != -1){
							mapValue = Utils.valueOf(String.valueOf(new BigDecimal(entry.getValue()).divide(new BigDecimal(days), 2,BigDecimal.ROUND_HALF_UP )),"#,###.00");
						}else{
							mapValue = Utils.valueOf(String.valueOf(new BigDecimal(entry.getValue()).divide(new BigDecimal(days), 2,BigDecimal.ROUND_HALF_UP )),"#,###.00")+"%";
						}
						*/
						
						if(entry.getKey().indexOf("sumFlow") != -1){
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
						}else{
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00")+"%";
						}
						map.put(entry.getKey(), mapValue);
					}
				}
				map = new HashMap<String, String>();
			}
			map.put("bizName", flow.get("name"));
			map.put(Common.flowNameMap.get(flow.get("type"))+"sumFlow", String.valueOf(flow.get("val")));
			map.put(Common.flowNameMap.get(flow.get("type"))+"Perc", String.valueOf(flow.get("perc")));
//			map.put("collecttime", String.valueOf(flow.get("collecttime")));
			id = String.valueOf(flow.get("id"));
			index ++;
			if(lists.size() == index){
				list.add(map);
				Iterator iterator = map.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(!"bizName".equals(entry.getKey())){
						String totalValue = totalMap.get(entry.getKey()) == null ? "0" : totalMap.get(entry.getKey());
						totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
						String mapValue = entry.getValue();
						/*
						if(entry.getKey().indexOf("sumFlow") != -1){
							totalMap.put(entry.getKey(), Utils.valueOf(String.valueOf(new BigDecimal(totalValue).divide(new BigDecimal(days), 2,BigDecimal.ROUND_HALF_UP )),"#,###.00"));
							mapValue = Utils.valueOf(String.valueOf(new BigDecimal(entry.getValue()).divide(new BigDecimal(days), 2,BigDecimal.ROUND_HALF_UP )),"#,###.00");
						}else{
							totalMap.put(entry.getKey(), Utils.valueOf(String.valueOf(new BigDecimal(totalValue).divide(new BigDecimal(days), 2,BigDecimal.ROUND_HALF_UP )),"#,###.00")+"%");
							mapValue = Utils.valueOf(String.valueOf(new BigDecimal(entry.getValue()).divide(new BigDecimal(days), 2,BigDecimal.ROUND_HALF_UP )),"#,###.00")+"%";
						}
						*/
						
						if(entry.getKey().indexOf("sumFlow") != -1){
							totalMap.put(entry.getKey(), Utils.valueOf(totalValue,"#,###.00"));
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
						}else{
							//totalMap.put(entry.getKey(), Utils.valueOf(totalValue,"#,###.00")+"%");
							totalMap.put(entry.getKey(), "100%");
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00")+"%";
						}
						map.put(entry.getKey(), mapValue);
					}
				}
				list.add(0, totalMap);
			}
		}
		
		return list;
	}

	/** 获取流量出口根节点  created by zhengWei */
	public FlowExitTree getExitRootNode() {
		FlowExitTree flowExitTree = flowDao.getExitRootNode();
		return flowExitTree;
	}
	
	/** 日均流量分析汇总 */
	public Page getTotalTreeTable(String getTime,String busiStr){
		Page page = new Page();
		page.setPageNo(1);
		page.setPageSize(50);
		List<FlowExitTree> list = getTreeTable(page, getTime, busiStr);
		List<Map<String, String>> totalList = new ArrayList<Map<String,String>>();
		Map<String, String> totalMap = new HashMap<String, String>();
		
		//获取当前月第一天
		String firstDay = DateUtil.DateFormatToString(DateUtil.getMonthFirstDay(getTime,0), "yyyy/MM/dd");
		//获取当前月最后一天
		String lastDay = DateUtil.getMonthLastDay(getTime);
		
		totalMap.put("collecttime", /*"所有汇总"+*/firstDay+" 至  "+lastDay);
		totalMap.put("sumFlag", "true");
		totalMap.put("name", "省网、铁通及第三方出口");
		
		int size = 0;
		String inflow = "0";
		String outflow = "0";
		String totalflow = "0";
		String totalflow_perc = "0";

		FlowExitTree fet;
		for(int i = 0;i < list.size();i++){
			fet = list.get(i);
			inflow = String.valueOf(new BigDecimal(fet.getInflow()).add(new BigDecimal(inflow)));	
			outflow = String.valueOf(new BigDecimal(fet.getOutflow()).add(new BigDecimal(outflow)));
			totalflow = String.valueOf(new BigDecimal(fet.getTotalflow()).add(new BigDecimal(totalflow)));
			totalflow_perc = String.valueOf(new BigDecimal(fet.getTotalflow_perc()).add(new BigDecimal(totalflow_perc)));
			
			if(list.get(i).getNumber() != null){
				size++;
			}
		}
		totalMap.put("inflow", Utils.valueOf(inflow,"#,###.00"));
		totalMap.put("outflow", Utils.valueOf(outflow ,"#,###.00"));
		totalMap.put("totalflow",  Utils.valueOf(totalflow ,"#,###.00"));
		totalMap.put("totalflow_perc", Utils.valueOf(String.valueOf(Double.valueOf(totalflow_perc)/size), "#,###.00"));
		
		totalList.add(totalMap);
		
		page = new Page();
		page.setRowCount(size);
		page.setResultList(totalList);
		
		return page;
	}
	
	/** 流量出口表格树形结构数据封装  created by zhengWei */
	public List<FlowExitTree> getTreeTable(Page page, String getTime, String busiStr) {
		Page pg = page;
		int startNum = (page.getPageNo()-1)*page.getPageSize();
		
		//获取当前月第一天时间
		Date getMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		Date nextMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		
		String tempTime = "", time;
		FlowExitTree exitDataTree = new FlowExitTree();
		List<FlowExitTree> list = new ArrayList<FlowExitTree>();
		
		List<Map<String, String>> tempTimeList = new ArrayList<Map<String, String>>();
		List<String> collecttimeList = new ArrayList<String>();
		
		//数据按天粒度分组，为每天的数据建立一棵树要以该map做临时缓存，key:每天时间，value:每天流量出口数据的HashMap
		Map<String, Map<String, FlowExitTree>> map = new TreeMap<String, Map<String, FlowExitTree>>();
		//树节点的临时存储，key:节点ID,value:每个树节点
		Map<String, FlowExitTree> tempMap = new HashMap<String, FlowExitTree>(list.size());
		//key:每天时间，value:已创建并已数据初始化完成的树
		Map<String, FlowExitTree> treeMap = new TreeMap<String, FlowExitTree>();
		List<FlowExitTree> treeList = new ArrayList<FlowExitTree>();
		int number = startNum;
		boolean numberFlag = true;
		
		//list = flowDao.getFlowExit();
		//流量出口数据按天分组（自动调用拦截器进行分页）
		tempTimeList = flowDao.getFlowExitPage(page, getMonthFirstDay, nextMonthFirstDay);
		for (Map<String, String> m: tempTimeList) {
			collecttimeList.add(m.get("collecttime"));
		}
		
		//按分组后的日期进行树形展示
		list = flowDao.getFlowExit(collecttimeList,busiStr);
	
		for (FlowExitTree flowExitTree: list) {
			time = flowExitTree.getCollecttime();
			if (!tempTime.equals(time)) {
				tempMap = new HashMap<String, FlowExitTree>(list.size());
				FlowExitTree rootNode = getExitRootNode();
				tempMap.put(rootNode.getId(), rootNode);
				tempMap.put(flowExitTree.getId(), flowExitTree);
			}
			
			if (map.containsKey(time)) {
				map.get(time).put(flowExitTree.getId(), flowExitTree);
			} else {
				map.put(time, tempMap);
			}
			tempTime = time;	
		}
		
		for (Map.Entry<String,  Map<String, FlowExitTree>> entry : map.entrySet()) {
			exitDataTree = FlowTree.buildFlowExitTree(entry.getValue());
			treeMap.put(entry.getKey(), exitDataTree);
		}
		
		for (Map.Entry<String,  FlowExitTree> entry : treeMap.entrySet()) {
			numberFlag = true;
			number++;
			List<FlowExitTree> subTree = entry.getValue().getSubTree();
			for (FlowExitTree tempTree: subTree) {
				if (numberFlag) {
					tempTree.setNumber(String.valueOf(number));
					numberFlag = false;
				}
				treeList.add(tempTree);
			}
		}
		return treeList;
	}
	
//	/** 测试分页 */
//	public List<Map<String, String>> getFlowExitPage(Page page, String objectType) {
//		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//		list = flowDao.getFlowExitPage(page, objectType);
//		return list;
//	}
	
	
	/**
	 * 业务访问流向以及资源访问流向日均流量及占比分析
	 */
	public List<Tree> getFlowTable(String flowtype, String brtype,
			String labeltype, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception {
		return flowDao.getFlowTable(flowtype, brtype, labeltype, getMonthFirstDay, nextMonthFirstDay);
	}
	
	/**
	 * 业务访问流向以及资源访问流向日均流量及占比分析(天粒度)
	 */
	public List<Tree> getFlowTableOfDay(String flowtype, String brtype,
			String labeltype, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception {
		return flowDao.getFlowTableOfDay(flowtype, brtype, labeltype, getMonthFirstDay, nextMonthFirstDay);
	}

	
	public List<Map<String, String>> findBusiVisitResFlowIngredientForm(String getTime, String timeType)
			throws Exception {
		String[] ingIdx = {"视频(含P2P类型)","HTTP及其他","P2P下载"};
		Date starttime;
	    Date endtime;
	    List<Map<String, String>> lists = null;
	    List<Map<String, String>> lists_last = null;
	    if("month".equals(timeType)){  //月粒度
	      //获取当前月第一天时间
	      starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
	      //获取下个月第一天时间
	      endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
	      lists = flowDao.findBusiVisitResFlowIngredientForm(starttime, endtime);
	      lists_last = flowDao.findBusiVisitResFlowIngredientForm(DateUtil.getLastDate(starttime,timeType), starttime);
	    }else{   //天粒度
	      //获取当天开始时间
	      starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
	      //获取当天结束时间
	      endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
	      lists = flowDao.findBusiVisitResFlowIngredientFormOfDay(starttime, endtime);
	      lists_last = flowDao.findBusiVisitResFlowIngredientFormOfDay(DateUtil.getLastDate(starttime,timeType), DateUtil.getLastDate(endtime,timeType));
	    }
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		//Map<String, String> netMap = null;//所有
		Map<String, String> netInMap = null;//网内他省
		Map<String, String> netOutMap = null;//网外
		Map<String, String> netOtherMap = null;//其他
		Map<String, String> netTotalMap = new HashMap<String, String>();//所有汇总
		Map<String, String> netOtherTotalMap = new HashMap<String, String>();//其他汇总
		netTotalMap.put("bizName", "所有流向汇总");
		netTotalMap.put("childName", "");
		netTotalMap.put("sumFlag", "true");
		netOtherTotalMap.put("bizName", "其他");
		netOtherTotalMap.put("childName", "");
		netOtherTotalMap.put("sumFlag", "true");
		Map<String, String> netInTotalMap = new HashMap<String, String>();//网内他省汇总
		Map<String, String> netOutTotalMap = new HashMap<String, String>();//网外汇总
		
		netInTotalMap.put("bizName", "网内他省");
		netInTotalMap.put("childName", "");
		netInTotalMap.put("sumFlag", "true");
		netOutTotalMap.put("bizName", "网外（电信、联通、海外）");
		netOutTotalMap.put("childName", "");
		netOutTotalMap.put("sumFlag", "true");
		for(String bizType : ingIdx){
			netInMap = new HashMap<String, String>();
			for (Map<String, String> biz : lists) {
				if(bizType.equals(biz.get("name"))){
					if(biz.get("childname") != null){
						if(biz.get("childname").indexOf("网内") != -1){
							if(bizType.indexOf("视频") != -1){
								netInMap.put("childName", biz.get("childname"));
							}else{
								netInMap.put("childName", "");
							}
							netInMap.put("bizName", biz.get("name"));
							netInMap.put(Common.flowNameMap.get(biz.get("parentname"))+"perDayFlow", String.valueOf(biz.get("val")));
							netInMap.put(Common.flowNameMap.get(biz.get("parentname"))+"Perc", String.valueOf(biz.get("perc")));
						}
					}
				}
			}
			if(netInMap.get("bizName") != null){
				list.add(netInMap);
				Iterator iterator = netInMap.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(entry.getKey().indexOf("Name") == -1){
						String totalValue = netInTotalMap.get(entry.getKey()) == null ? "0" : netInTotalMap.get(entry.getKey());
						totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
						netInTotalMap.put(entry.getKey(), totalValue);
						String mapValue = entry.getValue();
						if(entry.getKey().indexOf("perDayFlow") != -1){
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
							if(bizType.indexOf("P2P下载") != -1){
								netInTotalMap.put(entry.getKey(), Utils.valueOf(totalValue,"#,###.00"));
							}
						}else{
							mapValue = Utils.valueOf(entry.getValue())+"%";
							if(bizType.indexOf("P2P下载") != -1){
								netInTotalMap.put(entry.getKey(), Utils.valueOf(totalValue)+"%");
							}
						}
						netInMap.put(entry.getKey(), mapValue);
					}
				}
			}
		}
		for(String bizType : ingIdx){
			netOutMap = new HashMap<String, String>();
			for (Map<String, String> biz : lists) {
				if(bizType.equals(biz.get("name"))){
					if(biz.get("childname") != null){
						if(biz.get("childname").indexOf("网外") != -1){
							if(bizType.indexOf("视频") != -1){
								netOutMap.put("childName", biz.get("childname")+"（电信、联通、海外）");
							}else{
								netOutMap.put("childName", "");
							}
							netOutMap.put("bizName", biz.get("name"));
							netOutMap.put(Common.flowNameMap.get(biz.get("parentname"))+"perDayFlow", String.valueOf(biz.get("val")));
							netOutMap.put(Common.flowNameMap.get(biz.get("parentname"))+"Perc", String.valueOf(biz.get("perc")));
							
						}
					}
				}
			}
			if(netOutMap.get("bizName") != null){
				list.add(netOutMap);
				Iterator iterator = netOutMap.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(entry.getKey().indexOf("Name") == -1){
						String totalValue = netOutTotalMap.get(entry.getKey()) == null ? "0" : netOutTotalMap.get(entry.getKey());
						totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
						netOutTotalMap.put(entry.getKey(), totalValue);
						String mapValue = entry.getValue();
						if(entry.getKey().indexOf("perDayFlow") != -1){
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
							if(bizType.indexOf("P2P下载") != -1){
								netOutTotalMap.put(entry.getKey(), Utils.valueOf(totalValue,"#,###.00"));
							}
						}else{
							mapValue = Utils.valueOf(entry.getValue())+"%";
							if(bizType.indexOf("P2P下载") != -1){
								netOutTotalMap.put(entry.getKey(), Utils.valueOf(totalValue)+"%");
							}
						}
						netOutMap.put(entry.getKey(), mapValue);
					}
				}
			}
		}
		
		for(String bizType : ingIdx){
			netOtherMap = new HashMap<String, String>();
			for (Map<String, String> biz : lists) {
				if(bizType.equals(biz.get("name"))){
					if(biz.get("childname") != null){
						if(biz.get("childname").indexOf("其他") != -1){
							if(bizType.indexOf("视频") != -1){
								netOtherMap.put("childName", biz.get("childname")+"");
							}else{
								netOtherMap.put("childName", "");
							}
							netOtherMap.put("bizName", biz.get("name"));
							netOtherMap.put(Common.flowNameMap.get(biz.get("parentname"))+"perDayFlow", String.valueOf(biz.get("val")));
							netOtherMap.put(Common.flowNameMap.get(biz.get("parentname"))+"Perc", String.valueOf(biz.get("perc")));
							
						}
					}
				}
			}
			if(netOtherMap.get("bizName") != null){
				list.add(netOtherMap);
				Iterator iterator = netOtherMap.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(entry.getKey().indexOf("Name") == -1){
						String totalValue = netOtherTotalMap.get(entry.getKey()) == null ? "0" : netOtherTotalMap.get(entry.getKey());
						totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
						netOtherTotalMap.put(entry.getKey(), totalValue);
						String mapValue = entry.getValue();
						if(entry.getKey().indexOf("perDayFlow") != -1){
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
							if(bizType.indexOf("P2P下载") != -1){
								netOtherTotalMap.put(entry.getKey(), Utils.valueOf(totalValue,"#,###.00"));
							}
						}else{
							mapValue = Utils.valueOf(entry.getValue())+"%";
							if(bizType.indexOf("P2P下载") != -1){
								netOtherTotalMap.put(entry.getKey(), Utils.valueOf(totalValue)+"%");
							}
						}
						netOtherMap.put(entry.getKey(), mapValue);
					}
				}
			}
		}
		double netTotalBusiVisitperDayFlow = getValueForKey("busiVisitperDayFlow",netInTotalMap)+getValueForKey("busiVisitperDayFlow",netOutTotalMap)+getValueForKey("busiVisitperDayFlow",netOtherTotalMap);
		double netTotalIdcperDayFlow = getValueForKey("idcperDayFlow",netInTotalMap)+getValueForKey("idcperDayFlow",netOutTotalMap)+getValueForKey("idcperDayFlow",netOtherTotalMap);
		
		//求上一时期的流量数
		Map<String, Double> lastValue = getLastValue(lists_last, ingIdx);
		double IdcFlow = (lastValue.get("IDC网内") == null ? 0:lastValue.get("IDC网内").doubleValue())+(lastValue.get("IDC网外")== null ? 0:lastValue.get("IDC网外").doubleValue())+(lastValue.get("IDC其他")== null ? 0:lastValue.get("IDC其他").doubleValue());
		double quanbuFlow = (lastValue.get("全部网内")== null ? 0:lastValue.get("全部网内").doubleValue())+(lastValue.get("全部网外")== null ? 0:lastValue.get("全部网外").doubleValue())+(lastValue.get("全部其他")== null ? 0:lastValue.get("全部其他").doubleValue());
		
		netInTotalMap.put("busiVisitPerc",lastValue.get("全部网内") == null ? "0%" : Utils.valueOf(Utils.div(getValueForKey("busiVisitperDayFlow",netInTotalMap)*100, lastValue.get("全部网内").doubleValue(), 2)-100)+"%");
		netOutTotalMap.put("busiVisitPerc", lastValue.get("全部网外")== null ? "0%" : Utils.valueOf(Utils.div(getValueForKey("busiVisitperDayFlow",netOutTotalMap)*100, lastValue.get("全部网外").doubleValue(), 2)-100)+"%");
		netOtherTotalMap.put("busiVisitPerc", lastValue.get("全部其他")== null ? "0%" : Utils.valueOf(Utils.div(getValueForKey("busiVisitperDayFlow",netOtherTotalMap)*100, lastValue.get("全部其他").doubleValue(), 2)-100)+"%");
		netInTotalMap.put("idcPerc", lastValue.get("IDC网内")== null ? "0%" :Utils.valueOf( Utils.div(getValueForKey("idcperDayFlow",netInTotalMap)*100, lastValue.get("IDC网内").doubleValue(), 2)-100)+"%");
		netOutTotalMap.put("idcPerc", lastValue.get("IDC网外")== null ? "0%" : Utils.valueOf(Utils.div(getValueForKey("idcperDayFlow",netOutTotalMap)*100, lastValue.get("IDC网外").doubleValue(), 2)-100)+"%");
		if(getValueForKey("idcperDayFlow",netOtherTotalMap) > 0) {
			netOtherTotalMap.put("idcPerc", lastValue.get("IDC其他") == null ? "0%" : Utils.valueOf(Utils.div(getValueForKey("idcperDayFlow",netOtherTotalMap)*100, lastValue.get("IDC其他").doubleValue(), 2)-100)+"%");
		}
		if(netInTotalMap.get("busiVisitperDayFlow") != null){
			list.add(0,netInTotalMap);
		}
		if(netOutTotalMap.get("busiVisitperDayFlow") != null){
			list.add(4,netOutTotalMap);
		}
		if(netOtherTotalMap.get("busiVisitperDayFlow") != null){
			list.add(8,netOtherTotalMap);
		}
		DecimalFormat df = new DecimalFormat("#,###.00"); 

		netTotalMap.put("busiVisitperDayFlow", df.format(netTotalBusiVisitperDayFlow));
		netTotalMap.put("idcperDayFlow", df.format(netTotalIdcperDayFlow));
		netTotalMap.put("busiVisitPerc", Utils.valueOf(Utils.div(netTotalBusiVisitperDayFlow*100, quanbuFlow, 2)-100)+"%");
		netTotalMap.put("idcPerc", Utils.valueOf(Utils.div(netTotalIdcperDayFlow*100, IdcFlow, 2)-100)+"%");
		list.add(0,netTotalMap);
		return list;
	}

	private void putResultMap(String str,Map<String, String> map,Map<String, Double> resultMap){
		String childName = map.get("childname");
		if(childName != null && childName.indexOf(str) != -1) {
			String _str = map.get("parentname")+str;
			Double val = Double.valueOf(String.valueOf(map.get("val")));
			if(resultMap.get(_str) == null) {
				resultMap.put(_str, val);
			} else {
				resultMap.put(_str, val+resultMap.get(_str));
			}
			
		}
	}
	private Map<String, Double> getLastValue(List<Map<String, String>> lists_last,String[] ingIdx){
		Map<String, Double> resultMap = new HashMap<String,Double>();
		for(Map<String, String> map:lists_last){
			//String[] ingIdx = {"视频(含P2P类型)","HTTP及其他","P2P下载"};
			for (String str : ingIdx) {
				if(map.get("name").equals(str)){
					
					/*String childName = map.get("childname");
					if(childName != null && childName.indexOf("网内") != -1) {
						String _in = Common.flowNameMap.get(map.get("parentname"))+"_in";
						Double val = Double.valueOf(String.valueOf(map.get("val")));
						if(resultMap.get(_in) == null) {
							resultMap.put(_in, val);
						} else {
							resultMap.put(_in, val+resultMap.get(_in));
						}
						
					}*/
					putResultMap("网内",map,resultMap);
					putResultMap("网外",map,resultMap);
					putResultMap("其他",map,resultMap);
				}
			}
			
		}
		return resultMap;
	}
	private double getValueForKey(String key,Map map) throws ParseException{
		return Utils.getDoubleFromString(map.get(key) == null ? null :map.get(key).toString(), "#,###.00");
	}

	public List<Map<String, String>> findBusiVisitIngredientFormInfo(String getTime, String timeType)
			throws Exception {
		String[] ingIdx = {"视频(含P2P类型)","HTTP及其他","P2P下载"};
		Date starttime;
	    Date endtime;
	    List<Map<String, String>> lists = null;
	    if("month".equals(timeType)){  //月粒度
	       //获取当前月第一天时间
	       starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
	       //获取下个月第一天时间
	       endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
	       lists = flowDao.findBusiVisitIngredientFormInfo(starttime, endtime);
	    }else{   //天粒度
	       //获取当天开始时间
	       starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
	       //获取当天结束时间
	       endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
	       lists = flowDao.findBusiVisitIngredientFormInfoOfDay(starttime, endtime);
	    }
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> netInMap = null;//网内他省
		Map<String, String> groupExitMap = null;//网外(集团出口)
		Map<String, String> provinceExitMap = null;//网外(省级出口)
		Map<String, String> netInTotalMap = new HashMap<String, String>();//网内他省汇总
		Map<String, String> groupExitTotalMap = new HashMap<String, String>();//网外(集团出口)汇总
		Map<String, String> provinceExitTotalMap = new HashMap<String, String>();//网外(省级出口)汇总
		netInTotalMap.put("bizName", "网内(兄弟公司)所有成分汇总");
		netInTotalMap.put("childName", "");
		netInTotalMap.put("sumFlag", "true");
		groupExitTotalMap.put("bizName", "网外(集团出口)所有成分汇总");
		groupExitTotalMap.put("childName", "");
		groupExitTotalMap.put("sumFlag", "true");
		provinceExitTotalMap.put("bizName", "网外(省级出口)所有成分汇总");
		provinceExitTotalMap.put("childName", "");
		provinceExitTotalMap.put("sumFlag", "true");
		for(String bizType : ingIdx){//网内(兄弟公司)
			netInMap = new HashMap<String, String>();
			for (Map<String, String> biz : lists) {
				if(bizType.equals(biz.get("name"))){
					if(biz.get("childname") != null){
						if(biz.get("childname").indexOf("网内") != -1){
							if(bizType.indexOf("视频") != -1){
								netInMap.put("childName", "网内(兄弟公司)");
							}else{
								netInMap.put("childName", "");
							}
							netInMap.put("bizName", biz.get("name"));
							netInMap.put(Common.flowNameMap.get(biz.get("parentname"))+"perDayFlow", String.valueOf(biz.get("val")));
							netInMap.put(Common.flowNameMap.get(biz.get("parentname"))+"Perc", String.valueOf(biz.get("perc")));
						}
					}
				}
			}
			if(netInMap.get("bizName") != null){
				list.add(netInMap);
				Iterator iterator = netInMap.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(entry.getKey().indexOf("Name") == -1){
						String totalValue = netInTotalMap.get(entry.getKey()) == null ? "0" : netInTotalMap.get(entry.getKey());
						totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
						netInTotalMap.put(entry.getKey(), totalValue);
						String mapValue = entry.getValue();
						if(entry.getKey().indexOf("perDayFlow") != -1){
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
							if(bizType.indexOf("P2P下载") != -1){
								netInTotalMap.put(entry.getKey(), Utils.valueOf(totalValue,"#,###.00"));
							}
						}else{
							mapValue = Utils.valueOf(entry.getValue())+"%";
							if(bizType.indexOf("P2P下载") != -1){
								netInTotalMap.put(entry.getKey(), Utils.valueOf(totalValue)+"%");
							}
						}
						netInMap.put(entry.getKey(), mapValue);
					}
				}
			}
		}
		for(String bizType : ingIdx){//网外(集团出口)
			groupExitMap = new HashMap<String, String>();
			for (Map<String, String> biz : lists) {
				if(bizType.equals(biz.get("name"))){
					if(biz.get("childname") != null){
						if(biz.get("childname").indexOf("集团") != -1){
							if(bizType.indexOf("视频") != -1){
								groupExitMap.put("childName", "网外");
							}else{
								groupExitMap.put("childName", "");
							}
							groupExitMap.put("bizName", biz.get("name"));
							groupExitMap.put(Common.flowNameMap.get(biz.get("parentname"))+"perDayFlow", String.valueOf(biz.get("val")));
							groupExitMap.put(Common.flowNameMap.get(biz.get("parentname"))+"Perc", String.valueOf(biz.get("perc")));
							
						}
					}
				}
			}
			if(groupExitMap.get("bizName") != null){
				list.add(groupExitMap);
				Iterator iterator = groupExitMap.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(entry.getKey().indexOf("Name") == -1){
						String totalValue = groupExitTotalMap.get(entry.getKey()) == null ? "0" : groupExitTotalMap.get(entry.getKey());
						totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
						groupExitTotalMap.put(entry.getKey(), totalValue);
						String mapValue = entry.getValue();
						if(entry.getKey().indexOf("perDayFlow") != -1){
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
							if(bizType.indexOf("P2P下载") != -1){
								groupExitTotalMap.put(entry.getKey(), Utils.valueOf(totalValue,"#,###.00"));
							}
						}else{
							mapValue = Utils.valueOf(entry.getValue())+"%";
							if(bizType.indexOf("P2P下载") != -1){
								groupExitTotalMap.put(entry.getKey(), Utils.valueOf(totalValue)+"%");
							}
						}
						groupExitMap.put(entry.getKey(), mapValue);
					}
				}
			}
		}
		
		for(String bizType : ingIdx){//网外(省级出口)
			provinceExitMap = new HashMap<String, String>();
			for (Map<String, String> biz : lists) {
				if(bizType.equals(biz.get("name"))){
					if(biz.get("childname") != null && biz.get("childname").indexOf("省级") != -1){
						if(bizType.indexOf("视频") != -1){
							provinceExitMap.put("childName", "网外(省级出口)");
						}else{
							provinceExitMap.put("childName", "");
						}
						provinceExitMap.put("bizName", biz.get("name"));
						provinceExitMap.put(Common.flowNameMap.get(biz.get("parentname"))+"perDayFlow", String.valueOf(biz.get("val")));
						provinceExitMap.put(Common.flowNameMap.get(biz.get("parentname"))+"Perc", String.valueOf(biz.get("perc")));
						
					}
				}
			}
			if(provinceExitMap.get("bizName") != null){
				list.add(provinceExitMap);
				Iterator iterator = provinceExitMap.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(entry.getKey().indexOf("Name") == -1){
						String totalValue = provinceExitTotalMap.get(entry.getKey()) == null ? "0" : provinceExitTotalMap.get(entry.getKey());
						totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
						provinceExitTotalMap.put(entry.getKey(), totalValue);
						String mapValue = entry.getValue();
						if(entry.getKey().indexOf("perDayFlow") != -1){
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
							if(bizType.indexOf("P2P下载") != -1){
								provinceExitTotalMap.put(entry.getKey(), Utils.valueOf(totalValue,"#,###.00"));
							}
						}else{
							mapValue = Utils.valueOf(entry.getValue())+"%";
							if(bizType.indexOf("P2P下载") != -1){
								provinceExitTotalMap.put(entry.getKey(), Utils.valueOf(totalValue)+"%");
							}
						}
						provinceExitMap.put(entry.getKey(), mapValue);
					}
				}
			}
		}
		
		if(netInTotalMap.get("cmnetperDayFlow") != null){
			list.add(0,netInTotalMap);
		}
		if(groupExitTotalMap.get("cmnetperDayFlow") != null){
			list.add(4,groupExitTotalMap);
		}
		if(provinceExitTotalMap.get("cmnetperDayFlow") != null){
			list.add(8,provinceExitTotalMap);
		}
		return list;
	}


	public List<Map<String, String>> findResourceNodeIngredientInfo(String getTime, String timeType)
			throws Exception {
		String[] ingIdx = {"视频(含P2P类型)","HTTP及其他","P2P下载"};
		Date starttime;
        Date endtime;
        List<Map<String, String>> lists = null;
        List<Map<String, String>> lists_last = null;
        if("month".equals(timeType)){  //月粒度
          //获取当前月第一天时间
          starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
          //获取下个月第一天时间
          endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
          lists = flowDao.findResourceNodeIngredientInfo(starttime, endtime);
          lists_last = flowDao.findResourceNodeIngredientInfo(DateUtil.getLastDate(starttime,timeType), starttime);
        }else{   //天粒度
          //获取当天开始时间
          starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
          //获取当天结束时间
          endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
          lists = flowDao.findResourceNodeIngredientInfoOfDay(starttime, endtime);
          lists_last = flowDao.findResourceNodeIngredientInfoOfDay(DateUtil.getLastDate(starttime,timeType), DateUtil.getLastDate(endtime,timeType));
        }
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> netInMap = null;//网内
		Map<String, String> netOutMap = null;//网外
		Map<String, String> netTotalMap = new HashMap<String, String>();//所有成分汇总
		netTotalMap.put("bizName", "所有流向所有成分汇总");
		netTotalMap.put("childName", "");
		netTotalMap.put("sumFlag", "true");
		Map<String, String> netInTotalMap = new HashMap<String, String>();//网内所有成分汇总
		BigDecimal netIn = new BigDecimal(0);
		Map<String, String> netOutTotalMap = new HashMap<String, String>();//网外所有成分汇总
		BigDecimal netOut = new BigDecimal(0);
		netInTotalMap.put("bizName", "网内");
		netInTotalMap.put("childName", "");
		netInTotalMap.put("sumFlag", "true");
		netOutTotalMap.put("bizName", "网外");
		netOutTotalMap.put("childName", "");
		netOutTotalMap.put("sumFlag", "true");
		for(String bizType : ingIdx){//网内资源提供流向
			netInMap = new HashMap<String, String>();
			for (Map<String, String> biz : lists) {
				if(bizType.equals(biz.get("name"))){
					if(biz.get("childname") != null){
						if(biz.get("childname").indexOf("网内") != -1){
							if(bizType.indexOf("视频") != -1){
								netInMap.put("childName", biz.get("childname"));
							}else{
								netInMap.put("childName", "");
							}
							netInMap.put("bizName", biz.get("name"));
							netInMap.put(Common.flowNameMap.get(biz.get("parentname"))+"perDayFlow", String.valueOf(biz.get("val")));
							netIn = new  BigDecimal(String.valueOf(biz.get("val"))).add(netIn);
							netInMap.put(Common.flowNameMap.get(biz.get("parentname"))+"Perc", String.valueOf(biz.get("perc")));
						}
					}
				}
			}
			if(netInMap.get("bizName") != null){
				list.add(netInMap);
				Iterator iterator = netInMap.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(entry.getKey().indexOf("Name") == -1){
						String totalValue = netInTotalMap.get(entry.getKey()) == null ? "0" : netInTotalMap.get(entry.getKey());
						totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
						netInTotalMap.put(entry.getKey(), totalValue);
						String mapValue = entry.getValue();
						if(entry.getKey().indexOf("perDayFlow") != -1){
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
							if(bizType.indexOf("P2P下载") != -1){
								netInTotalMap.put(entry.getKey(), Utils.valueOf(totalValue,"#,###.00"));
							}
						}else{
							mapValue = Utils.valueOf(entry.getValue())+"%";
							if(bizType.indexOf("P2P下载") != -1){
								netInTotalMap.put(entry.getKey(), Utils.valueOf(totalValue)+"%");
							}
						}
						netInMap.put(entry.getKey(), mapValue);
					}
				}
			}
		}
		
		for(String bizType : ingIdx){//网外资源提供流向
			netOutMap = new HashMap<String, String>();
			for (Map<String, String> biz : lists) {
				if(bizType.equals(biz.get("name"))){
					if(biz.get("childname") != null){
						if(biz.get("childname").indexOf("网外") != -1){
							if(bizType.indexOf("视频") != -1){
								netOutMap.put("childName", biz.get("childname"));
							}else{
								netOutMap.put("childName", "");
							}
							netOutMap.put("bizName", biz.get("name"));
							netOutMap.put(Common.flowNameMap.get(biz.get("parentname"))+"perDayFlow", String.valueOf(biz.get("val")));
							netOut =new  BigDecimal(String.valueOf(biz.get("val"))).add(netOut);
							netOutMap.put(Common.flowNameMap.get(biz.get("parentname"))+"Perc", String.valueOf(biz.get("perc")));
						}
					}
				}
			}
			if(netOutMap.get("bizName") != null){
				list.add(netOutMap);
				Iterator iterator = netOutMap.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(entry.getKey().indexOf("Name") == -1){
						String totalValue = netOutTotalMap.get(entry.getKey()) == null ? "0" : netOutTotalMap.get(entry.getKey());
						totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
						netOutTotalMap.put(entry.getKey(), totalValue);
						String mapValue = entry.getValue();
						if(entry.getKey().indexOf("perDayFlow") != -1){
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
							if(bizType.indexOf("P2P下载") != -1){
								netOutTotalMap.put(entry.getKey(), Utils.valueOf(totalValue,"#,###.00"));
							}
						}else{
							mapValue = Utils.valueOf(entry.getValue())+"%";
							if(bizType.indexOf("P2P下载") != -1){
								netOutTotalMap.put(entry.getKey(), Utils.valueOf(totalValue)+"%");
							}
						}
						netOutMap.put(entry.getKey(), mapValue);
					}
				}
			}
		}
		
		
		//求上一时期的流量数
		Map<String, Double> lastValue = getLastValue(lists_last, ingIdx);
		netInTotalMap.put("idcPerc", lastValue.get("IDC网内") == null ? "0%" : Utils.valueOf(Utils.div(netIn.doubleValue()*100, lastValue.get("IDC网内").doubleValue(), 2)-100)+"%");
		netOutTotalMap.put("idcPerc", lastValue.get("IDC网外") == null ? "0%" : Utils.valueOf(Utils.div(netOut.doubleValue()*100, lastValue.get("IDC网外").doubleValue(), 2)-100)+"%");
		
		double IdcFlow = (lastValue.get("IDC网内") == null ? 0:lastValue.get("IDC网内").doubleValue())+(lastValue.get("IDC网外")== null ? 0:lastValue.get("IDC网外").doubleValue());

		
		netTotalMap.put("idcperDayFlow", String.valueOf(netOut.add(netIn)));
		netTotalMap.put("idcPerc", Utils.valueOf(Utils.div(netOut.add(netIn).doubleValue()*100, IdcFlow, 2)-100)+"%");
		
		if(netInTotalMap.get("idcperDayFlow") != null){
			list.add(0,netInTotalMap);
		}
		if(netOutTotalMap.get("idcperDayFlow") != null){
			list.add(4,netOutTotalMap);
		}
		list.add(0,netTotalMap);
		return list;
	}

	/** 创建流量树   */
	public Tree buildFlowTree(String flowtype, String brtype, String labeltype, String getTime, String timeType)
			throws Exception {
		Date starttime;
		Date endtime;
		List<Tree> list = null;
		if("month".equals(timeType)){  //月粒度
			//获取当前月第一天时间
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			list = getFlowTable(flowtype, brtype, labeltype, starttime, endtime);
		}else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			list = getFlowTableOfDay(flowtype, brtype, labeltype, starttime, endtime);
		}
		//临时map做树的列表存储, key是树的唯一id,value是树
		Map<String, Tree> treeMap = new HashMap<String, Tree>(list.size());
		for (Tree tempTree : list) {
			treeMap.put(tempTree.getId(), tempTree);
		}
		//做整棵树的封装
		Tree rootTree = FlowTree.buildTree(treeMap);
		return rootTree;	
	}

	/** 根据流量树创建拓补 */
	@Override
	public String createTopo(String flowtype, String brtype, String labeltype, String layout, String getTime, String timeType)
			throws Exception {
		String xml = "";
		//获取封装好的整棵树
		Tree rootTree = buildFlowTree(flowtype, brtype, labeltype, getTime, timeType);
		//获取树的最大层级
//		List<Tree> list = getFlowTable(flowtype, brtype, labeltype);
//		maxLevel = FlowTree.getMaxlevel(list);
		
		xml = FlowTree.createXmlWithLayout(rootTree, null, layout);
		return xml;	
	}
	//业务访问流量成分构成
	public List<FsChartVo> findFlowIngredient(String type1,String type2,String getTime, String timeType)throws Exception{
		Date starttime;
		Date endtime;
		List<FsChartVo> list = null;
		if("month".equals(timeType)){  //月粒度
			//获取当前月第一天时间
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			list = flowDao.findFlowIngredient(type1, type2, starttime, endtime);
		}else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			list = flowDao.findFlowIngredientOfDay(type1, type2, starttime, endtime);
		}
	    for (FsChartVo fsChartVo : list) {
	    	fsChartVo.setVal(Utils.valueOf(fsChartVo.getVal()));
	    	fsChartVo.setValue(Utils.valueOf(fsChartVo.getValue()));
		}
		return list;
	}
	
	//业务访问流量成分构成网内他省和网外对比
		public List<Map<String, String>> getFlowIngerdientByDirName(String type1,String type2,String getTime, String timeType)throws Exception{
			Date starttime;
			Date endtime;
			List<Map<String, String>> list = null;
			List<Map<String, String>> list1 = new ArrayList<Map<String,String>>();
			if("month".equals(timeType)){  //月粒度
				//获取当前月第一天时间
				starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
				//获取下个月第一天时间
				endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
				list = flowDao.getFlowIngerdientByDirName(type1, type2, starttime, endtime);
			}else{   //天粒度
				//获取当天开始时间
				starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
				//获取当天结束时间
				endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
				list = flowDao.getFlowIngerdientByDirNameOfDay(type1, type2, starttime, endtime);
			}
			Map<String, String> map1 = new HashMap<String, String>();
			double d1 = 0;
			double d2 = 0;
			double d3 = 0;
			double d4 = 0;
			double d5 = 0;
			double d6 = 0;
			String str;
			if (list.size()>0) {
				for (Map<String, String> map : list){
					str = String.valueOf(map.get("kpiName"));
					if (str.equals("HTTP及其他")||str.equals("P2P下载")||
							str.equals("视频(含P2P类型)")) {
						d5 = Double.valueOf(Utils.valueOf(String.valueOf(map.get("ww"))));
						d6 = Double.valueOf(Utils.valueOf(String.valueOf(map.get("wnts"))));
						d1 = d1 + Double.valueOf(Utils.valueOf(String.valueOf(map.get("ww"))));
						d3 = d3 + Double.valueOf(Utils.valueOf(String.valueOf(map.get("wnts"))));
//							map.put("wwPercent", Utils.valueOf(String.valueOf(map.get("ww")))+"%");
//							map.put("wntsPercent", Utils.valueOf(String.valueOf(map.get("wnts")))+"%");
						if ((d5+d6)!=0) {
							
							map.put("score1", Utils.valueOf(String.valueOf(d5/(d5+d6)*100)));
							map.put("score", Utils.valueOf(String.valueOf(d6/(d5+d6)*100)));
						}else{
							map.put("score1", Utils.valueOf(String.valueOf(0)));
							map.put("score", Utils.valueOf(String.valueOf(0)));
						}
						list1.add(map);
					}
					
					
				}
				
				if(d1==0){
					d2=0;
				}else{
					d2 = d1/(d1+d3)*100;
				}
				if (d3==0) {
					d4=0;
				} else {
					d4 = d3/(d1+d3)*100;
				}
				
				map1.put("kpiName", "全部");
				map1.put("ww", Utils.valueOf(d1));
				map1.put("score1", Utils.valueOf(d2));
				map1.put("wnts", Utils.valueOf(d3));
				map1.put("score", Utils.valueOf(d4));
				list1.add(map1);
			}
			
			return list1;
		}
	//流量性能分析--分页数据查询	
	public Page findFlowPropertyAnalyzeByPagination(Page page, String exitid, String getTime) throws Exception {
		Page retPage = new Page();
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		
		int startnum = ((page.getPageNo()-1)*page.getPageSize());
		//int endnum = (page.getPageNo()*page.getPageSize());
		//获取当前月第一天时间
		Date getMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		Date nextMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		
		List<Map<String, String>> lists = flowDao.findFlowPropertyAnalyzeByPagination(exitid, page.getPageNo(), page.getPageSize(), getMonthFirstDay, nextMonthFirstDay);
		HashMap<String, HashMap<String,String>> totalMap = new HashMap<String,  HashMap<String,String>>();
		//int id = startnum;//,index = 0,day = 0;
		String r = "0";
		HashMap<String, String> map ;
		for (Map<String, String> flow : lists) {
			r = String.valueOf(flow.get("r"));
			map = totalMap.get(r);
			if(map == null) {
				map  = new HashMap<String, String>();
				totalMap.put(r, map);
			}	
			
			map.put("id", r);
			map.put("time",flow.get("collecttime").toString());
			String key = Common.flowNameMap.get(flow.get("kpiname"));
			String mapValue = "";
			if(key.indexOf("Value") != -1){
				mapValue = String.valueOf(flow.get("val"));
				if(key.indexOf("UseRatio") != -1){
					mapValue = Utils.valueOf(mapValue)+"%";
				}else{
					mapValue = Utils.valueOf(mapValue,"#,###.00");
				}
			}
			map.put(key, mapValue);
		}	
		list = new ArrayList<HashMap<String, String>>(totalMap.values());
		Collections.sort(list, new Comparator<HashMap<String, String> >() {
				 
	            @Override
	            public int compare(HashMap<String, String>  o1, HashMap<String, String>  o2) {
	            	int id1 = Integer.parseInt(o1.get("id"));
	            	int id2 = Integer.parseInt(o2.get("id"));
	                if (id1< id2)
	                    return -1;
	                else if (id1 > id2)
	                    return 1;
	                else
	                    return 0;
	            }
        });
		retPage.setResultList(list);
		return retPage;
	}
	
	public Page findFlowPropertyAnalyze(String exitid,
			String getTime) throws Exception {
		Page page = new Page();
		List<Map<String, String>> totalList = new ArrayList<Map<String,String>>();
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> totalMap = new HashMap<String, String>();
		//获取当前月第一天
		String firstDay = DateUtil.DateFormatToString(DateUtil.getMonthFirstDay(getTime,0), "yyyy/MM/dd");
		//获取当前月最后一天
		String lastDay = DateUtil.getMonthLastDay(getTime);
//		totalMap.put("id", "0");
		totalMap.put("time", /*"所有汇总"+*/firstDay+" 至  "+lastDay);
		totalMap.put("sumFlag", "true");
		//获取当前月第一天时间
		Date getMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		Date nextMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		
		List<Map<String, String>> lists = flowDao.findFlowPropertyAnalyze(exitid, getMonthFirstDay, nextMonthFirstDay);
		int id = 1,index = 0,day = 0;
		for (Map<String, String> flow : lists) {
			if(flow.get("collecttime") != null){
				
				int getDay = DateUtil.getDay(String.valueOf(flow.get("collecttime")));
				if(getDay != day && day != 0){
					map.put("id", String.valueOf(id++));
					list.add(map);
					Iterator iterator = map.entrySet().iterator();
					while(iterator.hasNext()){
						Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
						if(entry.getKey().indexOf("Value") != -1){
							String totalValue = totalMap.get(entry.getKey()) == null ? "0" : totalMap.get(entry.getKey());
							totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
							totalMap.put(entry.getKey(), totalValue);
							/*String mapValue = entry.getValue();
						if(entry.getKey().indexOf("UseRatio") != -1){
							mapValue = Utils.valueOf(entry.getValue())+"%";
						}else{
							mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
						}
						map.put(entry.getKey(), mapValue);*/
						}
					}
					map = new HashMap<String, String>();
				}
				//map.put("time", DateUtil.DateFormatToString(String.valueOf(flow.get("collecttime")), "yyyy/MM/dd"));
				map.put(Common.flowNameMap.get(flow.get("kpiname")), String.valueOf(flow.get("val")));
				day = getDay;
				index ++;
				if(index == lists.size()){
					map.put("id", String.valueOf(id++));
					list.add(map);
					Iterator iterator = map.entrySet().iterator();
					while(iterator.hasNext()){
						Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
						if(entry.getKey().indexOf("Value") != -1){
							String totalValue = totalMap.get(entry.getKey()) == null ? "0" : totalMap.get(entry.getKey());
							totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
							//String mapValue = entry.getValue();
							double avgValue = Double.valueOf(totalValue) / Double.valueOf(String.valueOf(map.get("id")));
							if(entry.getKey().indexOf("UseRatio") != -1){
								totalMap.put(entry.getKey(), Utils.valueOf(String.valueOf(avgValue))+"%");
								//mapValue = Utils.valueOf(entry.getValue())+"%";
							}else{
								totalMap.put(entry.getKey(), Utils.valueOf(String.valueOf(avgValue),"#,###.00"));
								//mapValue = Utils.valueOf(entry.getValue(),"#,###.00");
							}
							//map.put(entry.getKey(), mapValue);
						}
					}
					totalList.add(totalMap);
					page.setResultList(totalList);   //汇总数据
					page.setRowCount(list.size());  //总长度
				}
			}
		}
		return page;
	}
	//不同访问类型的流量成分构成(饼图) 

	public List<FsChartVo> getFlowIngerdientOhtChart(String ingid, String getTime,String timeType)throws Exception{
		
		Date starttime;
		Date endtime;
		List<FsChartVo> list = null;
		if("month".equals(timeType)){  //月粒度
			//获取当前月第一天时间
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			list = flowDao.getFlowIngerdientOhtChart(ingid, starttime, endtime);
		}else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			list = flowDao.getFlowIngerdientOhtChartOfDay(ingid, starttime, endtime);
		}
	    for (FsChartVo fsChartVo : list) {
	    	fsChartVo.setVal(Utils.valueOf(fsChartVo.getVal()));
	    	fsChartVo.setValue(Utils.valueOf(fsChartVo.getValue()));
		}
		return list;

//			//获取当前月第一天时间
//			Date getMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
//			//获取下个月第一天时间
//			Date nextMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
//			List<FsChartVo> list = flowDao.getFlowIngerdientOhtChart(ingid,getMonthFirstDay,nextMonthFirstDay); 
//			for (FsChartVo fs : list) {
//				fs.setValue(Utils.valueOf(fs.getValue()));
//			}
//			return list;

		}
	//业务访问关键指标分析
	public List<Map<String, String>> findBusiKpiChart(String getTime, String timeType)throws Exception {
		Date starttime;
		Date endtime;
		List<Map<String, String>> list = null;
		if("month".equals(timeType)){  //月粒度
			//获取当前月第一天时间
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			list = flowDao.findBusinessVisitKeyKpiDetails(starttime, endtime);
		}else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			list = flowDao.findBusinessVisitKeyKpiDetailsOfDay(starttime, endtime);
		}
		
		return list;
			
	}

	
	public Map<String, Object> findFlowExitCityContrast(String exitid, String getTime) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> inFlowMap = null;
		Map<String, String> outFlowMap = null;
		Map<String, String> totalFlowMap = null;
		List<Map<String, String>> inFlowList = new ArrayList<Map<String,String>>();
		List<Map<String, String>> outFlowList = new ArrayList<Map<String,String>>();
		List<Map<String, String>> totalFlowList = new ArrayList<Map<String,String>>();
		String inFlowSum = "0",outFlowSum = "0",totalFlowSum = "0";
		//获取当前月第一天时间
		Date getMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		Date nextMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		List<Map<String, String>> list = flowDao.findFlowExitCityContrast(exitid, getMonthFirstDay, nextMonthFirstDay);
		for (Map<String, String> flow : list) {
			inFlowMap = new HashMap<String, String>();
			outFlowMap = new HashMap<String, String>();
			totalFlowMap = new HashMap<String, String>();
			inFlowMap.put("city", flow.get("city"));
			inFlowMap.put("flow", String.valueOf(flow.get("inflow")));//入流量
			outFlowMap.put("city", flow.get("city"));
			outFlowMap.put("flow", String.valueOf(flow.get("outflow")));//出流量
			totalFlowMap.put("city", flow.get("city"));
			totalFlowMap.put("flow", String.valueOf(flow.get("totalflow")));//双向总流量
			inFlowSum = Utils.valueOf(String.valueOf(new BigDecimal(String.valueOf(flow.get("inflow"))).add(new BigDecimal(inFlowSum))));//各地市入流量总流量
			outFlowSum = Utils.valueOf(String.valueOf(new BigDecimal(String.valueOf(flow.get("outflow"))).add(new BigDecimal(outFlowSum))));//各地市出流量总量
			totalFlowSum = Utils.valueOf(String.valueOf(new BigDecimal(String.valueOf(flow.get("totalflow"))).add(new BigDecimal(totalFlowSum))));//各地市双向总流量总流量
			inFlowList.add(inFlowMap);
			outFlowList.add(outFlowMap);
			totalFlowList.add(totalFlowMap);
		}
		MapComparator mc = new MapComparator();
		Collections.sort(inFlowList, mc);//入流量排序
		Collections.sort(outFlowList, mc);//出流量排序
		Collections.sort(totalFlowList, mc);//双向总流量排序
		
		for (int i = 0; i < inFlowList.size(); i++) {
			Map<String, String> inflow = inFlowList.get(i);
			inflow.put("id", ""+(i+1));
			String percentFlow = Utils.valueOf(String.valueOf(Utils.div(Double.parseDouble(inflow.get("flow")), Double.parseDouble(inFlowSum), 2)*100));
			inflow.put("percentFlow", percentFlow);
			String flow = "0".equals(inflow.get("flow")) ? "—" : Utils.valueOf(inflow.get("flow"),"#,###")+"GB";
			inflow.put("flow", flow);
			inflow.put("grade", getRankingGrade(Integer.parseInt(inflow.get("id"))));
			//System.out.println("入流量："+inflow.get("id")+" "+inflow.get("city")+" "+inflow.get("flow")+" "+inflow.get("grade")+" "+inflow.get("percentFlow"));
		}
		for (int i = 0; i < outFlowList.size(); i++) {
			Map<String, String> outflow = outFlowList.get(i);
			outflow.put("id", ""+(i+1));
			String percentFlow = Utils.valueOf(String.valueOf(Utils.div(Double.parseDouble(outflow.get("flow")), Double.parseDouble(outFlowSum), 2)*100));
			outflow.put("percentFlow", percentFlow);
			String flow = "0".equals(outflow.get("flow")) ? "—" : Utils.valueOf(outflow.get("flow"),"#,###")+"GB";
			outflow.put("flow", flow);
			outflow.put("grade", getRankingGrade(Integer.parseInt(outflow.get("id"))));
			//System.out.println("出流量："+outflow.get("id")+" "+outflow.get("city")+" "+outflow.get("flow")+" "+outflow.get("grade")+" "+outflow.get("percentFlow"));
		}
		for (int i = 0; i < totalFlowList.size(); i++) {
			Map<String, String> totalflow = totalFlowList.get(i);
			totalflow.put("id", ""+(i+1));
			String percentFlow = Utils.valueOf(String.valueOf(Utils.div(Double.parseDouble(totalflow.get("flow")), Double.parseDouble(totalFlowSum), 2)*100));
			totalflow.put("percentFlow", percentFlow);
			String flow = "0".equals(totalflow.get("flow")) ? "—" : Utils.valueOf(totalflow.get("flow"),"#,###")+"GB";
			totalflow.put("flow", flow);
			totalflow.put("grade", getRankingGrade(Integer.parseInt(totalflow.get("id"))));
			//System.out.println("双向总流量："+totalflow.get("id")+" "+totalflow.get("city")+" "+totalflow.get("flow")+" "+totalflow.get("grade")+" "+totalflow.get("percentFlow"));
		}
		map.put("入流量", inFlowList);
		map.put("出流量", outFlowList);
		map.put("双向总流量", totalFlowList);
		return map;
	}
	
	/**
	 * 根据排名序号在指定排名范围内获取泡泡大小  1=小泡泡、2=中泡泡、3=大泡泡
	 * @param id
	 * @return
	 */
	private String getRankingGrade(int id){
		String grade = "1";
		if(id>=1 && id<6){
			grade = "3";
		}else if(id>=6 && id<=12){
			grade = "2";
		}else{
			grade = "1";
		}
		return grade;
	}


	public List<Map<String, String>> findFlowExitName() throws Exception {
		List<Map<String, String>> list = flowDao.findFlowExitName();
		return list;
	}

	//资源提供关键指标分析
	public List<Map<String, String>> findResKPI(String getTime, String timeType) throws Exception{
		Date starttime;
		Date endtime;
		List<Map<String, String>> lists = null;
		if("month".equals(timeType)){  //月粒度
			//获取当前月第一天时间
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			lists = flowDao.findResKPI(starttime, endtime);
		}else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			lists = flowDao.findResKPIOfDay(starttime, endtime);
		}
		return lists;
	}


	public List<Map<String, String>> findFlowIngerdientTable(String getTime, String timeType)
			throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>();
		Date starttime;
		Date endtime;
		List<Map<String, String>> lists = null;
		if("month".equals(timeType)){  //月粒度
			//获取当前月第一天时间
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			lists = flowDao.findFlowIngerdientTable(starttime, endtime);
		}else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			lists = flowDao.findFlowIngerdientTableOfDay(starttime, endtime);
		}
		String id = "";
		int index = 0;
		for (Map<String, String> flow : lists) {
			if(!String.valueOf(flow.get("ingid")).equals(id) && !"".equals(id)){
				list.add(map);
				map = new HashMap<String, String>();
			}
			map.put("id", id);
			map.put("visitType", flow.get("ingtype"));
			String perc = flow.get("perc") == null ? "\\" : Utils.valueOf(String.valueOf(flow.get("perc")))+"%";
			map.put(Common.flowNameMap.get(flow.get("kpiname")), perc);
			map.put("collecttime", String.valueOf(flow.get("collecttime")));
			id = String.valueOf(flow.get("ingid"));
			index ++;
			if(lists.size() == index){
				list.add(map);
			}
		}
		return list;
	}


	public List<Map<String, Object>> findFlowExitLinkOverview(String getTime, String timeType, String busType)
			throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = null;
		Map<String, Object> inFlowMap = null;
		Map<String, Object> outFlowMap = null;
		Map<String, Object> totalFlowMap = null;
		List<Map<String, Object>> valueList = null;
		Date starttime;
		Date endtime;
		List<FlowExitLinkVo> lists = null;
//		if("month".equals(timeType)){  //月粒度
		   //获取当前月第一天时间
		   starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		   //获取下个月第一天时间
		   endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		   lists = flowDao.findFlowExitLinkOverview(starttime, endtime, busType);
//		}else{   //天粒度
//		   //获取当天开始时间
//		   starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
//		   //获取当天结束时间
//		   endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
//		   lists = flowDao.findFlowExitLinkOverviewOfDay(starttime, endtime, busType);
//		}
		for (int i = 0; i < lists.size(); i++) {
			FlowExitLinkVo flow = lists.get(i);
			map = new HashMap<String, Object>();
			map.put("flowExitName", flow.getExitname());
			map.put("level", flow.getLev());
			totalFlowMap = new HashMap<String, Object>();
			totalFlowMap.put("name", "双向总流量");
			totalFlowMap.put("value", Double.valueOf(flow.getTotalflow()));
			totalFlowMap.put("perc", Utils.valueOf(flow.getTotalflowperc()));
			inFlowMap = new HashMap<String, Object>();
			inFlowMap.put("name", "入流量");
			inFlowMap.put("value", Utils.valueOf(flow.getInflow()));
			inFlowMap.put("perc", Utils.valueOf(flow.getInflowperc()));
			outFlowMap = new HashMap<String, Object>();
			outFlowMap.put("name", "出流量");
			outFlowMap.put("value", Double.valueOf(flow.getOutflow()));
			outFlowMap.put("perc", Utils.valueOf(flow.getOutflowperc()));
			valueList = new ArrayList<Map<String,Object>>();
			valueList.add(totalFlowMap);
			valueList.add(inFlowMap);
			valueList.add(outFlowMap);
			map.put("value", valueList);
			list.add(map);
		}
		
		return list;
	}
	
	//按时间查询流量性能指标值
	public Map<String, String> findPerformanceKpiValue(String exitid, String getTime) throws Exception{
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> totalMap = new HashMap<String, String>();
		//获取当前月第一天时间
		Date getMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		Date nextMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		
		List<Map<String, String>> lists = flowDao.findFlowPropertyAnalyze(exitid, getMonthFirstDay, nextMonthFirstDay);
		int id = 1,index = 0,day = 0;
		for (Map<String, String> flow : lists) {
			if(flow.get("collecttime") != null){
				
				int getDay = DateUtil.getDay(String.valueOf(flow.get("collecttime")));
				if(getDay != day && day != 0){
					map.put("id", String.valueOf(id++));
					list.add(map);
					Iterator iterator = map.entrySet().iterator();
					while(iterator.hasNext()){
						Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
						if(entry.getKey().indexOf("Value") != -1){
							String totalValue = totalMap.get(entry.getKey()) == null ? "0" : totalMap.get(entry.getKey());
							totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
							totalMap.put(entry.getKey(), totalValue);
						}
					}
					map = new HashMap<String, String>();
				}
				map.put(Common.flowNameMap.get(flow.get("kpiname")), String.valueOf(flow.get("val")));
				day = getDay;
				index ++;
				if(index == lists.size()){
					map.put("id", String.valueOf(id++));
					list.add(map);
					Iterator iterator = map.entrySet().iterator();
					while(iterator.hasNext()){
						Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
						if(entry.getKey().indexOf("Value") != -1){
							String totalValue = totalMap.get(entry.getKey()) == null ? "0" : totalMap.get(entry.getKey());
							totalValue = String.valueOf(new BigDecimal(entry.getValue()).add(new BigDecimal(totalValue)));
							double avgValue = Double.valueOf(totalValue) / Double.valueOf(String.valueOf(map.get("id")));
							totalMap.put(entry.getKey(), Utils.valueOf(String.valueOf(avgValue)));
						}
					}
				}
			}
		}
		return totalMap;
	}
	
	// 性能指标概览
	public Map<String, String> findPerformanceView(String exitid, String getTime) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		//获取上个月第一天时间
		String beforeMonthFirstDay  = DateUtil.getMonthFirstDay(getTime, -1);
		Map<String, String> getMonthMap = findPerformanceKpiValue(exitid, getTime);
		Map<String, String> beforeMonthMap = findPerformanceKpiValue(exitid, beforeMonthFirstDay);
		Iterator iterator = getMonthMap.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
			map.put(entry.getKey(), Utils.valueOf(entry.getValue(),"#,###.00"));
			String beforeMonthValue = beforeMonthMap.get(entry.getKey());
			double getMonthValue = 0;
			if(beforeMonthMap.get(entry.getKey()) != null){
				getMonthValue = new BigDecimal(entry.getValue()).subtract(new BigDecimal(beforeMonthMap.get(entry.getKey()))).doubleValue();
			}
			if(beforeMonthValue == null || "0".equals(beforeMonthValue)){
				map.put(entry.getKey()+"Perc", "0");
			}else{
				double percValue = Utils.div(getMonthValue, Double.valueOf(beforeMonthValue), 2);
				map.put(entry.getKey()+"Perc", String.valueOf(percValue));
			}
		}
		return map;
	}

	public List<Map<String, Object>> findFlowDetailClass(String getTime, String timeType)
			throws Exception {
		List<Map<String, Object>> listAll = new ArrayList<Map<String,Object>>();
		Map<String, Object> mapAll = null;
		List<Map<String, Object>> list = null;
		Map<String, Object> groupMap = null, thirdpartyMap = null;//集团出口、省级出口
		String ingId = "";
		int index = 0;
		Date starttime;
	    Date endtime;
	    List<Map<String, Object>> lists = null;
	    if("month".equals(timeType)){  //月粒度
		     //获取当前月第一天时间
		     starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		     //获取下个月第一天时间
		     endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		     lists = flowDao.findFlowDetailClass(starttime, endtime);
	    }else{   //天粒度
		     //获取当天开始时间
	         starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
	         //获取当天结束时间
		     endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
		     lists = flowDao.findFlowDetailClassOfDay(starttime, endtime);
	    }
		for (Map<String, Object> map : lists) {
			if(!String.valueOf(map.get("ingId")).equals(ingId)){
				if(!"".equals(ingId)){
					list = new ArrayList<Map<String,Object>>();
					list.add(groupMap);
					list.add(thirdpartyMap);
					mapAll.put("value", list);
					listAll.add(mapAll);
				}
				groupMap = new HashMap<String, Object>();
				thirdpartyMap = new HashMap<String, Object>();
				mapAll = new HashMap<String, Object>();
				groupMap.put("insertType", "集团出口");
				thirdpartyMap.put("insertType", "第三方出口");
				mapAll.put("type", map.get("ingType"));
			}
			
			groupMap.put(Common.flowNameMap.get(map.get("objectName")), Utils.valueOf(String.valueOf(map.get("groupFlow"))));
			thirdpartyMap.put(Common.flowNameMap.get(map.get("objectName")), Utils.valueOf(String.valueOf(map.get("thirdpartyFlow"))));
			ingId = String.valueOf(map.get("ingId"));
			index ++;
			if(lists.size() == index){
				list = new ArrayList<Map<String,Object>>();
				list.add(groupMap);
				list.add(thirdpartyMap);
				mapAll.put("value", list);
				listAll.add(mapAll);
			}
		}
		return listAll;
	}

	//根据出口ID和指标ID查询当天全省各小时流量趋势变化情况
	public List<Map<String, Object>> findFlowTendencyAnalyze(String exitId,String kpiId) throws Exception {
		
		return flowDao.findFlowTendencyAnalyze(exitId, kpiId);
	}


	//互联网流量分析-流量流向分析表格数据Excel导出
	public byte[] flowDirectionExcelTable(String getTime, String timeType) throws Exception {
		List<Map<String, String>> bizVisitKeyList = findBusinessVisitKeyKpiDetails(getTime, timeType);//业务访问关键指标详情
		List<Map<String, String>> resourceProvideKeyList = findResourceProvideKeyKpiDetails(getTime, timeType);//资源提供关键指标详情
		List<Map<String, String>> netInOutResourceList = findNetInOutResourceFlowDir(getTime);//业务访问网内及网外资源流量流向详表
		List<Map<String, String>> netInOtherProvinceIDCList = findNetInOtherProvinceIDCFlowDir(getTime);//业务访问网内他省IDC流量流向详表
		List<Map<String, String>> resourceNodeList = findResourceNodeFlowDir(getTime);//本省资源节点提供流量流向详表
		List<Map<String, String>> otherProvinceIDCList = findOtherProvinceIDCFlow(getTime);//本省IDC资源节点提供给网内他省IDC流量详表
		List<Map<String, String>> cityVisitCTCCAndCUCCList = findCityVisitCTCCAndCUCCFlow(getTime, timeType);//各地市访问电信及联通流量分析
		
		/************************** Excel导出 *******************************/
		/*** 互联网流量分析_流量流向分析_业务访问关键指标详情***/
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet =  wb.createSheet("业务访问关键指标详情");
		String[] str = new String[]{"业务访问关键指标","CMNET","CMWAP","WLAN","家庭客户","集团客户"};//表头名称
		String[] mapKeyName = new String[]{"bizName","cmnetValue","cmnetPerc","cmwapValue","cmwapPerc","wlanValue","wlanPerc","homeGeekValue","homeGeekPerc","groupGeekValue","groupGeekPerc"};//Map的Key值
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
			if(i == 0){
				HSSFCell cell = rowTit.createCell(i);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
			}else{
				HSSFCell cell = rowTit.createCell(2*i-1);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
				sheet.addMergedRegion(new Region(0, (short) (2*i-1), 0,(short) (2*i)));
			}
		}
		str = new String[]{"指标值","环比","指标值","环比","指标值","环比","指标值","环比","指标值","环比"};
		rowTit = sheet.createRow(1);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i+1);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i == 0){//合并第0列的第0行和第1行
				sheet.addMergedRegion(new Region(0, (short) i, 1,(short) i));
			}
		}
		//内容
		if(bizVisitKeyList.size() > 0){
			for (int i = 0; i < bizVisitKeyList.size(); ++i) {
				Map<String, String> map = (Map<String, String>)bizVisitKeyList.get(i);
				HSSFRow row = sheet.createRow(i + 2);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : map.get(mapKeyName[j]);
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}
		
		/*** 互联网流量分析_流量流向分析_资源提供关键指标详情 ***/
		sheet =  wb.createSheet("资源提供关键指标详情");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"资源提供关键指标","IDC","Cache"};
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			if(i == 0){
				HSSFCell cell = rowTit.createCell(i);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
			}else{
				HSSFCell cell = rowTit.createCell(2*i-1);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
				sheet.addMergedRegion(new Region(0, (short) (2*i-1), 0,(short) (2*i)));
			}
		}
		str = new String[]{"指标值","环比","指标值","环比"};
		mapKeyName = new String[]{"bizName","idcValue","idcPerc","caCheValue","caChePerc"};//Map的Key值
		rowTit = sheet.createRow(1);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i+1);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i == 0){//合并第0列的第0行和第1行
				sheet.addMergedRegion(new Region(0, (short) i, 1,(short) i));
			}
		}
		//内容
		if(resourceProvideKeyList.size() > 0){
			for (int i = 0; i < resourceProvideKeyList.size(); ++i) {
				Map<String, String> map = (Map<String, String>)resourceProvideKeyList.get(i);
				HSSFRow row = sheet.createRow(i + 2);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : map.get(mapKeyName[j]);
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}
		
		/*** 互联网流量分析_流量流向分析_业务访问网内及网外资源流量流向详表 ***/
		sheet =  wb.createSheet("业务访问网内及网外资源流量流向详表");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"业务访问流向","CMNET","CMWAP","WLAN","家庭客户","集团客户"};
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			if(i == 0){
				HSSFCell cell = rowTit.createCell(i);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
			}else{
				HSSFCell cell = rowTit.createCell(2*i-1);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
				sheet.addMergedRegion(new Region(0, (short) (2*i-1), 0,(short) (2*i)));
			}
		}
		str = new String[]{"日均流量(TB)","占比(%)","日均流量(TB)","占比(%)","日均流量(TB)","占比(%)","日均流量(TB)","占比(%)","日均流量(TB)","占比(%)"};
		mapKeyName = new String[]{"bizName","cmnetperDayFlow","cmnetPerc","cmwapperDayFlow","cmwapPerc","wlanperDayFlow","wlanPerc","homeGeekperDayFlow","homeGeekPerc","groupGeekperDayFlow","groupGeekPerc"};//Map的Key值
		rowTit = sheet.createRow(1);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i+1);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i == 0){//合并第0列的第0行和第1行
				sheet.addMergedRegion(new Region(0, (short) i, 1,(short) i));
			}
		}
		//内容
		if(netInOutResourceList.size() > 0){
			for (int i = 0; i < resourceProvideKeyList.size(); ++i) {
				Map<String, String> map = (Map<String, String>)netInOutResourceList.get(i);
				HSSFRow row = sheet.createRow(i + 2);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : map.get(mapKeyName[j]);
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}
		
		/*** 互联网流量分析_流量流向分析_业务访问网内他省IDC流量流向详表 ***/
		sheet =  wb.createSheet("业务访问网内他省IDC流量流向详表");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"业务访问流向","CMNET","CMWAP","WLAN","家庭客户","集团客户"};
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			if(i == 0){
				HSSFCell cell = rowTit.createCell(i);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
			}else{
				HSSFCell cell = rowTit.createCell(2*i-1);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
				sheet.addMergedRegion(new Region(0, (short) (2*i-1), 0,(short) (2*i)));
			}
		}
		str = new String[]{"日均流量(TB)","占比(%)","日均流量(TB)","占比(%)","日均流量(TB)","占比(%)","日均流量(TB)","占比(%)","日均流量(TB)","占比(%)"};
		mapKeyName = new String[]{"bizName","cmnetperDayFlow","cmnetPerc","cmwapperDayFlow","cmwapPerc","wlanperDayFlow","wlanPerc","homeGeekperDayFlow","homeGeekPerc","groupGeekperDayFlow","groupGeekPerc"};//Map的Key值
		rowTit = sheet.createRow(1);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i+1);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i == 0){//合并第0列的第0行和第1行
				sheet.addMergedRegion(new Region(0, (short) i, 1,(short) i));
			}
		}
		//内容
		if(netInOtherProvinceIDCList.size() > 0){
			for (int i = 0; i < netInOtherProvinceIDCList.size(); ++i) {
				Map<String, String> map = (Map<String, String>)netInOtherProvinceIDCList.get(i);
				HSSFRow row = sheet.createRow(i + 2);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : map.get(mapKeyName[j]);
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}
		
		/*** 互联网流量分析_流量流向分析_本省资源节点提供流量流向详表 ***/
		sheet =  wb.createSheet("本省资源节点提供流量流向详表");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"资源流提供流向","IDC","Web Cache(双向流量)","P2P Cache(双向流量)"};
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(2*i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i > 0){
				sheet.addMergedRegion(new Region(0, (short) (2*i), 0,(short) (2*i+1)));
			}
		}
		str = new String[]{"日均流量(TB)","占比(%)","日均流量(TB)","占比(%)","日均流量(TB)","占比(%)"};
		mapKeyName = new String[]{"parentName","childName","idcperDayFlow","idcPerc","webCaCheperDayFlow","webCaChePerc","p2pCaCheperDayFlow","p2pCaChePerc"};//Map的Key值
		rowTit = sheet.createRow(1);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i+2);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i == 0){//合并第0列和第1列的第0行和第1行
				sheet.addMergedRegion(new Region(0, (short) 0, 1,(short) 1));
			}
		}
		//内容
		if(resourceNodeList.size() > 0){
			for (int i = 0; i < resourceNodeList.size(); ++i) {
				Map<String, String> map = (Map<String, String>)resourceNodeList.get(i);
				HSSFRow row = sheet.createRow(i + 2);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : map.get(mapKeyName[j]);
					cell.setCellValue(new HSSFRichTextString(value));
				}
				if(i == 0){//合并第3行的第0列和第1列
					sheet.addMergedRegion(new Region(2, (short) 0, 2,(short) 1));
				}
			}
		}
		
		/*** 互联网流量分析_流量流向分析_本省IDC资源节点提供给网内他省IDC流量详表 ***/
		sheet =  wb.createSheet("本省IDC资源节点提供给网内他省IDC流量详表");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"资源流提供流向","本省IDC"};
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			if(i == 0){
				HSSFCell cell = rowTit.createCell(i);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
			}else{
				HSSFCell cell = rowTit.createCell(2*i-1);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
				sheet.addMergedRegion(new Region(0, (short) (2*i-1), 0,(short) (2*i)));
			}
		}
		str = new String[]{"日均流量(TB)","占比(%)"};
		mapKeyName = new String[]{"bizName","idcperDayFlow","idcPerc"};//Map的Key值
		rowTit = sheet.createRow(1);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i+1);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i == 0){//合并第0列的第0行和第1行
				sheet.addMergedRegion(new Region(0, (short) i, 1,(short) i));
			}
		}
		//内容
		if(otherProvinceIDCList.size() > 0){
			for (int i = 0; i < otherProvinceIDCList.size(); ++i) {
				Map<String, String> map = (Map<String, String>)otherProvinceIDCList.get(i);
				HSSFRow row = sheet.createRow(i + 2);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : map.get(mapKeyName[j]);
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}
		
		/*** 互联网流量分析_流量流向分析_各地市访问电信及联通流量分析***/
		sheet =  wb.createSheet("各地市访问电信及联通流量分析");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"业务访问流向","电信","联通","电信加联通"};
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			if(i == 0){
				HSSFCell cell = rowTit.createCell(i);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
			}else{
				HSSFCell cell = rowTit.createCell(2*i-1);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
				sheet.addMergedRegion(new Region(0, (short) (2*i-1), 0,(short) (2*i)));
			}
		}
		str = new String[]{"日均流量(TB)","占比(%)","日均流量(TB)","占比(%)","日均流量(TB)","占比(%)"};
		mapKeyName = new String[]{"bizName","ctccsumFlow","ctccPerc","cuccsumFlow","cuccPerc","ctccAndcuccsumFlow","ctccAndcuccPerc"};//Map的Key值
		rowTit = sheet.createRow(1);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i+1);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i == 0){//合并第0列的第0行和第1行
				sheet.addMergedRegion(new Region(0, (short) i, 1,(short) i));
			}
		}
		//内容
		if(cityVisitCTCCAndCUCCList.size() > 0){
			for (int i = 0; i < cityVisitCTCCAndCUCCList.size(); ++i) {
				Map<String, String> map = (Map<String, String>)cityVisitCTCCAndCUCCList.get(i);
				HSSFRow row = sheet.createRow(i + 2);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : map.get(mapKeyName[j]);
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
	
	//互联网流量分析-流量成分分析表格数据Excel导出
	public byte[] flowIngredientExcelTable(String getTime, String timeType) throws Exception {
		List<Map<String, Object>> flowDetailClasslist = findFlowDetailClass(getTime, timeType);//不同访问类型的流量成分构成
		List<Map<String, String>>  busiVisitResFlowList = findBusiVisitResFlowIngredientForm(getTime, timeType);//业务访问及资源提供流量成分构成
		List<Map<String, String>>  busiVisitIngredientList = findBusiVisitIngredientFormInfo(getTime, timeType);//业务访问成分构成详表(省网出口)
		List<Map<String, String>>  resourceNodeIngredientList = findResourceNodeIngredientInfo(getTime, timeType);//资源节点内容提供成分详表(省网出口)
		
		/************************** Excel导出 *******************************/
		/*** 互联网流量分析_流量成分分析_不同访问类型的流量成分构成***/
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet =  wb.createSheet("不同访问类型的流量成分构成");
		String[] str = new String[]{"                    业务访问                                          \n\n接入类型","业务网内(兄弟公司)访问成分构成","业务网外(电信、联通、海外)访问成分构成(集团出口)","业务网外(电信、联通、海外)访问成分构成(省级出口)"};//表头名称
		String[] str1 = new String[]{"视频PPTV(含P2P类)\n日均流量(TB/天)","视频PPS(含P2P类)\n日均流量(TB/天)","HTTP优酷\n日均流量(TB/天)","HTTP土豆\n日均流量(TB/天)","HTTP乐视\n日均流量(TB/天)","HTTP奇艺\n日均流量(TB/天)","HTTP酷6\n日均流量(TB/天)","HTTP搜狐\n日均流量(TB/天)"};
		String[] mapKeyName = new String[]{"insertType","pptvFlow","ppsFlow","youkuFlow","tudouFlow","letvFlow","iqiyiFlow","ku6Flow","sohuFlow"};//Map的Key值
		sheet.setDefaultRowHeightInPoints(15);
		sheet.setDefaultColumnWidth(20);
		//标题样式
		HSSFCellStyle cellStyleTit = wb.createCellStyle();
		cellStyleTit.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyleTit.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyleTit.setWrapText(true);// 指定单元格自动换行
		HSSFFont fontTit = wb.createFont();
		fontTit.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontTit.setFontHeightInPoints((short) 12);
		fontTit.setFontName("微软雅黑");
		fontTit.setFontHeight((short) 200);
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
		
		//业务网内(兄弟公司)访问成分构成
		HSSFRow rowTit = sheet.createRow(0);
		for (int i = 0; i < 2; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i > 0){
				sheet.addMergedRegion(new Region(0, (short) i, 0,(short) 8));
			}
		}
		rowTit = sheet.createRow(1);
		for (int i = 0; i < str1.length; ++i) {
			String tit = (String)str1[i];
			HSSFCell cell = rowTit.createCell(i+1);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i == 0){//合并第0列的第0行和第1行
				sheet.addMergedRegion(new Region(0, (short) i, 1,(short) i));
			}
		}
		//表头第一个单元格画斜线
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFClientAnchor anchor = new HSSFClientAnchor();
		//anchor.setAnchor(cell.getCellNum(), rowTit.getRowNum(), 0, 0, (short) (cell.getCellNum() + 1),rowTit.getRowNum() + 1, 0, 0);
		anchor.setAnchor((short) 0, 0, 0, 0, (short) 1, 2, 0, 0);
		patriarch.createSimpleShape(anchor);
		
		//业务网外(电信、联通、海外)访问成分构成(集团出口)
		rowTit = sheet.createRow(4);
		for (int i = 0; i < 2; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i > 0){
				sheet.addMergedRegion(new Region(4, (short) i, 4,(short) 8));
			}
		}
		rowTit = sheet.createRow(5);
		for (int i = 0; i < str1.length; ++i) {
			String tit = (String)str1[i];
			HSSFCell cell = rowTit.createCell(i+1);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i == 0){//合并第0列的第0行和第1行
				sheet.addMergedRegion(new Region(4, (short) i, 5,(short) i));
			}
		}
		//表头第一个单元格画斜线
		anchor = new HSSFClientAnchor();
		anchor.setAnchor((short) 0, 4, 0, 0, (short) 1, 6, 0, 0);
		patriarch.createSimpleShape(anchor);
		
		//业务网外(电信、联通、海外)访问成分构成(省级出口)
		rowTit = sheet.createRow(8);
		for (int i = 0; i < 2; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i > 0){
				sheet.addMergedRegion(new Region(8, (short) i, 8,(short) 8));
			}
		}
		rowTit = sheet.createRow(9);
		for (int i = 0; i < str1.length; ++i) {
			String tit = (String)str1[i];
			HSSFCell cell = rowTit.createCell(i+1);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i == 0){//合并第0列的第0行和第1行
				sheet.addMergedRegion(new Region(8, (short) i, 9,(short) i));
			}
		}
		//表头第一个单元格画斜线
		anchor = new HSSFClientAnchor();
		anchor.setAnchor((short) 0, 8, 0, 0, (short) 1, 10, 0, 0);
		patriarch.createSimpleShape(anchor);
		
		//内容
		if(flowDetailClasslist.size() > 0){
			int rowSize = 0;
			for (int i = 0; i < flowDetailClasslist.size(); ++i) {
				Map<String, Object> map = (Map<String, Object>)flowDetailClasslist.get(i);
				List<Map<String, Object>> list = (List<Map<String, Object>>)map.get("value");
				for(int j = 0;j < list.size(); ++j){
					Map<String, Object> maps = list.get(j);
					HSSFRow row = sheet.createRow(j + 2 + rowSize);
					for (int k = 0; k < mapKeyName.length; k++) {
						HSSFCell cell = row.createCell(k);
						cell.setCellStyle(cellStyle);
						String value = String.valueOf(maps.get(mapKeyName[k])) == null ? "" : String.valueOf(maps.get(mapKeyName[k]));
						cell.setCellValue(new HSSFRichTextString(value));
					}
				}
				rowSize += list.size() + 2;
			}
		}
		
		/*** 互联网流量分析_流量成分分析_业务访问及资源提供流量成分构成***/
		sheet =  wb.createSheet("业务访问及资源提供流量成分构成");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"流量成分","业务访问","资源提供(仅IDC)"};
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			if(i == 0){
				HSSFCell cell = rowTit.createCell(i);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
			}else{
				HSSFCell cell = rowTit.createCell(2*i-1);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
				sheet.addMergedRegion(new Region(0, (short) (2*i-1), 0,(short) (2*i)));
			}
		}
		str = new String[]{"日均流量(TB)","占比(%)","日均流量(TB)","占比(%)"};
		mapKeyName = new String[]{"bizName","busiVisitperDayFlow","busiVisitPerc","idcperDayFlow","idcPerc"};//Map的Key值
		rowTit = sheet.createRow(1);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i+1);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i == 0){//合并第0列的第0行和第1行
				sheet.addMergedRegion(new Region(0, (short) i, 1,(short) i));
			}
		}
		//内容
		if(busiVisitResFlowList.size() > 0){
			for (int i = 0; i < busiVisitResFlowList.size(); ++i) {
				Map<String, String> map = (Map<String, String>)busiVisitResFlowList.get(i);
				HSSFRow row = sheet.createRow(i + 2);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : map.get(mapKeyName[j]);
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}
		
		/*** 互联网流量分析_流量成分分析_业务访问成分构成详表(省网出口)***/
		sheet =  wb.createSheet("业务访问成分构成详表(省网出口)");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"业务访问流向","CMNET","CMWAP","WLAN","家庭客户","集团客户"};
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(2*i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i > 0){
				sheet.addMergedRegion(new Region(0, (short) (2*i), 0,(short) (2*i+1)));
			}
		}
		str = new String[]{"日均流量(TB)","占比(%)","日均流量(TB)","占比(%)","日均流量(TB)","占比(%)","日均流量(TB)","占比(%)","日均流量(TB)","占比(%)"};
		mapKeyName = new String[]{"childName","bizName","cmnetperDayFlow","cmnetPerc","cmwapperDayFlow","cmwapPerc","wlanperDayFlow","wlanPerc","homeGeekperDayFlow","homeGeekPerc","groupGeekperDayFlow","groupGeekPerc"};//Map的Key值
		rowTit = sheet.createRow(1);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i+2);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i == 0){//合并第0列和第1列的第0行和第1行
				sheet.addMergedRegion(new Region(0, (short) 0, 1,(short) 1));
			}
		}
		//内容
		if(busiVisitIngredientList.size() > 0){
			String bizName = "";
			int index = 3;
			for (int i = 0; i < busiVisitIngredientList.size(); ++i) {
				Map<String, String> map = (Map<String, String>)busiVisitIngredientList.get(i);
				HSSFRow row = sheet.createRow(i + 2);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : map.get(mapKeyName[j]);
					if(j == 0){
						if(map.get(mapKeyName[j+1]).indexOf("所有成分汇总") != -1){
							cell.setCellValue(new HSSFRichTextString(map.get(mapKeyName[j+1])));
						}else{
							cell.setCellValue(new HSSFRichTextString(value));
						}
					}else{
						cell.setCellValue(new HSSFRichTextString(value));
					}
				}
				if(map.get("bizName").indexOf("所有成分汇总") != -1){//所有成分汇总跨两列
					sheet.addMergedRegion(new Region(i+2, (short) 0, i+2,(short) 1));
				}
				if(!"".equals(map.get("childName")) && i > 1){
					sheet.addMergedRegion(new Region(index, (short) 0, i,(short) 0));//P2P类型跨行
					index = i + 2;
				}
			}
			sheet.addMergedRegion(new Region(index, (short) 0, busiVisitIngredientList.size() + 1,(short) 0));//P2P类型跨行
		}
		
		/*** 互联网流量分析_流量成分分析_资源节点内容提供成分详表(省网出口)***/
		sheet =  wb.createSheet("资源节点内容提供成分详表(省网出口)");
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"资源流提供流向","IDC","Web Cache(双向流量)","P2P Cache(双向流量)"};
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(2*i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i > 0){
				sheet.addMergedRegion(new Region(0, (short) (2*i), 0,(short) (2*i+1)));
			}
		}
		str = new String[]{"日均流量(TB)","占比(%)","日均流量(TB)","占比(%)","日均流量(TB)","占比(%)"};
		mapKeyName = new String[]{"childName","bizName","idcperDayFlow","idcPerc","webCaCheperDayFlow","webCaChePerc","p2pCaCheperDayFlow","p2pCaChePerc"};//Map的Key值
		rowTit = sheet.createRow(1);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i+2);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
			if(i == 0){//合并第0列和第1列的第0行和第1行
				sheet.addMergedRegion(new Region(0, (short) 0, 1,(short) 1));
			}
		}
		//内容
		if(resourceNodeIngredientList.size() > 0){
			String bizName = "";
			int index = 3;
			for (int i = 0; i < resourceNodeIngredientList.size(); ++i) {
				Map<String, String> map = (Map<String, String>)resourceNodeIngredientList.get(i);
				HSSFRow row = sheet.createRow(i + 2);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : map.get(mapKeyName[j]);
					if(j == 0){
						if(map.get(mapKeyName[j+1]).indexOf("所有成分汇总") != -1){
							cell.setCellValue(new HSSFRichTextString(map.get(mapKeyName[j+1])));
						}else{
							cell.setCellValue(new HSSFRichTextString(value));
						}
					}else{
						cell.setCellValue(new HSSFRichTextString(value));
					}
				}
				if(map.get("bizName").indexOf("所有成分汇总") != -1){//所有成分汇总跨两列
					sheet.addMergedRegion(new Region(i+2, (short) 0, i+2,(short) 1));
				}
				if(!"".equals(map.get("childName")) && i > 1){
					sheet.addMergedRegion(new Region(index, (short) 0, i,(short) 0));//P2P类型跨行
					index = i + 2;
				}
			}
			sheet.addMergedRegion(new Region(index, (short) 0, resourceNodeIngredientList.size() + 1,(short) 0));//P2P类型跨行
		}
		
		try {
			ByteArrayOutputStream fos=new ByteArrayOutputStream();
			wb.write(fos);
			/*FileOutputStream writeFile = new FileOutputStream("D:/桌面/互联网流量分析_流量成分分析.xls");
			wb.write(writeFile);
			writeFile.close();*/
			byte[] content =fos.toByteArray();
			return content;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	//互联网流量分析-流量出口分析表格数据Excel导出
	public byte[] flowExitExcelTable(String getTime) throws Exception{
		Page page = new Page();
		page.setPageNo(1);
		page.setPageSize(50);
		Page totalPage = getTotalTreeTable(getTime,"全部");//日均流量分析汇总
		List<FlowExitTree> firstList = getTreeTable(page, getTime,"全部");
		
		/************************** Excel导出 *******************************/
		/*** 互联网流量分析_流量出口分析_日均流量分析***/
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet =  wb.createSheet("日均流量分析");
		String[] str = new String[]{"序号","时间","出口分类","双向总流量(GB)","出流量(GB)","入流量(GB)","双向总流量环比增幅(%)"};//表头名称
		String[] totalTitle = new String[]{"","所有汇总","出口分类汇总","双向流量汇总值","出流量汇总","入流量汇总","环比"};
		String[] mapKeyName = new String[]{"number","collecttime","name","totalflow","outflow","inflow","totalflow_perc"};//Map的Key值
		sheet.setDefaultRowHeightInPoints(10);
		sheet.setDefaultColumnWidth(25);
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
		cellStyle.setWrapText(true);// 指定单元格自动换行
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		sheet.setColumnWidth(3, 50*256);//设置第四列的宽度
		sheet.setColumnWidth(4, 50*256);//设置第五列的宽度
		
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = null;
			if(i < 3){
				cell = row.createCell(i);
			}else{
				cell = row.createCell(i+2);
			}
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
		}
		sheet.addMergedRegion(new Region(0, (short) 2, 0,(short) 4));//表头出口分类跨三列
		
		//内容 汇总
		row = sheet.createRow(row.getRowNum() + 1);
		if(totalPage.getResultList().size() > 0){
			for (int i = 0; i < totalPage.getResultList().size(); ++i) {
				Map<String, String> map = (Map<String, String>)totalPage.getResultList().get(i);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = null;
					if(j < 3){
						cell = row.createCell(j);
					}else{
						cell = row.createCell(j+2);
					}
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : map.get(mapKeyName[j]);
					cell.setCellValue(new HSSFRichTextString(totalTitle[j]+"\n"+value));
				}
			}
		}
		sheet.addMergedRegion(new Region(1, (short) 2, 1,(short) 4));//出口分类汇总跨三列
		
		//内容
		if(firstList.size() > 0){
			int rowNum = 0;
			String name = "省网及第三方出口";
			for (int i = 0; i < firstList.size(); ++i) {
				FlowExitTree firstTree = firstList.get(i);//获取一级出口链路
				row = sheet.createRow(row.getRowNum() + 1);
				if(name.equals(firstTree.getName())){
					rowNum = row.getRowNum();
				}
				
				//通过反射实体类获取属性名称和get方法值
				Field[] fields = firstTree.getClass().getDeclaredFields();
				Field field = null;
				for (int j = 0; j < mapKeyName.length; ++j) {
					for (int k = 0; k < fields.length; ++k) {
						field = fields[k];
						field.setAccessible(true);//修改访问权限
						if(mapKeyName[j].equals(field.getName())){
							HSSFCell cell = null;
							if(j < 3){
								cell = row.createCell(j);
							}else{
								cell = row.createCell(j+2);
							}
							cell.setCellStyle(cellStyle);
							Object value = field.get(firstTree) == null ? "" : field.get(firstTree);
							cell.setCellValue(new HSSFRichTextString((String)value));
							break;
						}
					}
				}
				//System.out.println("获取第一级行数的序号："+row.getRowNum());
				if(firstTree.getSubTree().size() > 0){
					List<FlowExitTree> secondList = firstTree.getSubTree();
					for(int j = 0; j < secondList.size(); ++j){
						row = sheet.createRow(row.getRowNum() + 1);  
						FlowExitTree secondTree = secondList.get(j);//获取二级出口链路
						//通过反射实体类获取属性名称和get方法值
						fields = secondTree.getClass().getDeclaredFields();
						for (int n = 0; n < mapKeyName.length; ++n) {
							for (int k = 0; k < fields.length; ++k) {
								field = fields[k];
								field.setAccessible(true);//修改访问权限
								if(mapKeyName[n].equals(field.getName())){
									HSSFCell cell = null;
									if(n < 3){
										if(n==2){
											cell = row.createCell(n+1);
										}else{
											cell = row.createCell(n);
										}
									}else{
										cell = row.createCell(n+2);
									}
									cell.setCellStyle(cellStyle);
									Object value = field.get(secondTree) == null ? "" : field.get(secondTree);
									cell.setCellValue(new HSSFRichTextString((String)value));
									break;
								}
							}
						}
					
						//System.out.println("获取第二级行数的序号："+row.getRowNum());
						if(secondTree.getSubTree().size() > 0){
							List<FlowExitTree> thirdList = secondTree.getSubTree();
							
							for(int k = 0; k < thirdList.size(); ++k){
								row = sheet.createRow(row.getRowNum() + 1);
								FlowExitTree thirdTree = thirdList.get(k);//获取三级出口链路
								//通过反射实体类获取属性名称和get方法值
								fields = thirdTree.getClass().getDeclaredFields();
								for (int n = 0; n < mapKeyName.length; ++n) {
									for (int k1 = 0; k1 < fields.length; ++k1) {
										field = fields[k1];
										field.setAccessible(true);//修改访问权限
										if(mapKeyName[n].equals(field.getName())){
											HSSFCell cell = null;
											if(n < 3){
												if(n==2){
													cell = row.createCell(n+2);
												}else{
													cell = row.createCell(n);
												}
											}else{
												cell = row.createCell(n+2);
											}
											cell.setCellStyle(cellStyle);
											Object value = field.get(thirdTree) == null ? "" : field.get(thirdTree);
											cell.setCellValue(new HSSFRichTextString((String)value));
											break;
										}
									}
								}
							}
						}
					}
					
				}
				if(!name.equals(firstTree.getName())){
					sheet.addMergedRegion(new Region(rowNum, (short) 0, row.getRowNum(),(short) 0));//序号跨行
					sheet.addMergedRegion(new Region(rowNum, (short) 1, row.getRowNum(),(short) 1));//时间跨行
				}
			}
			
		}
		
		try {
			ByteArrayOutputStream fos=new ByteArrayOutputStream();
			wb.write(fos);
			/*FileOutputStream writeFile = new FileOutputStream("D:/桌面/互联网流量分析_流量出口分析.xls");
			wb.write(writeFile);
			writeFile.close();*/
			byte[] content =fos.toByteArray();
			return content;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//互联网流量分析-流量性能分析表格数据Excel导出
	public byte[] flowPerformanceExcelTable(String getTime) throws Exception {
		Page page = new Page();
		page.setPageSize(31);
		page.setRowCount(31);
		Page totalPage = null,dayPage = null;
		
		/************************** Excel导出 *******************************/
		/*** 互联网流量分析_流量性能分析***/
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = null;
		String[] bizTypeId = new String[]{"250","251","254"};
		Map<String, String> bizTypeName = new HashMap<String, String>(){{put("250","省网出口");put("251","第三方出口");put("254","铁通出口");}};
		String[] str = new String[]{};
		String[] mapKeyName = new String[]{"id","time","inFlowAvgValue","outFlowAvgValue","bwPeakValue","bwPeakValueUseRatio"};//Map的Key值
		
		//标题样式
		HSSFCellStyle cellStyleTit = wb.createCellStyle();
		cellStyleTit.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyleTit.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
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
		cellStyle.setWrapText(true);// 指定单元格自动换行
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		
		for(int n = 0; n < bizTypeId.length; ++n){
			totalPage = findFlowPropertyAnalyze(bizTypeId[n], getTime);//流量性能分析月流量汇总
			dayPage = findFlowPropertyAnalyzeByPagination(page, bizTypeId[n], getTime);//流量性能分析
			str = new String[]{"序号","时间","入流速均值(Mbps)","出流速均值(Mbps)","带宽峰值(Mbps)","带宽峰值利用率(%)"};//表头名称
			
			sheet = wb.createSheet("互联网流量分析_流量性能分析_"+bizTypeName.get(bizTypeId[n]));
			HSSFRow rowTit = sheet.createRow(0);
			sheet.setDefaultRowHeightInPoints(10);    
			sheet.setDefaultColumnWidth(30);
			for (int i = 0; i < str.length; ++i) {//填充表头内容
				String tit = (String)str[i];
				HSSFCell cell = rowTit.createCell(i);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
			}
			//填充单元格内容_流量汇总
			str = new String[]{"所有时间","入流速均值(Mbps)","出流速均值(Mbps)","带宽峰值(Mbps)","带宽峰值利用率(%)"};//流量汇总标题
			if(totalPage.getResultList().size() > 0){
				for (int i = 0; i < totalPage.getResultList().size(); ++i) {
					Map<String, Object> map = (Map<String, Object>)totalPage.getResultList().get(i);
					HSSFRow row = sheet.createRow(i + 1);
					for (int j = 1; j < mapKeyName.length; j++) {
						HSSFCell cell = row.createCell(j);
						cell.setCellStyle(cellStyle);
						String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
						cell.setCellValue(new HSSFRichTextString(str[j-1]+"\n"+value));
					}
				}
			}
			//填充单元格内容_日流量
			if(dayPage.getResultList().size() > 0){
				for (int i = 0; i < dayPage.getResultList().size(); ++i) {
					Map<String, Object> map = (Map<String, Object>)dayPage.getResultList().get(i);
					HSSFRow row = sheet.createRow(i + 2);
					for (int j = 0; j < mapKeyName.length; j++) {
						HSSFCell cell = row.createCell(j);
						cell.setCellStyle(cellStyle);
						String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
						cell.setCellValue(new HSSFRichTextString(value));
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


	//资源提供关键指标分析(柱状图)
	public List<Map<String, String>> findResKpiHistogram(String kpiId, String getTime, String timeType) throws Exception {
		Date starttime;
		Date endtime;
		List<Map<String, String>> list = null;
		if("month".equals(timeType)){  //月粒度
			//获取当前月前12个月第一天时间
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, -11), "yyyy-MM-dd hh:mm:ss");
			//获取当前月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			list = flowDao.findResKpiHistogram(kpiId, starttime, endtime);
		}else{   //天粒度
			//获取当天开始时间的前七天
			starttime = DateUtil.StringToDate(DateUtil.getBefore7Day(getTime), "yyyy-MM-dd HH:mm:ss");
//			System.out.println("starttime==="+starttime);
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			list = flowDao.findResKpiHistogramOfDay(kpiId, starttime, endtime);
//			System.out.println("list=="+list);
		}
		return list;
	}


	//查询全国IDC流量分布图
	public List<Map<String, String>> findProvinceIDCFlowMap(String id, String type, String getTime, String timeType) throws Exception {
		Date starttime;
		Date endtime;
		List<Map<String, String>> list = null;
		if("month".equals(timeType)){  //月粒度
			//获取当前月第一天时间
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			list = flowDao.findProvinceIDCFlowMap(id, type, starttime, endtime);
		}else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			list = flowDao.findProvinceIDCFlowMapOfDay(id, type, starttime, endtime);
		}
		for (Map<String, String> map : list) {
			String val = map.get("val") != null ? Utils.valueOf(map.get("val")) : "—";
			String score = map.get("score") != null ? Utils.valueOf(map.get("score")) : "0";
			map.put("val", val);
			map.put("score", score);
		}
		return list;
	}

	
	

	@Override
	public List<Map<String, Object>> findDIC(String type) {
		/**
		 * 
		 * ING_IDX __ 全部 视频（含P2P） P2P下载 HTTP及其他
		 * FLOW_PM_EXIT—— 省网出口 三方出口 IDC 区域
		 * 
		 */
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = flowDao.findDIC(type);
		Map<String, Object> map = new HashMap<String,Object>();
		//OBJECTID,OBJECTNAME
		map.put("OBJECTID", null);
		if (type.equals("ING_IDX")) {
			map.put("OBJECTNAME", "全部");
			list.add(0, map);
		}
		if (type.equals("FLOW_PM_EXIT")){
			map.put("OBJECTNAME", "区域");
			list.add(map);
		}
		
		return list;
	}


	@Override
	public List<Map<String, Object>> findDICChild(String parentid) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list =  flowDao.findDICChild(parentid);
		if(list != null && list.size() > 0) {
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("OBJECTID", null);
			map.put("OBJECTNAME", "全部");
			list.add(0, map);
		}
		return list;
	}


	@Override
	public Page findCustomBusiFlow(String timeType, String type, String dirId,
			String starttimeStr, String endtimeStr, int pageSize, int pageNo) throws ParseException {
		// TODO Auto-generated method stub
		Date starttime,endtime;
		SimpleDateFormat sdf;
		if(timeType.equals("month")) {
			sdf=new SimpleDateFormat("yyyy-MM");
		} else {
			sdf=new SimpleDateFormat("yyyy-MM-dd");
		}
		starttime = sdf.parse(starttimeStr);
		endtime = sdf.parse(endtimeStr);
		if(timeType.equals("month")){//当月最后一天
			endtime = DateUtil.getMothLastDay(endtime);
		} else {
			endtime = DateUtil.getDayLast(endtime);
		}
			
		List<CustomFlowDirection> list = flowDao.findCustomBusiFlow(timeType, type, dirId, starttime, endtime, pageSize, pageNo);
		int rowCount = flowDao.findCustomBusiFlowTotal(timeType, type, dirId, starttime, endtime);
		Page page = new Page();
		page.setResultList(list);
		page.setRowCount(rowCount);
		
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		return page;
	}


	@Override
	public Page findCustomFlowComponent(String timeType, String dirname,
			String kpiid, String starttimeStr, String endtimeStr, int pageSize, int pageNo) throws ParseException {
		Date starttime,endtime;
		SimpleDateFormat sdf;
		if(timeType.equals("month")) {
			sdf=new SimpleDateFormat("yyyy-MM");
		} else {
			sdf=new SimpleDateFormat("yyyy-MM-dd");
		}
		starttime = sdf.parse(starttimeStr);
		endtime = sdf.parse(endtimeStr);
		if(timeType.equals("month")){//当月最后一天
			endtime = DateUtil.getMothLastDay(endtime);
		} else {
			endtime = DateUtil.getDayLast(endtime);
		}
		
		if(dirname.equals("网内他省")){
			dirname = "网内%";
		}
//		if(dirname.equals("网外")){
//			dirname = "网外";
//		}
		List<HashMap<String,Object>> list = flowDao.findCustomFlowComponent(timeType, dirname, kpiid, starttime, endtime, pageSize, pageNo);
		int rowCount = flowDao.findCustomFlowComponentTotal(timeType, dirname, kpiid, starttime, endtime);
		Page page = new Page();
		page.setResultList(list);
		page.setRowCount(rowCount);
		
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		return page;
	}


	@Override
	public Page findCustomComponentDetails(String timeType, String dirid,
			String kpiid, String starttimeStr, String endtimeStr, int pageSize,
			int pageNo) throws ParseException {
		
		Date starttime,endtime;
		SimpleDateFormat sdf;
		if(timeType.equals("month")) {
			sdf=new SimpleDateFormat("yyyy-MM");
		} else {
			sdf=new SimpleDateFormat("yyyy-MM-dd");
		}
		starttime = sdf.parse(starttimeStr);
		endtime = sdf.parse(endtimeStr);
		if(timeType.equals("month")){//当月最后一天
			endtime = DateUtil.getMothLastDay(endtime);
		} else {
			endtime = DateUtil.getDayLast(endtime);
		}
		List<HashMap<String,Object>> list = flowDao.findCustomComponentDetails(timeType, dirid, kpiid, starttime, endtime, pageSize, pageNo);
		int rowCount = flowDao.findCustomComponentDetailsTotal(timeType, dirid, kpiid, starttime, endtime);
		Page page = new Page();
		page.setResultList(list);
		page.setRowCount(rowCount);
		
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		return page;
	}


	@Override
	public Page findCustomResComponentDetails(String timeType, String dirid,
			String kpiid, String starttimeStr, String endtimeStr, int pageSize,
			int pageNo) throws ParseException {
		Date starttime,endtime;
		SimpleDateFormat sdf;
		if(timeType.equals("month")) {
			sdf=new SimpleDateFormat("yyyy-MM");
		} else {
			sdf=new SimpleDateFormat("yyyy-MM-dd");
		}
		starttime = sdf.parse(starttimeStr);
		endtime = sdf.parse(endtimeStr);
		if(timeType.equals("month")){//当月最后一天
			endtime = DateUtil.getMothLastDay(endtime);
		} else {
			endtime = DateUtil.getDayLast(endtime);
		}
		List<HashMap<String,Object>> list = flowDao.findCustomResComponentDetails(timeType, dirid, kpiid, starttime, endtime, pageSize, pageNo);
		int rowCount = flowDao.findCustomResComponentDetailsTotal(timeType, dirid, kpiid, starttime, endtime);
		Page page = new Page();
		page.setResultList(list);
		page.setRowCount(rowCount);
		
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		return page;
	}





	
	
	

	@Override
	public Page findCustomCityTelecomUnicomFlow(String timeType,
			String opername, String cityid, String starttimeStr,
			String endtimeStr, int pageSize, int pageNo) throws ParseException {
		Date starttime,endtime;
		SimpleDateFormat sdf;
		if(timeType.equals("month")) {
			sdf=new SimpleDateFormat("yyyy-MM");
		} else {
			sdf=new SimpleDateFormat("yyyy-MM-dd");
		}
		starttime = sdf.parse(starttimeStr);
		endtime = sdf.parse(endtimeStr);
		if(timeType.equals("month")){//当月最后一天
			endtime = DateUtil.getMothLastDay(endtime);
		} else {
			endtime = DateUtil.getDayLast(endtime);
		}
		List<HashMap<String,Object>> list = flowDao.findCustomCityTelecomUnicomFlow(timeType, opername, cityid, starttime, endtime, pageSize, pageNo);
		int rowCount = flowDao.findCustomCityTelecomUnicomFlowTotal(timeType, opername, cityid, starttime, endtime);
		Page page = new Page();
		page.setResultList(list);
		page.setRowCount(rowCount);
		
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		return page;
	}

	private Page findCustomKeyIndex(String timeType,String type,String type2,String kpiid,String starttimeStr, String endtimeStr, int pageSize, int pageNo) throws ParseException{
		Date starttime,endtime;
		SimpleDateFormat sdf;
		if(timeType.equals("month")) {
			sdf=new SimpleDateFormat("yyyy-MM");
		} else {
			sdf=new SimpleDateFormat("yyyy-MM-dd");
		}
		starttime = sdf.parse(starttimeStr);
		endtime = sdf.parse(endtimeStr);
		if(timeType.equals("month")){//当月最后一天
			endtime = DateUtil.getMothLastDay(endtime);
		} else {
			endtime = DateUtil.getDayLast(endtime);
		}
		List<HashMap<String,Object>> list = flowDao.findCustomKeyIndex(timeType, type, type2, kpiid, starttime, endtime, pageSize, pageNo);
		int rowCount = flowDao.findCustomKeyIndexTotal(timeType, type, type2, kpiid, starttime, endtime);
		Page page = new Page();
		page.setResultList(list);
		page.setRowCount(rowCount);
		
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		return page;
	}

	@Override
	public Page findCustomBusKeyIndex(String timeType, String type2,
			String kpiid, String starttimeStr, String endtimeStr, int pageSize,
			int pageNo)  throws ParseException {
		// TODO Auto-generated method stub
		return findCustomKeyIndex(timeType, "bus", type2, kpiid, starttimeStr, endtimeStr, pageSize, pageNo);
	}


	@Override
	public Page findCustomResKeyIndex(String timeType, String kpiid,
			String starttimeStr, String endtimeStr, int pageSize, int pageNo) throws ParseException {
		// TODO Auto-generated method stub
		return findCustomKeyIndex(timeType, "res", "IDC", kpiid, starttimeStr, endtimeStr, pageSize, pageNo);
	}


	@Override
	public Page findCustomResDirection(String timeType, String dirid,
			String starttimeStr, String endtimeStr, int pageSize, int pageNo) throws ParseException {
		Date starttime,endtime;
		SimpleDateFormat sdf;
		if(timeType.equals("month")) {
			sdf=new SimpleDateFormat("yyyy-MM");
		} else {
			sdf=new SimpleDateFormat("yyyy-MM-dd");
		}
		starttime = sdf.parse(starttimeStr);
		endtime = sdf.parse(endtimeStr);
		if(timeType.equals("month")){//当月最后一天
			endtime = DateUtil.getMothLastDay(endtime);
		} else {
			endtime = DateUtil.getDayLast(endtime);
		}
		List<HashMap<String,Object>> list = flowDao.findCustomResDirection(timeType, dirid, starttime, endtime, pageSize, pageNo);
		int rowCount = flowDao.findCustomResDirectionTotal(timeType, dirid, starttime, endtime);
		Page page = new Page();
		page.setResultList(list);
		page.setRowCount(rowCount);
		
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		return page;
	}

}

package com.ultrapower.flowmanage.modules.quality.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.Region;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.common.utils.DateUtil;
import com.ultrapower.flowmanage.common.utils.FlowTree;
import com.ultrapower.flowmanage.common.utils.Utils;
import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.common.vo.Tree;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;
import com.ultrapower.flowmanage.modules.health.service.HealthService;
import com.ultrapower.flowmanage.modules.health.vo.CityContrastVo;
import com.ultrapower.flowmanage.modules.quality.dao.QualityDao;
import com.ultrapower.flowmanage.modules.quality.dao.mapper.QualityMapper;
import com.ultrapower.flowmanage.modules.quality.service.QualityService;
import com.ultrapower.flowmanage.modules.quality.vo.Test;
import com.ultrapower.flowmanage.modules.quality.vo.ThresholdConfigVo;


@Service("qualityService")
public class QualityServiceImpl implements QualityService {
	private static Logger logger = Logger.getLogger(QualityServiceImpl.class);
	@Resource(name="qualityDao")
	private QualityDao qualityDao;
	@Autowired
	private HealthService healthservice;
	@Autowired
	private SqlSessionFactoryBean sessionFactory;


	public List<Map<String, Object>> findCityTop200WebsiteTimedelay(String startTime,
			String endTime, String timeType, String companyId) throws Exception{
		/*//获取当前月第一天时间
		Date getMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		Date nextMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");*/
		List<Date> listDate = getDate4StartEnd(startTime, endTime, timeType);
		Date start = listDate.get(0);
		Date end = listDate.get(1);
		List<Map<String, Object>> list = qualityDao.findCityTop200WebsiteTimedelay(start, end,timeType,companyId);
		for (Map<String, Object> map : list) {
			map.put("successrate", Utils.valueOf(String.valueOf(map.get("successrate")))+"%");
			map.put("packetlossrate", Utils.valueOf(String.valueOf(map.get("packetlossrate")))+"Kbp/s");
			map.put("avgtimedelay", Utils.valueOf(String.valueOf(map.get("avgtimedelay"))));
		}
		return list;
	}
	private List<Date> getDate4StartEnd(String startTime,
			String endTime, String timeType){
		Date start = null;
		Date end = null;
		if(endTime == null) {
			if(timeType.equals("month")) {
				//获取当前月第一天时间
				start = DateUtil.StringToDate(DateUtil.getMonthFirstDay(startTime, 0), "yyyy-MM-dd hh:mm:ss");
				end  = start;
				/*//获取下个月第一天时间
				 end = DateUtil.StringToDate(DateUtil.getMonthFirstDay(startTime, 1), "yyyy-MM-dd hh:mm:ss");*/
			} else if(timeType.equals("week") ) {
				Map<String,Date> map = DateUtil.getDateByType(timeType, startTime);
				start = map.get("startTime");
				end  = map.get("endTime");
			} else if(timeType.equals("day")) {
				start = DateUtil.StringToDate(startTime,"yyyy-MM-dd");
				end = start;
			}

		} else {
			if(timeType.equals("day")) {
				start = DateUtil.StringToDate(startTime,"yyyy-MM-dd");
				end = DateUtil.StringToDate(endTime,"yyyy-MM-dd");
			} else if(timeType.equals("month")) {
				start = DateUtil.StringToDate(startTime,"yyyy-MM");
				end = DateUtil.StringToDate(endTime,"yyyy-MM");
			}

		}
		List<Date> list = new ArrayList<Date>();
		list.add(0,start);
		list.add(1,end);
		return list;
	}
	public List<Map<String, Object>> findGroupHotspotTop10Net(String startTime,
			String endTime, String timeType, String companyId) throws Exception {

		List<Date> listDate = getDate4StartEnd(startTime, endTime, timeType);
		Date start = listDate.get(0);
		Date end = listDate.get(1);

		List<Map<String, Object>> list = qualityDao.findGroupHotspotTop10Net(start, end,timeType,companyId);
		for (Map<String, Object> map : list) {
			map.put("videoPausePerc", Utils.valueOf(String.valueOf(map.get("videoPausePerc")))+"%");
			map.put("tmVideoDownloadRate", Utils.valueOf(String.valueOf(map.get("tmVideoDownloadRate")))+"KBps");
			map.put("lmVideoDownloadRate", Utils.valueOf(String.valueOf(map.get("lmVideoDownloadRate")))+"KBps");
		}
		return list;
	}

	//接入KQI地市对比
	public	List<Map<String,String>> findKQIMenu() throws Exception{
		return qualityDao.findKQIMenu();
	}
	//接入KQI出口对比
	public List<FsChartVo>findKQIExit(String exitid,String getTime)throws Exception{
		//获取当前月第一天时间
		Date getMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		Date nextMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");

		return qualityDao.findKQIExit(exitid, getMonthFirstDay, nextMonthFirstDay);
	}
	//接入KQI地市对比
	public List<Map<String, Object>> findKQICityComponent(String kqiType,String kpiId,String startTime,
			String endTime, String timeType, String companyId,String cityId) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Date> listDate = getDate4StartEnd(startTime, endTime, timeType);
		Date start = listDate.get(0);
		Date end = listDate.get(1);

		//接入或业务KQI地市对比
		List<FsChartVo> kqiList = qualityDao.findKQICityComponent(kqiType, kpiId, start, end,timeType,companyId,cityId);
		if(kqiList.size() > 0){
			/*List<String> avgList1 = new ArrayList<String>();
			List<String> avgList2 = new ArrayList<String>();
			for (FsChartVo kqi : kqiList) {
				avgList1.add(Utils.valueOf(kqi.getValue1()));
				avgList2.add(Utils.valueOf(kqi.getValue2()));
			}
			String avg2 = Utils.getAverage(avgList2);//求KQI平均值
			String avg1 = Utils.getAverage(avgList1);//求KQI平均值
*/
			List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
			Map map_lv11 = new HashMap();
			map_lv11.put("seriesName", "飞思达");
			map_lv11.put("color", "798EDD");
			list1.add(map_lv11);
			List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();

			Map map_lv12 = new HashMap();
			map_lv12.put("seriesName", "普天");
			map_lv12.put("color", "ff0000");
			list2.add(map_lv12);
			List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();

			Map map_lv13 = new HashMap();
			map_lv13.put("seriesName", "阀值");
			map_lv13.put("renderAs", "Line");
			list3.add(map_lv13);//{seriesName:'2004', renderAs:'Line'},
			for (int i = 0; i < kqiList.size(); i++) {
				FsChartVo kqi = kqiList.get(i);
				Map map_lv = new HashMap();
				map_lv.put("label", kqi.getLabel());
				list.add(map_lv);

				Map map_lv1 = new HashMap();
				map_lv1.put("value", kqi.getValue1());


				map_lv1.put("color", "798EDD");
				/*if(Double.valueOf(kqi.getValue1()) < Double.valueOf(avg1)){
					map_lv1.put("color", "ff0000");
				}else{
					map_lv1.put("color","B8DCF8");
				}*/

				list1.add(map_lv1);


				Map map_lv2 = new HashMap();
				map_lv2.put("value", kqi.getValue2());



				map_lv2.put("color", "ff0000");
				/*if(Double.valueOf(kqi.getValue2()) < Double.valueOf(avg2)){
					map_lv2.put("color", "798EDD");
				}else{
					map_lv2.put("color","CD1515");
				}*/

				list2.add(map_lv2);


				Map map_lv3 = new HashMap();
				map_lv3.put("value", kqi.getLineValue());
				map_lv3.put("color","B8DCF8");

				list3.add(map_lv3);
			}
			if(companyId.equals("1026")) {
				list.addAll(list1);
				list.addAll(list2);
				list.addAll(list3);

			}else if(companyId.equals("1027")) {
				list.addAll(list1);
				list.addAll(list3);
			}else if(companyId.equals("1028")) { 

				list.addAll(list2);
				list.addAll(list3);
			}
		}
		System.out.println("list--"+list);
		//			
		//		}	else if(companyId.equals("1028")) { 
		//		if(companyId.equals("1026")) {
		//			Map map_lv11 = new HashMap();
		//			map_lv11.put("seriesName", "飞思达");
		//			list.add(map_lv11);
		//			for (int i = 0; i < kqiList.size(); i++) {
		//				FsChartVo kqi = kqiList.get(i);
		//				Map map_lv = new HashMap();
		//				map_lv.put("value", kqi.getValue1());
		//				if(Double.valueOf(kqi.getValue1()) < Double.valueOf(avg1)){
		//					map_lv.put("color", "ff0000");
		//				}else{
		//					map_lv.put("color","B8DCF8");
		//				}
		//				list.add(map_lv);
		//			}
		//			Map map_lv12 = new HashMap();
		//			map_lv12.put("seriesName", "普天");
		//			list.add(map_lv12);
		//			for (int i = 0; i < kqiList.size(); i++) {
		//				FsChartVo kqi = kqiList.get(i);
		//				Map map_lv = new HashMap();
		//				map_lv.put("value", kqi.getValue2());
		//				if(Double.valueOf(kqi.getValue2()) < Double.valueOf(avg2)){
		//					map_lv.put("color", "ff0000");
		//				}else{
		//					map_lv.put("color","B8DCF8");
		//				}
		//				list.add(map_lv);
		//			}
		//			
		//			
		//			
		//			
		//		} else if(companyId.equals("1027")) {
		//			
		//		}	else if(companyId.equals("1028")) { 
		//			Map map_lv12 = new HashMap();
		//			map_lv12.put("seriesName", "普天");
		//			list.add(map_lv12);
		//			for (int i = 0; i < kqiList.size(); i++) {
		//				FsChartVo kqi = kqiList.get(i);
		//				Map map_lv = new HashMap();
		//				map_lv.put("value", kqi.getValue2());
		//				if(Double.valueOf(kqi.getValue2()) < Double.valueOf(avg2)){
		//					map_lv.put("color", "ff0000");
		//				}else{
		//					map_lv.put("color","B8DCF8");
		//				}
		//				list.add(map_lv);
		//			}
		//		}
		//		Map map_lv11 = new HashMap();
		//		map_lv11.put("seriesName", "阀值");
		//		map_lv11.put("renderAs", "Line");
		//		list.add(map_lv11);//{seriesName:'2004', renderAs:'Line'},
		//		
		//		for (int i = 0; i < kqiList.size(); i++) {
		//			FsChartVo kqi = kqiList.get(i);
		//			Map map_lv = new HashMap();
		//			map_lv.put("value", kqi.getLineValue());
		//			
		//			list.add(map_lv);
		//		}
		return list;

	}
	//	public Map<String, Object> findKQICityComponent(String kqiType,String kpiId,String startTime,
	//			String endTime, String timeType, String companyId,String cityId) throws Exception{
	//		Map<String, Object> map = new HashMap<String, Object>();
	//		List<String> avgList = new ArrayList<String>();
	//		StringBuilder sb = null;
	//
	//		List<Date> listDate = getDate4StartEnd(startTime, endTime, timeType);
	//		Date start = listDate.get(0);
	//		Date end = listDate.get(1);
	//		/*//获取当前月第一天时间
	//		Date getMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
	//		//获取下个月第一天时间
	//		Date nextMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");*/
	//		//接入或业务KQI地市对比
	//		List<FsChartVo> kqiList = qualityDao.findKQICityComponent(kqiType, kpiId, start, end,timeType,companyId,cityId);
	//		//接入或业务KPI地市对比
	//		List<Map<String, String>> kpiList = qualityDao.findKPICityComponent(kqiType, kpiId, start, end,timeType,companyId,cityId);
	//		for (FsChartVo kqi : kqiList) {
	//			avgList.add(Utils.valueOf(kqi.getValue()));
	//		}
	//		String avg = Utils.getAverage(avgList);//求KQI平均值
	//		map.put("target", avg);
	//		for (FsChartVo kqi : kqiList) {
	//			//如果KQI值小于全省平均值,设置地市对应的柱子为红色
	//			if(Double.valueOf(kqi.getValue()) < Double.valueOf(avg)){
	//				kqi.setColor("ff0000");
	//			}else{
	//				kqi.setColor("B8DCF8");
	//			}
	//			kqi.setValue(Utils.valueOf(kqi.getValue()));
	//			sb = new StringBuilder();
	//			if(kqi.getKqiName() != null){
	//				sb.append(kqi.getLabel()+""+kqi.getKqiName()+"KQI值:"+kqi.getValue()+"{br}"+kqi.getLabel()+""+kqi.getKqiName()+"KPI值");
	//			}
	//			for (Map<String, String> kpi : kpiList) {
	//				if(kpi.get("kpiName") != null){
	//					if(kqi.getLabel().equals(kpi.get("label"))){//设置柱状图toolText值
	//						sb.append("{br}"+kpi.get("kpiName")+"："+Utils.valueOf(String.valueOf(kpi.get("value")))+"%");
	//					}
	//				}
	//			}
	//			kqi.setToolText(sb.toString());
	//		}
	//		map.put("value", kqiList);
	//		return map;
	//	}
	//业务KQI地市对比 -导航详情
	@Override
	public List<Tree> getBusiKqiNavTable(String type) throws Exception {

		return qualityDao.getBusiKqiNavTable(type);
	}
	//创建业务KQI地市对比 导航树 
	@Override
	public Tree buildBusiKqiNavTree(String type) throws Exception {

		List<Tree> list = getBusiKqiNavTable( type);
		//临时map做树的列表存储, key是树的唯一id,value是树
		Map<String, Tree> treeMap = new HashMap<String, Tree>(list.size());
		for (Tree tempTree : list) {
			treeMap.put(tempTree.getId(), tempTree);
		}
		//做整棵树的封装
		Tree rootTree = FlowTree.buildTree(treeMap);
		return rootTree;
	}
	//业务KQI地市对比
	public	List<Map<String,String>> findBUIKQIMenu() throws Exception{
		return qualityDao.findBUIKQIMenu();
	}

	//根据业务类型查询互联网投诉及用户满意度地市对比-互联网质量评估
	public Map<String, Object> findNetComplaintCSATCityContrast(String kpiId,String getTime) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		//获取当前月第一天时间
		Date getMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		Date nextMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		List<FsChartVo> lists = qualityDao.findNetComplaintCSATCityContrast(kpiId, getMonthFirstDay, nextMonthFirstDay);
		for (FsChartVo fs : lists) {
			fs.setValue(Utils.valueOf(fs.getValue()));
			list.add(fs.getValue());
		}
		String avg = Utils.getAverage(list);
		map.put("value", lists);
		map.put("avg", avg);
		return map;
	}

	//根据字典表类型查询客户投诉及满意度
	public List<Map<String, Object>> findBusinessName(String bizType) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list1 = qualityDao.findBusinessName(bizType);
		for (Map<String, Object> map : list1) {
			if(!map.get("name").equals("IM-MSN") && !map.get("name").equals("IM-QQ")){
				list.add(map);
			}
		}
		return list;
	}

	//根据地市ID和业务类型查询省内热点网站情况
	public List<Map<String, Object>> findProvincialHotspotNetTable(String bizId, String cityId, String startTime,
			String endTime, String timeType, String companyId) throws Exception {
		/*//获取当前月第一天时间
		Date getMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		Date nextMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");*/
		List<Date> listDate = getDate4StartEnd(startTime, endTime, timeType);
		Date start = listDate.get(0);
		Date end = listDate.get(1);
		List<Map<String, Object>> list = qualityDao.findProvincialHotspotNetTable(bizId, cityId, start, end,timeType,companyId);
		for (Map<String, Object> map : list) {
			if(String.valueOf(map.get("kpiName")).indexOf("成功率") != -1 
					|| String.valueOf(map.get("kpiName")).indexOf("掉线率") != -1 
					|| String.valueOf(map.get("kpiName")).indexOf("完整率") != -1){
				map.put("cmcVal", Utils.valueOf(String.valueOf(map.get("cmcVal")))+"%");
				map.put("ctcVal", Utils.valueOf(String.valueOf(map.get("ctcVal")))+"%");
				map.put("crcVal", Utils.valueOf(String.valueOf(map.get("crcVal")))+"%");
			}else{
				map.put("cmcVal", Utils.valueOf(String.valueOf(map.get("cmcVal"))));
				map.put("ctcVal", Utils.valueOf(String.valueOf(map.get("ctcVal"))));
				map.put("crcVal", Utils.valueOf(String.valueOf(map.get("crcVal"))));
			}
		}		
		return list;
	}

	//根据接入或业务类型查询KQI地市对比(菜单)
	public List<Map<String, Object>> findKQICityMenu(String objectType)throws Exception {
		List<Map<String, Object>> kqiList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> lists = null;
		List<Map<String, Object>> list = qualityDao.findKQICityMenu(objectType);
		if("QU_ACC_KQI".equals(objectType)){
			for (Map<String, Object> map : list) {
				if(String.valueOf(map.get("lev")).equals("1")){
					kqiList.add(map);
				}
			}
		}else{
			for (Map<String, Object> map : list) {
				if(String.valueOf(map.get("lev")).equals("2")){
					kqiList.add(map);
				}
			}
		}
		for (Map<String, Object> kqi : kqiList) {
			lists = new ArrayList<Map<String,Object>>();
			for (Map<String, Object> map : list) {
				if(String.valueOf(kqi.get("objectId")).equals(String.valueOf(map.get("parenntId")))){
					lists.add(map);
				}
			}
			kqi.put("values", lists);
		}
		return kqiList;
	}

	//根据业务类型和业务级别查询接入KQI出口指标名称
	public List<Map<String, String>> findAccKQIExitName(String objectType,String lev) throws Exception {
		return qualityDao.findAccKQIExitName(objectType, lev);
	}

	//根据KPIID查询接入KQI出口对比
	public List<FsChartVo> findAccKQIExitContrast(String kpiId, String getTime)throws Exception {
		List<FsChartVo> list = new ArrayList<FsChartVo>();
		//List<String> avgList = new ArrayList<String>();
		StringBuilder sb = null;
		//获取当前月第一天时间
		Date getMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		//获取下个月第一天时间
		Date nextMonthFirstDay = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		//接入KQI出口对比
		List<FsChartVo> kqiList = qualityDao.findAccKQIExitContrast(kpiId, getMonthFirstDay, nextMonthFirstDay);
		//接入KPI出口对比
		List<Map<String, String>> kpiList =  qualityDao.findAccKPIExitContrast(kpiId, getMonthFirstDay, nextMonthFirstDay);

		/*for (FsChartVo kqi : kqiList) {
			avgList.add(Utils.valueOf(kqi.getValue()));
		}
		//求KQI平均值
		String avg = Utils.getAverage(avgList);*/

		for (FsChartVo kqi : kqiList) {
			/*//如果KQI值小于全省平均值,设置地市对应的柱子为红色
			if(Double.valueOf(kqi.getValue()) < Double.valueOf(avg)){
				kqi.setColor("ff0000");
			}else{
				kqi.setColor("B8DCF8");
			}*/

			kqi.setValue(Utils.valueOf(kqi.getValue()));
			sb = new StringBuilder();
			if(kqi.getKqiName() != null){
				sb.append(kqi.getLabel()+""+kqi.getKqiName()+"KPI值");
			}
			boolean flag = false;
			for (Map<String, String> kpi : kpiList) {
				if(kpi.get("kpiName") != null){
					flag = true;
					if(kqi.getLabel().equals(kpi.get("label"))){//设置柱状图toolText值
						sb.append("{br}"+kpi.get("kpiName")+"："+Utils.valueOf(String.valueOf(kpi.get("value")))+"%");
					}
				}
			}
			if(flag == true){
				kqi.setToolText(sb.toString());
			}
		}
		return kqiList;
	}

	//互联网质量分析表格数据Excel导出
	public byte[] qualityExcelTable(String startTime,
			String endTime, String timeType) throws Exception {
		/*		List<Map<String, Object>> siteTimedelayNetList = findCityTop200WebsiteTimedelay(startTime,endTime,timeType,null);//地市TOP200网站时延
		List<Map<String, Object>> top10NetList = findGroupHotspotTop10Net(startTime,endTime,timeType,null);//集团热点TOP10网站信息
		List<Map<String, Object>> bizNameList = findBusinessName("QU_HOTSPOT");//业务名称
		List<CityContrastVo> cityList = healthservice.findCityInfo();//地市名称

		 *//************************** Excel导出   *******************************//*
		 *//*** 省内热点网站情况***//*
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = null;
		String[] str = new String[]{"地市","业务","测试点","移动","电信","铁通"};//表头名称
		String[] mapKeyName = new String[]{"panentName","kpiName","cmcVal","ctcVal","crcVal"};//Map的Key值
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

		cityList.remove(0);//移除全省
		for(int i = 0; i < bizNameList.size(); ++i){//遍历业务名称
			Map<String, Object> bizName = bizNameList.get(i);
			sheet =  wb.createSheet("省内热点网站情况_"+bizName.get("name"));
			sheet.setDefaultRowHeightInPoints(10);   
			sheet.setDefaultColumnWidth(30);
			HSSFRow rowTit = sheet.createRow(0);//添加表头
			for (int j = 0; j < str.length; ++j) {//遍历表头单元格信息
				String tit = (String)str[j];
				HSSFCell cell = rowTit.createCell(j);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
			}
			int rowSize = 0;
			for(int j = 0; j < cityList.size(); ++j){//遍历地市名称
				CityContrastVo city = cityList.get(j);
				//根据业务ID、地市ID、时间查询省内热点网站情况
				List<Map<String, Object>> list = findProvincialHotspotNetTable(String.valueOf(bizName.get("id")), city.getCityid(), startTime,endTime,timeType,null);
				if(list.size() > 0){
					for (int k = 0; k < list.size(); ++k) {
						Map<String, Object> map = (Map<String, Object>)list.get(k);
						HSSFRow row = sheet.createRow((k + 1 ) +  rowSize);
						HSSFCell cell = row.createCell(0);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(new HSSFRichTextString(city.getCityname()));
						for (int n = 0; n < mapKeyName.length; n++) {
							cell = row.createCell(n+1);
							cell.setCellStyle(cellStyle);
							String value = map.get(mapKeyName[n]) == null ? "" : String.valueOf(map.get(mapKeyName[n]));
							cell.setCellValue(new HSSFRichTextString(value));
						}
					}
					sheet.addMergedRegion(new Region(rowSize + 1, (short) 0, list.size() + rowSize,(short) 0));//地市跨行
					sheet.addMergedRegion(new Region(rowSize + 1, (short) 1, list.size() + rowSize,(short) 1));//业务跨行
					rowSize += list.size();
				}
			}
		}

		  *//*** 地市TOP200网站时延***//*
		sheet =  wb.createSheet("地市TOP200网站时延");
		str = new String[]{"测试点","成功率","丢包率","平均时延(毫秒)"};//表头名称
		mapKeyName = new String[]{"city","successrate","packetlossrate","avgtimedelay"};//Map的Key值
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		HSSFRow rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
		}
		//内容
		if(siteTimedelayNetList.size() > 0){
			for (int i = 0; i < siteTimedelayNetList.size(); ++i) {
				Map<String, Object> map = (Map<String, Object>)siteTimedelayNetList.get(i);
				HSSFRow row = sheet.createRow(i + 1);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}

		   *//*** 集团热点TOP10网站信息 ***//*
		sheet =  wb.createSheet("集团热点TOP10网站信息");
		sheet.setDefaultRowHeightInPoints(10);   
		sheet.setDefaultColumnWidth(30);
		str = new String[]{"省份","网站","本月平均停顿次数","上月平均停顿次数","次数变化","视频停顿时长占比(%)","本月平均视频下载速率(KBps)","上月平均视频下载速率(KBps)"};
		mapKeyName = new String[]{"city","kpiName","tmAvgPauseNumber","lmAvgPauseNumber","numberChange","videoPausePerc","tmVideoDownloadRate","lmVideoDownloadRate"};//Map的Key值
		rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			String tit = (String)str[i];
			HSSFCell cell = rowTit.createCell(i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new HSSFRichTextString(tit));
		}
		//内容
		if(top10NetList.size() > 0){
			for (int i = 0; i < top10NetList.size(); ++i) {
				Map<String, Object> map = (Map<String, Object>)top10NetList.get(i);
				HSSFRow row = sheet.createRow(i + 1);
				for (int j = 0; j < mapKeyName.length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
			sheet.addMergedRegion(new Region(1, (short) 0, top10NetList.size(),(short) 0));//湖北省跨行
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
		}*/

		String[] str = {"HTTP浏览业务","HTTP/FTP下载业务","网页视频业务","即时通讯业务","邮件业务","网络游戏"};
		int[] num={2,2,3,4,2,3};
		String[] str_cn = {"拨测时间","拨测厂商","所属地市","网页打开成功率","页面打开时长","下载成功率","下载速率","视频连接成功率","视频开始播放等待时长","视频播放停顿次数","连接时间","鉴权时长","总时间","成功率","邮件发送速率","邮件接收速率","时延","抖动","丢包率"};
		String[] str_en = {};

		try {
			FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
			Workbook wb = new SXSSFWorkbook(100);
			wb.write(fileOut);
			fileOut.close();
			// out = pageContext.pushBody();
			//			ByteArrayOutputStream fos=new ByteArrayOutputStream();
			//			wb.write(fos);
			//			byte[] content =fos.toByteArray();
			//			return content;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//		 for(int rownum = 0; rownum < 100000; rownum++){
		//		 Row row = sh.createRow(rownum); 
		//		 for(int cellnum = 0; cellnum < 10; cellnum++){ 
		//		 Cell cell = row.createCell(cellnum); 
		//		 String address = new CellReference(cell).formatAsString(); 
		//		 cell.setCellValue(address); 
		//		 } 
		//		 }

		return null;
	}
	public byte[] daochuTianzhouyue(int yewuType,String timeType,String city, String company, String starttimeStr,
			String endtimeStr,int pageSize, int pageNo) throws ParseException, IOException {
		List<Date> listDate = getDate4StartEnd(starttimeStr, endtimeStr, timeType);
		Date start = listDate.get(0);
		Date end = listDate.get(1);
		return daochu(yewuType, timeType, city, company, start, end, pageSize, pageNo);
	}
	private byte[] daochu(int yewuType,String timeType,String city, String company, Date starttime,
			Date endtime,int pageSize, int pageNo)throws ParseException, IOException{
		Workbook wb = null;
		String[] str = null;
		int[] num = null;
		String[] str_cn = null;
		String[] str_en = null;
		List<HashMap<String,Object>> list = null;
		if(yewuType == 1) {
			list = qualityDao.getZidingyi1(timeType,city, company, starttime, endtime,pageSize,pageNo);
			str = new String[]{"HTTP浏览业务","HTTP/FTP下载业务","网页视频业务","即时通讯业务","邮件业务","网络游戏"};
			num = new int[]{2,2,3,4,2,3};
			str_cn = new String[]{"拨测时间","拨测厂商","所属地市",
					"网页打开成功率","页面打开时长","下载成功率","下载速率","视频连接成功率","视频开始播放等待时长","视频播放停顿次数","连接时间","鉴权时长","总时间","成功率","邮件发送速率","邮件接收速率","时延","抖动","丢包率"};
			str_en = new String[]{"COLLECTTIME","company","city","SuccessRate","OpenPageTime","DealSuccRate","DealSpeed","SuccessRate","FirstByteOffsetTime","CacheNum","SetupConnectOffsetTime","ConventionTime","LoginOffsetTime","LinkRate","UploadSendSpeed","DownloadSendSpeed","Delay","Shake","LoseByteRate"};
			wb = setWorkbook("业务KQI地市对比",list,str_cn,str_en,str,num);
		} else if(yewuType == 2){
			list = qualityDao.getZidingyi2(timeType,city, company, starttime, endtime,pageSize,pageNo);
			str = new String[]{"TOP2000网页","视频","IM-飞信","游戏","邮箱"};
			num = new int[]{7,8,4,3,5};
			str_cn = new String[]{"拨测时间","拨测厂商","所属地市",
					"页面全部下载平均时延（S）","网页显示完整率","平均90%元素下载速率（KB/S)","连接成功率","成功率","DNS解析成功率",
					"90%元素下载平均时延","平均首帧时延（秒）","平均缓存总时长（秒）","平均播放速率（KB/S)","平均播放时延（秒）","平均DNS查询时延（秒）",
					"连接成功率","成功率","DNS解析成功率","总时间","连接时间","鉴权时长","成功率","时延","抖动","丢包率","认证成功率","平均下载速率（KB/S)","平均发送邮件时延（S）","连接成功率","成功率"};
			str_en = new String[]{"COLLECTTIME","company","city","Url100POffSetTime","WebDispCompRate","DealSpeed","ConnSuccRate","SuccessRate","DnsResSuccRATE","Url90POffSetTime","FirstByteOffsetTime",
					"acheOffsetTime","DealSpeed","DealOffsetTime","DnsQueryOffsetTime","ConnSuccRate","SuccessRate","DnsResSuccRATE","LoginOffsetTime","SetupConnectOffsetTime","ConventionTime","LinkRate","Delay","Shake","LoseByteRate","AuthSuccRate","DownloadSendSpeed","AvgSendMailDelay","ConnSuccRate","SuccRate"};
			wb = setWorkbook("省内热点网站情况",list,str_cn,str_en,str,num);

		} else if(yewuType == 3){
			list = qualityDao.getZidingyi3(timeType,city, company, starttime, endtime,pageSize,pageNo);
			str_cn = new String[]{"拨测时间","拨测厂商","所属地市",
					"成功率","吞吐率","总时间"};
			str_en = new String[]{"COLLECTTIME","company","city",
					"SuccessRate","DealSpeed","DealTotalOffSetTime"};

			wb = setWorkbook("地市TOPN网站时延",list,str_cn,str_en);

		} else if(yewuType == 4){
			list = qualityDao.getZidingyi4(timeType,city, company, starttime, endtime,pageSize,pageNo);
			str_cn = new String[]{"拨测时间","拨测厂商","所属地市",
					"平均停顿次数","视频停顿时长占比","平均视频下载速率"};
			str_en = new String[]{"COLLECTTIME","company","city",
					"TM_AVGPAUSENUMBER","TM_VIDEODOWNLOADRATE","VIDEOPAUSEPERC"};

			wb = setWorkbook("集团TOPN热点网站信息",list,str_cn,str_en);

		}
		//		FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
		//        wb.write(fileOut);
		//        fileOut.close();
		ByteArrayOutputStream fos=new ByteArrayOutputStream();
		wb.write(fos);
		byte[] content =fos.toByteArray();
		return content;
	}
	public byte[] daochuZidingyi(int yewuType,String timeType,String city, String company, String starttimeStr,
			String endtimeStr,int pageSize, int pageNo) throws ParseException, IOException {

		Date starttime,endtime;
		SimpleDateFormat sdf;
		if(timeType.equals("month")) {
			sdf=new SimpleDateFormat("yyyy-MM");

		} else {
			sdf=new SimpleDateFormat("yyyy-MM-dd");
		}
		starttime = sdf.parse(starttimeStr);
		endtime = sdf.parse(endtimeStr);
		return daochu(yewuType, timeType, city, company, starttime, endtime, pageSize, pageNo);
	}

	private Workbook setWorkbook(String sheetname,List<HashMap<String,Object>> list,String[] str_cn,String[] str_en,String[] str,int[] num){
		Workbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk 
		Sheet sheet = wb.createSheet(sheetname); 
		//标题样式
		CellStyle cellStyleTit = wb.createCellStyle();
		cellStyleTit.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyleTit.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		//			cellStyleTit.setWrapText(true);// 指定单元格自动换行
		Font fontTit = wb.createFont();
		fontTit.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontTit.setFontHeightInPoints((short) 12);
		fontTit.setFontName("微软雅黑");
		fontTit.setFontHeight((short) 220);
		cellStyleTit.setFont(fontTit);

		Row row = sheet.createRow((short) 0);
		Row row1 = sheet.createRow((short) 1);

		for (int i = 0; i < 3; i++) {
			Cell cell = row.createCell((short) i);
			cell.setCellValue(str_cn[i]);
			cell.setCellStyle(cellStyleTit);

			sheet.addMergedRegion(new CellRangeAddress(
					0, //first row (0-based)
					1, //last row  (0-based)
					i, //first column (0-based)
					i  //last column  (0-based)
					));
		}



		for (int i = 3; i < str_cn.length; i++) {
			Cell cell13 = row1.createCell(i);
			cell13.setCellValue(str_cn[i]);
			cell13.setCellStyle(cellStyleTit);
		}
		int cellstart = 3;
		for (int i = 0; i < str.length; i++) {

			Cell cell3 = row.createCell((short) cellstart);
			cell3.setCellValue(str[i]);
			cell3.setCellStyle(cellStyleTit);

			sheet.addMergedRegion(new CellRangeAddress(
					0, //first row (0-based)
					0, //last row  (0-based)
					cellstart, //first column (0-based)
					cellstart+num[i]-1  //last column  (0-based)
					));
			cellstart = cellstart+num[i];
		}
		for (int cIndex = 0; cIndex < str_en.length; cIndex++) {
			sheet.autoSizeColumn(cIndex);
		}
		CreationHelper createHelper = wb.getCreationHelper();
		Pattern p = Pattern.compile("^\\d*(\\.?\\d+)?$");//
		for (int i = 0; i < list.size(); i++) {
			Row row2i = sheet.createRow((short) 2+i);
			HashMap<String,Object> map = list.get(i);
			for (int j = 0; j < str_en.length; j++) {

				if(map.get(str_en[j]) != null){
					Cell cell2ij = row2i.createCell(j);
					//					System.out.println((2+i)+"---"+j+"---"+map.get(str_en[j]).toString());
					//					cell2ij.setCellValue(createHelper.createRichTextString(map.get(str_en[j]).toString()));
					if(p.matcher(map.get(str_en[j]).toString()).matches()) {
						cell2ij.setCellValue(Double.parseDouble(map.get(str_en[j]).toString()));
					} else {
						cell2ij.setCellValue(createHelper.createRichTextString(map.get(str_en[j]).toString()));
					}
				}

			}
		}


		return wb;
	}

	private Workbook setWorkbook(String sheetname,List<HashMap<String,Object>> list,String[] str_cn,String[] str_en){
		Workbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk 
		Sheet sheet = wb.createSheet(sheetname); 
		//标题样式
		CellStyle cellStyleTit = wb.createCellStyle();
		cellStyleTit.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyleTit.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		//			cellStyleTit.setWrapText(true);// 指定单元格自动换行
		Font fontTit = wb.createFont();
		fontTit.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontTit.setFontHeightInPoints((short) 12);
		fontTit.setFontName("微软雅黑");
		fontTit.setFontHeight((short) 220);
		cellStyleTit.setFont(fontTit);

		Row row = sheet.createRow((short) 0);
		/*Row row1 = sheet.createRow((short) 1);*/

		/*for (int i = 0; i < 3; i++) {
			Cell cell = row.createCell((short) i);
		    cell.setCellValue(str_cn[i]);
		    cell.setCellStyle(cellStyleTit);

		    sheet.addMergedRegion(new CellRangeAddress(
		            0, //first row (0-based)
		            1, //last row  (0-based)
		            i, //first column (0-based)
		            i  //last column  (0-based)
		    ));
		}*/



		for (int i = 0; i < str_cn.length; i++) {
			Cell cell13 = row.createCell(i);
			cell13.setCellValue(str_cn[i]);
			cell13.setCellStyle(cellStyleTit);
		}
		for (int cIndex = 0; cIndex < str_en.length; cIndex++) {
			sheet.autoSizeColumn(cIndex);
		}
		/*int cellstart = 3;
	    for (int i = 0; i < str.length; i++) {

	    	Cell cell3 = row.createCell((short) cellstart);
		    cell3.setCellValue(str[i]);
		    cell3.setCellStyle(cellStyleTit);

		    sheet.addMergedRegion(new CellRangeAddress(
		            0, //first row (0-based)
		            0, //last row  (0-based)
		            cellstart, //first column (0-based)
		            cellstart+num[i]-1  //last column  (0-based)
		    ));
		    cellstart = cellstart+num[i];
		}*/
		CreationHelper createHelper = wb.getCreationHelper();
		Pattern p = Pattern.compile("^\\d*(\\.?\\d+)?$");
		for (int i = 0; i < list.size(); i++) {
			Row row2i = sheet.createRow((short) 1+i);
			HashMap<String,Object> map = list.get(i);
			for (int j = 0; j < str_en.length; j++) {
				if(map.get(str_en[j]) != null){
					Cell cell2ij = row2i.createCell(j);
					//System.out.println((2+i)+"---"+j+"---"+map.get(str_en[j]).toString());
					if(p.matcher(map.get(str_en[j]).toString()).matches()) {
						cell2ij.setCellValue(Double.parseDouble(map.get(str_en[j]).toString()));
					} else {
						cell2ij.setCellValue(createHelper.createRichTextString(map.get(str_en[j]).toString()));
					}
				}

			}
		}


		return wb;
	}
	public static void main(String[] args){
		try {
			new QualityServiceImpl().qualityExcelTable(null, null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertTest() throws Exception {
		//true  程序运行时间： 8281ms
		//false 程序运行时间： 8484ms
		//true 100万数据  1万一次  程序运行时间： 87359ms
		//true 100万数据  10万一次  程序运行时间： 80156ms
		//false 100万数据  10万一次  程序运行时间： 85422ms
		SqlSession session = sessionFactory.getObject().openSession(ExecutorType.BATCH, true);
		Test test = null;
		int size = 10000;
		try {
			/*
	    	test = new Test();
	    	test.setId(String.valueOf(1));
	    	test.setName("小明"+3);
	    	session.getMapper(QualityMapper.class).insertTest(test);
	    	session.commit();
	    	test = new Test();
	    	test.setId(String.valueOf(8));
	    	test.setName("小林"+1);
	    	session.getMapper(QualityMapper.class).insertTest(test);
	    	session.commit();
			 */
			//throw new RuntimeException("Error");

			long startTime=System.currentTimeMillis();   //获取开始时间


			for(int i = 0;i<1000000;i++){
				test = new Test();
				test.setId(String.valueOf(i));
				test.setName("小明"+i);
				//qualityDao.insertTest(test);

				session.getMapper(QualityMapper.class).insertTest(test);
				if (i % 100000 == 0 || i == 1000000 - 1) {
					long endTime=System.currentTimeMillis(); //获取结束时间
					System.out.println("插入 "+i+" 条数据运行时间： "+(endTime-startTime)+"ms");
					//手动每10000个一提交，提交后无法回滚
					session.commit();
					//清理缓存，防止溢出
					session.clearCache();
				}
			}
		} catch (Exception e) {
			//没有提交的数据可以回滚
			session.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public List<HashMap<String, String>> findCompany() throws Exception {
		// TODO Auto-generated method stub
		return qualityDao.findCompany();
	}
	@Override
	public Page getZidingyi(int yewuType,String timeType,String city, String company, String starttimeStr,
			String endtimeStr,int pageSize, int pageNo) throws ParseException {
		// TODO Auto-generated method stub

		List<HashMap<String,Object>> list = null;
		int total = 0;
		Date starttime,endtime;
		SimpleDateFormat sdf;
		if(timeType.equals("month")) {
			sdf=new SimpleDateFormat("yyyy-MM");

		} else {
			sdf=new SimpleDateFormat("yyyy-MM-dd");
		}
		starttime = sdf.parse(starttimeStr);
		endtime = sdf.parse(endtimeStr);
		if(yewuType == 1) {
			list = qualityDao.getZidingyi1(timeType,city, company, starttime, endtime,pageSize,pageNo);
			total = qualityDao.getZidingyiCount1(timeType,city, company, starttime, endtime);
		} else if(yewuType == 2){
			list = qualityDao.getZidingyi2(timeType,city, company, starttime, endtime,pageSize,pageNo);
			total = qualityDao.getZidingyiCount2(timeType,city, company, starttime, endtime);
		} else if(yewuType == 3){
			list = qualityDao.getZidingyi3(timeType,city, company, starttime, endtime,pageSize,pageNo);
			total = qualityDao.getZidingyiCount3(timeType,city, company, starttime, endtime);
		} else if(yewuType == 4){
			list = qualityDao.getZidingyi4(timeType,city, company, starttime, endtime,pageSize,pageNo);
			total = qualityDao.getZidingyiCount4(timeType,city, company, starttime, endtime);
		}
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setResultList(list);
		page.setRowCount(total);
		return page;
	}
	@Override
	public Page getXiangxi4(String webname, List<String> city, String company,
			String starttimeStr, String endtimeStr, int pageSize, int pageNo) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		Date starttime = sdf.parse(starttimeStr);
		Date endtime = sdf.parse(endtimeStr);
		List<HashMap<String,Object>> list = null;
		int total = 0;
		list = qualityDao.getXiangxi4(webname,city, company, starttime, endtime,pageSize,pageNo);
		total = qualityDao.getXiangxiCount4(webname,city, company, starttime, endtime);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setResultList(list);
		page.setRowCount(total);
		return page;
	}
	@Override
	public Page getXiangxi3(List<String> city, String company, String starttimeStr,
			String endtimeStr, int pageSize, int pageNo) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		Date starttime = sdf.parse(starttimeStr);
		Date endtime = sdf.parse(endtimeStr);
		List<HashMap<String,Object>> list = null;
		int total = 0;
		list = qualityDao.getXiangxi3(city, company, starttime, endtime,pageSize,pageNo);
		total = qualityDao.getXiangxiCount3(city, company, starttime, endtime);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setResultList(list);
		page.setRowCount(total);
		return page;
	}
	@Override
	public Page getXiangxi12(int yewuType, String yewu, List<String> city,
			String company, String starttime, String endtime, int pageSize,
			int pageNo) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		Date start = sdf.parse(starttime);
		Date endt = sdf.parse(endtime);
		List<HashMap<String,Object>> list = null;
		int total = 0;
		if(yewuType == 1) {
			list = qualityDao.getXiangxi1(yewu,city, company, start, endt,pageSize,pageNo);
			total = qualityDao.getXiangxiCount1(yewu,city, company, start, endt);
		} else if(yewuType == 2){
			list = qualityDao.getXiangxi2(yewu,city, company, start, endt,pageSize,pageNo);
			total = qualityDao.getXiangxiCount2(yewu,city, company, start, endt);
		}

		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setResultList(list);
		page.setRowCount(total);
		return page;
	}
	@Override
	public byte[] daochuXiangxi12(int yewuType, String yewu, List<String> city,
			String company, String starttime, String endtime, int pageSize,
			int pageNo) throws Exception {
		// TODO Auto-generated method stub

		Workbook wb = null;
		String[] str_cn = null;
		String[] str_en = null;


		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		Date start = sdf.parse(starttime);
		Date endt = sdf.parse(endtime);
		List<HashMap<String,Object>> list = null;
		if(yewuType == 1) {
			list = qualityDao.getXiangxi1(yewu,city, company, start, endt,pageSize,pageNo);
			if(company.equals("飞思达")){
				if(yewu.equals("网页浏览")) {
					str_cn = new String[]{"时间","厂商","地市",
							"网页打开成功率",
					"页面打开时长"};
					str_en = new String[]{"time","company","city",
							"SuccessRate",
					"OpenPageTime"};
				} else if(yewu.equals("HTTP/FTP下载")) {
					str_cn = new String[]{"时间","厂商","地市",
							"下载成功率",
					"下载速率"};

					str_en = new String[]{"time","company","city",
							"DealSuccRate",
					"DealSpeed"};
				} else if(yewu.equals("网页视频")) {
					str_cn = new String[]{"时间","厂商","地市",
							"视频连接成功率",
							"视频开始播放等待时长",
					"视频播放停顿次数"};
					str_en = new String[]{"time","company","city",
							"SuccessRate",
							"FirstByteOffsetTime",
					"CacheNum"};
				} else if(yewu.equals("即时通信")) {
					str_cn = new String[]{"时间","厂商","地市",
							"连接时间",
							"鉴权时长",
							"总时间",
					"成功率"};

					str_en = new String[]{"time","company","city",
							"SetupConnectOffsetTime",
							"ConventionTime",
							"LoginOffsetTime",
					"LinkRate"};
				} else if(yewu.equals("邮件")) {
					str_cn = new String[]{"时间","厂商","地市",
							"邮件发送速率",
					"邮件接收速率"};

					str_en = new String[]{"time","company","city",
							"UploadSendSpeed",
					"DownloadSendSpeed"};
				} else if(yewu.equals("游戏")) {
					str_cn = new String[]{"时间","厂商","地市",
							"时延",
							"抖动",
					"丢包率"};

					str_en = new String[]{"time","company","city",
							"Delay",
							"Shake",
					"LoseByteRate"};
				}
			} else if(company.equals("普天")){
				if(yewu.equals("网页浏览")) {
					str_cn = new String[]{"时间","厂商","地市",
							"成功次数","测试次数",
							"网页打开成功率",
					"页面打开时长"};
					str_en = new String[]{"time","company","city",
							"SuccessCount",
							"TestCount",
							"SuccessRate",
					"OpenPageTime"};
				} else if(yewu.equals("网页视频")) {
					str_cn = new String[]{"时间","厂商","地市",
							"连接成功次数","测试次数",
							"视频连接成功率",
							"视频开始播放等待时长",
					"视频播放停顿次数"};
					str_en = new String[]{"time","company","city",
							"ConnectSuccessCount",
							"TestCount",
							"SuccessRate",
							"FirstByteOffsetTime",
					"CacheNum"};
				} 
			}

		} else if(yewuType == 2){
			list = qualityDao.getXiangxi2(yewu,city, company, start, endt,pageSize,pageNo);
			if(company.equals("飞思达")){
				if(yewu.equals("网页")) {
					str_cn = new String[]{"时间","厂商","地市",
							"页面全部下载平均时延（S）","网页显示完整率","平均90%元素下载速率（KB/S)","连接成功率","成功率","DNS解析成功率","90%元素下载平均时延"};
					str_en = new String[]{"time","company","city",
							"Url100POffSetTime","WebDispCompRate","DealSpeed","ConnSuccRate","SuccessRate","DnsResSuccRATE","Url90POffSetTime"};

				} else if(yewu.equals("视频")) {
					str_cn = new String[]{"时间","厂商","地市",
							"平均首帧时延（秒）","平均缓存总时长（秒）","平均播放速率（KB/S)","平均播放时延（秒）","平均DNS查询时延（秒）","连接成功率","成功率","DNS解析成功率"};

					str_en = new String[]{"time","company","city",
							"FirstByteOffsetTime",
							"CacheOffsetTime",
							"DealSpeed",
							"DealOffsetTime",
							"DnsQueryOffsetTime",
							"ConnSuccRate",
							"SuccessRate",
					"DnsResSuccRATE"};
				} else if(yewu.equals("IM-飞信")) {
					str_cn = new String[]{"时间","厂商","地市",
							"总时间","连接时间","鉴权时长","成功率"};

					str_en = new String[]{"time","company","city",
							"LoginOffsetTime",
							"SetupConnectOffsetTime",
							"ConventionTime",
					"LinkRate"};
				} else if(yewu.equals("游戏")) {
					str_cn = new String[]{"时间","厂商","地市",
							"时延",
							"抖动",
					"丢包率"};

					str_en = new String[]{"time","company","city",
							"Delay",
							"Shake",
					"LoseByteRate"};
				} else if(yewu.equals("邮箱")) {
					str_cn = new String[]{"时间","厂商","地市",
							"认证成功率",
							"平均下载速率（KB/S)",
							"平均发送邮件时延（S）",
							"连接成功率",
					"成功率"};

					str_en = new String[]{"time","company","city",
							"AuthSuccRate",
							"DownloadSendSpeed",
							"AvgSendMailDelay",
							"ConnSuccRate",
					"SuccRate"};
				} 
			} else if(company.equals("普天")){
				if(yewu.equals("网页")) {
					str_cn = new String[]{"时间","厂商","地市",
							"页面全部下载平均时延（S）","网页显示完整次数","网页显示完整率","平均90%元素下载速率（KB/S)","连接成功次数","连接成功率","连接成功次数","成功率","DNS解析成功率","DNS解析成功次数","测试次数","90%元素下载平均时延"};
					str_en = new String[]{"time","company","city",
							"Url100POffSetTime","ResourcesSuccessCount","WebDispCompRate","DealSpeed","SetupConnectSuccessCount","ConnSuccRate","SuccessCount","SuccessRate","DnsResSuccRATE","DnsSuccessCount","TestCount","Url90POffSetTime"};

				} else if(yewu.equals("视频")) {
					str_cn = new String[]{"时间","厂商","地市",
							"平均首帧时延（秒）","平均缓存总时长（秒）","平均播放速率（KB/S)","平均播放时延（秒）","平均DNS查询时延（秒）","连接成功率","成功率","DNS解析成功率",
							"视频连接成功次数","测试次数","测试成功次数","DNS解析成功次数"};

					str_en = new String[]{"time","company","city",
							"FirstByteOffsetTime",
							"CacheOffsetTime",
							"DealSpeed",
							"DealOffsetTime",
							"DnsQueryOffsetTime",
							"ConnSuccRate",
							"SuccessRate",
							"DnsResSuccRATE",
							"ConnectSuccessCount", /*视频连接成功次数*/

							"TestCount", /*测试次数*/
							"SuccessCount", /*测试成功次数*/
							"DnsSuccessCount" /*DNS解析成功次数*/
					};
				} 
			}

		}
		String sheetname = "";
		if(yewuType == 1){
			sheetname = "业务KQI地市对比";
		} else if(yewuType == 2){
			sheetname = "省内热点网站情况";
		}
		sheetname +=company+"_"+yewu;
		wb = setWorkbook(sheetname,list,str_cn,str_en);
		ByteArrayOutputStream fos=new ByteArrayOutputStream();
		wb.write(fos);
		byte[] content =fos.toByteArray();
		return content;
	}
	@Override
	public byte[] daochuXiangxi3(List<String> city, String company,
			String starttimeStr, String endtimeStr, int pageSize, int pageNo)
					throws Exception {
		// TODO Auto-generated method stub

		Workbook wb = null;
		String[] str_cn = null;
		String[] str_en = null;


		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		Date starttime = sdf.parse(starttimeStr);
		Date endtime = sdf.parse(endtimeStr);
		List<HashMap<String,Object>> list = null;
		list = qualityDao.getXiangxi3(city, company, starttime, endtime,pageSize,pageNo);
		if(company.equals("飞思达")){
			str_cn = new String[]{"时间","厂商","地市",
					"吞吐率","总时间","成功率"
					,"网站名称","网站IP"};
			str_en = new String[]{"time","company","city",
					"DealSpeed","DealTotalOffSetTime","SuccessRate"
					,"webname","webip"};
		} else if(company.equals("普天")){
			str_cn = new String[]{"时间","厂商","地市",
					"吞吐率","总时间","成功率"
					,"成功次数","测试次数"};
			str_en = new String[]{"time","company","city",
					"DealSpeed","DealTotalOffSetTime","SuccessRate"
					,"SuccessCount","TestCount"};
		}
		wb = setWorkbook("地市网站时延_"+company,list,str_cn,str_en);
		ByteArrayOutputStream fos=new ByteArrayOutputStream();
		wb.write(fos);
		byte[] content =fos.toByteArray();
		return content;
	}
	@Override
	public byte[] daochuXiangxi4(String webname, List<String> city,
			String company, String starttimeStr, String endtimeStr, int pageSize,
			int pageNo) throws Exception {
		// TODO Auto-generated method stub
		Workbook wb = null;
		String[] str_cn = null;
		String[] str_en = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		Date starttime = sdf.parse(starttimeStr);
		Date endtime = sdf.parse(endtimeStr);
		List<HashMap<String,Object>> list = null;
		list = qualityDao.getXiangxi4(webname,city, company, starttime, endtime,pageSize,pageNo);
		if(company.equals("飞思达")){
			str_cn = new String[]{"时间","厂商","地市",
					"平均停顿次数","下载速率（KB/S）","平均视频下载速率","网站名称","视频停顿时长占比","主机IP","网站URL"};
			str_en = new String[]{"time","company","city",
					"CacheNum","DealSpeed","CacheOffsetTime"
					,"webname","CacheRate","HostIP","WebUrl"};

		} else if(company.equals("普天")){
			str_cn = new String[]{"时间","厂商","地市",
					"平均停顿次数","下载速率（KB/S）","平均视频下载速率"};
			str_en = new String[]{"time","company","city",
					"CacheNum","DealSpeed","CacheOffsetTime"};

		}
		wb = setWorkbook("集团热点网站信息_"+company,list,str_cn,str_en);
		ByteArrayOutputStream fos=new ByteArrayOutputStream();
		wb.write(fos);
		byte[] content =fos.toByteArray();
		return content;
	}

	/**
	 * 根据时间和时间类型查询重点指标概述
	 * @param timeType 时间类型
	 * @param getTime 时间
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findQualityKPIInfo(String timeType, String getTime) throws Exception{
		Map<String, Date> dateMap = DateUtil.getDateByType(timeType, getTime);
		Map<String, List<ThresholdConfigVo>> thresholdMap = findThresholdConfig("QU_BUSI_KQI", "QU_BUSI_KQI");
		List<Map<String, String>> list = qualityDao.findQualityKPIInfo(timeType, dateMap.get("startTime"), dateMap.get("endTime"));
		for (Map<String, String> map : list) {
			map.put("COLOR", getColor(String.valueOf(map.get("VAL")), thresholdMap.get(String.valueOf(map.get("OBJECTID")))));
		}
		return list;
	}
	
	public Map<String, List<ThresholdConfigVo>> findThresholdConfig(String moduleName, String bizType){
		Map<String, List<ThresholdConfigVo>> kpiMap = new HashMap<String, List<ThresholdConfigVo>>();
		List<ThresholdConfigVo> cfgList = null;
		List<ThresholdConfigVo> list;
		try {
			list = qualityDao.findThresholdConfig(moduleName, bizType);
			for (ThresholdConfigVo cfgVo : list) {
				if(!kpiMap.containsKey(cfgVo.getKpiid())){
					cfgList = new ArrayList<ThresholdConfigVo>();
					cfgList.add(cfgVo);
					kpiMap.put(cfgVo.getKpiid(), cfgList);
				}else{
					kpiMap.get(cfgVo.getKpiid()).add(cfgVo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kpiMap;
	}
	
	public String getColor(String score, List<ThresholdConfigVo> list){
		String color = "DDDDDD";//灰色
		for(ThresholdConfigVo cfgVo : list){
			if(Double.parseDouble(score) >= Double.parseDouble(cfgVo.getMinvalue()) &&
					Double.parseDouble(score) <= Double.parseDouble(cfgVo.getMaxvalue())){
				return cfgVo.getResultvalue();
			}
		}
		return color;
	}
	
}

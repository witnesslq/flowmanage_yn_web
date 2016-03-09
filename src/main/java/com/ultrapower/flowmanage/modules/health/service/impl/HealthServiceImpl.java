package com.ultrapower.flowmanage.modules.health.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.rtf.RtfWriter2;
import com.ultrapower.flowmanage.common.utils.Common;
import com.ultrapower.flowmanage.common.utils.DateUtil;
import com.ultrapower.flowmanage.common.utils.FlowTree;
import com.ultrapower.flowmanage.common.utils.MapComparator;
import com.ultrapower.flowmanage.common.utils.Utils;
import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.common.vo.TableContent;
import com.ultrapower.flowmanage.common.vo.Tree;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;
import com.ultrapower.flowmanage.modules.health.dao.HealthDao;
import com.ultrapower.flowmanage.modules.health.service.HealthService;
import com.ultrapower.flowmanage.modules.health.utils.WordDocUtil;
import com.ultrapower.flowmanage.modules.health.vo.AssDimRelevance;
import com.ultrapower.flowmanage.modules.health.vo.CityContrastVo;
import com.ultrapower.flowmanage.modules.health.vo.CountryContrastVo;
import com.ultrapower.flowmanage.modules.health.vo.Emp;
import com.ultrapower.flowmanage.modules.health.vo.ThreeNetHealth;
import com.ultrapower.flowmanage.modules.userPermissions.service.UserPermissionsService;

@Service("healthservice")
public class HealthServiceImpl implements HealthService{
	private static Logger logger = Logger.getLogger(HealthServiceImpl.class);
	
	@Resource(name="healthdao")
	private HealthDao healthdao;
	@Resource(name="userPermissionsService")
	private UserPermissionsService userPermissionsService;
	
	public List<Emp> findEmp() throws Exception{
		return healthdao.findEmp();
	}

	public List<Emp> findEmpByEmpno(String empno) throws Exception{
		return healthdao.findEmpByEmpno(empno);
	}

	public List<Map<String, String>> findThreeNetHealth(String healthtype, String getTime, String timeType) throws Exception{
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Date starttime;
		Date endtime;
		List<ThreeNetHealth> lists;
		if("month".equals(timeType)){  //月粒度
			//获取当前月第一天时间
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			lists =  healthdao.findThreeNetHealth(healthtype, starttime,  endtime);
		}else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			lists =  healthdao.findThreeNetHealthOfDay(healthtype, starttime,  endtime);
		}
		Map<String, String> map = new HashMap<String, String>();
		
		for (int i = 0; i < lists.size(); i++) {
			ThreeNetHealth net = lists.get(i);
			String score = Utils.valueOf(net.getScore());
			String unit = net.getUnit() == null ? "" : net.getUnit(); 
			String value = net.getVal() == null ? "缺失" : Utils.valueOf(net.getVal())+""+unit;
			map.put("level" + net.getLev() + "name", net.getObjectname());
			map.put("level" + net.getLev() + "score", score);
			map.put("level" + net.getLev() + "value", value);
			map.put("level" + net.getLev() + "objectid", net.getObjectid());
			map.put("level" + net.getLev() + "detail", net.getObjectid());
			if("-1".equals(net.getParenntid())){
				list.add(map);
				map = new HashMap<String, String>();
			}
		}
		return list;
	}

	public List<CityContrastVo> findCityHealthScoreById(String healthId, String healthType, String getTime, String timeType) throws Exception{
		Date starttime;
		Date endtime;
		List<CityContrastVo> list;
		if("month".equals(timeType)){  //月粒度
			//获取当前月第一天时间
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			list = healthdao.findCityHealthScoreById(healthId,healthType,starttime,endtime);
		}else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			list = healthdao.findCityHealthScoreByIdOfDay(healthId,healthType,starttime,endtime);
		}
		
		List<CityContrastVo> healths = healthdao.findHealthBizScope();
		for (int i = 0; i < list.size(); i++) {
			CityContrastVo city = list.get(i);
			city.setScopevalue("1");
			for (int j = 0; j < healths.size(); j++) {
				CityContrastVo city1 = healths.get(j);
				if(j < healths.size() - 1){
					CityContrastVo city2 = healths.get(j+1);
					String score = Utils.valueOf(city.getScore());
					if(Double.parseDouble(score) > Double.parseDouble(city1.getScopevalue()) &&
							Double.parseDouble(score) <= Double.parseDouble(city2.getScopevalue())){
						city.setScopevalue(Integer.toString((j+1)));
						break;
					}
				}
			}
		}
		for(int i = 0; i < list.size(); i++){
			CityContrastVo city = list.get(i);
			if("0".equals(city.getScore())){
				city.setScore("—");
			}else{
				city.setScore(Utils.valueOf(city.getScore(),"###"));
			}
		}
		
		//权限过滤地市数据
		/* 
		List<CityContrastVo> cityList = null;
		Map<String, Object> userMap = userPermissionsService.getUserPermissions();
		if(userMap.get("isAdmin").equals(false)){
			Set<String>  region = (Set<String>)userMap.get("region");
			cityList = new ArrayList<CityContrastVo>();
			if(region.size() > 0){
				int i = 0;
				for (String city : region) {
					for(CityContrastVo cityVo: list){
						if(city.equals(cityVo.getCityid())){
							cityVo.setId(String.valueOf(i+=1));
							cityList.add(cityVo);
							break;
						}
					}
				}
			}
			list = cityList;
		}
		*/
		return list;
	}

	public List<Map<String, Object>> findHealthNameAndScoreById(String healthId, String healthtype, String getTime, String timeType) throws Exception{
		List<Map<String,Object>> lists = new ArrayList<Map<String,Object>>();
		Date starttime;
		Date endtime;
		List<CityContrastVo> list;
		if("month".equals(timeType)){  //月粒度
			//获取当前月第一天时间
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			list = healthdao.findHealthNameAndScoreById(healthId,healthtype,starttime,endtime);
		}else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			list = healthdao.findHealthNameAndScoreByIdOfDay(healthId,healthtype,starttime,endtime);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		for(String cityName:Common.cityName){
			map.put("cityname", Common.cityMap.get(cityName));
			for (CityContrastVo cityContrastVo : list) {
				if(cityName.equals(cityContrastVo.getCityname())){
					if(Common.healthNameMap.get(cityContrastVo.getHealthname()) != null){
						map.put(Common.healthNameMap.get(cityContrastVo.getHealthname()), Utils.valueOf(cityContrastVo.getScore()));
					}
				}
	//			System.out.println(Common.healthNameMap.get(cityContrastVo.getHealthname())+"  "+Common.cityMap.get(cityContrastVo.getCityname()));
			}
			lists.add(map);
			map = new HashMap<String, Object>();
		}
		return lists;
	}

	public List<CityContrastVo> findCityInfo() throws Exception{
		List<CityContrastVo> list = healthdao.findCityInfo();
		CityContrastVo city = new CityContrastVo();
		city.setId("0");
		city.setCityid("0");
		city.setCityname("全省");
		list.add(0, city);
		return list;
	}

	public List<CityContrastVo> findHealthNameByType(String healthType) throws Exception{
		return healthdao.findHealthNameByType(healthType);
	}

	public List<Map<String, Object>> findHealthNameScoreByHealthId(String healthId, String healthType, String getTime, String timeType) throws Exception{
		List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> healthmap = new HashMap<String, Object>();
		Map<String, Object> map = null;
		
		Date starttime;
		Date endtime;
		List<CityContrastVo> healthList;
		if("month".equals(timeType)){  //月粒度
			//获取当前月第一天时间
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			healthList = healthdao.findHealthNameScoreByHealthId(healthId, healthType, starttime, endtime);
		}else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			healthList = healthdao.findHealthNameScoreByHealthIdOfDay(healthId, healthType, starttime, endtime);
		}
		
		for (CityContrastVo health : healthList) {
			map = new HashMap<String, Object>();
			map.put("cityid", health.getCityid());
			map.put("cityname", health.getCityname());
			map.put("healthtwoid", health.getHealthtwoid());
			map.put("healthtwoname", health.getHealthtwoname());
			map.put("healthtwoscore", health.getHealthtwoscore());
			list.add(map);
		}	
		Common.removeDuplicateWithOrder(list);//去List集合重复元素
		
		for(String city:Common.cityName){
			healthmap.put("cityname", Common.cityMap.get(city));
			for (Map<String, Object> m : list) {
				//System.out.println(m.get("cityid")+" "+m.get("cityname")+" "+m.get("healthtwoid")+" "+m.get("healthtwoname")+" "+m.get("healthtwoscore"));
				if(city.equals(m.get("cityname"))){
					healthmap.put(Common.healthNameMap.get(m.get("healthtwoname")), Utils.valueOf(String.valueOf(m.get("healthtwoscore"))));
					Map<String, Object> threeMap = new HashMap<String, Object>();
					for (CityContrastVo health : healthList) {
						if(m.get("cityid").equals(health.getCityid()) && m.get("healthtwoid").equals(health.getHealthtwoid())){
							threeMap.put(Common.healthNameMap.get(health.getHealththreename()), Utils.valueOf(String.valueOf(health.getHealththreescore())));
							healthmap.put(Common.healthThreeFactor.get(m.get("healthtwoname")), threeMap);
						}
					}
				}
			}
			lists.add(healthmap);
			healthmap = new HashMap<String, Object>();
		}
		return lists;
	}
	
	/** 获取健康度基本表信息  created by zhengWei */
	public List<Tree> getHealthTable(String type, String area, String city, Date starttime, Date endtime, String timeType) throws Exception {
		if("month".equals(timeType)){
			return healthdao.getHealthTable(type, area, city, starttime, endtime);
		}else{
			List<Tree> list = healthdao.getHealthTableOfDay(type, area, city, starttime, endtime);
			return list;
		}
	}
	
	/** 获取健康度基本表信息  created by zhengWei */
	public List<Tree> getMenuTable(String type,String lev) throws Exception {
		return healthdao.getMenuTable(type,lev);
	}
	
	/** 创建健康树 created by zhengWei */
	public Tree buildMenuTree(String type,String lev) throws Exception {
		List<Tree> list = getMenuTable(type,lev);
		//临时map做树的列表存储, key是树的唯一id,value是树
		Map<String, Tree> treeMap = new HashMap<String, Tree>(list.size());
		for (Tree tempTree : list) {
			treeMap.put(tempTree.getId(), tempTree);
		}
		//做整棵树的封装
		Tree rootTree = FlowTree.buildTree(treeMap);
		return rootTree;	
	}
	
	/** 创建健康树 created by zhengWei */
	public Tree buildHealthTree(String type, String area, String city, String getTime, String timeType) throws Exception {
		Date starttime;
		Date endtime;
		if("month".equals(timeType)){  //月粒度
			//获取当前月第一天时间
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		}else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
		}
		List<Tree> list = getHealthTable(type, area, city, starttime, endtime,timeType);
		//临时map做树的列表存储, key是树的唯一id,value是树
		Map<String, Tree> treeMap = new HashMap<String, Tree>(list.size());
		for (Tree tempTree : list) {
			treeMap.put(tempTree.getId(), tempTree);
		}
		//做整棵树的封装
		Tree rootTree = FlowTree.buildTree(treeMap);
		return rootTree;	
	}
	
	/** 创建拓补xml created by zhengWei */
	public String createTopo(String type, String area, String city, String getTime, String timeType ) throws Exception {
		Date starttime;
		Date endtime;
		if("month".equals(timeType)){  //月粒度
			//获取当前月第一天时间
			starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			//获取下个月第一天时间
			endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
		}else{   //天粒度
			//获取当天开始时间
			starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			//获取当天结束时间
			endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
		}
		String xml = "", maxLevel = "0";
		//获取封装好的整棵树
		Tree rootTree = buildHealthTree(type, area, city, getTime, timeType);
		//获取树的最大层级
		List<Tree> list = getHealthTable(type, area, city, starttime, endtime, timeType);
		maxLevel = FlowTree.getMaxlevel(list);
		
		xml = FlowTree.createXml(rootTree, null, maxLevel);
//		System.out.println("-------------------------------"+xml);
		return xml;	
	}
	//全国对比
		
		public List<CountryContrastVo> getCountryConstrastList(String healthtype,String getTime, String timeType)throws Exception  {
			Date starttime;
			Date endtime;
			List<CountryContrastVo> list;
			if("month".equals(timeType)){  //月粒度
				//获取当前月第一天时间
				starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
				//获取下个月第一天时间
				endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
				list = healthdao.getCountryConstrastList(healthtype,starttime,endtime);
			}else{   //天粒度
				//获取当天开始时间
				starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
				//获取当天结束时间
				endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
				list = healthdao.getCountryConstrastListOfDay(healthtype,starttime,endtime);
				System.out.println();
			}			
			
			for(int i = 0; i < list.size(); i++){
				CountryContrastVo country = list.get(i);
				if("0".equals(country.getScore())){
					country.setScore("—");
				}
			}
			return list;
		}
		//评估点地市对比
		public List<AssDimRelevance> getCityContrast(String healthtype)throws Exception  {
			
			return healthdao.getCityContrast(healthtype);
		}
		//评估点地市对比子节点
		public	List<AssDimRelevance> getCityContrastChild(String fatherId)throws Exception {
			return healthdao.getCityContrastChild(fatherId);
		}
		//全省纵览
		public 	List<Map<String, String>> getprovinceView(String healthType, String getTime, String timeType)throws Exception {
			Date starttime;
			Date endtime;
			if("month".equals(timeType)){  //月粒度
				//获取当前月第一天时间
				starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
				//获取下个月第一天时间
				endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
				return healthdao.getprovinceView(healthType, starttime, endtime);
			}else{   //天粒度
				//获取当天开始时间
				starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
				//获取当天结束时间
				endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
				List<Map<String, String>> list =  healthdao.getprovinceViewOfDay(healthType, starttime, endtime);
				return list; 
			}
			
		}
		//竟争对手对比
		public 	List<Map<String,String>>getOperView(String getTime, String timeType)throws Exception {
			Date starttime;
			Date endtime;
			List<Map<String,String>> list;
			if("month".equals(timeType)){  //月粒度
				//获取当前月第一天时间
				starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
				//获取下个月第一天时间
				endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
				list = healthdao.getOperView(starttime, endtime);
			}else{   //天粒度
				//获取当天开始时间
				starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
				//获取当天结束时间
				endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
				list = healthdao.getOperViewOfDay(starttime, endtime);
				System.out.println();
			}
			return list;
		}
		//评估点地市对比charts
		public List<Map<String,String>>getCityConstrastCharts(String healthname,String healthtype, String getTime, String timeType)throws Exception {
			Date starttime;
			Date endtime;
			List<Map<String, String>> list;
			if("month".equals(timeType)){  //月粒度
				//获取当前月第一天时间
				starttime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
				//获取下个月第一天时间
				endtime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
				list = healthdao.getCityConstrastCharts(healthname,healthtype,starttime,endtime);
			}else{   //天粒度
				//获取当天开始时间
				starttime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
				//获取当天结束时间
				endtime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
				list = healthdao.getCityConstrastChartsOfDay(healthname,healthtype,starttime,endtime);
			}
			
			MapComparator mc = new MapComparator();
			Collections.sort(list, mc);
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).get("city").equals("湖北")){
					list.add(0, list.get(i));
					list.remove(i+1);
				}
			}
			return list;
		}

	//健康度表格数据Excel导出
	public byte[] healthExcelTable(String healthType, String getTime, String timeType)throws Exception {
		List<CityContrastVo> citys = findCityInfo();//获取各地市名称
		if(citys.size() < 1){
			return null;
		}
		HSSFWorkbook wb = new HSSFWorkbook();;
		HSSFSheet sheet = null;
		
		for (CityContrastVo city : citys) {//遍历地市名称
			List<Map<String, String>> list = findThreeNetHealth(healthType, getTime, timeType);//各地市健康度指标详情
			sheet = wb.createSheet(city.getCityname());
			if(list.size()<1){
				continue;
			}
			
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
			String[] str = new String[]{};
			if("HEALTH_WLANNET".equals(healthType)){
				str = new String[]{"","得分","评估维度","得分","评估点","得分","评估指标","指标值","得分"};
			}else{
				str = new String[]{"","得分","评估维度","得分","评估指标","指标值","得分"};
			}
			for (int i = 0; i < str.length; ++i) {
				String tit = (String)str[i];
				HSSFCell cell = rowTit.createCell(i);
				cell.setCellStyle(cellStyleTit);
				cell.setCellValue(new HSSFRichTextString(tit));
			}
			String level1objectid = "",level2objectid = "",level3objectid = "";
			for (int j = 0; j < list.size(); ++j) {
				HSSFRow row = sheet.createRow(j + 1);
				Map<String, String> map = list.get(j);
				
				HSSFCell cell0 = row.createCell(0);
				cell0.setCellStyle(cellStyle);
				cell0.setCellValue(new HSSFRichTextString(map.get("level1name")));
				//四个参数分别是：起始行，起始列，结束行，结束列
				if(!"".equals(level1objectid) && map.get("level1objectid").equals(level1objectid)){
					sheet.addMergedRegion(new Region(j, (short) 0, j+1,(short) 0));
				}
				
				HSSFCell cell1 = row.createCell(1);
				cell1.setCellStyle(cellStyle);
				cell1.setCellValue(new HSSFRichTextString(map.get("level1score")));
				if(!"".equals(level1objectid) && map.get("level1objectid").equals(level1objectid)){
					sheet.addMergedRegion(new Region(j, (short) 1, j+1,(short) 1));
				}
				level1objectid = map.get("level1objectid");
				
				HSSFCell cell2 = row.createCell(2);
				cell2.setCellStyle(cellStyle);
				cell2.setCellValue(new HSSFRichTextString(map.get("level2name")));
				if(!"".equals(level2objectid) && map.get("level2objectid").equals(level2objectid)){
					sheet.addMergedRegion(new Region(j, (short) 2, j+1,(short) 2));
				}
				
				HSSFCell cell3 = row.createCell(3);
				cell3.setCellStyle(cellStyle);
				cell3.setCellValue(new HSSFRichTextString(map.get("level2score")));
				if(!"".equals(level2objectid) && map.get("level2objectid").equals(level2objectid)){
					sheet.addMergedRegion(new Region(j, (short) 3, j+1,(short) 3));
				}
				level2objectid = map.get("level2objectid");
				
				HSSFCell cell4 = row.createCell(4);
				cell4.setCellStyle(cellStyle);
				cell4.setCellValue(new HSSFRichTextString(map.get("level3name")));
				if(!"".equals(level3objectid) && map.get("level3objectid").equals(level3objectid)){
					sheet.addMergedRegion(new Region(j, (short) 4, j+1,(short) 4));
				}
				if(!"HEALTH_WLANNET".equals(healthType)){
					HSSFCell cell5 = row.createCell(5);
					cell5.setCellStyle(cellStyle);
					cell5.setCellValue(new HSSFRichTextString(map.get("level3value")));
					HSSFCell cell6 = row.createCell(6);
					cell6.setCellStyle(cellStyle);
					cell6.setCellValue(new HSSFRichTextString(map.get("level3score")));
				}else{
					HSSFCell cell5 = row.createCell(5);
					cell5.setCellStyle(cellStyle);
					cell5.setCellValue(new HSSFRichTextString(map.get("level3score")));
					if(!"".equals(level3objectid) && map.get("level3objectid").equals(level3objectid)){
						sheet.addMergedRegion(new Region(j, (short) 5, j+1,(short) 5));
					}
					level3objectid = map.get("level3objectid");
					
					HSSFCell cell6 = row.createCell(6);
					cell6.setCellStyle(cellStyle);
					cell6.setCellValue(new HSSFRichTextString(map.get("level4name")));
					HSSFCell cell7 = row.createCell(7);
					cell7.setCellStyle(cellStyle);
					cell7.setCellValue(new HSSFRichTextString(map.get("level4value")));
					HSSFCell cell8 = row.createCell(8);
					cell8.setCellStyle(cellStyle);
					cell8.setCellValue(new HSSFRichTextString(map.get("level4score")));
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

	//健康度word报表导出，zouhaibo
	public byte[] healthWordTable(String getTime)throws Exception {
//    	System.out.println("service healthWordTable 执行WORD数据汇聚存储");
//    	Map para = new HashMap();
//    	para.put("impDate", new SimpleDateFormat("yyyy-MM-dd").parse(getTime));
//    	healthdao.proWordExportDay(para);

    	System.out.println("service healthWordTable 获取WORD数据");
    	List<Map<String, BigDecimal>> list = healthdao.getWordDataForDate(getTime);
    	System.out.println("获取数据列数：" + list.size());
    	if(list.size() > 0)
    	{
    		Map<String, BigDecimal> map = list.get(0);
    		logger.debug("service 开始生成WORD文档");
	    	System.out.println("service 开始生成WORD文档");
			Document document = new Document(PageSize.A4);
	    	System.out.println("service 生成 document");
			try {
				ByteArrayOutputStream fos=new ByteArrayOutputStream();
				RtfWriter2.getInstance(document,
						fos);
	
		    	System.out.println("service 生成 RtfWriter2 instance");
				document.open();
	
		    	System.out.println("service 打开document");
				document.setMargins(80, 80, 20, 20);
	
				addPart1(document, getTime, map);
				addPart2(document, getTime, map);
				addPart3(document, getTime, map);
				
				document.close();
				byte[] content =fos.toByteArray();
				System.out.println("service content: " + content);
				return content;
				
			} catch (Exception e) {
				logger.error("健康度word报表错误：" + e.getMessage());
				System.out.println("健康度word报表错误：" + e.getMessage());
				e.printStackTrace();
			}
    	}
		return null;
	}
	
	private void addPart1(Document document, String impDate, Map<String, BigDecimal> map)
	{
    	System.out.println("service addPart1");
		try {
			String date = new SimpleDateFormat("M月d日").format(new SimpleDateFormat("yyyy-MM-dd").parse(impDate));
	    	System.out.println("service addPart1 生成段落");
			// 设置段落
	    	Paragraph pa = WordDocUtil.setParagraphStyle("一、互联网流量本网率", WordDocUtil.f, 0,
					Paragraph.ALIGN_LEFT);
			document.add(pa);
//	    	document.add(new Paragraph("sss"));

	    	System.out.println("service addPart1 生成表格1");
			List<TableContent> content = new ArrayList<TableContent>();
			content.add(new TableContent("", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("下行流量（GB）", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("占比", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("本网率（不加铁通）", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("本网率（加铁通）", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("业务-IDC", WordDocUtil.f1, Paragraph.ALIGN_LEFT));
			content.add(new TableContent(getBigDecimalInMap(map, "idcDnFlow"), WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent(getBigDecimalInMap(map, "idcRate") + "%", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent(getBigDecimalInMap(map, "bwlNtt") + "%", WordDocUtil.f2, Paragraph.ALIGN_CENTER, 9, 0));
			content.add(new TableContent(getBigDecimalInMap(map, "bwlTt") + "%", WordDocUtil.f2, Paragraph.ALIGN_CENTER, 9, 0));
			content.add(new TableContent("业务-Cache", WordDocUtil.f1, Paragraph.ALIGN_LEFT));
			content.add(new TableContent(getBigDecimalInMap(map, "cacheDnFlow"), WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent(getBigDecimalInMap(map, "cacheRate") + "%", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("业务-移动", WordDocUtil.f1, Paragraph.ALIGN_LEFT));
			content.add(new TableContent(getBigDecimalInMap(map, "ydDnFlow"), WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent(getBigDecimalInMap(map, "ydRate") + "%", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("业务-电信", WordDocUtil.f1, Paragraph.ALIGN_LEFT));
			content.add(new TableContent(getBigDecimalInMap(map, "dxDnFlow"), WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent(getBigDecimalInMap(map, "dxRate") + "%", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("业务-联通", WordDocUtil.f1, Paragraph.ALIGN_LEFT));
			content.add(new TableContent(getBigDecimalInMap(map, "ltDnFlow"), WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent(getBigDecimalInMap(map, "ltRate") + "%", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("业务-铁通", WordDocUtil.f1, Paragraph.ALIGN_LEFT));
			content.add(new TableContent(getBigDecimalInMap(map, "ttDnFlow"), WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent(getBigDecimalInMap(map, "ttRate") + "%", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("业务-其他", WordDocUtil.f1, Paragraph.ALIGN_LEFT));
			content.add(new TableContent(getBigDecimalInMap(map, "qtDnFlow"), WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent(getBigDecimalInMap(map, "qtRate") + "%", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("总计", WordDocUtil.f1, Paragraph.ALIGN_LEFT));
			content.add(new TableContent(getBigDecimalInMap(map, "totalDnFlow"), WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("100.00%", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("IDC+直连+缓存占比", WordDocUtil.f1, Paragraph.ALIGN_LEFT));
			content.add(new TableContent(getBigDecimalInMap(map, "idcZlCacheDnFlow"), WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			content.add(new TableContent(getBigDecimalInMap(map, "idcZlCacheRate") + "%", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
			WordDocUtil.addTable(document, 95, 5, content);

	    	System.out.println("service addPart1 生成段落2");
			String t1 = date + "湖北省CMNET下行流量总计" + getBigDecimalInMap(map, "totalDnFlow") + "GB，" +
					"其中省内资源服务流量（包括IDC及缓存系统）" + add(getBigDecimalInMap(map, "idcDnFlow"), getBigDecimalInMap(map, "cacheDnFlow")) + "GB，" +
					"占比" + add(getBigDecimalInMap(map, "idcRate"), getBigDecimalInMap(map, "cacheRate")) + "%，" +
					"本网下行流量（包括移动到业务的下行流量、省内IDC及缓存服务流量）" + add(getBigDecimalInMap(map, "ydDnFlow"), add(getBigDecimalInMap(map, "idcDnFlow"), getBigDecimalInMap(map, "cacheDnFlow"))) + "GB，" +
					"占比" + add(getBigDecimalInMap(map, "ydRate"), add(getBigDecimalInMap(map, "idcRate"), getBigDecimalInMap(map, "cacheRate"))) + "%。";
			String t2 = "集团本网率算法发生改变，本网率改为了包含铁通方向的流量。" +
					date + "集团本网率算上铁通为" + getBigDecimalInMap(map, "bwlTt") + "%。";
			document.add(WordDocUtil.setParagraphStyle(t1, WordDocUtil.f3, 32, 30, 8,
					Paragraph.ALIGN_LEFT));
			document.add(WordDocUtil.setParagraphStyle(t2, WordDocUtil.f3, 32, 30, 8,
					Paragraph.ALIGN_LEFT));

	    	System.out.println("service addPart1 生成表格2");
	    	content = new ArrayList<TableContent>();
	    	content.add(new TableContent("", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
	    	content.add(new TableContent("下行流量（GB）", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
	    	content.add(new TableContent("占比", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
	    	content.add(new TableContent("业务-Cache", WordDocUtil.f1, Paragraph.ALIGN_LEFT));
	    	content.add(new TableContent(getBigDecimalInMap(map, "cacheDnFlow2"), WordDocUtil.f1, Paragraph.ALIGN_CENTER));
	    	content.add(new TableContent(getBigDecimalInMap(map, "cacheRate2") + "%", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
	    	content.add(new TableContent("业务-移动-直连", WordDocUtil.f1, Paragraph.ALIGN_LEFT));
	    	content.add(new TableContent(getBigDecimalInMap(map, "ydZlDnFlow"), WordDocUtil.f1, Paragraph.ALIGN_CENTER));
	    	content.add(new TableContent(getBigDecimalInMap(map, "ydZlRate") + "%", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
	    	content.add(new TableContent("业务-移动-IDC", WordDocUtil.f1, Paragraph.ALIGN_LEFT));
	    	content.add(new TableContent(getBigDecimalInMap(map, "ydIdcDnFlow"), WordDocUtil.f1, Paragraph.ALIGN_CENTER));
	    	content.add(new TableContent(getBigDecimalInMap(map, "ydIdcRate") + "%", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
	    	content.add(new TableContent("合计/总下行流量", WordDocUtil.f1, Paragraph.ALIGN_LEFT));
	    	content.add(new TableContent(getBigDecimalInMap(map, "idcZlCacheDnFlow"), WordDocUtil.f1, Paragraph.ALIGN_CENTER));
	    	content.add(new TableContent(getBigDecimalInMap(map, "idcZlCacheRate") + "%", WordDocUtil.f1, Paragraph.ALIGN_CENTER));
	    	System.out.println("service addPart1 创建表格2");
			WordDocUtil.addTable(document, 70, 3, content);
			

	    	System.out.println("service addPart1 生成段落3");
			document.add(WordDocUtil.setParagraphStyle("注：IDC包含外省IDC服务我省流量", WordDocUtil.f4, 65, 0, 0,
					Paragraph.ALIGN_LEFT));
	    	System.out.println("service addPart1 end");

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addPart2(Document document, String impDate, Map<String, BigDecimal> map)
	{
    	System.out.println("service addPart2");
		try {
	    	
			document.add(WordDocUtil.setParagraphStyle("", WordDocUtil.f, 40,
					Paragraph.ALIGN_LEFT));
			
			document.add(WordDocUtil.setParagraphStyle("二、集团关注Cache指标", WordDocUtil.f, 0,
					Paragraph.ALIGN_LEFT));

			List<TableContent> content = new ArrayList<TableContent>();
			content.add(new TableContent("", WordDocUtil.f4, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("P2P Cache有效缓存比", WordDocUtil.f4, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("Web Cache有效缓存比", WordDocUtil.f4, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("集团关注Cache指标", WordDocUtil.f4, Paragraph.ALIGN_LEFT));
			content.add(new TableContent(getBigDecimalInMap(map, "p2pYxhcb") + "%", WordDocUtil.f5, Paragraph.ALIGN_CENTER));
			content.add(new TableContent(getBigDecimalInMap(map, "webYxhcb") + "%", WordDocUtil.f5, Paragraph.ALIGN_CENTER));
			WordDocUtil.addTable(document, 0, 3, content);
			
			
			document.add(WordDocUtil.setParagraphStyle("", WordDocUtil.f, 10,
					Paragraph.ALIGN_LEFT));
			
			
			document.add(WordDocUtil.setParagraphStyle("其中：", WordDocUtil.f6, 50, 0, 0,
					Paragraph.ALIGN_LEFT));

			Paragraph p = new Paragraph();
			p.add(new Chunk("P2P Cache有效缓存比", WordDocUtil.f7));
			p.add(new Chunk("=P2P Cache出流量/ （P2P Cache出流量 +本省骨干出口", WordDocUtil.f6));
			p.add(new Chunk("电信联通方向", WordDocUtil.f8));
			p.add(new Chunk("和第三方出口网间P2P流量）", WordDocUtil.f6));
			
			document.add(WordDocUtil.setParagraphStyle(p, 50, 0, 0,
					Paragraph.ALIGN_LEFT));

			Paragraph p1 = new Paragraph();
			p1.add(new Chunk("Web Cache有效缓存比", WordDocUtil.f7));
			p1.add(new Chunk("=Web Cache出流量/ （Web Cache出流量 +本省骨干出口", WordDocUtil.f6));
			p1.add(new Chunk("电信联通方向", WordDocUtil.f8));
			p1.add(new Chunk("和第三方出口网间Web流量）", WordDocUtil.f6));
			
			document.add(WordDocUtil.setParagraphStyle(p1, 50, 0, 0,
					Paragraph.ALIGN_LEFT));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addPart3(Document document, String impDate, Map<String, BigDecimal> map)
	{
    	System.out.println("service addPart3");
		try {
			String date = new SimpleDateFormat("M月d日").format(new SimpleDateFormat("yyyy-MM-dd").parse(impDate));
			
			document.add(WordDocUtil.setParagraphStyle("", WordDocUtil.f, 40,
					Paragraph.ALIGN_LEFT));
			
			document.add(WordDocUtil.setParagraphStyle("三、网络运行指标情况", WordDocUtil.f, 0,
					Paragraph.ALIGN_LEFT));
			
			document.add(WordDocUtil.setParagraphStyle("", WordDocUtil.f, 20,
					Paragraph.ALIGN_LEFT));
			
			document.add(WordDocUtil.setParagraphStyle("6、互联网拨测指标", WordDocUtil.f7, 0,
					Paragraph.ALIGN_LEFT));

			List<TableContent> content = new ArrayList<TableContent>();
			
			/**
			 * 此部分被替换掉, 替换为"热点内容拨测"
			 */
			/*
			content.add(new TableContent("", WordDocUtil.f5, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("网页下载成功率", WordDocUtil.f5, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("下载平均时延(秒)", WordDocUtil.f5, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("平均下载速率(千字节/秒)", WordDocUtil.f5, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("网站首页打开时延(秒)", WordDocUtil.f5, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("视频首帧显示时长(秒)", WordDocUtil.f5, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("单段视频平均卡顿次数", WordDocUtil.f5, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("TOP550网页测试（移动）", WordDocUtil.f4, Paragraph.ALIGN_LEFT));
			content.add(new TableContent(getBigDecimalInMap(map, "ydWebDnSuccRate") + "%", WordDocUtil.f6, Paragraph.ALIGN_CENTER));
			content.add(new TableContent(getBigDecimalInMap(map, "ydAvgDnTimedelay"), WordDocUtil.f6, Paragraph.ALIGN_CENTER));
			content.add(new TableContent(getBigDecimalInMap(map, "ydAvgDnSpeed"), WordDocUtil.f6, Paragraph.ALIGN_CENTER));
			content.add(new TableContent(getBigDecimalInMap(map, "ydFisrtpageTimedelay"), WordDocUtil.f6, Paragraph.ALIGN_CENTER));
			content.add(new TableContent(getBigDecimalInMap(map, "ydVideoFfTime"), WordDocUtil.f6, Paragraph.ALIGN_CENTER));
			content.add(new TableContent(getBigDecimalInMap(map, "ydSvideoKfNum"), WordDocUtil.f5, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("TOP550网页测试（电信）", WordDocUtil.f4, Paragraph.ALIGN_LEFT));
			content.add(new TableContent(getBigDecimalInMap(map, "dxWebDnSuccRate") + "%", WordDocUtil.f6, Paragraph.ALIGN_CENTER));
			content.add(new TableContent(getBigDecimalInMap(map, "dxAvgDnTimedelay"), WordDocUtil.f6, Paragraph.ALIGN_CENTER));
			content.add(new TableContent(getBigDecimalInMap(map, "dxAvgDnSpeed"), WordDocUtil.f6, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("_", WordDocUtil.f6, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("_", WordDocUtil.f6, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("_", WordDocUtil.f5, Paragraph.ALIGN_CENTER));
			*/
			
			content.add(new TableContent("", WordDocUtil.f5, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("TOP1000网站达标率", WordDocUtil.f5, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("TOP15视频质量得分", WordDocUtil.f5, Paragraph.ALIGN_CENTER));
			content.add(new TableContent("热点内容拨测", WordDocUtil.f4, Paragraph.ALIGN_LEFT));
			content.add(new TableContent(getBigDecimalInMap(map, "standardWebRate") + "%", WordDocUtil.f6, Paragraph.ALIGN_CENTER));
			content.add(new TableContent(getBigDecimalInMap(map, "videoQuality"), WordDocUtil.f6, Paragraph.ALIGN_CENTER));
			
			
			WordDocUtil.addTable(document, 50, 3, content);
			
			
			document.add(WordDocUtil.setParagraphStyle("", WordDocUtil.f, 10,
					Paragraph.ALIGN_LEFT));

			/*
			String t1 = "目前互联网拨测系统使用普天拨测系统进行武汉节点的拨测，" +
					"测试网站为省内top550热点网站，测试时间为晚忙时。" +
					date + "TOP550网页平均全部元素下载时延移动为" + getBigDecimalInMap(map, "ydAvgDnTimedelay") + "秒，" +
					"电信为" + getBigDecimalInMap(map, "dxAvgDnTimedelay") + "秒。";
			*/
			
			String t1 = "目前互联网拨测系统使用普天拨测系统进行武汉节点的拨测，" +
				"测试网站为集团下发top1000热点网站和top15视频，测试时间为全天。" +
				date + "TTOP1000网站达标率为" + getBigDecimalInMap(map, "standardWebRate") + "%，" +
				"top15视频质量得分为" + getBigDecimalInMap(map, "videoQuality") + "。";
			
			document.add(WordDocUtil.setParagraphStyle(t1, WordDocUtil.f3, 32, 30, 8,
					Paragraph.ALIGN_LEFT));

			document.add(WordDocUtil.setParagraphStyle("", WordDocUtil.f, 5,
					Paragraph.ALIGN_LEFT));
			

			/**
			 * 此部分被替换掉, "无线用户"替换为"移动互联网"
			 */
			/*
			document.add(WordDocUtil.setParagraphStyle("2、无线用户点击本网率", WordDocUtil.f7, 0,
					Paragraph.ALIGN_LEFT));

			String t2 = "目前由数据网管采集DNS数据按照集团算法计算无线用户点击本网率指标， " +
					date + "无线用户点击本网率" + getBigDecimalInMap(map, "wlanClickBwl") + "%。";
			*/
			
			document.add(WordDocUtil.setParagraphStyle("2、移动互联网点击本网率", WordDocUtil.f7, 0,
					Paragraph.ALIGN_LEFT));
			
			String t2 = "目前由数据网管采集DNS数据按照集团算法计算移动互联网点击本网率指标， " +
			date + "移动互联网点击本网率" + getBigDecimalInMap(map, "wlanClickBwl") + "%。";
			
			document.add(WordDocUtil.setParagraphStyle(t2, WordDocUtil.f3, 32, 30, 8,
					Paragraph.ALIGN_LEFT));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getBigDecimalInMap(Map<String, BigDecimal> map, String colName)
	{
		return map.get(colName) == null ? "0" : map.get(colName).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	private String add(String a, String b)
	{
		return new BigDecimal(a).add(new BigDecimal(b)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}

	//根据时间粒度、健康度指标ID、健康度类型查询各地市健康度指标名称和分数
	public List<Map<String, Object>> findHealthNameScoreByTime(String timeType, String healthType, String healthId, int lev, String getTime) throws Exception {
		String hid = lev == 1 ? null : healthId;
		List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> valList = null;
		List<CityContrastVo> tempList = null; 
		Map<String, Object> healthmap = null, map = null;
		List<String> strList = null;
		Map<String, Date> dateMap = DateUtil.getDateByType(timeType, getTime);
		List<CityContrastVo> list = healthdao.findHealthNameScoreByTime(timeType, healthType, hid, dateMap.get("startTime"), dateMap.get("endTime"));
		if (list == null) return null;
		for(String city:Common.cityName){
			tempList = new ArrayList<CityContrastVo>();
			healthmap = new HashMap<String, Object>();
			healthmap.put("cityname", Common.cityMap.get(city));
			for (CityContrastVo vo : list) {
				if(city.equals(vo.getCityname())){
					tempList.add(vo);
				}
			}
			healthmap.put("values", tempList);
			lists.add(healthmap);
		}
		if(lev == 1){
			for (Map<String, Object> mp : lists) {
				List<CityContrastVo> list1 = (List<CityContrastVo>) mp.get("values");
				valList = new ArrayList<Map<String,Object>>();
				for (CityContrastVo v1 : list1) {
					if(healthId.equals(v1.getParentid())){
						strList = new ArrayList<String>();
						map = new HashMap<String, Object>();
						map.put("nodeName", v1.getHealthname()+":"+Utils.valueOf(v1.getScore(),"###")+"分");
						for (CityContrastVo v2 : list1) {
							if(v1.getHealthid().equals(v2.getParentid())){
								strList.add(v2.getHealthname()+":"+Utils.valueOf(v2.getScore(),"###")+"分");
							}
						}
						map.put("childNode", strList);
						valList.add(map);
					}
					mp.put("values", valList);
				}
			}
		}else{
			for (Map<String, Object> mp : lists) {
				List<CityContrastVo> list1 = (List<CityContrastVo>) mp.get("values");
				strList = new ArrayList<String>();
				for (CityContrastVo v : list1) {
					map = new HashMap<String, Object>();
					strList.add(v.getHealthname()+":"+Utils.valueOf(v.getScore(),"###")+"分");
				}
				mp.put("values", strList);
			}
		}
		
		return lists;
	}
	
	//根据时间粒度、健康度指标ID、健康度类型查询健康度分数和指标值
	public List<Map<String, Object>> findHealthScoreKpiByType(String healthType, String healthId, String getTime, String timeType, String num) throws Exception {
		List<Map<String, Object>> allList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = null;
		List<Map<String, String>> chartList = null, tableList = null;
		Map<String, String> chartMap = null, scoreMap = null, kpiMap = null, dateMap = new HashMap<String, String>();
		StringBuffer sql = null;
		if("HEALTH_INTERNET".equals(healthType) && "1".equals(num)){
			sql = new StringBuffer("SELECT A.OBJECTID,A.OBJECTNAME KPINAME,(case when A.PARENNTID = '-1' then ''  else '互联网健康度-' end) || A.OBJECTNAME OBJECTNAME"
					+ "  FROM T_DIC A  WHERE A.OBJECTTYPE = '"+healthType+"' AND A.LEV in (1, 2)");
		}else if("HEALTH_INTERNET".equals(healthType) && "2".equals(num)){
			sql = new StringBuffer("SELECT T.OBJECTID,T.OBJECTNAME KPINAME," 
					+ "(SELECT A.OBJECTNAME||'-'||T.OBJECTNAME FROM T_DIC A WHERE A.OBJECTTYPE = '"+healthType+"' AND A.OBJECTID = T.PARENNTID) OBJECTNAME "
					+ "FROM T_DIC T WHERE T.OBJECTTYPE = '" +healthType+"' AND T.LEV = 3 ");
		}else if("HEALTH_NETWORK".equals(healthType) && "1".equals(num)){
			sql = new StringBuffer("SELECT A.OBJECTID,A.OBJECTNAME KPINAME,(case when A.PARENNTID = '-1' then '互联网'  else '互联网网间健康度-' end) || A.OBJECTNAME OBJECTNAME"
					+ "  FROM T_DIC A  WHERE A.OBJECTTYPE = '"+healthType+"' AND A.LEV in (1, 2)");
		}else if("HEALTH_NETWORK".equals(healthType) && "2".equals(num)){
			sql = new StringBuffer("SELECT T.OBJECTID,T.OBJECTNAME KPINAME," 
					+ "(SELECT A.OBJECTNAME||'-'||T.OBJECTNAME FROM T_DIC A WHERE A.OBJECTTYPE = '"+healthType+"' AND A.OBJECTID = T.PARENNTID) OBJECTNAME "
					+ "FROM T_DIC T WHERE T.OBJECTTYPE = '" +healthType+"' AND T.LEV = 3 ");
		}else return null;
		if(healthId != null){
			sql.append("AND T.OBJECTID = "+healthId+"");
		}
		List<Map<String, String>> dicList = healthdao.findDICInfo(sql.toString());
//		System.out.println("dicList=="+dicList);
		Map<String, Date> dateMap2 = null;
		if("week".equals(timeType)){
			dateMap2 = DateUtil.getDateByType(timeType, getTime, -6);
		}else{
			dateMap2 = DateUtil.getDateByType(timeType, getTime, -4);
		}
		
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");  
        
        //根据开始时间和结束时间返回时间段内的时间集合 
        List<Date> listDate = DateUtil.getDatesBetweenTwoDate(dateMap2.get("startTime"), dateMap2.get("endTime"), timeType);  
        for(int i=0;i<listDate.size();i++){  
            dateMap.put(sdf.format(listDate.get(i)), "th"+(i+1));
        }
        
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd"); 
        String startTime =formatter.format(dateMap2.get("startTime"));
        String endTime =formatter.format(dateMap2.get("endTime"));
//        System.out.println("startTime "+startTime+" endTime "+endTime);
        
        Date dBegin = DateUtil.StringToDate(startTime, "yyyy-MM-dd");  
        Date dEnd = DateUtil.StringToDate(endTime, "yyyy-MM-dd");
        //每个健康度指标对应时间段内的记录数
        int total = Integer.parseInt(String.valueOf(((dEnd.getTime() - dBegin.getTime())/(3600*24*1000)))) + 1;
        
        List<Map<String, String>> kpiList = healthdao.findHealthScoreKpiByType(healthType, healthId, startTime, endTime, timeType, num);
        for(Map<String, String> dicMap : dicList){
        	chartList = new ArrayList<Map<String,String>>();//图形
        	tableList = new ArrayList<Map<String,String>>();//表格
        	map = new HashMap<String, Object>();
        	scoreMap = new HashMap<String, String>();//得分
        	kpiMap = new HashMap<String, String>();//指标
        	map.put("kpiId", dicMap.get("OBJECTID"));
        	map.put("caption", dicMap.get("OBJECTNAME"));
        	map.put("seriesNameScore", dicMap.get("KPINAME")+" 得分");
        	map.put("seriesNameKpi", dicMap.get("KPINAME")+" 指标值");
        	
        	scoreMap.put("kpiNameKpi", dicMap.get("KPINAME")+" 得分");
        	kpiMap.put("kpiNameKpi", dicMap.get("KPINAME")+" 指标值");
        	int i = 0;
        	for(Map<String, String> km : kpiList){
        		if(i==total) break;
        		if(String.valueOf(dicMap.get("OBJECTID")).equals(String.valueOf(km.get("OBJECTID")))){
        			chartMap = new HashMap<String, String>();
        			chartMap.put("label", km.get("LABEL"));
        			chartMap.put("score", String.valueOf(km.get("SCORE")));
        			chartMap.put("val", String.valueOf(km.get("VAL")));
        			chartList.add(chartMap);
        			
        			scoreMap.put(dateMap.get(km.get("LABEL")), String.valueOf(km.get("SCORE")));
        			kpiMap.put(dateMap.get(km.get("LABEL")), String.valueOf(km.get("VAL")));
        			i++;
        		}
        	}
        	tableList.add(scoreMap);
        	tableList.add(kpiMap);
        	map.put("chartValues", chartList);
        	map.put("tableValues", tableList);
        	allList.add(map);
        }
		return allList;
	}
	
	//指标详情 TOP7 charts
	public List<Map<String,String>>getKPIDetailTop7(String kpiId, String getTime, String timeType)throws Exception {
		Date startTime;
		Date endTime;
		List<Map<String, String>> list = null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String startTimeStr;
		String endTimeStr;
		if("month".equals(timeType)){  //月粒度
			startTime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 0), "yyyy-MM-dd hh:mm:ss");
			endTime = DateUtil.StringToDate(DateUtil.getMonthFirstDay(getTime, 1), "yyyy-MM-dd hh:mm:ss");
			startTimeStr =sdf.format(startTime);
			endTimeStr =sdf.format(endTime);
			list = healthdao.getKPIDetailTop7(kpiId,startTime, endTime, startTimeStr, endTimeStr);
		}else if("day".equals(timeType)){   //天粒度
			startTime = DateUtil.StringToDate(DateUtil.getStartTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			endTime = DateUtil.StringToDate(DateUtil.getEndTimeOfDay(getTime), "yyyy-MM-dd HH:mm:ss");
			startTimeStr =sdf.format(startTime);
			endTimeStr =sdf.format(endTime);
			list = healthdao.getKPIDetailTop7OfDay(kpiId,startTime, endTime, startTimeStr, endTimeStr);
		}
		return list;
	}
	
	@Override
	public Page getUserDefinedInfo(String healthType, String kpiId,String timeType, String starttimeStr, String endtimeStr, int pageSize, int pageNo) throws ParseException {
		List<Map<String, String>> recordList = new ArrayList<Map<String,String>>();
		//缓存开始日期的前一天到结束日期的结果集
		ConcurrentHashMap<String, ConcurrentHashMap<String, String>> cacheMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
		//currentMap ==> 当前天的记录 lastMap ==> 上一天的记录
		ConcurrentHashMap<String, String> lastMap = null, currentMap = null;
		
		List<Map<String, String>> subList = null;
		
		String[] keyArr = {"col1","col2","col3","col4","col5"};
//		String[] kpiIdArr = {"1254","1255","1256","1257","1258","1259","1260"};
		int total = 0;
		Date starttime,endtime;
		SimpleDateFormat sdf;
//		if(timeType.equals("month")) {
//			sdf=new SimpleDateFormat("yyyy-MM");
//		} else {
			sdf=new SimpleDateFormat("yyyy-MM-dd");
//		}
		starttime = sdf.parse(starttimeStr);
		endtime = sdf.parse(endtimeStr);
		
		List<Map<String, String>> list = healthdao.getUserDefinedInfo(healthType, kpiId, timeType, starttime, endtime, pageSize, pageNo);
		total = healthdao.getUserDefinedInfoCount(healthType, kpiId, timeType, starttime, endtime);
		System.out.println("list="+list);
		if(total > 0){
			ConcurrentHashMap<String, String> kpiMap = null;
			for(Map<String, String> mp : list) {
				if (!cacheMap.containsKey(mp.get("COLLECTTIME"))) {
					kpiMap = new ConcurrentHashMap<String, String>();
					for (String str: keyArr) {
						kpiMap.put(str, "-");
					}
					kpiMap.put(mp.get("COLNAME"), String.valueOf(mp.get("SCORE")));
					cacheMap.put(mp.get("COLLECTTIME"), kpiMap);
				} else {
					cacheMap.get(mp.get("COLLECTTIME")).put(mp.get("COLNAME"), String.valueOf(mp.get("SCORE")));
				}
			}

			String strCurrentTime = starttimeStr;
			//bijiaoshijian
			while (strCurrentTime.compareTo(endtimeStr) <= 0) {
				currentMap = cacheMap.get(strCurrentTime);
				if(currentMap == null){
					if(timeType.equals("month")){
						strCurrentTime = DateUtil.getMonthFirstDay(strCurrentTime, 1).substring(0, 10); 
					}else {
						strCurrentTime = DateUtil.getSomeDay(strCurrentTime, 1); 
					}
					continue;
				}
				currentMap.put("COLLECTTIME", strCurrentTime);
				if(timeType.equals("month")){
					lastMap = cacheMap.get(DateUtil.getMonthFirstDay(strCurrentTime, -1).substring(0, 10));
				}else {
					lastMap = cacheMap.get(DateUtil.getSomeDay(strCurrentTime, -1)); 
				}

				//求环比
				String kpiNum = null;
				String currentVal = null, lastVal = null;

				DecimalFormat df = new DecimalFormat("0.00%");

				if (currentMap != null) {
					for (Map.Entry<String, String> entry: currentMap.entrySet()) {
						kpiNum = entry.getKey();
						String num = kpiNum.substring(3);
						if(kpiNum == "COLLECTTIME" || kpiNum.contains("perc"))
							continue;
						currentVal = entry.getValue();
						if(StringUtils.isEmpty(currentVal) || !currentVal.matches("-?[0-9]+.*[0-9]*")){
							currentMap.put(kpiNum, "-");
							currentMap.put("perc" + num, "-");
							continue;
						}

						if (lastMap != null) {
							lastVal = lastMap.get(kpiNum);
						}

						if (StringUtils.isNotEmpty(currentVal) && StringUtils.isNotEmpty(lastVal) && lastVal != "-" && !lastVal.equals("0")) {
//							if(lastVal.equals("0")) {currentMap.put("perc" + num, "∞"); continue;}
							BigDecimal c = new BigDecimal(currentVal);
							BigDecimal l = new BigDecimal(lastVal);

							if (l.intValue() != 0) {
								BigDecimal per = c.subtract(l).divide(l,4,RoundingMode.HALF_UP);
								currentMap.put("perc" + num, df.format(per));
							}
						}else 
							currentMap.put("perc" + num, "-");
					}
				}

				recordList.add(currentMap);
				if(timeType.equals("month")){
					strCurrentTime = DateUtil.getMonthFirstDay(strCurrentTime, 1).substring(0, 10); 
				}else {
					strCurrentTime = DateUtil.getSomeDay(strCurrentTime, 1); 
				}
			}
			
			//分页
			int pagecount=total/pageSize;
			if(pagecount < pageNo){
				subList= recordList.subList((pageNo-1)*pageSize,total);
			}else{
				subList = recordList.subList((pageNo-1)*pageSize,pageSize*(pageNo));
			}
		}else{
			subList = null;
		}

		Page page = new Page();
		page.setPageNo(pageNo);//当前页
		page.setPageSize(pageSize);//每页的记录数
		page.setRowCount(total);//总行数
		page.setResultList(subList);//每页的记录
		return page;
	}
	
	@Override
	public Page getExitHealthDetail(String healthType, String kpiId,String timeType, String starttimeStr, String endtimeStr, int pageSize, int pageNo) throws ParseException {
		List<Map<String, String>> recordList = new ArrayList<Map<String,String>>();
		ConcurrentHashMap<String, ConcurrentHashMap<String, String>> cacheMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
		ConcurrentHashMap<String, String> currentMap = null;
		
		List<Map<String, String>> subList = null;
		
		String[] keyArr = {"col1","col2","col3","col4","col5"};
		int total = 0;
		Date starttime,endtime;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		starttime = sdf.parse(starttimeStr);
		endtime = sdf.parse(endtimeStr);
		
		List<Map<String, String>> list = healthdao.getExitHealthDetail(healthType, kpiId, timeType, starttime, endtime, pageSize, pageNo);
		total = healthdao.getUserDefinedInfoCount(healthType, kpiId, timeType, starttime, endtime);
		
		if(total > 0){
			ConcurrentHashMap<String, String> kpiMap = null;
			for(Map<String, String> mp : list) {
				if (!cacheMap.containsKey(mp.get("COLLECTTIME"))) {
					kpiMap = new ConcurrentHashMap<String, String>();
					for (String str: keyArr) {
						kpiMap.put(str, "-");
					}
					kpiMap.put(mp.get("COLNAME"), String.valueOf(mp.get("VAL")));
					cacheMap.put(mp.get("COLLECTTIME"), kpiMap);
				} else {
					cacheMap.get(mp.get("COLLECTTIME")).put(mp.get("COLNAME"), String.valueOf(mp.get("VAL")));
				}
			}
			while (starttimeStr.compareTo(endtimeStr) <= 0) {
				currentMap = cacheMap.get(starttimeStr);
				if(currentMap == null){
					if(timeType.equals("month")){
						starttimeStr = DateUtil.getMonthFirstDay(starttimeStr, 1).substring(0, 10); 
					}else {
						starttimeStr = DateUtil.getSomeDay(starttimeStr, 1); 
					}
					continue;
				}
				currentMap.put("COLLECTTIME", starttimeStr);
				recordList.add(currentMap);
				if(timeType.equals("month")){
					starttimeStr = DateUtil.getMonthFirstDay(starttimeStr, 1).substring(0, 10); 
				}else {
					starttimeStr = DateUtil.getSomeDay(starttimeStr, 1); 
				}
			}
			
			//分页
			int pagecount=total/pageSize;
			if(pagecount < pageNo){
				subList= recordList.subList((pageNo-1)*pageSize,total);
			}else{
				subList = recordList.subList((pageNo-1)*pageSize,pageSize*(pageNo));
			}
		}else{
			subList = null;
		}
		
		Page page = new Page();
		page.setPageNo(pageNo);//当前页
		page.setPageSize(pageSize);//每页的记录数
		page.setRowCount(total);//总行数
		page.setResultList(subList);//每页的记录
		return page;
	}
}

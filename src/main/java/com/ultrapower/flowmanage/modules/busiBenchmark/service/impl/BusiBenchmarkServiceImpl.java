package com.ultrapower.flowmanage.modules.busiBenchmark.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.common.utils.DateUtil;
import com.ultrapower.flowmanage.modules.busiBenchmark.dao.BusiBenchmarkDao;
import com.ultrapower.flowmanage.modules.busiBenchmark.service.BusiBenchmarkService;



@Service("busiBenchmarkService")
public class BusiBenchmarkServiceImpl implements BusiBenchmarkService {
	private static Logger logger = Logger.getLogger(BusiBenchmarkServiceImpl.class);
	@Resource(name="busiBenchmarkDao")
    private BusiBenchmarkDao busiBenchmarkDao;
	
	/**
	 * 根据业务类型查询字典表信息
	 * @param busiType
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findBusinessInfoByType(String busiType, String busiId) throws Exception{
		List<Map<String, String>> list = busiBenchmarkDao.findBusinessInfoByType(busiType, busiId);
		return list;
	}
	
	
	/**
	 * 根据业务ID和时间查询关注网站
	 * 重点关注网站:两个及两个以上指标越线的网站
	 * 需关注网站:一个指标越线的网站 
	 * @param busiId
	 * @param getTime
	 * @param timeType
	 * @param overLineWebType
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findEmphasisNeedConcernWebInfo(String busiId, String getTime, String timeType, int overLineWebType) throws Exception {
		//获取当前时间
		Date getDay = DateUtil.StringToDate(DateUtil.getDayOfDate(getTime, -1), "yyyy-MM-dd hh:mm:ss");
		//获取后一天时间
		Date nextDay = DateUtil.StringToDate(DateUtil.getDayOfDate(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		List<Map<String, String>> list = busiBenchmarkDao.findEmphasisNeedConcernWebInfo(busiId, getDay, nextDay, timeType, overLineWebType);
		return list;
	}
	
	/**
	 * 根据业务ID、网站名称、时间查询30天网站各指标波动情况
	 * @param busiId
	 * @param webName
	 * @param getDay
	 * @param beforeDay
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findWebKpifloatDay30(String busiId, String webName, String getTime) throws Exception{
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = null;
		List<Map<String, String>> dayList = null, weekList = null, monthList = null;
		Map<String, String> dayMap = null, weekMap = null, monthMap = null;
		
		//获取当前时间
		Date getDay = DateUtil.StringToDate(DateUtil.getDayOfDate(getTime, -1), "yyyy-MM-dd hh:mm:ss");
		//获取前30天时间
		Date beforeDay = DateUtil.StringToDate(DateUtil.getDayOfDate(getTime, -31), "yyyy-MM-dd hh:mm:ss");
		//查询业务基准线阀值信息
		List<Map<String, String>> bizList = busiBenchmarkDao.findBusiBenchmarkThreshold(busiId);
		//根据业务ID、网站名称、时间查询30天网站各指标波动情况
		List<Map<String, String>> kpiList = busiBenchmarkDao.findWebKpifloatDay30(busiId, webName, getDay, beforeDay);
		if(kpiList.size() > 0){
			for (Map<String, String> bizMap : bizList) {//遍历个指标阀值
				map = new HashMap<String, Object>();
				dayList = new ArrayList<Map<String,String>>();
				weekList = new ArrayList<Map<String,String>>();
				monthList = new ArrayList<Map<String,String>>();
				for (Map<String, String> kpiMap : kpiList) {//遍历网站的各指标的波动值
					if(bizMap.get("kpiName").equals(kpiMap.get("KPINAME"))){
						dayMap = new HashMap<String, String>();
						weekMap = new HashMap<String, String>();
						monthMap = new HashMap<String, String>();
						//每日波动
						dayMap.put("label", kpiMap.get("COLLECTTIME"));
						dayMap.put("forward", String.valueOf(kpiMap.get("DAYAVGFORWARD30")));
						dayMap.put("dayavg", String.valueOf(kpiMap.get("DAYAVG")));
						dayMap.put("back", String.valueOf(kpiMap.get("DAYAVGBACK30")));
						//7天波动
						weekMap.put("label", kpiMap.get("COLLECTTIME"));
						weekMap.put("forward", String.valueOf(kpiMap.get("WEEKAVGFORWARD30")));
						weekMap.put("dayavg", String.valueOf(kpiMap.get("DAYAVG")));
						weekMap.put("back", String.valueOf(kpiMap.get("WEEKAVGBACK30")));
						//30天波动
						monthMap.put("label", kpiMap.get("COLLECTTIME"));
						monthMap.put("forward", String.valueOf(kpiMap.get("MONTHAVGFORWARD30")));
						monthMap.put("dayavg", String.valueOf(kpiMap.get("DAYAVG")));
						monthMap.put("back", String.valueOf(kpiMap.get("MONTHAVGBACK30")));
						dayList.add(dayMap);
						weekList.add(weekMap);
						monthList.add(monthMap);
					}
				}
				map.put("kpiName", bizMap.get("kpiName"));
				map.put("maxValue", bizMap.get("maxValue"));
				map.put("minValue", bizMap.get("minValue"));
				map.put("unit", bizMap.get("unit"));
				map.put("daylist", dayList);
				map.put("weeklist", weekList);
				map.put("monthlist", monthList);
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 根据业务ID、时间、浮动时间粒度(单日、7天、30天浮动)查询需关注网站各项指标的正向、逆向越线TOP3网站
	 * @param busiId
	 * @param getDay
	 * @param nextDay
	 * @param timeType
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findNeedfocusWebKpiPasslineTOP3(String busiId, String getTime, String timeType) throws Exception{
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = null;
		StringBuffer forwardWebSb = null, backWebSb = null;
		//获取当前时间
		Date getDay = DateUtil.StringToDate(DateUtil.getDayOfDate(getTime, -1), "yyyy-MM-dd hh:mm:ss");
		//获取后一天时间
		Date nextDay = DateUtil.StringToDate(DateUtil.getDayOfDate(getTime, 0), "yyyy-MM-dd hh:mm:ss");
		List<Map<String, String>> busiList = busiBenchmarkDao.findBusinessInfoByType("BUSI_BENCHMARK_LINE_KPI", busiId);
		List<Map<String, String>> kpiList = busiBenchmarkDao.findNeedfocusWebKpiPasslineTOP3(busiId, getDay, nextDay, timeType);
		for (Map<String, String> busiMap : busiList) {
			map = new HashMap<String, Object>();
			map.put("kpiName", busiMap.get("OBJECTNAME"));
			map.put("forwardName", "正向越线TOP3网站");
			map.put("backName", "逆向越线TOP3网站");
			forwardWebSb = new StringBuffer();
			backWebSb = new StringBuffer();
			for (Map<String, String> kpiMap : kpiList) {
				if(busiMap.get("OBJECTNAME").equals(kpiMap.get("KPINAME"))){
					if(kpiMap.get("FLAG").equals("TRUE")){
						forwardWebSb.append(kpiMap.get("WEBNAME")+" ");
					}else{
						backWebSb.append(kpiMap.get("WEBNAME")+" ");
					}
				}
			}
			map.put("forwardWeb", forwardWebSb.toString());
			map.put("backWeb", backWebSb.toString());
			list.add(map);
		}
		return list;
	}

}
